package components;

/**
 * Created by alexschmidt-gonzales on 10/25/17.
 */
public class TrainPrinter {
    private int trackLength;
    public int index;
    private String tracks = "";
    public TrainPrinter(int trackLen,int index){
        trackLength = trackLen;
        initTracks();

    }

    public void initTracks(){
        tracks = "||";
        for(int i = 0; i < trackLength; i++){
            tracks += "-";
        }
        tracks += "||";
        System.out.println(tracks);
    }
    public void moveTrain(int index){
        //tracks = tracks.replace(tracks.charAt(index-1), '-');
        tracks = tracks.replace(tracks.charAt(index), 'T');
        printTracks();
        System.out.println("dffffsdfsdfsd");
        ////

    }

    public void printTracks(){
        System.out.println(tracks);
    }
}
