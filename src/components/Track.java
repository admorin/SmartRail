/**
 * Alex Schmidt
 *
 * Tyson Craner
 * Andrew Morin
 *
 * Train System
 * Project 3
 * 11/15/2017
 */

package components;
import java.util.PriorityQueue;
import java.util.Queue;

public class Track extends Thread implements Component {

    public boolean hasTrain = false;
    public boolean isSwitchD = false;
    public boolean isSwitchU = false;
    public int direction = -1;
    public boolean isOpen = true;
    public volatile boolean begin = false;
    public boolean isRed = true;
    public volatile boolean isWaiting = false;

    public Train train;
    private Track next;
    private Track nextR;
    private Track nextD;
    private Track nextU;
    private Track nextL;
    private Station station;
    private Station startStation;
    private int isReserved = 0;
    private Queue<Integer> trainOrder = new PriorityQueue<>();
    private int lightId;
    private Station endStation;
    private String NAME;

    public Track() {

    }

    public void setTrack(Track nextR, Track nextL, String name) {
        this.NAME = name;
        setName(name);
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
        this.isSwitchD = true;
        this.NAME = name;
        setName(name);
    }

    public void setSwitchTrackU(Track nextR, Track nextL, Track nextU, int direction, String name) {
        this.direction = direction;
        this.nextU = nextU;
        this.nextL = nextL;
        this.nextR = nextR;
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

    public void setLightId (int lightId){
        this.lightId = lightId;
    }

    public boolean isRed()
    {
        return isRed;
    }

    public void setRed(boolean red)
    {
        if (red)
            isRed = true;
        else
            isRed = false;
    }

    public int getLightId()
    {
        return lightId;
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


    private synchronized void moveTrain() {

        try {

            train.changeTrack(this);
            isOpen = true;
            Thread.sleep(1250);
            next.getTrain(train);
            this.hasTrain = true;

        }
        catch (Exception e) {

        }
    }

    public void trainOnWay(int trainNumber){
        trainOrder.add(trainNumber);
    }

    public void trainPassed(int trainNumber){
        trainOrder.remove(trainNumber);
    }
    public synchronized void getTrain(Train train) {
        this.train = train;
        this.startStation = train.getStartStation();
        this.endStation = train.getEndStation();

    }

    public int peekQ(){
        return trainOrder.peek();
    }
    private synchronized void findNext() {

        String direction = train.peekDirection();

        if (direction != null) {
            if (direction.equals("Right")) {
                next = nextR;
            }
            else if (direction.equals("Down")) {
                next = nextD;

            }
            else if (direction.equals("Up")) {
                next = nextU;

            }
            else if (direction.equals("Left")) {
                next = nextL;

            }
            begin = false;
        }
    }

    private boolean hasPriority(int trainNumber){
        if(trainOrder.peek() == trainNumber){
            return true;
        } else {
            return false;
        }
    }

    private synchronized void operation(){
        boolean moved = false;
        while (!moved) {
            if (this.next.isOpen &&
                    this.next.hasPriority(this.train.returnTrainNumber())) {
                this.hasTrain = true;

                moveTrain();
                this.isWaiting = false;

                synchronized (next) {
                    this.next.begin = true;
                    this.next.hasTrain = true;
                    this.next.notify();
                }
                moved = true;
            } else {
                if(next.isSwitchD || next.isSwitchU){
                    next.setRed(true);
                }
                this.isWaiting = true;
            }
        }
    }


    public void run() {
        while (isAlive()) {
            synchronized (this) {
                try {
                    while (!begin) {
                        isOpen = true;
                        hasTrain = false;
                        this.isRed = true;
                        this.wait();

                    }

                }
                catch (InterruptedException e) {

                }

                if((this.isSwitchU || this.isSwitchD) &&
                        (nextR != null && nextL != null)){

                    this.setRed(false);
                }
                findNext();
                if (this.station != null && this.station.equals(endStation)) {

                    this.isOpen = true;
                    moveTrain();
                    train.reserveOrReleasePath(false);
                    this.hasTrain = false;
                    train.trainHasArrived = true;
                    this.station.getTrainFromTrack(train);
                    this.begin = false;

                }
                else if (this.next != null) {
                    operation();
                }
                else if(this.station != null &&
                        !this.station.equals(endStation) &&
                        !this.station.equals(train.startDest)){

                    this.next = this.station.firstTrack();
                    this.train.changeTrack(this.station.firstTrack());

                    System.out.println(next);
                    operation();
                }
            }
        }
    }
}
