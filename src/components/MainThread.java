package components;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class MainThread extends Thread {
    public Track[][] trackMap = new Track[2][7];
    public Object[][] trainMap = new Object[7][2];
    public MainThread(){

    }

    public Component[][] initialize(){
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

        TrackSwitch TS3 = new TrackSwitch();


        Station X = new Station("Station X", T5);
        Station A = new Station("Station A", T1);

        Station Y = new Station("Station Y", B5);
        Station B = new Station("Station B", B1);

        T5.setTrackRStation(X, T4, "T5");
        T4.setTrack(T5, T3, "T4");
        T3.setSwitchTrackD(T4, T2, B4, "Switch1");
        T2.setTrack(T3, T1, "T2");
        T1.setTrackLStation(T2, A, "T1");

        B5.setTrackRStation(Y, B4, "B5");
        B4.setSwitchTrackU(B5, B3, T3, "B4");
        B3.setTrack(B4, B2, "B3");
        B2.setTrack(B3, B1, "B2");
        B1.setTrackLStation(B2, B, "B1");



        Component[][] myMap = new Component[7][2];
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


        Y.finishLine(A);
        Y.start();
//        A.finishLine(X);
//        A.start();



        return myMap;
    }

}
