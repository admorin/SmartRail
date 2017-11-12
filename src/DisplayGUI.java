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

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class DisplayGUI extends AnimationTimer {
    private Pane pane;
    private final int WIDTH = 7;
    private final int HEIGHT = 2;
    private final int SIZEX = 150;
    private final int SIZEY = 150;
    private final int trackLen = 100;
    public boolean hasArrived = false;
    private Thread[][] map = new Thread[WIDTH][HEIGHT];
    private Shape[][] guiMap2 = new Shape[WIDTH][HEIGHT];
    private PathTransition[][] guiMap = new PathTransition[WIDTH][HEIGHT];
    private Rectangle train = new Rectangle(trackLen, 20);
    private Rectangle station = new Rectangle(30, 40);
    private Circle light = new Circle(5);
    private MainThread mainT;
    private boolean runDisplay = false;
    private Circle testTrain = new Circle(10);
    //private ArrayList<Station> stations = new ArrayList<>();
    private LinkedList<Station> pickedStations = new LinkedList<>();
    private LinkedList<Station> stations = new LinkedList<>();
    double newX = -1;
    double newY = -1;
    Timeline timeline = new Timeline();


    public DisplayGUI(Pane pane, MainThread mainT) {

        this.pane = pane;
        this.mainT = mainT;

        map = mainT.initialize();
        this.stations = mainT.stationList;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                //guiMap[i][j] = new Rectangle(trackLen, 2);
                //guiMap[i][j] = new Path()
            }
        }


        pane.getChildren().add(testTrain);
        initStation();
        initTracks();

    }

    public void animationTrack() {

    }



    public synchronized void trackListener(){
        Circle displayTrain = new Circle(10);
        boolean flag = false;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).hasTrain) {
                        Train train = ((Track) map[i][j]).train;
                        displayTrain = train.returnTrain();
                        String dir = train.returnCurrentDirection();
                        if(train.isInterrupted()){
                            newX = -1;
                            newY = -1;
                        }
                        else if (train.newX == -1 && train.newY == -1) {
                            train.newX = (i * SIZEX+150);
                            train.newY = ((j+1) * SIZEY);
                            pane.getChildren().add(train.trainDisplay);


                        }
                        else {
                            if (dir.equals("Down")) {
                                //newX = newX + 2;
                                // = newY + 2;
                                train.newX = train.newX + 2;
                                train.newY = train.newY + 2;
                                train.moveTrain();

                            }

                            else if (dir.equals("Right")) {
                                train.newX = train.newX + 2;
                                train.newY = ((j + 1) * SIZEY);
                                train.moveTrain();


                            }

                            else if (dir.equals("Left")) {
                                train.newX = train.newX - 2;
                                train.newY = ((j+1) * SIZEY);
                                train.moveTrain();

                            }

                            else if (dir.equals("Up")) {
                                train.newX = train.newX - 2;
                                train.newY = train.newY - 2;
                                train.moveTrain();

                            }

                            else if(dir.equals("End")){
                                newX = -1;
                                newY = -1;
                                break;
                            }
                        }
                    }
                }
            }
        }


            //pane.getChildren().remove(testTrain);


    }



    public void initTracks() {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch) {
                        Line line = new Line();
                        Line switchLine = new Line();

                        Circle light = new Circle(10);

                        switchLine.setStartX(i*SIZEX);
                        switchLine.setStartY((j+1) * SIZEY);
                        switchLine.setEndX((i*SIZEX) + SIZEX);
                        switchLine.setEndY((j+1) * SIZEY);

                        line.setStartX((i*SIZEX+150));
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX+150)+150);
                        line.setEndY((j+1) * SIZEY+150);

                        pane.getChildren().addAll(line, switchLine);

                    }

                    else if (((Track) map[i][j]).isOpen) {
                        Line line = new Line();
                        line.setStartX(i*SIZEX);
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX) + SIZEX);
                        line.setEndY((j+1) * SIZEY);



                        pane.getChildren().add(line);


//                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
//                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
//                        guiMap[i][j].setFill(Color.BLACK);

                    }
                    else {


                    }
                }
            }
        }
    }

    public void trainAnimation(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(map[i][j].getClass().getSimpleName().equals("Track")){
                    if(((Track)map[i][j]).hasTrain){
                        guiMap[i][j].play();

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

    public void stationListener(){
        boolean flag = false;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Station")) {


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
                    rect.setTranslateX((i * SIZEX) + 50);
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

                            //pickedStations.getFirst();

                            //newX = pickedStations.getFirst().getDisplayStation().getTranslateX();
                            System.out.println(newX);
                            //pathGuiTest(pickedStations.getFirst(), pickedStations.getLast());
                            pickedStations.clear();

                        }

                    });
                    rect.setOnMouseReleased(event ->{

                        //System.out.println(event.toString());
                        if(pickedStations.size() == 2){
                            rect.setFill(Color.AQUA);

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
        stationListener();
       //trackListenerBeta();


    }

    private void redraw() {
        //pane.getChildren().remove(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
               // pane.getChildren().remove(guiMap2[i][j]);
            }
        }

        //pane.getChildren().add(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                //pane.getChildren().add(guiMap2[i][j]);
            }
        }
    }
}
