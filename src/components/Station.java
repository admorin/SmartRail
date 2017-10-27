package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Station extends Thread implements Component
{
    private boolean isStart;
    private String name;
    private boolean isLeft;
    private Track next;
    private Station endStation;
    private boolean returning = false;

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

    @Override
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
        next.getTrain(train);
        next.start();
    }

    public boolean isStation(){
        return true;
    }

    public String returnName()
    {
        return name;
    }

    public void getTrain(Train train){
        System.out.println("Station got train.");
    }
}
