package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Station extends Thread implements Component
{
    private boolean isStart;
    private String name = "Blank";
    private boolean isLeft;
    private Train train;
    public boolean hasArrived = false;
    private Track next;
    private Station endStation;
    private boolean startStation;
    private Rectangle station;
    private boolean returning = false;
    public Rectangle displayStation = new Rectangle(30, 60);

    public Station(){

    }

    public Station(String name, Track firstTrack)
    {
        this.name = name;
        setName(name);
        this.next = firstTrack;

    }

    public Track firstTrack(){
        return next;
    }

    public void finishLine(Station endHere){
        this.endStation = endHere;


    }

    public boolean isEnd(){
        return (endStation == null);
    }

    public void run()
    {
        Train train;
        train = new Train(this, endStation, 1, true);
        next.getTrain(train);
        next.start();
        train.start();
        while(train.isAlive()){

        }



    }

    public Rectangle getDisplayStation(){
        this.station = new Rectangle(30, 60);
        station.setOnMousePressed(event -> {
           System.out.println(this.getName());
        });

        return station;
    }

    public String returnName()
    {
        return name;
    }

    public void getTrain(Train train){
        this.train = train;
        System.out.println(name + " got train.");
        System.out.println(this.isAlive());
    }
}
