package components;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Station extends Thread implements Component {
    private boolean isStart;
    private String name = "Blank";
    private boolean isLeft;

    public Train train;
    public volatile boolean hasArrived = false;
    public Track next;
    private Station endStation;
    private boolean startStation;
    public Rectangle station;
    public Object lock = new Object();
    public boolean isStarting = false;
    public boolean hasTrain = false;
    public volatile boolean selected1 = false;
    public volatile boolean selected2 = false;
    public volatile boolean trainArrival = false;
    public volatile boolean initStation = false;
    public Rectangle displayStation = new Rectangle(30, 60);
    public Pane pane;

    public Station() {

    }


    public Station(String name, Track firstTrack, Pane pane) {
        this.pane = pane;
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

    public void run() {
        while (isAlive()) {
            synchronized (this){
                try{
                    while(!initStation){

                        wait();
                    }
                }
                catch(Exception e){

                }
            }

            if(this.selected1){
                Train train = new Train(this, endStation, 1, true, pane);
                synchronized (next) {
                    //trainStarter(train);

                    next.begin = true;
                    //train.start();
                    next.notify();
                    next.getTrain(train);


                    this.selected1 = false;

                }

            }

        }

    }
    public synchronized void trainStarter(Train train){
        synchronized (train){
            train.start();
        }
    }

    public Rectangle getDisplayStation() {
        Rectangle station = new Rectangle(100, 60);

        station.setFill(Color.AQUA);


        station.setOnMousePressed(event -> {
            System.out.print("event " + event.toString());
        });


        return station;
    }

    public String returnName() {
        return name;
    }

    public synchronized Train returnTrain(){
        return train;
    }

    public synchronized void getTrainFromTrack(Train train) {
        this.train = train;
        this.initStation = false;

        System.out.println(name + " got train.");

        this.train.startDest.hasArrived = true;
        this.train.endDest.hasArrived = true;
        this.train.trainHasArrived = true;
        this.train.interrupt();
        try{
            wait(100);

            train.reset();

        }
        catch (Exception e){

        }

        System.out.println("Train State = " + train.getState());

    }

}
