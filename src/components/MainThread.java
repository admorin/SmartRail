package components;

import javafx.scene.layout.Pane;

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
    public ArrayList<Station> stationList = new ArrayList<>();
    public ArrayList<Station> pickedStations = new ArrayList<>();
    public ArrayList<Track> trackList = new ArrayList<>();
    public boolean beenClicked = false;
    Object[][] myMap = new Object[7][2];
    public MainThread(Pane pane){
        this.pane = pane;

    }

    public Object[][] initialize(){
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








        X = new Station("Station X", T5);
        A = new Station("Station A", T1);

        Y = new Station("Station Y", B5);
        B = new Station("Station B", B1);

        T5.setTrackRStation(X, T5, "T5");
        T4.setTrack(T5, T3, "T4");
        T3.setSwitchTrackD(T4, T2, B4, "Switch1");
        T2.setTrack(T3, T1, "T2");
        T1.setTrackLStation(T2, A, "T1");

        B5.setTrackRStation(Y, B5, "B5");
        B4.setTrack(B5, B3, "B4");

        B3.setTrack(B4, B2, "B3");
        B2.setTrack(B3, B1, "B2");
        B1.setTrackLStation(B2, B, "B1");




        myMap = new Object[7][2];
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
        //A.finishLine(Y);
        //A.start();



        return myMap;
    }



    public void setStartStation(ArrayList<Station> stations){

        if(stations.size() == 2){
            Station start =  stations.get(0);
            Station end = stations.get(1);
            start.start();
            start.finishLine(end);

        }

    }

    public void tempArr(){
        for(int i = 0; i < 7; i++){
            for(int j = 0; j < 2; j++){
                String t = myMap[i][j].getClass().getSimpleName();
                System.out.println(t);
                temp[i][j] = t;


            }
        }
    }

    public void selected(){
        {
            A.start();
        }
    }
    public void recursive(int x, int y, Station end, Station start){

        temp[x][y] = myMap[x][y].getClass().getSimpleName() + "1";

        if(!inBounds(x, y)) return;



        if(temp[x][y].equals(end.getClass().getSimpleName())){

            temp[x][y] = myMap[x][y].getClass().getSimpleName() + "64";



        }


        if(temp[x][y].equals(myMap[x][y].getClass().getSimpleName() + "1")) return;





        for(int i = 0; i < DX.length; i++){

                System.out.println("X = " + x + ", Y = " + y);
                recursive(DX[i] + x, DY[i] + y, end, start);

        }
    }


    public boolean inBounds(int x, int y){
        return(x >= 0 && y >= 0 && x < 7 && y < 2);
    }

    public void search() {

        tempArr();
//        for(int i = 0; i < 7; i++){
//            for(int j = 0; j < 2; j++){
//                recursive(i, j, X, A);
//            }
//        }
        recursive(0, 0, Y, B);
    }

    public void print(){
        for(int i = 0; i < 2; i++){
            for(int j = 0; j < 7; j++){
                System.out.print(temp[j][i]);
            }
            System.out.println();

        }
    }
    public static void main(String[] args){
        MainThread t =new MainThread(null);
        t.initialize();
        t.search();
        t.print();

    }

}
