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

    private final double X_LENGTH = 125;
    private final double Y_LENGTH = 75;

    private LinkedList<Light> lightArray = new LinkedList<>();
    private LinkedList<Circle> circleArray = new LinkedList<>();

    private Thread[][] map;
    private Shape[][] guiMap;
    private MainThread mainT;
    private LinkedList<Station> pickedStations = new LinkedList<>();
    //private final double DX = 37.5;
    //private final double DY = 225;
    private double DX;
    private double DY;
    private static int lightCount = 0;

    private double tempX = 0;
    private double tempY = 0;
    boolean starting = false;



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
                            train.newY = ((j + 1) * (X_LENGTH - Y_LENGTH));
                            train.addTrain();
                        }
                        else {

                            if (dir.equals("Down") && !track.isWaiting) {
                                train.moveTrainDown();
                            }
                            else if (dir.equals("Right") && !track.isWaiting) {
                                train.moveTrainRight(((j + 1) * Y_LENGTH));
                            }
                            else if (dir.equals("Left") && !track.isWaiting) {
                                if (starting) {
                                    train.newX = (i * X_LENGTH) + X_LENGTH;
                                    this.starting = false;
                                }

                                train.moveTrainLeft(((j + 1) * Y_LENGTH));

                            }
                            else if (dir.equals("Up") && !track.isWaiting) {
                                train.moveTrainUp();
                            }
                            else if (dir.equals("End")) break;
                        }
                    }
                }

                if (map[i][j] != null && map[i][j].getClass().getSimpleName().equals("Station")) {
                    train = ((Station) map[i][j]).returnTrain();
                    if (((Station) map[i][j]).hasArrived) {
                        guiMap[i][j].setFill(Color.AQUA);
                        if (train != null) train.removeDisplay();
                    }
                }
            }
        }
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

                        if(track.direction == 0) {
                            switchLine.setStartX(((i * X_LENGTH) + X_LENGTH + (i * DX)));
                            switchLine.setStartY(((j + 1) * Y_LENGTH) + Y_LENGTH);
                            switchLine.setEndX((i * X_LENGTH) + (i - 1) * DX);
                            switchLine.setEndY((j + 1) * Y_LENGTH);
                            pane.getChildren().add(switchLine);

                        }
                        else {

                            switchLine.setStartX(((i * X_LENGTH) - X_LENGTH + (i-2) * DX));
                            switchLine.setStartY(((j + 1) * Y_LENGTH) + Y_LENGTH);
                            switchLine.setEndX((i * X_LENGTH) + (i-1) * DX);
                            switchLine.setEndY((j + 1) * Y_LENGTH);
                            pane.getChildren().add(switchLine);
                        }

                        pane.getChildren().addAll(line);

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

