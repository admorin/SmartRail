package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Track extends Thread implements Component {
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain;
    public boolean isSwitch;
    public Track next;
    public Track nextR;
    public Track nextD;
    public Track nextU;
    public Track nextL;
    public Circle light = new Circle(10);
    public Train train;
    public Station station;
    public Object lock = new Object();
    public Station startStation;
    public boolean hasArrived = false;
    public boolean isOpen = true;
    public int isReserved = 0;
    private boolean atEnd = false;
    public boolean isLight = false;
    public boolean visited = false;
    public volatile boolean begin = false;
    public String NAME;
    int index = 1;
    TrainPrinter printer;
    //public Circle stopLight = new Circle(20);


    public Track() {


    }


    public void setTrack(Track nextR, Track nextL, String name) {
        this.NAME = name;
        setName(name);
        //printer = print;
        this.nextR = nextR;
        this.nextL = nextL;
    }

    public void setTrackRStation(Station nextR, Track nextL, String name) {
        this.NAME = name;
        setName(name);
        //printer = print;
        this.nextR = null;
        this.station = nextR;
        this.nextL = nextL;
    }

    public void setTrackLStation(Track nextR, Station nextL, String name) {
        this.NAME = name;
        setName(name);
        this.nextR = nextR;
        this.nextL = null;
        this.station = nextL;
    }

    public void setSwitchTrackD(Track nextR, Track nextL, Track nextD, String name) {
        this.nextD = nextD;
        this.nextL = nextL;
        this.nextR = nextR;
        //this.isLight = true;
        setName(name);
    }

    public void setSwitchTrackU(Track nextR, Track nextL, Track nextU, String name) {
        this.nextU = nextU;
        this.nextL = nextL;
        this.nextR = nextR;
        setName(name);
    }

    public Track[] returnNeighbors() {
        Track[] neighbors = new Track[]{nextU, nextR, nextD, nextL};
        return neighbors;
    }

    public synchronized Station returnStation() {
        return station;
    }

    public boolean hasStation() {
        if (station == null) {
            return false;
        } else {
            return true;
        }
    }

    public void setReserved(int trainNumber) {
        isReserved = trainNumber;
    }

    public int getReserved() {
        return isReserved;
    }

    public synchronized void moveTrain() {


        try {
            train.changeTrack(this);
            this.begin = false;
            isOpen = false;
            System.out.println("Train is on track " + this.getName());
            Thread.sleep(1000);
            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());
            next.getTrain(train);
            this.hasTrain = false;


        } catch (Exception e) {

        }


    }

    public synchronized void findNext() {


        String direction = train.peekDirection();

        if (direction != null) {

            if (direction.equals("Right")) {
                next = nextR;
            } else if (direction.equals("Down")) {
                next = nextD;
                //System.out.println("Switching Tracks");
            } else if (direction.equals("Up")) {
                next = nextU;
                //System.out.println("Switching Tracks");
            } else {
                next = nextL;
            }
        }

    }

    public void getTrain(Train train) {
        this.train = train;
        this.startStation = train.getStartStation();
    }

    public void trackSwitch(Track track) {

    }



    public void run() {

        //while(isOpen) {
        while (!Thread.currentThread().isInterrupted()) {

            synchronized (this) {
                try {

                    while (!begin) {
                        //findNext();
                        this.wait();

                    }
                }
                catch(InterruptedException e){
                }

                findNext();
                //System.out.println(next);
                if (station != null && !station.isStarting) {
//                System.out.println("Arrived at " + station.returnName());

                    System.out.println("Train has ended");
                    station.getTrain(train);
                    isOpen = false;
                    begin = false;
                    atEnd = true;
                    moveTrain();
                    hasTrain = false;
                    this.hasArrived = true;

                } else if (this.next.isOpen) {
                    System.out.println("entered");
                    this.hasArrived = false;

                    hasTrain = true;
                    moveTrain();
                    this.next.begin = true;
                    next.hasTrain = true;
                    synchronized (next){

                        //next.findNext();
                        next.notify();
                    }
                    // next.hasTrain = true;
                }
            }


//                    if (begin) {
//                        if (station != null && !station.isStarting) {
////                System.out.println("Arrived at " + station.returnName());
//
//                            station.getTrain(train);
//                            isOpen = false;
//                            begin = false;
//                            atEnd = true;
//                            moveTrain();
//                            hasTrain = false;
//                            this.hasArrived = true;
//                        } else if (this.next.isOpen) {
//                            System.out.println("entered");
//                            this.hasArrived = false;
//                            hasTrain = true;
//                            moveTrain();
//                            this.next.begin = true;
//                            this.next.notify();
//                            next.hasTrain = true;
                            //Thread.currentThread().wait();
                            //Thread.currentThread().interrupt();
                        }
//                    } else {
//                        System.out.println("Track " + getName() + " is occupied...");
//                        System.out.println("Waiting...");
//                        Thread.sleep(2000);
//                    }

//                    } else {
//                        wait();
//                    }
//                }
//
//                catch(Exception e){
//
//                }
            }



}





