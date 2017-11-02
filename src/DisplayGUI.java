import components.Component;
import components.MainThread;
import components.Station;
import components.Track;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class DisplayGUI extends AnimationTimer {
    private Pane pane;
    private final int WIDTH = 7;
    private final int HEIGHT = 2;
    private Component[][] map = new Component[WIDTH][HEIGHT];
    private Shape[][] guiMap = new Shape[WIDTH][HEIGHT];
    private Rectangle train = new Rectangle(50, 10);
    private Rectangle station = new Rectangle(30, 40);
    private Circle stopLight = new Circle(5);
    private MainThread mainT;
    private ArrayList<Station> stations = new ArrayList<>();


    public DisplayGUI(Pane pane, MainThread mainT) {
        this.pane = pane;
        this.mainT = mainT;
        this.stations = mainT.stationList;
        //pane.getChildren().add(station);
        map = mainT.initialize();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                 guiMap[i][j] = new Rectangle(50, 2);
            }
        }
        pane.getChildren().addAll(train, stopLight);
        initStation();
    }

    public void draw() {
        Rectangle x = new Rectangle(100, 100, 100, 100);
        Line line = new Line(150, 150, 500, 100);
        Rectangle y = new Rectangle(500, 100, 100, 100);


    }

    public void initTracks() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {

                    if (((Track) map[i][j]).hasTrain) {
                        guiMap[i][j].setFill(Color.RED);
                        train.setTranslateX((i * 60) + 50);
                        train.setTranslateY((j+1) * 100);
                    }
                    else if (((Track) map[i][j]).isOpen) {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                    }
                    else if(((Track)map[i][j]).isLight) {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                        guiMap[i][j].setFill(Color.BLACK);
                        stopLight.setTranslateX((i * 60) + 50);
                    }
                }
            }
        }
    }

    public void initStations() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Station")) {
                    if (((Station) map[i][j]).isEnd()) {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                        station.setTranslateX(25);
                        station.setTranslateY((j + 1) * 100);

                    }
                    else {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                        station.setTranslateX(WIDTH * 60 + 50);
                    }
                }
            }
        }
    }

    public void initStation(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){


                    for(Station r : stations) {
                        Rectangle station = r.getDisplayStation();
                        if (map[i][j].getClass().getSimpleName().equals("Station")) {


                            //System.out.println(station.toString());


                            station.setTranslateX((i * 60) + 50);
                            station.setTranslateY((j + 1) * 100);

                            station.setOnMouseClicked(event -> {
                                station.setFill(Color.RED);
                                r.start();
                                //System.out.println(station.toString());
                                //TODO initialize station start and end through mouse click
                            });
                            pane.getChildren().add(station);


                        }
                    }


            }
        }
    }


    public void handle(long currentNanoTime) {

        initTracks();
        //initStations();
        redraw();

    }


    private void redraw() {
        pane.getChildren().removeAll(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().remove(guiMap[i][j]);
            }
        }
        pane.getChildren().addAll(train);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().add(guiMap[i][j]);
            }
        }
    }
}
