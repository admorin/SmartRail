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
    public void setSwicthTrackD(Track nextR, Track nextL, Track nextD, String name){
        this.nextD = nextD;
        this.nextL = nextL;
        this.nextR = nextR;
        setName(name);
    }

    public void moveTrain(){

        try {

            if(this.isSwitch){
                System.out.println("This is a switch track");
            }
            train.changeTrack(this);
            isOpen = false;
            System.out.println("Train is on track "+ this.getName());
            Thread.sleep(1000);
            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());
            next.getTrain(train);
            hasTrain = false;



        }
        catch (Exception e){

        }

    }

    public void findNext(){
        String direction = train.peekDirection();

        if(direction != null) {
            if (direction.equals("Right")) {
                next = nextR;
            } else if (direction.equals("Down")) {
                next = nextD;
                System.out.println("Switching Tracks");
            } else {
                next = nextL;
            }
        }
    }

    public void getTrain(Train train){
        this.train = train;
        this.startStation = train.getStartStation();
    }

    public void trackSwitch(Track track){

    }

    public void setDisplay(){
        display.setWidth(width);
        display.setHeight(height);
        display.setFill(Color.BLACK);
    }


    public void setOccupied(){
        this.next.isOpen = false;

    }

    public void run()
    {
        findNext();
        while(isOpen) {
            if(station != null)
            {
//                System.out.println("Arrived at " + station.returnName());
                station.getTrain(train);
                isOpen = false;
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
