package components;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.layout.Pane;
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
    public String NAME;
    private ArrayList<String> directions = new ArrayList<>();
    static private int trainNumber = 1;

    public Rectangle train = new Rectangle(width, height);
    private int instruction = 0;
    public Track myTrack;

    public Train(Station start, Track myTrack, Station end, Pane pane, String name)
    {

        this.NAME = name;
        //this.pane = pane;
        this.myTrack = myTrack;
        this.startDest = start;
        this.endDest = end;


    }

    public Train(Station startDest, Station endDest, int dir, boolean isSearcher){
        this.myTrack = startDest.firstTrack();
        this.startDest = startDest;
        this.endDest = endDest;
        searchAlgorithm(endDest);
        trainNumber++;

    }

    public void turnAround(int directionStart, Track[] neighbors){
        boolean turned = false;
        boolean foundStation = false;
        int moves = 0;
        while(!turned){
            if(directionStart == 2){
                if(neighbors[3] != null){
                    directions.add("Left");
                    neighbors = neighbors[3].returnNeighbors();
                    moves++;
                } else {
                    for(int i = 0; i < moves; i++){
                        directions.add("Right");
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
                    }
                    turned = true;
                }
            }
        }
    }

    public void searchAlgorithm(Station end){
        System.out.println(endDest.returnName() + " " + startDest.returnName());
        boolean endFound = false;
        Track[] neighbors = myTrack.returnNeighbors();
        Station trackStation = myTrack.returnStation();
        myTrack.setReserved(trainNumber);
        int direction = 0; //1 = Right, 2 = Left.
        //Returns Up, Right, Down, Left, Station.
        while(!endFound){
            if(trackStation != null && trackStation.equals(end)){
                endFound = true;
            } else if(neighbors[0] != null && neighbors[0].getReserved() != trainNumber){
                directions.add("Up");
                neighbors[0].setReserved(trainNumber);
                if(neighbors[0].returnStation() != null) {
                    trackStation = neighbors[0].returnStation();
                }
                neighbors = neighbors[0].returnNeighbors();
            } else if(neighbors[1] != null && neighbors[1].getReserved() != trainNumber){
                if(direction == 0) direction++;
                if(direction == 2){
                    turnAround(2, neighbors);
                } else {
                    directions.add("Right");
                    neighbors[1].setReserved(trainNumber);
                    if (neighbors[1].hasStation()) {
                        trackStation = neighbors[1].returnStation();
                    }
                }
                neighbors = neighbors[1].returnNeighbors();
            } else if(neighbors[2] != null && neighbors[2].getReserved() != trainNumber){
                directions.add("Down");
                neighbors[2].setReserved(trainNumber);
                if(neighbors[2].returnStation() != null) {
                    trackStation = neighbors[2].returnStation();
                }
                neighbors = neighbors[2].returnNeighbors();
            } else if(neighbors[3] != null && neighbors[3].getReserved() != trainNumber){
                if(direction == 0) direction += 2;
                if(direction == 1){
                    turnAround(1, neighbors);
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
            for(String s : directions) System.out.println(s);
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

    public String peekDirection(){
        instruction++;
        if(instruction > directions.size()){
            return directions.get(1);
        }
        return directions.get(instruction - 1);
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
        while(!endDest.hasArrived) {


        }
    }

}

