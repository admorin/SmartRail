package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Track extends Thread implements Component
{
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain;
    public boolean isSwitch;
    public Track next;
    public Track nextR;
    public Track nextD;
    public Track nextU;
    public Track nextL;
    public Rectangle display = new Rectangle();
    public Train train;
    public Station station;
    public Station startStation;
    public boolean isOpen = true;
    public int isReserved = 0;
    private boolean atEnd = false;
    private boolean trainHere = false;
    public String NAME;
    int index = 1;
    TrainPrinter printer;

    public Track(){

    }


    public void setTrack(Track nextR, Track nextL, String name)
    {
        this.NAME = name;
        setName(name);
        //printer = print;
        this.nextR = nextR;
        this.nextL = nextL;
    }
    public void setTrackRStation(Station nextR, Track nextL, String name)
    {
        this.NAME = name;
        setName(name);
        //printer = print;
        this.nextR = null;
        this.station = nextR;
        this.nextL = nextL;
    }
    public void setTrackLStation(Track nextR, Station nextL, String name)
    {
        this.NAME = name;
        setName(name);
        //printer = print;
        this.nextR = nextR;
        this.nextL = null;
        this.station = nextL;
    }
    public void setTrack2Switch(Track nextR, Track nextL, Track nextD, String name){
        this.NAME = name;
        setName(name);
        this.nextR = nextR;
        this.nextD = nextD;
        this.nextL = nextL;
    }

    public void moveTrain(){

        try {
            train.changeTrack(this);
            isOpen = false;
//            System.out.println(train.currentTrack());
            System.out.println("Train is on track "+ this.getName());
            Thread.sleep(1000);
//               System.out.println("Current Track = " +
//                       Thread.currentThread().getName() +
//                       " Next Track = " + next.getName());
            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());
            next.getTrain(train);
            trainHere = false;
            hasTrain = false;



        }
        catch (Exception e){

        }

    }

    public void findNext(){
        if(train.peekDirection().equals("Right"))
        {
            next = nextR;
        }
        else if(train.peekDirection().equals("Down"))
        {
            next = nextD;
        }
        else
        {
            next = nextL;
        }
    }

    public void getTrain(Train train){
        this.train = train;
        this.startStation = train.getStartStation();
        trainHere = true;
    }

    public void setDisplay(){
        display.setWidth(width);
        display.setHeight(height);
        display.setFill(Color.BLACK);
    }


    public void setOccupied(){
        this.next.isOpen = false;

    }

    public boolean hasTrain(){
        return trainHere;
    }

    public void run()
    {
        findNext();
        while(isOpen) {
            if(station != null)
            {
//                System.out.println("Arrived at " + station.returnName());
                station.getTrain(train);
                atEnd = true;
                moveTrain();
            }
            try {
                if (this.next.isOpen) {
                    moveTrain();
                    next.start();
                    next.hasTrain = true;
                    Thread.currentThread().interrupt();
                }
                else{

                    System.out.println("Track " + getName() + " is occupied...");
                    System.out.println("Waiting...");
                    Thread.sleep(2000);
                }
            }
            catch(Exception e){
//                System.out.println(e.getMessage());
//                System.out.println("EXCEPTION");
                //System.exit(0);
            }
        }

    }



}
