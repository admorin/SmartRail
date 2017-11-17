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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Station extends Thread implements Component {

    public volatile boolean selected1 = false;
    public volatile boolean initStation = false;
    public volatile boolean hasArrived = false;
    private String name = "Blank";
    private Train train;
    private Track next;
    private Station endStation;
    private Pane pane;

    public Station() {

    }


    public Station(String name, Track firstTrack, Pane pane) {
        this.pane = pane;
        this.name = name;
        setName(name);
        this.next = firstTrack;
        this.hasArrived = false;

    }

    public synchronized Track firstTrack()
    {
        return next;
    }

    public synchronized void finishLine(Station endHere) {
        this.endStation = endHere;

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
                Train train = new Train(this, endStation, pane);
                synchronized (next) {
                    next.begin = true;
                    next.notify();
                    next.getTrain(train);
                    this.selected1 = false;

                }

            }

        }

    }

    public Rectangle getDisplayStation() {
        Rectangle station = new Rectangle(70, 40);
        station.setFill(Color.AQUA);
        return station;
    }

    public synchronized Train returnTrain(){
        return train;
    }

    public synchronized void getTrainFromTrack(Train train) {
        this.train = train;
        this.initStation = false;

        this.train.startDest.hasArrived = true;
        this.train.endDest.hasArrived = true;
        this.train.trainHasArrived = true;
        try{
            wait(100);
            train.myTrack.isRed = true;
            train.reset();
        }
        catch (Exception e){

        }
    }
}
