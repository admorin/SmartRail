/**
 * Alex Schmidt
 *
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.SimpleTimeZone;

public class DisplayGUI extends AnimationTimer {
    private Pane pane;
    private final int WIDTH = 7;
    private final int HEIGHT = 3;
    private final int SIZEX = 150;
    private final int SIZEY = 150;
    private final int trackLen = 100;
    public boolean hasArrived = false;
    private Thread[][] map = new Thread[WIDTH][HEIGHT];
    private Shape[][] guiMap2 = new Shape[WIDTH][HEIGHT];
    private PathTransition[][] guiMap = new PathTransition[WIDTH][HEIGHT];
    //private Rectangle train = new Rectangle(trackLen, 20);
    private Rectangle station = new Rectangle(30, 40);
    private Circle light = new Circle(5);
    private MainThread mainT;
    private boolean runDisplay = false;
    private Circle testTrain = new Circle(10);
    //private ArrayList<Station> stations = new ArrayList<>();
    private LinkedList<Station> pickedStations = new LinkedList<>();
    private LinkedList<Station> stations = new LinkedList<>();
    double newX = -1;
    private final double DX = 37.5;
    private final double DY = 225;
    boolean starting = false;
    Timeline timeline = new Timeline();
    public Train train;



    public DisplayGUI(Pane pane, MainThread mainT) {

        this.pane = pane;
        this.mainT = mainT;

        map = mainT.initialize();
        this.stations = mainT.stationList;
        initTracks();
        initStation();

    }

    public boolean trackShift(Station start){

        return (start.getName().equals("Station X") ||
                start.getName().equals("Station Y"));

    }

    public void engageTrackSwitchUp(Train train){
        ArrayList<String> temp = train.getDirections();
        //LinkedList<String> temp = train.getDirections();
        if(temp.contains("Up")) trackAnimation();
    }



//Finished Method

    public synchronized void trackListener(){
        Train train;
        Track track;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).hasTrain) {

                        train = ((Track) map[i][j]).train;
                        track = (Track) map[i][j];
                        String dir = train.returnCurrentDirection();

                        if (train.newX == -1 && train.newY == -1) {

                            starting = true;
                            train.newX = (i * SIZEX);
                            train.newY = ((j + 1) * SIZEY);
                            train.addTrain();

                        }
                        else {
                            if (dir.equals("Down") && !track.isWaiting){
                                train.moveTrainDown();
                            }
                            else if (dir.equals("Right") && !track.isWaiting) {
                                train.moveTrainRight(((j + 1) * SIZEY));
                            }
                            else if (dir.equals("Left") && !track.isWaiting) {
                                if(starting){
                                    train.newX = i*SIZEX+150;
                                    starting = false;
                                 }
                                train.moveTrainLeft(((j + 1) * SIZEY));
                            }
                            else if (dir.equals("Up") && !track.isWaiting) {
                                train.moveTrainUp();
                            }
                            else if (dir.equals("End")) {
                                System.out.println("GUI train finished...");
                                break;
                            }
                        }
                    }
                }

                if (map[i][j].getClass().getSimpleName().equals("Station")) {
                    train = ((Station) map[i][j]).returnTrain();
                    if (((Station) map[i][j]).hasArrived ) {
                        guiMap2[i][j].setFill(Color.AQUA);
                        if(train != null) {
                            train.removeDisplay();
                        }
                    }
                }
            }
        }

    }

    public void trackAnimation() {
        Track track;

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch){
                        track = (Track) map[i][j];

                        Line switchLine = new Line();
                        if(track.switchX < (i*SIZEX) +300) {
                            switchLine.setStartX(track.startX);
                            switchLine.setStartY((j + 1) * SIZEY);
                            switchLine.setEndX(track.switchX);
                            switchLine.setEndY(((j + 1) * SIZEY) + 150);
                            System.out.print(track.switchX += 2);

//                        pane.getChildren().remove(switchLine);
                            pane.getChildren().add(switchLine);
                        }
                    }

                }
            }
        }
    }




    public void initTracks() {

        Track track;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch) {
                        track = (Track) map[i][j];
                        Line line = new Line();
                        Line switchLine = new Line();

                        line.setStartX(i*SIZEX);
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX) + SIZEX);
                        line.setEndY((j+1) * SIZEY);



                        switchLine.setStartX((i*SIZEX)+DX);
                        switchLine.setStartY((j+1) * SIZEY);
                        switchLine.setEndX(((i*SIZEX)+DY));
                        switchLine.setEndY(((j+1) * SIZEY)+SIZEY);

                        track.switchX = (i*SIZEX)+150;
                        track.startX = (i*SIZEX);



                        pane.getChildren().addAll(line, switchLine);

                    }

                    else if (((Track) map[i][j]).isOpen) {
                        Line line = new Line();
                        line.setStartX(i*SIZEX);
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX) + SIZEX);
                        line.setEndY((j+1) * SIZEY);
                        pane.getChildren().add(line);

                    }
                }
            }
        }
    }


    public void lights() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    Circle light = new Circle(10);
                    Line line = new Line();
                    if (((Track) map[i][j]).isLight) {

                        line.setStartX((i + 1) * 70);
                        line.setStartY((j + 1) * 30);
                        line.setEndX((i + 1) * 70);
                        line.setEndY(i * 30);
                        light.setTranslateX((i + 1) * 70);
                        light.setTranslateY((j + 1) * 30);
                        pane.getChildren().addAll(light, line);
                        if (!((Track) map[i][j]).nextR.isOpen) {
                            light.setFill(Color.RED);
                        } else light.setFill(Color.GREEN);
                    }
                }
            }
        }
    }



    public void initStation() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Rectangle rect;
                if (map[i][j].getClass().getSimpleName().equals("Station")) {

                    Station t = (Station) map[i][j];
                    Text text = new Text(t.getName());

                    rect = t.getDisplayStation();
                    rect.setTranslateX((i* SIZEX)+25);
                    rect.setTranslateY((j + 1) * SIZEY);
                    rect.setFill(Color.AQUA);

                    text.setTranslateX(rect.getTranslateX());
                    text.setTranslateY(rect.getTranslateY());
                    text.setFont(Font.font(20));

                    pane.getChildren().add(text);

                    rect.setOnMousePressed(event -> {

                        rect.setFill(Color.RED);
                        pickedStations.add(t);

                        if(pickedStations.size() == 2) {
                            mainT.setStartStation(pickedStations);
                            pickedStations.clear();
                        }
                    });

                    guiMap2[i][j] = rect;
                    pane.getChildren().add(guiMap2[i][j]);

                }
            }
        }
    }



    public void handle(long currentNanoTime) {

        trackListener();


    }

}
