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
    private final int SIZEX = 60;
    private final int SIZEY = 100;
    private Object[][] map = new Object[WIDTH][HEIGHT];
    private Shape[][] guiMap = new Shape[WIDTH][HEIGHT];
    private Rectangle train = new Rectangle(50, 10);
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

                guiMap[i][j] = new Rectangle(50, 2);
            }
        }
        //pane.getChildren().add(train);
        stationListener();
    }


    public void initTracks() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                if (map[i][j].getClass().getSimpleName().equals("Track")) {

                    if (((Track) map[i][j]).hasTrain) {
                        guiMap[i][j].setFill(Color.RED);
                        train.setTranslateX((i * SIZEX) + 50);
                        train.setTranslateY((j + 1) * SIZEY);
                    } else if (((Track) map[i][j]).isOpen) {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
                    } else if (((Track) map[i][j]).isLight) {
                        guiMap[i][j].setFill(Color.BLACK);
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);


                    } else {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
                        guiMap[i][j].setFill(Color.BLACK);
                        pane.getChildren().remove(train);
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

    public void stationListener() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                Rectangle rect;
                if (map[i][j].getClass().getSimpleName().equals("Station")) {
                    Station t = (Station) map[i][j];

                    rect = t.getDisplayStation();
                    System.out.println(map[i][j]);
                    rect.setTranslateX((i * SIZEX) + 50);
                    rect.setTranslateY((j + 1) * SIZEY);
                    rect.setFill(Color.BLACK);
                    rect.setOnMousePressed(event -> {
                        rect.setFill(Color.RED);
                        pickedStations.add(t);
                        if(pickedStations.size() == 2) {

                            mainT.setStartStation(pickedStations);
                            pickedStations.clear();
                        }

                    });

                    guiMap[i][j] = rect;

                }
            }
        }
    }

    public void handle(long currentNanoTime) {


        initTracks();
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
