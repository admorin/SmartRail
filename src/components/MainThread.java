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

    public LinkedList<Station> stationList = new LinkedList<>();
    public ArrayList<Station> pickedStations = new ArrayList<>();

    public Object lock = new Object();
    Thread[][] myMap;
    public Station start;
    public Station end;
    public Character[][] inputArr;


    public MainThread(Pane pane) {
        this.pane = pane;

    }


    public Thread[][] reader() {
        int counter = 1;
        int index = 0;
        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("components/map/TrackFile.txt")));
            String str;
            LENGTH = Character.getNumericValue(reader.readLine().charAt(0));
            WIDTH = Character.getNumericValue(reader.readLine().charAt(0));
            inputArr = new Character[LENGTH][WIDTH];
            myMap = new Thread[LENGTH][WIDTH];


            int row = 0;


            while ((str = reader.readLine()) != null && !str.equals("END")) {
                for (int i = 0; i < str.length(); i++) {
                    char temp = str.charAt(i);
                    if (Character.isUpperCase(temp)) {
                        myMap[i][index] = new Station();
                        inputArr[i][index] = temp;

                    } else if (temp == '-' || Character.isDigit(temp)) {

                        myMap[i][index] = new Track();
                        inputArr[i][index] = temp;

                    } else {

                    }
                }
                index++;
                row++;

            }
        } catch (IOException e) {
            System.out.print("File not found.");
        }


        for (int i = 0; i < LENGTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (inputArr[i][j] != null) {
                    if (Character.isUpperCase(inputArr[i][j])) {
                        myMap[0][j] = new Station("Station " + inputArr[0][j], (Track) myMap[1][j], pane);
                        myMap[LENGTH - 1][j] = new Station("Station " + inputArr[LENGTH - 1][j], (Track) myMap[LENGTH - 2][j], pane);

                    }
                    else if (inputArr[i][j] == '-') {


                        if (i == 1) {
                            Track t = (Track) myMap[1][j];
                            t.setTrackLStation((Track) myMap[2][j], (Station) myMap[0][j], "" + i);
                            myMap[1][j] = t;

                        } else if (i == LENGTH - 2) {
                            Track t = (Track) myMap[i][j];
                            t.setTrackRStation((Station) myMap[LENGTH - 1][j], (Track) myMap[LENGTH - 2][j], "" + i);
                            myMap[i][j] = t;

                        } else  {
                            System.out.print("i = " + i);
                            System.out.print("j = " + j);
                            System.out.println();
                            Track t = (Track) myMap[i][j];
                            t.setTrack((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], "" + i);
                            myMap[i][j] = t;
                        }




                    }

                    else if(Character.isDigit(inputArr[i][j]) && counter != 2 ){
                        System.out.println("ASDASDASDASD");
                        if(j < WIDTH -1 && (inputArr[i-1][j+1] != null || inputArr[i+1][j+1] != null)){
                            if(inputArr[i][j] == inputArr[i-1][j+1]) {
                                Track t = (Track) myMap[i][j];
                                Track t2 = (Track) myMap[i - 1][j + 1];
                                t.setSwitchTrackD((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], (Track) myMap[i - 1][j + 1], 1, i + "");
                                t2.setSwitchTrackU((Track) myMap[i - 2][j + 1], (Track) myMap[i][j + 1], (Track) myMap[i][j], 0, 2 + i + "");
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
        fuck();
        return myMap;
    }

    public void fuck(){
        for(int i = 0; i < LENGTH; i++){
            for(int j = 0; j < WIDTH; j++){
                System.out.print(myMap[i][j].getClass().getSimpleName());
            }
            System.out.println();
        }
    }



    public Thread[][] initialize(){


//        myMap = new Thread[LENGTH][WIDTH];
//            for(int j = 0; j < 9; j++){
//                System.out.print(test[i][j]);
//                if(test[i][j] != null){
//                    myMap[i][j] = new Station(test[0][j] ,
//
//                }
//            }
//            System.out.println();
//        }
//
//        System.out.println("Stations: " + stations);
//        System.out.println("Tracks: " + tracks);
//        System.out.println("Uh-Ohs: " + other);

        //Station A = new Station();
//        Track T1 = new Track();
//        Track T2 = new Track();
//        Track T3 = new Track();
//        Track T4 = new Track();
//        Track T5 = new Track();
//        Track B1 = new Track();
//        Track B2 = new Track();
//        Track B3 = new Track();
//        Track B4 = new Track();
//        Track B5 = new Track();
//
//        Track C1 = new Track();
//        Track C2 = new Track();
//        Track C3 = new Track();
//        Track C4 = new Track();
//        Track C5 = new Track();


//
//        for(int i = 1; i < LENGTH-2; i++){
//            for(int j = 0; j < WIDTH; j++){
//                myMap[i][j] = new Track();
//            }
//        }
//
//        for(int j = 0; j < WIDTH; j++){
//            myMap[0][j] = new Station();
//            myMap[LENGTH - 1][j] = new Station();
//        }
//
//        for(int j = 0; j < WIDTH; j++){
//            myMap[0][j] = new Station("Station " + j, (Track)myMap[LENGTH-2][j] , pane);
//            myMap[LENGTH-1][j] = new Station("Station " + j, (Track)myMap[1][j], pane);
//        }

//
//        Station X = new Station("Station X", T5, pane);
//        Station A = new Station("Station A", T1, pane);
//
//        Station Y = new Station("Station Y", B5, pane);
//        Station B = new Station("Station B", B1, pane);
//
//        Station Z = new Station("Station Z", C5, pane);
//        Station C = new Station("Station C", C1, pane);



        //RIGHT LEFT UP/DOWN (0 = Right, 1 = Left) NAME

//        T5.setTrackRStation(X, T4, "T5");
//        T4.setTrack(T5, T3, "T4");
//        T3.setTrack(T4, T2, "T3");
//        T2.setSwitchTrackD(T3, T1, B3, 0, "Switch2");
//        T1.setTrackLStation(T2, A, "T1");
//
//        B5.setTrackRStation(Y, B4, "B5");
//        B4.setTrack(B5, B3, "B4");
//        //B4.setSwitchTrackD(B3, B1, C2, 0, "Switch3");
//        B3.setSwitchTrackU(B4, B2, T2, 1,"Switch2");
//        B2.setTrack(B3, B1, "B2");
//        B1.setTrackLStation(B2, B, "B1");
//
//        C5.setTrackRStation(Z, C4, "C5");
//        C4.setTrack(C5, C3, "C4");
//        C3.setTrack(C4, C2, "C3");
//        //C3.setSwitchTrackD(C4, C2, B4, 1, "Switch4");
//        C2.setTrack(C3, C1, "C3");
//        C1.setTrackLStation(C2, C, "C1");



       // myMap = new Thread[LENGTH][WIDTH];
//        myMap[0][0] = A;
//        myMap[1][0] = T1;
//        myMap[2][0] = T2;
//        myMap[3][0] = T3;
//        myMap[4][0] = T4;
//        myMap[5][0] = T5;
//        myMap[6][0] = X;
//        myMap[0][1] = B;
//        myMap[1][1] = B1;
//        myMap[2][1]= B2;
//        myMap[3][1] = B3;
//        myMap[4][1] = B4;
//        myMap[5][1] = B5;
//        myMap[6][1] = Y;
//        myMap[0][2] = C;
//        myMap[1][2] = C1;
//        myMap[2][2] = C2;
//        myMap[3][2] = C3;
//        myMap[4][2] = C4;
//        myMap[5][2] = C5;
//        myMap[6][2] = Z;
//        for(int i = 1; i < LENGTH-1; i++){
//            for(int j = 0; j < WIDTH; j++){
//                myMap[i][j] = new Track();
//            }
//        }
//
//        for(int j = 0; j < WIDTH; j++){
//            myMap[0][j] = new Station();
//            myMap[LENGTH-1][j] = new Station();
//        }
//
//        for(int j = 0; j < WIDTH; j++) {
//            myMap[0][j] = new Station("Station " + j, (Track) myMap[1][j], pane);
//            myMap[LENGTH - 1][j] = new Station("Station " + j+4, (Track) myMap[LENGTH-2][j], pane);
//        }
//
//        for(int i = 1; i < LENGTH-1; i++){
//            for(int j = 0; j < WIDTH; j++){
//                if(i % 2 != 0) {
//                    //System.out.println(i);
//                    if (i == 1) {
//                        Track t = (Track) myMap[i][j];
//                        t.setTrackLStation((Track) myMap[2][j], (Station) myMap[0][j], "" + i);
//                        myMap[i][j] = t;
//                    } else if (i < LENGTH-2) {
//                        Track t = (Track) myMap[i][j];
//                        t.setTrack((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], "" + i);
//                        myMap[i][j] = t;
//                    }
//                    else{
//                        Track t = (Track) myMap[i][j];
//                        t.setTrackRStation((Station)myMap[LENGTH -1][j], (Track)myMap[LENGTH-2][j], ""+i);
//                        myMap[i][j] = t;
//                    }
//                }
//                else {
//                    Track t = (Track)myMap[i][j];
//                    t.setTrack((Track)myMap[i+1][j], (Track)myMap[i-1][j], ""+i);
//                    myMap[i][j] = t;
//
//                }
//            }
//        }

//
        myMap = reader();

        for(int i = 0; i < LENGTH; i++){
            for(int j = 0; j < WIDTH; j++){

                myMap[i][j].start();
            }
        }
        return myMap;
    }
    public synchronized void test(){
        for(int i = 1; i < 6; i++){
            for(int j = 0; j < 2; j++){
                System.out.println("Track States = " + myMap[i][j].getState());
            }
        }
    }



    public synchronized void setStartStation(LinkedList<Station> stations){

        if(stations.size() == 2){
            Station start = stations.getFirst();
            Station end = stations.getLast();

            System.out.println("Station = " + start.getState());
            System.out.println("Next = " + start.next.getState());

            //Train train = new Train(start, end, 1, true, pane);
            //queue.add(train);

            start.finishLine(end);
            pickStation(start, end);


        }

    }
    public synchronized void pickStation(Station start, Station end){

        start.initStation = end.initStation = true;

        synchronized (start){

            start.selected1 = true;
            //start.setPriority(1);
            start.notify();

            //start.next.begin = true;

        }

    }

    public void run(){

    }
}
