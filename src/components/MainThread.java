package components;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class MainThread extends Thread {
    public Track[][] trackMap = new Track[2][7];
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
    Thread[][] myMap = new Thread[7][2];
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


        Station X = new Station("Station X", T5);
        Station A = new Station("Station A", T1);

        Station Y = new Station("Station Y", B5);
        Station B = new Station("Station B", B1);

        //RIGHT LEFT UP/DOWN (0 = Right, 1 = Left) NAME
        T5.setTrackRStation(X, T4, "T5");
        T4.setTrack(T5, T3, "T4");
        //T3.setSwitchTrackD(T4, T2, B4, "Switch1");
        T3.setTrack(T4, T2, "T3");
        T2.setSwitchTrackD(T3, T2, B3, 0, "Switch2");
        //T2.setTrack(T3, T1, "T2");
        T1.setTrackLStation(T2, A, "T1");

        B5.setTrackRStation(Y, B4, "B5");
        //B4.setSwitchTrackU(B5, B3, T3, "B4");
        B3.setSwitchTrackU(B4, B2, T2, 1,"Switch2");
        //B3.setTrack(B4, B2, "B3");
        B4.setTrack(B5, B3, "B4");
        B2.setTrack(B3, B1, "B2");
        B1.setTrackLStation(B2, B, "B1");




        myMap = new Thread[7][2];
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

//
        stationList.add(X);
        stationList.add(A);
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 2; j++){
                myMap[i][j].start();

            }
        }





        return myMap;
    }
    public synchronized void test(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 2; j++){
                System.out.println(myMap[i][j].getState());
            }
        }
    }



    public synchronized void setStartStation(LinkedList<Station> stations){

        Station start;
        Station end;
        if(stations.size() == 2){
            start = stations.getFirst();
            System.out.println("Station = " + start.getState());
            System.out.println("Next = " + start.next.getState());
          //  start.isStarting = true;
            end = stations.getLast();
            pickStation(start, end);
//            start.finishLine(end);
//            start.selected1 = true;
//            start.next.begin = true;
//            System.out.println(start.next.begin);
//            test();

            //System.out.println(start.returnName() + " " + end.returnName());
        }

    }
    public synchronized void pickStation(Station start, Station end){
        start.isStarting = true;
        start.finishLine(end);
        Train train = new Train(start, end, 1, true);
        synchronized (start){
            start.train = train;
            start.train.start();
            start.selected1 = true;
            start.next.begin = true;
            start.notify();
            test();

        }
        synchronized (end){
            //test();
        }
    }

    public void run(){

    }
}
