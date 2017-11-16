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

import com.sun.xml.internal.ws.handler.HandlerException;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.*;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class MainThread extends Thread {
    public final int LENGTH = 7;
    public final int WIDTH = 3;
    public Track[][] trackMap = new Track[WIDTH][LENGTH];
    public Object[][] trainMap = new Object[7][2];
    public int[] DX = {0, 1, 0, -1, 1};
    public int[] DY = {0, 0, 1, 0, 1};
    public Station A;
    public Station X;
    public Station B;
    public Station Y;
    public Pane pane;

    public String[][] temp = new String[7][2];
    public LinkedList<Station> stationList = new LinkedList<>();
    public ArrayList<Station> pickedStations = new ArrayList<>();
    public ArrayList<Track> trackList = new ArrayList<>();
    public boolean beenClicked = false;
    public Object lock = new Object();
    Thread[][] myMap = new Thread[LENGTH][WIDTH];
    public Station start;
    public Station end;


    public MainThread(Pane pane){
        this.pane = pane;

    }

    public Thread[][] initialize(){
//        Station station1 = new Station();
//        Track track1 = new Track();
//        Track track2 = new Track();
//        Track track3 = new Track();
//        Track track4 = new Track();
//        Track track5 = new Track();
//        Station station2 = new Station();

        //Station A = new Station();
        Track T1 = new Track();
        Track T2 = new Track();
        Track T3 = new Track();
        Track T4 = new Track();
        Track T5 = new Track();
        Track B1 = new Track();
        Track B2 = new Track();
        Track B3 = new Track();
        Track B4 = new Track();
        Track B5 = new Track();

        Track C1 = new Track();
        Track C2 = new Track();
        Track C3 = new Track();
        Track C4 = new Track();
        Track C5 = new Track();

        for(int i = 1; i < LENGTH-2; i++){
            for(int j = 0; j < WIDTH; j++){
                myMap[i][j] = new Track();
            }
        }

        for(int j = 0; j < WIDTH; j++){
            myMap[0][j] = new Station();
            for(int x = 0; x < WIDTH; x++) {

                myMap[LENGTH - 1][x] = new Station();
            }
        }

        for(int j = 0; j < WIDTH; j++){
            myMap[0][j] = new Station("Station " + j, (Track)myMap[LENGTH-2][j] , pane);
            for(int x = 0; x < WIDTH; x++)
            myMap[LENGTH-1][x] = new Station("Station " + x, (Track)myMap[1][x], pane);
        }

//
        Station X = new Station("Station X", T5, pane);
        Station A = new Station("Station A", T1, pane);

        Station Y = new Station("Station Y", B5, pane);
        Station B = new Station("Station B", B1, pane);

        Station Z = new Station("Station Z", C5, pane);
        Station C = new Station("Station C", C1, pane);



        //RIGHT LEFT UP/DOWN (0 = Right, 1 = Left) NAME

        T5.setTrackRStation(X, T4, "T5");
        T4.setTrack(T5, T3, "T4");
        T3.setTrack(T4, T2, "T3");
        T2.setSwitchTrackD(T3, T1, B3, 0, "Switch2");
        T1.setTrackLStation(T2, A, "T1");

        B5.setTrackRStation(Y, B4, "B5");
        B4.setTrack(B5, B3, "B4");
        //B4.setSwitchTrackD(B3, B1, C2, 0, "Switch3");
        B3.setSwitchTrackU(B4, B2, T2, 1,"Switch2");
        B2.setTrack(B3, B1, "B2");
        B1.setTrackLStation(B2, B, "B1");

        C5.setTrackRStation(Z, C4, "C5");
        C4.setTrack(C5, C3, "C4");
        C3.setTrack(C4, C2, "C3");
        //C3.setSwitchTrackD(C4, C2, B4, 1, "Switch4");
        C2.setTrack(C3, C1, "C3");
        C1.setTrackLStation(C2, C, "C1");


        myMap = new Thread[LENGTH][WIDTH];
        myMap[0][0] = A;
        myMap[1][0] = T1;
        myMap[2][0] = T2;
        myMap[3][0] = T3;
        myMap[4][0] = T4;
        myMap[5][0] = T5;
        myMap[6][0] = X;
        myMap[0][1] = B;
        myMap[1][1] = B1;
        myMap[2][1]= B2;
        myMap[3][1] = B3;
        myMap[4][1] = B4;
        myMap[5][1] = B5;
        myMap[6][1] = Y;
        myMap[0][2] = C;
        myMap[1][2] = C1;
        myMap[2][2] = C2;
        myMap[3][2] = C3;
        myMap[4][2] = C4;
        myMap[5][2] = C5;
        myMap[6][2] = Z;
        for(int i = 1; i < LENGTH-2; i++){
            for(int j = 0; j < WIDTH; j++){
                myMap[i][j] = new Track();
            }
        }

//        for(int j = 0; j < WIDTH; j++){
//            myMap[0][j] = new Station();
//            myMap[LENGTH-1][j] = new Station();
//        }

        for(int j = 0; j < WIDTH; j++) {
            myMap[0][j] = new Station("Station " + j, (Track) myMap[1][j], pane);
            myMap[LENGTH - 1][j] = new Station("Station " + j+4, (Track) myMap[LENGTH-2][j], pane);
        }

        for(int i = 1; i < LENGTH-1; i++){
            for(int j = 0; j < WIDTH; j++){
                if(i % 2 != 0) {
                    //System.out.println(i);
                    if (i == 1) {
                        Track t = (Track) myMap[i][j];
                        t.setTrackLStation((Track) myMap[2][j], (Station) myMap[0][j], "" + i);
                        myMap[i][j] = t;
                    } else if (i < LENGTH-2) {
                        Track t = (Track) myMap[i][j];
                        t.setTrack((Track) myMap[i + 1][j], (Track) myMap[i - 1][j], "" + i);
                        myMap[i][j] = t;
                    }
                    else{
                        Track t = (Track) myMap[i][j];
                        t.setTrackRStation((Station)myMap[LENGTH -1][j], (Track)myMap[LENGTH-2][j], ""+i);
                        myMap[i][j] = t;
                    }
                }
                else {
                    Track t = (Track)myMap[i][j];
                    t.setTrack((Track)myMap[i+1][j], (Track)myMap[i-1][j], ""+i);
                    myMap[i][j] = t;

                }
            }
        }

//
        stationList.add(X);
        stationList.add(A);
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
