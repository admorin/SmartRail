package components;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import util.Logger;

import java.util.ArrayList;
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
    public volatile boolean isRunning = false;
    public String NAME;
    private ArrayList<String> directions = new ArrayList<>();
    static private int trainNumber = 1;
    private String currentDirection;
    public double newX = -1;
    public double newY = -1;
    public volatile boolean trainOver = false;
    public Circle trainDisplay = new Circle(10);
    public volatile boolean trainHasArrived = false;


    public Rectangle train = new Rectangle(width, height);
    private int instruction = 0;
    public Track myTrack;

    public Train(Station start, Track myTrack, Station end, Pane pane, String name)
    {

        this.NAME = name;
        this.pane = pane;
        this.myTrack = myTrack;
        this.startDest = start;
        this.endDest = end;


    }

    public Train(Station startDest, Station endDest, int dir, boolean isSearcher, Pane pane){
        this.myTrack = startDest.firstTrack();
        this.startDest = startDest;
        this.endDest = endDest;
        this.pane = pane;
        searchAlgorithm(endDest);
        trainNumber++;

    }
    public synchronized void clearDirections(){
        directions.clear();
    }

    public void turnAround(int directionStart, Track[] neighbors){
        boolean turned = false;
        boolean foundStation = false;
        int moves = 0;
        while(!turned){
            if(directionStart%2 == 0){
                if(neighbors[3] != null){
                    directions.add("Left");
                    neighbors = neighbors[3].returnNeighbors();
                    moves++;
                } else {
                    for(int i = 0; i < moves; i++){
                        directions.add("Right");
                        neighbors = neighbors[1].returnNeighbors();
                    }
                    turned = true;
                }
            } else {
                if(neighbors[1] != null){
                    directions.add("Right");
                    neighbors = neighbors[1].returnNeighbors();
                    moves++;
                } else {
                    for(int i = 0; i < moves; i++){
                        directions.add("Left");
                        neighbors = neighbors[3].returnNeighbors();
                    }
                    turned = true;
                }
            }
        }
    }

    public synchronized void searchAlgorithm(Station end){
        //System.out.println(endDest.returnName() + " " + startDest.returnName());
        boolean endFound = false;
        Track[] neighbors = myTrack.returnNeighbors();
        Station trackStation = myTrack.returnStation();
        myTrack.setReserved(trainNumber);
        int direction = 0; //1 = Right, 2 = Left.
        //Returns Up, Right, Down, Left, Station.
        while(!endFound){
            if(trackStation != null && trackStation.equals(end)){
                endFound = true;
                //UP------------------------------------------------------------------------
            } else if(neighbors[0] != null && neighbors[0].getReserved() != trainNumber){
                if(neighbors[0].returnDirection() != direction%2){
                    turnAround(direction, neighbors);
                    direction++;
                }
                directions.add("Up");
                neighbors[0].setReserved(trainNumber);
                if(neighbors[0].returnStation() != null) {
                    trackStation = neighbors[0].returnStation();
                }
                neighbors = neighbors[0].returnNeighbors();
                //RIGHT---------------------------------------------------------------------
            } else if(neighbors[1] != null && neighbors[1].getReserved() != trainNumber){
                if(direction == 0) direction++;
                if(direction%2 == 0 && direction != 0){
                    turnAround(direction, neighbors);
                    direction++;
                }
                directions.add("Right");
                neighbors[1].setReserved(trainNumber);
                if (neighbors[1].hasStation()) {
                    trackStation = neighbors[1].returnStation();
                }
                neighbors = neighbors[1].returnNeighbors();
                //DOWN----------------------------------------------------------------------
            } else if(neighbors[2] != null && neighbors[2].getReserved() != trainNumber){
                if(neighbors[2].returnDirection() != direction%2){
                    turnAround(direction, neighbors);
                    direction++;
                }
                directions.add("Down");
                neighbors[2].setReserved(trainNumber);
                if(neighbors[2].returnStation() != null) {
                    trackStation = neighbors[2].returnStation();
                }
                neighbors = neighbors[2].returnNeighbors();
                //LEFT----------------------------------------------------------------------
            } else if(neighbors[3] != null && neighbors[3].getReserved() != trainNumber){
                if(direction == 0) direction += 2;
                if(direction%2 == 1){
                    turnAround(direction, neighbors);
                    direction++;
                }
                directions.add("Left");
                neighbors[3].setReserved(trainNumber);
                if (neighbors[3].returnStation() != null) {
                    trackStation = neighbors[3].returnStation();
                }
                neighbors = neighbors[3].returnNeighbors();

            } else {
//                System.out.println("Popping");
//                System.out.println(neighbors[3]);
                String last = directions.get(directions.size()-1);
                directions.remove(directions.size()-1);
                if(last.equals("Up")){
                    neighbors = neighbors[2].returnNeighbors();
//                    trackStation = neighbors[2].returnStation();
                } else if(last.equals("Right")){
//                    System.out.println("Last was Right");
                    neighbors = neighbors[3].returnNeighbors();
//                    trackStation = neighbors[3].returnStation();
                } else if(last.equals("Down")){
                    neighbors = neighbors[0].returnNeighbors();
//                    trackStation = neighbors[0].returnStation();
                } else {
                    neighbors = neighbors[1].returnNeighbors();
//                    trackStation = neighbors[1].returnStation();
                }
            }

//            for(String s : directions) System.out.println(s);
        }
    }

    public Station getStartStation(){

        return startDest;
    }

    public Station getEndStation(){
        return endDest;
    }

    public Train(Station startDest, Station endDest){
        this.startDest = startDest;

        this.directions = directions;
    }

    public synchronized String peekDirection(){
        instruction++;
        if(instruction > directions.size()){
            //currentDirection = directions.get(1);
            currentDirection = "End";
        }
        else {
            currentDirection = directions.get(instruction - 1);
        }
        return currentDirection;
    }

    public String returnCurrentDirection(){
        return currentDirection;
    }

    public ArrayList<String> getDirections() {
        return directions;
    }



    public synchronized void changeTrack(Track newTrack){
        this.myTrack = null;
        this.myTrack = newTrack;

        System.out.println("My Track = " + myTrack);


    }


    public String currentTrack(){
        return myTrack.toString();

    }

    public synchronized void addTrain(){
        pane.getChildren().add(this.trainDisplay);
    }

    public void moveTrain(){
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);
    }
    public synchronized void moveTrainDown(){
        newX = newX + 2;
        newY = newY + 2;
        trainDisplay.setTranslateX(newX);
        trainDisplay.setTranslateY(newY);

    }
    public synchronized void moveTrainRight(double y){
        newX = newX + 2;
        newY = y;
        trainDisplay.setTranslateX(newX);
        trainDisplay.setTranslateY(newY);

    }
    public synchronized void moveTrainLeft(double y){
        newX = newX - 2;
        newY = y;
        trainDisplay.setTranslateX(newX);
        trainDisplay.setTranslateY(newY);
    }

    public synchronized void moveTrainUp(){
        newX = newX - 2;
        newY = newY - 2;
        trainDisplay.setTranslateX(newX);
        trainDisplay.setTranslateY(newY);

    }
    public synchronized void removeDisplay(){
        pane.getChildren().remove(trainDisplay);

    }

    public synchronized void reset(){
        this.endDest.hasArrived = false;
        this.startDest.hasArrived = false;
        this.trainHasArrived = false;
        this.clearDirections();
        this.interrupt();
    }


    public void run() {

        while(!isInterrupted()){

            try {
                System.out.println("Train is alive...");
                Thread.sleep(2000);

            } catch (Exception e) {

            }

        }
        System.out.println("TRAIN HAS DIED");
        currentThread().interrupt();
        System.out.println("Current State = " + getState());

    }

}

