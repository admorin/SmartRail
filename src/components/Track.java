/**
 * Andrew Morin
 * Tyson Craner
 * Alex Schmidt
 *
 * Train System
 * Project 3
 * 11/15/2017
 */

package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.PriorityQueue;
import java.util.Queue;

public class Track extends Thread implements Component {
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain = false;
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
    public Queue<Integer> trainOrder = new PriorityQueue<>();
    private boolean atEnd = false;
    public boolean isLight = false;
    public boolean visited = false;
    public volatile boolean begin = false;
    public Station endStation;
    public String NAME;
    public volatile boolean isWaiting = false;
    int index = 1;
    public double switchX = -1;
    public double startX = -1;

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
            isOpen = true;

            Thread.sleep(1250);

            System.out.println("Attempting to move Train from Track " + getName() + " to Track " + next.getName());
            System.out.println("Train is on track " + this.getName());

            next.getTrain(train);
            this.hasTrain = true;
            //this.begin = false;



        } catch (Exception e) {

        }


    }

    public synchronized void findNext() {

        for (int i = 0; i < train.getDirections().size(); i++) {
            System.out.println(train.getDirections().get(i));
        }
        System.out.println("------------");
        int index = train.getDirections().size()-1;

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

    public void trainOnWay(int trainNumber){
        trainOrder.add(trainNumber);
    }

    public void trainPassed(int trainNumber){
        trainOrder.remove(trainNumber);
    }

    private boolean hasPriority(int trainNumber){
        if(trainOrder.peek() == trainNumber){
            return true;
        } else {
            return false;
        }
    }

    public synchronized void getTrain(Train train) {
        this.train = train;
        this.startStation = train.getStartStation();
        this.endStation = train.getEndStation();

    }

    public int peekQ(){
        return trainOrder.peek();
    }

    public synchronized void operation(boolean flag){
        boolean moved = false;
        while (!moved) {
            if (this.next.isOpen && this.next.hasPriority(this.train.returnTrainNumber())) {
                System.out.println("Start dest = " + train.startDest);
                System.out.println("End dest = " + train.endDest);
                //this.station.hasArrived = false;
                this.hasTrain = true;

                moveTrain(true);
                this.isWaiting = false;

                synchronized (next) {
                    this.next.begin = true;
                    this.next.hasTrain = true;
                    this.next.notify();
                }
                // next.hasTrain = true;
                moved = true;
            } else {
                this.isWaiting = true;
                //System.out.println("WAITING FOR OPEN");
            }
        }
    }


    public void run() {
        boolean test = false;
        while (isAlive()) {

            synchronized (this) {
                try {

                    while (!begin) {
                        isOpen = true;
                        hasTrain = false;
                        this.wait();

                    }
                } catch (InterruptedException e) {

                }

                findNext();
                if (this.station != null && this.station.equals(endStation)) {
                System.out.println("Arrived at " + station.returnName()
                + " from " + train.startDest.returnName());

                    System.out.println("Train has ended");
                   // moveTrain(false);

                    this.isOpen = true;


                    moveTrain(false);

                    atEnd = true;
                    train.reserveOrReleasePath(false);
                    this.hasTrain = false;
                    train.trainHasArrived = true;

                    this.station.getTrainFromTrack(train);

                    this.begin = false;



                }
                else if (this.next != null) {
                    operation(true);
                }
                else if(this.station != null && !this.station.equals(endStation) && !this.station.equals(train.startDest)){
                    //this.next = this.station.firstTrack();

                    this.next = this.station.firstTrack();
                    this.train.changeTrack(this.station.firstTrack());

                    System.out.println(next);
                    operation(false);
                }


//
//
//                    boolean moved = false;
//                    while (!moved) {
//                        if (this.next.isOpen && this.next.hasPriority(this.train.returnTrainNumber())) {
//                            System.out.println("Start dest = " + train.startDest);
//                            System.out.println("End dest = " + train.endDest);
//                            //this.station.hasArrived = false;
//                            hasTrain = true;
//                            moveTrain(true);
//                            this.isWaiting = false;
//
//                            synchronized (next) {
//                                this.next.begin = true;
//                                next.hasTrain = true;
//                                this.next.notify();
//                            }
//                            // next.hasTrain = true;
//                            moved = true;
//                        } else {
//                            this.isWaiting = true;
//                            //System.out.println("WAITING FOR OPEN");
//                        }
//                    }
//                }
            }
        }
    }

}





