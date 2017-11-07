import components.Component;
import components.MainThread;
import components.Station;
import components.Track;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class DisplayGUI extends AnimationTimer {
    private Pane pane;
    private final int WIDTH = 7;
    private final int HEIGHT = 2;
    private final int SIZEX = 150;
    private final int SIZEY = 100;
    private final int trackLen = 100;
    public boolean hasArrived = false;
    private Thread[][] map = new Thread[WIDTH][HEIGHT];
    private Shape[][] guiMap = new Shape[WIDTH][HEIGHT];
    private Rectangle train = new Rectangle(trackLen, 10);
    private Rectangle station = new Rectangle(30, 40);
    private Circle light = new Circle(5);
    private MainThread mainT;
    //private ArrayList<Station> stations = new ArrayList<>();
    private LinkedList<Station> pickedStations = new LinkedList<>();
    private LinkedList<Station> stations = new LinkedList<>();


    public DisplayGUI(Pane pane, MainThread mainT) {

        this.pane = pane;
        this.mainT = mainT;

        map = mainT.initialize();
        this.stations = mainT.stationList;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                guiMap[i][j] = new Rectangle(trackLen, 2);
                //guiMap[i][j] = new Path()
            }
        }
        train.setVisible(false);
        pane.getChildren().add(train);
        stationListener();
        initTracks();
    }


    public void initTracks() {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                if (map[i][j].getClass().getSimpleName().equals("Track")) {


                    if (((Track) map[i][j]).hasTrain) {

                        guiMap[i][j].setFill(Color.RED);

                        train.setTranslateX((i * SIZEX) + 50);
                        train.setTranslateY((j + 1) * SIZEY);
                        train.setVisible(true);

                    }
                    else if(((Track)map[i][j]).hasArrived){
                        pane.getChildren().remove(train);
                        guiMap[i][j].setFill(Color.BLACK);

                    }
                    else if (((Track) map[i][j]).isOpen) {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);

                    }
                    else if (((Track) map[i][j]).isLight) {
                        guiMap[i][j].setFill(Color.BLACK);
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);


                    } else {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
                        guiMap[i][j].setFill(Color.BLACK);
                        //pane.getChildren().remove(train);

                    }
                }
            }
        }
    }

    public void reset(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if((map[i][j].getClass().getSimpleName().equals("Station"))) {
                    if(!map[i][j].isInterrupted())
                    guiMap[i][j].setFill(Color.AQUA);
                }
            }
        }
    }

    public void stationSetter(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                if(map[i][j].getClass().getSimpleName().equals("Track")){

                    Path path = new Path();
                    MoveTo location = new MoveTo();
                    LineTo line = new LineTo();
                    PathTransition pathTransition = new PathTransition();
                    location.setX(i*SIZEX);
                    location.setY(j*SIZEY);
                    line.setY(j*SIZEY);
                    line.setX(i*SIZEX+100);
                    path.getElements().add(location);
                    path.getElements().add(line);
                    pathTransition.setPath(path);
                    pathTransition.setRate(2);
                    Circle c = new Circle(20);
                    pathTransition.setNode(train);
                    pane.getChildren().addAll(path, c);
                    if(((Track) map[i][j]).hasTrain){
                        pathTransition.play();

                    }
                   // pathMaker((i*SIZEX), (j+1) * SIZEY);

                }
            }
        }

    }

    public Path pathMaker(double x, double y){
        Path path = new Path();

        MoveTo location = new MoveTo();
        LineTo line = new LineTo();
        location.setX(x);
        location.setY(y);
        line.setY(y);
        line.setX(x+100);
        path.getElements().add(location);
        path.getElements().add(line);
        pane.getChildren().add(path);
        return path;

    }

    public void pathGuiTest(Station start, Station end){
        Path path = new Path();
        Rectangle rect1 = start.getDisplayStation();
        Rectangle rect2 = end.getDisplayStation();
        PathTransition pathTransition = new PathTransition();
        MoveTo location = new MoveTo();

        location.setX(0);
        location.setY(200);

        LineTo line = new LineTo();
        line.setX(0);
        line.setY(500);




        path.getElements().add(location);
        path.getElements().add(line);
        Circle c = new Circle(20);
        pathTransition.setPath(path);
        pathTransition.setRate(2);
        pathTransition.setNode(c);
        pathTransition.setAutoReverse(true);
        pane.getChildren().addAll(path, c);
        pathTransition.play();

    }
    public Path trackPath(){
        Path trackP = new Path();
        return trackP;
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

    public void stationListener() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Rectangle rect;
                if (map[i][j].getClass().getSimpleName().equals("Station")) {
                    Station t = (Station) map[i][j];

                    rect = t.getDisplayStation();
                    train.setTranslateX(rect.getTranslateX());
                    train.setTranslateY(rect.getTranslateY());
                    //train.setVisible(false);
                    //System.out.println(map[i][j]);
                    rect.setTranslateX((i * SIZEX) + 50);
                    rect.setTranslateY((j + 1) * SIZEY);
                    //rect.setFill(Color.BLACK);
                    rect.setOnMousePressed(event -> {
                        rect.setFill(Color.RED);
                        pickedStations.add(t);
                        if(pickedStations.size() == 2) {
                            mainT.setStartStation(pickedStations);
                            pathGuiTest(pickedStations.getFirst(), pickedStations.getLast());
                            pickedStations.clear();
                            //pane.getChildren().add(train);

                        }

                    });

                    guiMap[i][j] = rect;

                }
            }
        }
    }

    public void handle(long currentNanoTime) {


        //stationSetter();

        initTracks();
        redraw();

    }


    private void redraw() {
        //pane.getChildren().remove(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().remove(guiMap[i][j]);
            }
        }

        //pane.getChildren().add(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().add(guiMap[i][j]);
            }
        }
    }
}
