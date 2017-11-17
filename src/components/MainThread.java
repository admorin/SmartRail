/**
 * Andrew Morin
 * Tyson Craner
 * Alex Schmidt
 *
 * Train System
 * Project 3
 * 11/15/2017
 */

package components;

import com.sun.org.apache.regexp.internal.CharacterArrayCharacterIterator;
import com.sun.xml.internal.ws.handler.HandlerException;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class MainThread extends Thread {
//    public int LENGTH = 9;
//    public int WIDTH = 9;
    public int LENGTH;
    public int WIDTH;

    public Pane pane;

    private BufferedReader reader;

    public LinkedList<Station> stationList = new LinkedList<>();
    public ArrayList<Station> pickedStations = new ArrayList<>();

    public Object lock = new Object();
    Thread[][] myMap;
    public Station start;
    public Station end;
    public Character[][] inputArr;

    public int testing = 0;


    public MainThread(Pane pane) {
        this.pane = pane;
        getDimensions();
    }

    public void getDimensions(){
        reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("components/map/TrackFile.txt")));
        try {
            LENGTH = Character.getNumericValue(reader.readLine().charAt(0));
            WIDTH = Character.getNumericValue(reader.readLine().charAt(0));
        } catch (IOException e){
            System.out.println("File not found.");
        }
    }


    public Thread[][] reader() {
        int counter = 1;
        int index = 0;
        try {
            String str;
            inputArr = new Character[LENGTH][WIDTH];
            myMap = new Thread[LENGTH][WIDTH];
            while ((str = reader.readLine()) != null && !str.equals("END")) {
                for (int i = 0; i < str.length(); i++) {
                    char temp = str.charAt(i);
                    if (Character.isUpperCase(temp)) {
                        testing++;
                        myMap[i][index] = new Station();
                        inputArr[i][index] = temp;

                    } else if (temp == '-' || Character.isDigit(temp)) {
                        myMap[i][index] = new Track();
                        inputArr[i][index] = temp;
                        testing++;
                    } else {

                    }
                }
                index++;
            }
        } catch (IOException e) {
            System.out.print("File not found.");
        }

        int flag = 1;

        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (inputArr[i][j] != null) {
                    if (Character.isUpperCase(inputArr[i][j])) {
                        if(i == 0) {
                            myMap[0][j] = new Station("Station " + inputArr[0][j], (Track) myMap[1][j], pane);
                            if(myMap[LENGTH - 1][j] != null) {
                                myMap[LENGTH - 1][j] = new Station("Station " + inputArr[LENGTH - 1][j], (Track) myMap[LENGTH - 2][j], pane);
                            }
                        }
                    }
                    else if (inputArr[i][j] == '-') {
                        flag++;
                        if (i == 1) {
                            Track t = (Track) myMap[1][j];
                            t.setTrackLStation((Track) myMap[i+1][j], (Station) myMap[i-1][j], "" + i);
                            myMap[i][j] = t;

                        } else if (i == LENGTH - 2) {
                            Track t = (Track) myMap[i][j];
                            t.setTrackRStation((Station) myMap[i+1][j], (Track) myMap[i-1][j], "" + i);
                            myMap[i][j] = t;

                        } else {
                            Track t = (Track) myMap[i][j];
                            t.setTrack((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], "" + i);
                            myMap[i][j] = t;
                        }

                    }

                    else if(Character.isDigit(inputArr[i][j]) && counter != WIDTH){
                        flag++;
                        if(j < WIDTH -1 && (inputArr[i-1][j+1] != null || inputArr[i+1][j+1] != null)){
                            if(inputArr[i][j] == inputArr[i-1][j+1]) {
                                Track t = (Track) myMap[i][j];
                                Track t2 = (Track) myMap[i - 1][j + 1];
                                t.setSwitchTrackD((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], (Track) myMap[i - 1][j + 1], 1, i + "");
                                t2.setSwitchTrackU((Track) myMap[i][j + 1], (Track) myMap[i - 2][j + 1], (Track) myMap[i][j], 0, i + "");
                                myMap[i][j] = t;
                                myMap[i - 1][j + 1] = t2;
                                counter++;

                            }


                        else if(inputArr[i][j] == inputArr[i+1][j+1]) {
                                Track t = (Track) myMap[i][j];
                                Track t2 = (Track) myMap[i + 1][j + 1];
                                t.setSwitchTrackD((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], (Track) myMap[i + 1][j + 1], 0, i + "T");
                                t2.setSwitchTrackU((Track) myMap[i + 2][j + 1], (Track) myMap[i][j + 1], (Track) myMap[i][j], 1, i + "T");
                                myMap[i][j] = t;
                                myMap[i+1][j+1] = t2;
                                counter++;
                            }
                        }
                    }
                }
            }
        }
        System.out.println(testing);
        return myMap;
    }




    public Thread[][] initialize(){

        myMap = reader();
        for(int i = 0; i < LENGTH; i++){
            for(int j = 0; j < WIDTH; j++){
                if(myMap[i][j] != null) myMap[i][j].start();
            }
        }
        return myMap;
    }


    public synchronized void setStartStation(LinkedList<Station> stations){

        if(stations.size() == 2){
            Station start = stations.getFirst();
            Station end = stations.getLast();

            start.finishLine(end);
            pickStation(start, end);

        }
    }

    public synchronized void pickStation(Station start, Station end){

        start.initStation = end.initStation = true;

        synchronized (start){
            start.selected1 = true;
            start.notify();

        }

    }

}
