package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Station extends Thread implements Component {
    private boolean isStart;
    private String name = "Blank";
    private boolean isLeft;
    private Train train;
    public boolean hasArrived = false;
    public Track next;
    private Station endStation;
    private boolean startStation;
    private Rectangle station;
    public Object lock = new Object();
    public boolean isStarting = false;
    public volatile boolean selected1 = false;
    public volatile boolean selected2 = false;
    private boolean returning = false;
    public Rectangle displayStation = new Rectangle(30, 60);

    public Station() {

    }

    public Station(String name, Track firstTrack) {
        this.name = name;
        setName(name);
        this.next = firstTrack;
        this.hasArrived = false;

    }

    public synchronized Track firstTrack() {
        return next;
    }

    public synchronized void finishLine(Station endHere) {
        this.endStation = endHere;


    }

    public boolean isEnd() {
        return (endStation == null);
    }

    public void begin() {
        Train train;
        train = new Train(this, endStation, 1, true);
        next.getTrain(train);
        next.start();
        train.start();
    }

    public void run() {
        while (Thread.currentThread().isAlive()) {

            if(this.selected1){
                Train train = new Train(this, endStation, 1, true);
                next.getTrain(train);
                train.start();
                next.begin = true;
                synchronized (next) {
                    next.notify();
                    this.selected1 = false;

                }

            }

        }

    }

    public Rectangle getDisplayStation() {
        this.station = new Rectangle(100, 60);
        station.setFill(Color.AQUA);


        station.setOnMousePressed(event -> {
            //System.out.println(this.getName());
        });

        return station;
    }

    public String returnName() {
        return name;
    }

    public synchronized void getTrain(Train train) {
        this.train = train;
        System.out.println(name + " got train.");
        train.myTrack.hasTrain = false;
        this.hasArrived = true;

        System.out.println(this.isAlive());
    }
}
