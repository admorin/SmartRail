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
    double newY = -1;
    Timeline timeline = new Timeline();
    public Train train;


    public DisplayGUI(Pane pane, MainThread mainT) {

        this.pane = pane;
        this.mainT = mainT;

        map = mainT.initialize();
        this.stations = mainT.stationList;
       // pane.getChildren().add(testTrain);
        initStation();
        initTracks();

    }

    public void animationTrack() {

    }

    public boolean trackShift(Station start){

        return (start.getName().equals("Station X") ||
                start.getName().equals("Station Y"));

    }



    public synchronized void trackListener(){
        Circle displayTrain = new Circle(10);
        boolean flag = false;
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

                            if(trackShift(train.startDest)) {
                                train.newX = (i * SIZEX );
                                train.newY = ((j + 1) * SIZEY);
                                train.addTrain();
                            }
                            else {
                                train.newX = (i * SIZEX);
                                train.newY = ((j + 1) * SIZEY);
                                train.addTrain();
                            }

                        } else {

                            if (dir.equals("Down") && !track.isWaiting) {

                                train.moveTrainDown();

                            } else if (dir.equals("Right") && !track.isWaiting) {

                                train.moveTrainRight(((j + 1) * SIZEY));


                            } else if (dir.equals("Left") && !track.isWaiting) {
                                train.moveTrainLeft(((j + 1) * SIZEY));

                            } else if (dir.equals("Up") && !track.isWaiting) {

                                train.moveTrainUp();

                            } else if (dir.equals("End")) {
                                train.newX = -1;
                                train.newY = -1;
                                System.out.println("DSFSDF");
                                train.removeDisplay();

                            }

                        }
                    }

                }
              if (map[i][j].getClass().getSimpleName().equals("Station")) {
                   train = ((Station) map[i][j]).returnTrain();

                    if (((Station) map[i][j]).hasArrived ) {
                        guiMap2[i][j].setFill(Color.AQUA);
                        //System.out.println((Station) map[i][j]);
                        if(train != null) {
                            train.removeDisplay();
                        }
                    }
                }
            }
        }

    }

    public void trackAnimation() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch){
                        
                    }
                }
            }
        }
    }




    public void initTracks() {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch) {
                        Line line = new Line();
                        Line switchLine = new Line();

                        line.setStartX(i*SIZEX);
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX) + SIZEX);
                        line.setEndY((j+1) * SIZEY);


                        switchLine.setStartX((i*SIZEX));
                        switchLine.setStartY((j+1) * SIZEY);
                        switchLine.setEndX(((i*SIZEX)+150));
                        switchLine.setEndY(((j+1) * SIZEY)+150);

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

                    //t.getDisplayStation().setTranslateY((j + 1) * SIZEY);
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
