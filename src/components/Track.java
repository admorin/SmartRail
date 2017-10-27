package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Track extends Thread implements Component
{
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain;
    public Track next;
    public Track nextR;
    public Track nextL;
    public Rectangle display = new Rectangle();
    public Train train;
    public Station station;
    public Station startStation;
    public boolean isOpen = true;
    private boolean atEnd = false;
    public String NAME;
    int index = 1;
    TrainPrinter printer;

    public Track(){

    }


    public void setTrack(Track nextR, Track nextL, String name, TrainPrinter print)
    {
        this.NAME = name;
        setName(name);
        printer = print;
        this.nextR = nextR;
        this.nextL = nextL;
        hasTrain = false;
    }
    public void setTrackRStation(Station nextR, Track nextL, String name, TrainPrinter print)
    {
        this.NAME = name;
        setName(name);
        printer = print;
        this.nextR = null;
        this.station = nextR;
        this.nextL = nextL;
        hasTrain = false;
    }
    public void setTrackLStation(Track nextR, Station nextL, String name, TrainPrinter print)
    {
        this.NAME = name;
        setName(name);
        printer = print;
        this.nextR = nextR;
        this.nextL = null;
        this.station = nextL;
        hasTrain = false;
    }

    public void moveTrain(){

        try {
            isOpen = false;
            train.changeTrack(this);
            System.out.println("Train is on track "+ this.getName());
            Thread.sleep(1000);
//               System.out.println("Current Track = " +
//                       Thread.currentThread().getName() +
//                       " Next Track = " + next.getName());
            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());
            next.getTrain(train);



        }
        catch (Exception e){

        }

    }

    public void findNext(){
        if(train.peekDirection() == "Right")
        {
            next = nextR;
        }
        else
        {
            next = nextL;
        }
    }

    public void getTrain(Train train){
        this.train = train;
        this.startStation = train.getStartStation();
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
            if(this.next == null)
            {
                System.out.println("Arrived at " + station.returnName());
                isOpen = false;
                atEnd = true;
            }
            try {
                if (this.next.isOpen) {
                    moveTrain();
                    //printer.moveTrain(index+1);
                    next.start();
                    isOpen = false;
                    Thread.sleep(1000);
                }
                else{

                    System.out.println("Track " + getName() + " is occupied...");
                    System.out.println("Waiting...");
                    Thread.sleep(2000);



                }
            }
            catch(Exception e){
                isOpen = false;
                System.out.println(e.getMessage());
                System.out.println("EXCEPTION");
                System.exit(0);
            }
        }
        isOpen = false;

    }


}
