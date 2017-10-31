package components;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class MainThread extends Thread {
    public Track[][] trackMap = new Track[2][5];
    public MainThread(){

    }

    public Component[] initialize(){
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

        T5.setTrackRStation(X, T5, "5");
        T4.setTrack(T5, T3, "4");
        T3.setTrack(T4, T2, "3");
        T2.setTrack(T3, T1, "2");
        T1.setTrackLStation(T2, A, "1");

        B5.setTrackRStation(Y, B5, "5");
        B4.setTrack(B5, B3, "4");
        B3.setTrack(B4, B2, "3");
        B2.setTrack(B3, B1, "2");
        B1.setTrackLStation(B2, B, "1");



        Component[] myMap = new Component[10];
        //myMap[0] = A;
        myMap[0] = T1;
        myMap[1] = T2;
        myMap[2] = T3;
        myMap[3] = T4;
        myMap[4] = T5;
       // myMap[6] = X;
        //myMap[7] = B;
        myMap[5] = B1;
        myMap[6] = B2;
        myMap[7] = B3;
        myMap[8] = B4;
        myMap[9] = B5;
        //myMap[13] = Y;

        A.finishLine(X);
        A.start();

        return myMap;
    }




}
