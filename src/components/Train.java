package components;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import util.Logger;

import java.util.LinkedList;
import java.util.concurrent.CountDownLatch;

public class Train extends Thread
{
    Component curComponent;
    Station startDest;
    Station endDest;


    Pane pane;
    static private int width = 75;
    static private int height = 20;
    public volatile boolean allClear = true;
    public boolean finder;
    public String NAME;
    private LinkedList<String> directions = new LinkedList<>();
    private char direction;

    public Rectangle train = new Rectangle(width, height);
    private int secsPassed;
    public Track myTrack;

    public Train(Component startComp, Track myTrack, Component endComp, Pane pane, String name)
    {

        this.NAME = name;
        //this.pane = pane;
        this.myTrack = myTrack;
        startDest = (Station) startComp;
        endDest = (Station) endComp;


    }

    public void searchAlgorithim(Station start, Station end){
        Train temp = new Train(start, end, 1);

    }

    public Station getStartStation(){
        return startDest;
    }

    public Train(Station startDest, Station endDest, int dir){
        this.startDest = startDest;

        //TODO Search algorithm.

        if(dir == 1) {
            directions.add("Right");
            directions.add("Right");
            directions.add("Down");
            directions.add("Right");
            directions.add("Right");
            directions.add("Right");
        } else {
            directions.add("Left");
            directions.add("Left");
            directions.add("Left");
            directions.add("Left");
            directions.add("Left");
            directions.add("Left");
        }
    }

    public void popDirection(){
        directions.pop();
    }

    public String peekDirection(){
        return directions.poll();
    }

    public Rectangle returnTrain(){

        return train;
    }

    public void changeTrack(Track newTrack){
        this.myTrack = null;
        this.myTrack = newTrack;


    }

    public String currentTrack(){
        return myTrack.toString();

    }

    public void run(){
        while(!Thread.interrupted()){
            if(finder){
                Station startTemp = startDest;
                Station endTemp = endDest;

            }
        }
    }
//        int step = 0;
//        System.out.println("New route request : Train ID [0] from Station[" + startDest.getName() + "] to Station[B]");
//
//        try {
//            while (allClear) {
//                train.setTranslateX(step += train.getLayoutX());
//                step += 1000;
//                pane.getChildren().add(train);
//            }
//        }
//           catch(Exception e) {
//           }
//
//       }
    }

