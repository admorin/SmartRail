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
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.Random;

public class Train extends Thread
{
    public Station startDest;
    public Station endDest;
    Pane pane;

    private final int WIDTH = 9;
    private final int HEIGHT = 3;
    private final double T_LENGTH = 125;

    private double DX = T_LENGTH/(WIDTH-3);
    private double DY = (2*DX) + T_LENGTH;

    private double dx = (DX + T_LENGTH)/75;



    public String NAME;
    private ArrayList<String> directions = new ArrayList<>();
    private ArrayList<String> GUIdirections = new ArrayList<>();
    static private int trainNumber = 1;
    private int thisTrain;
    private String currentDirection;
    private String GUIdirection;
    public double newX = -1;
    public double newY = -1;
    public Circle trainDisplay = new Circle(10);
    public volatile boolean trainHasArrived = false;
    private Random rand;


    private int instruction = 0;
    public Track myTrack;
    public Track startTrack;

    public Train(Station startDest, Station endDest, Pane pane){
        this.myTrack = startDest.firstTrack();
        this.startTrack = this.myTrack;
        this.startDest = startDest;
        this.endDest = endDest;
        this.pane = pane;
        this.thisTrain = trainNumber;
        trainNumber++;
        searchAlgorithm(endDest);
        rand = new Random();

    }
    public synchronized void clearDirections(){
        directions.clear();
    }

    public void reserveOrReleasePath(boolean reserving){
        if(reserving)startTrack.trainOnWay(thisTrain);
        else startTrack.trainPassed(thisTrain);
        Track[] neighbors = startTrack.returnNeighbors();
        int step = 0;
        while(step < directions.size()){
            System.out.println(trainNumber);
            System.out.println(thisTrain);
            if(directions.get(step).equals("Up")){
                if(reserving)neighbors[0].trainOnWay(thisTrain);
                else neighbors[0].trainPassed(thisTrain);

                neighbors = neighbors[0].returnNeighbors();
            } else if(directions.get(step).equals("Right")){
                if(neighbors[1] != null) {
                    if (reserving) neighbors[1].trainOnWay(thisTrain);
                    else neighbors[1].trainPassed(thisTrain);

                    neighbors = neighbors[1].returnNeighbors();
                }
            } else if(directions.get(step).equals("Down")){
                if(reserving)neighbors[2].trainOnWay(thisTrain);
                else neighbors[2].trainPassed(thisTrain);

                neighbors = neighbors[2].returnNeighbors();
            } else {
                if(neighbors[3] != null) {
                    if (reserving) neighbors[3].trainOnWay(thisTrain);
                    else neighbors[3].trainPassed(thisTrain);

                    neighbors = neighbors[3].returnNeighbors();
                }
            }
            step++;
        }
    }

    public boolean turnAround(int directionStart, Track[] neighbors){
        boolean turned = false;
        int moves = 0;
        while(!turned){
            if(directionStart%2 == 0){

                if(neighbors[3] != null){
                    directions.add("Left");
                    if(neighbors[3].returnStation() != null && neighbors[3].returnStation().equals(endDest)){
//                        directions.add("Left");
                        return true;
                    }
                    neighbors = neighbors[3].returnNeighbors();
                    moves++;
                } else {
//                    directions.add("Left");
                    for(int i = 0; i < moves; i++){
                        directions.add("Right");
                        neighbors = neighbors[1].returnNeighbors();
                    }
                    turned = true;
                }
            } else {
                if(neighbors[1] != null){
                    directions.add("Right");
                    System.out.println(neighbors[1].returnStation());
                    if(neighbors[1].returnStation() != null && neighbors[1].returnStation().equals(endDest)){
//                        directions.add("Right");
                        return true;
                    }
                    neighbors = neighbors[1].returnNeighbors();
                    moves++;
                } else {
//                    directions.add("Right");
                    for(int i = 0; i < moves; i++){
                        directions.add("Left");
                        neighbors = neighbors[3].returnNeighbors();
                    }
                    turned = true;
                }
            }
        }
        return false;
    }

    public synchronized void searchAlgorithm(Station end){
        boolean endFound = false;
        Track[] neighbors = startTrack.returnNeighbors();
        Station trackStation = startTrack.returnStation();
        startTrack.setReserved(thisTrain);
        int direction = 0; //1 = Right, 2 = Left.
        //Returns Up, Right, Down, Left, Station.
        while(!endFound){
            if(trackStation != null && trackStation.equals(end)){
                endFound = true;
//                if(direction%2 == 0) directions.add("Left");
//                else directions.add("Right");
                reserveOrReleasePath(true);
                //UP------------------------------------------------------------------------
            } else if(neighbors[0] != null && neighbors[0].getReserved() != thisTrain){
                if(neighbors[0].returnDirection() != direction%2){
                    if(turnAround(direction, neighbors)){
                        reserveOrReleasePath(true);
                        break;
                    }
                    direction++;
                }
                directions.add("Up");
                neighbors[0].setReserved(thisTrain);
                if(neighbors[0].returnStation() != null) {
                    trackStation = neighbors[0].returnStation();
                }
                neighbors = neighbors[0].returnNeighbors();
                //RIGHT---------------------------------------------------------------------
            } else if(neighbors[1] != null && neighbors[1].getReserved() != thisTrain){
                if(direction == 0) direction++;
                if(direction%2 == 0 && direction != 0){
                    if(turnAround(direction, neighbors)){
                        reserveOrReleasePath(true);
                        break;
                    }
                    direction++;
                }
                directions.add("Right");
                neighbors[1].setReserved(thisTrain);
                if (neighbors[1].hasStation()) {
                    trackStation = neighbors[1].returnStation();
                }
                neighbors = neighbors[1].returnNeighbors();
                //DOWN----------------------------------------------------------------------
            } else if(neighbors[2] != null && neighbors[2].getReserved() != thisTrain){
                if(neighbors[2].returnDirection() != direction%2){
                    if(turnAround(direction, neighbors)){
                        reserveOrReleasePath(true);
                        break;
                    }
                    direction++;
                }
                directions.add("Down");
                neighbors[2].setReserved(thisTrain);
                if(neighbors[2].returnStation() != null) {
                    trackStation = neighbors[2].returnStation();
                }
                neighbors = neighbors[2].returnNeighbors();
                //LEFT----------------------------------------------------------------------
            } else if(neighbors[3] != null && neighbors[3].getReserved() != thisTrain){
                if(direction == 0) direction += 2;
                if(direction%2 == 1){
                    if(turnAround(direction, neighbors)){
                        reserveOrReleasePath(true);
                        break;
                    }
                    direction++;
                }
                directions.add("Left");
                neighbors[3].setReserved(thisTrain);
                if (neighbors[3].returnStation() != null) {
                    trackStation = neighbors[3].returnStation();
                }
                neighbors = neighbors[3].returnNeighbors();

            } else {
                String last = directions.get(directions.size()-1);
                if(last.equals("Up")){
                    neighbors = neighbors[2].returnNeighbors();
                } else if(last.equals("Right")){
                    if(neighbors[3] != null) {
                        neighbors = neighbors[3].returnNeighbors();
                    } else {
                        directions.remove(directions.size()-1);
                    }
                } else if(last.equals("Down")){
                    neighbors = neighbors[0].returnNeighbors();
                } else {
                    if(neighbors[1] != null) {
                        neighbors = neighbors[1].returnNeighbors();
                    } else {
                        directions.remove(directions.size()-1);
                    }
                }
                directions.remove(directions.size()-1);
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

    public synchronized String peekDirection(){
        instruction++;
        if(instruction > directions.size()){
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

    }

    public int returnTrainNumber(){
        return thisTrain;
    }


    public synchronized void addTrain(){

        this.trainDisplay.setFill(Color.rgb(rand.nextInt(255), rand.nextInt(255), rand.nextInt(255)));
        pane.getChildren().add(this.trainDisplay);
    }

    public synchronized void trainWait(double x, double y){
        this.newX = x;
        this.newY = y;
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);
    }

    public synchronized void moveTrainDown(){

        //this.newX += 2.5;
        this.newX += dx;
        this.newY += 1;
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);

    }
    public synchronized void moveTrainRight(double y){


        //this.newX += 2.5;
        this.newX += dx;
        this.newY = y;
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);

    }
    public synchronized void moveTrainLeft(double y){

        //this.newX -= 2.5;
        this.newX -= dx;
        this.newY = y;
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);

    }

    public synchronized void moveTrainUp(){

        //this.newX -= 2.5;
        this.newX += dx;
        this.newY -= 1;
        this.trainDisplay.setTranslateX(newX);
        this.trainDisplay.setTranslateY(newY);

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


