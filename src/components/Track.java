package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Track extends Thread implements Component {
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain;
    public boolean isSwitch = false;
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
    public int direction;
    //public boolean hasArrived = false;
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

    public void setSwitchTrackD(Track nextR, Track nextL, Track nextD, int direction, String name) {
        this.direction = direction;
        this.nextD = nextD;
        this.nextL = nextL;
        this.nextR = nextR;
        this.isSwitch = true;
        this.NAME = name;
        setName(name);
    }

    public void setSwitchTrackU(Track nextR, Track nextL, Track nextU, int direction, String name) {
        this.direction = direction;
        this.nextU = nextU;
        this.nextL = nextL;
        this.nextR = nextR;
        this.isSwitch = true;
        this.NAME = name;
        setName(name);
    }

    public Track[] returnNeighbors() {
        Track[] neighbors = new Track[]{nextU, nextR, nextD, nextL};
        return neighbors;
    }

    public int returnDirection(){
        return direction;
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

    public synchronized void moveTrain(boolean flag) {

        try {

            train.changeTrack(this);
            this.begin = false;
            isOpen = true;

            Thread.sleep(1300);

            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());
            System.out.println("Train is on track " + this.getName());

            if(flag) next.getTrain(train);
            this.hasTrain = false;


        } catch (Exception e) {

        }


    }

    public synchronized void findNext() {

        for (int i = 0; i < train.getDirections().size(); i++) {
            System.out.println(train.getDirections().get(i));
        }
        System.out.println("------------");


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
            } else if (direction.equals("Left")) {
                next = nextL;
            }
            begin = false;

        }
    }



    public void getTrain(Train train) {
        this.train = train;
        this.startStation = train.getStartStation();
    }

    public void trackSwitch(Track track) {

    }



    public void run() {
        while (isAlive()) {

            synchronized (this) {
                try {

                    while (!begin) {
                        this.wait();

                    }
                } catch (InterruptedException e) {
                }

                findNext();
                //System.out.println(next);
                if (this.station != null && !this.station.isStarting && this.station.equals(train.endDest)) {
                System.out.println("Arrived at " + station.returnName()
                + " from " + train.startDest);

                    System.out.println("Train has ended");
                   // moveTrain(false);
                    station.getTrain(train);
                    isOpen = true;
                    begin = false;
                    //moveTrain(false);
                    atEnd = true;
                    hasTrain = false;
                    train.clearDirections();

                }

                else if (this.next != null && this.next.isOpen) {
                    System.out.println("Start dest = " + train.startDest);
                    System.out.println("End dest = " + train.endDest);
                    //this.station.hasArrived = false;
                    hasTrain = true;
                    moveTrain(true);

                    synchronized (next) {
                        this.next.begin = true;
                        next.hasTrain = true;
                        this.next.notify();
                    }
                    // next.hasTrain = true;
                }
            }
        }
    }

}





