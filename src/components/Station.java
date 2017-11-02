package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Station extends Thread implements Component
{
    private boolean isStart;
    private String name;
    private boolean isLeft;
    private Train train;
    private Track next;
    private Station endStation;
    private boolean returning = false;
    public Rectangle displayStation = new Rectangle(30, 60);

    public Station(){

    }

    public Station(String name, Track firstTrack)
    {
        this.name = name;
        this.next = firstTrack;
    }

    public void finishLine(Station endHere){
        this.endStation = endHere;
        //TODO run a search algorithm to find a path to the destination.
        //TODO somehow store the directions. (Queue?)
    }

    public boolean isEnd(){
        if(endStation == null){
            return false;
        } else {
            return true;
        }
    }

    public void run()
    {
        Train train;
        if(returning == false) {
            train = new Train(this, endStation, 1);
        }
        else
        {
            train = new Train(this, endStation, 2);
        }
        //TODO add a timer so it can't be spam clicked
        next.start();
        next.getTrain(train);
    }

    public boolean isStation(){
        return true;
    }

    public String returnName()
    {
        return name;
    }

    public void getTrain(Train train){
        this.train = train;
        System.out.println(name + " got train.");
    }
}
