/**
 * Alex Schmidt
 * Tyson Craner
 * Andrew Morin
 *
 * Train System
 * Project 3
 * 11/15/2017
 */

import components.*;
import javafx.animation.*;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SimpleTimeZone;


public class DisplayGUI extends AnimationTimer {
    private Pane pane;
    private final int WIDTH;
    private final int HEIGHT;
    private final int SIZEX = 150;
    private final int SIZEY = 150;

    private final double X_LENGTH = 125;
    private final double Y_LENGTH = 75;

    private LinkedList<Light> lightArray = new LinkedList<>();
    private LinkedList<Circle> circleArray = new LinkedList<>();

    private Thread[][] map;
    private Shape[][] guiMap;
    private MainThread mainT;
    private LinkedList<Station> pickedStations = new LinkedList<>();
    private LinkedList<Station> stations = new LinkedList<>();
    //private final double DX = 37.5;
    //private final double DY = 225;
    private double DX;
    private double DY;
    private static int lightCount = 0;

    private double tempX = 0;
    private double tempY = 0;
    boolean starting = false;
    boolean c = false;
    private double M = 0;


    DisplayGUI(Pane pane, MainThread mainT) {

        this.pane = pane;
        this.mainT = mainT;

        WIDTH = mainT.LENGTH;
        HEIGHT = mainT.WIDTH;

        DX = X_LENGTH / (WIDTH - 3);
        DY = (2 * DX) + X_LENGTH;

        map = new Thread[WIDTH][HEIGHT];
        guiMap = new Shape[WIDTH][HEIGHT];

        map = mainT.initialize();
        this.stations = mainT.stationList;
        initTracks();
        initStation();
        initLights();

    }

    public boolean trackShift(Station start) {

        return (start.getName().equals("Station X") ||
                start.getName().equals("Station Y"));

    }

    private boolean engageTrackSwitchUp(Train train) {
        ArrayList<String> temp = train.getDirections();
        //LinkedList<String> temp = train.getDirections();
        if (temp.contains("Up")) return true;
        return false;
    }


//Finished Method

    public synchronized void trackListener() {
        Train train;
        Track track;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j] != null && (map[i][j].getClass().getSimpleName().equals("Track")
                        || map[i][j].getClass().getSimpleName().equals("Light"))) {
                    if (((Track) map[i][j]).hasTrain) {

                        train = ((Track) map[i][j]).train;
                        track = (Track) map[i][j];
                        String dir = train.returnCurrentDirection();

                        if (train.newX == -1 && train.newY == -1) {

                            starting = true;
                            train.newX = ((i * X_LENGTH));
                            train.newY = ((j + 1) * (X_LENGTH -Y_LENGTH));
                            c = engageTrackSwitchUp(train);
                            train.addTrain();
                        }
                        else {
                            //engageTrackSwitchUp(train);
                            if (dir.equals("Down") && !track.isWaiting) {
                                train.moveTrainDown();
                            } else if (dir.equals("Right") && !track.isWaiting) {
                                train.moveTrainRight(((j + 1) * Y_LENGTH));
                            } else if (dir.equals("Left") && !track.isWaiting) {
                                engageTrackSwitchUp(train);
                                if (starting) {
                                    train.newX = (i * X_LENGTH) + X_LENGTH;
                                    this.starting = false;
                                }
                                train.moveTrainLeft(((j + 1) * Y_LENGTH));
                            } else if (dir.equals("Up") && !track.isWaiting) {
                                train.moveTrainUp();
                            } else if (dir.equals("End")) {

                                //System.out.println("GUI train finished...");

                                break;
                            }
                        }
                    }
//                    else if(((Track)map[i][j]).isSwitch){
//                        if(c) {
//                            Track t = (Track) map[i][j];
//                            trackAnimation(t);
//                        }
//                    }

                }


                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Station")) {
                    train = ((Station) map[i][j]).returnTrain();
                    if (((Station) map[i][j]).hasArrived) {
                        guiMap[i][j].setFill(Color.AQUA);
                        if (train != null) {
                            train.removeDisplay();
                        }
                    }
                }
            }
        }

    }

    public void trackAnimation(Track track) {
        //Track track;

        Line switchLine = new Line();
        switchLine.setStartX(track.startX);
        switchLine.setStartY(track.startY);
        while ((tempY > track.switchY && tempX < track.switchX) || (tempY > track.startY && tempX < track.startX)) {
            //track.switchX += DX;
            switchLine.setEndX(track.switchX + tempX);
            switchLine.setEndY(track.switchY + tempY);
            tempY += 2;
            tempX += 2;
            //System.out.print(track.switchX += 2);
            pane.getChildren().remove(switchLine);

            //}
        }
        pane.getChildren().add(switchLine);
    }


    public void initTracks() {

        Track track;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitchD){
                        track = (Track) map[i][j];
                        Line line = new Line();
                        Line switchLine = new Line();

                        line.setStartX(i * X_LENGTH);
                        line.setStartY((j + 1) * Y_LENGTH);
                        line.setEndX((i * X_LENGTH) + X_LENGTH);
                        line.setEndY((j + 1) * Y_LENGTH);

                        if(track.getDirection == 0) {
                            switchLine.setStartX(((i * X_LENGTH) + X_LENGTH + (i * DX)));
                            switchLine.setStartY(((j + 1) * Y_LENGTH) + Y_LENGTH);
                            switchLine.setEndX((i * X_LENGTH) + (i - 1) * DX);
                            switchLine.setEndY((j + 1) * Y_LENGTH);

                        }
                        else {

                            switchLine.setStartX(((i * X_LENGTH) + X_LENGTH + (i * DX)));
                            switchLine.setStartY(((j + 1) * Y_LENGTH) + Y_LENGTH);
                            switchLine.setEndX((i * X_LENGTH) + (i - 1) * DX);
                            switchLine.setEndY((j + 1) * Y_LENGTH);
                        }




                        pane.getChildren().addAll(line, switchLine);

                    }

                    else if(((Track) map[i][j]).isSwitchU){

                    }

                    else if (((Track) map[i][j]).isOpen) {
                        Line line = new Line();
                        line.setStartX(i * X_LENGTH);
                        line.setStartY((j + 1) * Y_LENGTH);
                        line.setEndX((i * X_LENGTH) + X_LENGTH);
                        line.setEndY((j + 1) * Y_LENGTH);
                        pane.getChildren().add(line);

                    }
                }
            }
        }
    }


    public void initLights() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Track")) {
                    Track temp = (Track)map[i][j];
                    if(temp.isSwitchD || temp.isSwitchU) {
                        int lightHeightOffset = 50;
                        Line line = new Line();

                        line.setStartX(i * X_LENGTH + (X_LENGTH/2));
                        line.setStartY((j + 1) * Y_LENGTH);
                        line.setEndX((i * X_LENGTH) + X_LENGTH);
                        line.setEndY((j + 1) * Y_LENGTH);

                        temp.setLightId(lightCount);
                        lightCount++;

                        Circle light = new Circle(12);
                        Line lightStick = new Line();
                        light.setCenterX((line.getStartX() + line.getEndX()) / 2);
                        light.setCenterY(((line.getStartY() + line.getEndY()) / 2) - lightHeightOffset);
                        lightStick.setStartX(light.getCenterX());
                        lightStick.setStartY(light.getCenterY());
                        lightStick.setEndX(light.getCenterX());
                        lightStick.setEndY(light.getCenterY() + lightHeightOffset);
                        circleArray.add(light);
                        pane.getChildren().add(line);
                        pane.getChildren().add(lightStick);
                        pane.getChildren().add(light);
                    }
                }
            }
        }
    }


    public void initStation() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Rectangle rect;
                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Station")) {

                    Station t = (Station) map[i][j];
                    Text text = new Text(t.getName());

                    rect = t.getDisplayStation();
                    rect.setTranslateX((i * X_LENGTH) + 25);
                    rect.setTranslateY(((j + 1) * Y_LENGTH));
                    rect.setFill(Color.AQUA);

                    text.setTranslateX(rect.getTranslateX());
                    text.setTranslateY(rect.getTranslateY());
                    text.setFont(Font.font(20));

                    pane.getChildren().add(text);

                    rect.setOnMousePressed(event -> {

                        rect.setFill(Color.RED);
                        pickedStations.add(t);

                        if (pickedStations.size() == 2) {
                            mainT.setStartStation(pickedStations);
                            pickedStations.clear();
                        }
                    });

                    guiMap[i][j] = rect;
                    pane.getChildren().add(guiMap[i][j]);

                }
            }
        }
    }


    public void lightListener() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Track")) {
                    Track temp = (Track) map[i][j];
                    if (temp.isSwitchU || temp.isSwitchD) {
                        if (temp.isRed())
                            circleArray.get(temp.getLightId()).setFill(Color.RED);
                        else
                            circleArray.get(temp.getLightId()).setFill(Color.GREEN);
                    }
                }
            }
        }
    }

    public void handle(long currentNanoTime) {

        trackListener();
        lightListener();
    }

}

