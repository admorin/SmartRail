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
    private ArrayList<Station> stations = new ArrayList<>();


    public DisplayGUI(Pane pane, MainThread mainT) {
        this.pane = pane;
        this.mainT = mainT;
        this.stations = mainT.stationList;
        map = mainT.initialize();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                 guiMap[i][j] = new Rectangle(50, 2);
            }
        }
        pane.getChildren().add(train);
        initStation();
    }


    public void initTracks() {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                if (map[i][j].getClass().getSimpleName().equals("Track")) {

                    if (((Track) map[i][j]).hasTrain) {
                        guiMap[i][j].setFill(Color.RED);
                        train.setTranslateX((i * SIZEX) + 50);
                        train.setTranslateY((j+1) * SIZEY);
                    }
                    else if (((Track) map[i][j]).isOpen) {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
                    }
                    else if(((Track)map[i][j]).isLight) {
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);


                    }
                    else{
                        guiMap[i][j].setTranslateX((i * SIZEX) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * SIZEY);
                        guiMap[i][j].setFill(Color.BLACK);
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

                        line.setStartX((i+1)*70);
                        line.setStartY((j+1) * 30);
                        line.setEndX((i+1)*70);
                        line.setEndY(i*30);
                        light.setTranslateX((i+1)*70);
                        light.setTranslateY((j+1) * 30);
                        pane.getChildren().addAll(light, line);
                        if(!((Track) map[i][j]).nextR.isOpen){
                            light.setFill(Color.RED);
                        }
                        else light.setFill(Color.GREEN);
                    }
                }
            }
        }
    }



    public void initStation(){
        ArrayList<Station> temp = new ArrayList<>();
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){
                    for(Station r : stations) {
                        Rectangle station = r.getDisplayStation();
                        if (map[i][j].getClass().getSimpleName().equals("Station")) {


                            //System.out.println(station.toString());


                            station.setTranslateX((i * SIZEX) + 50);
                            station.setTranslateY((j + 1) * SIZEY);

                            station.setOnMouseClicked(event -> {
                                station.setFill(Color.RED);
                                temp.add(r);
                                mainT.setStartStation(temp);

                            });

                            pane.getChildren().add(station);

                        }

                    }


            }
        }
    }


    public void handle(long currentNanoTime) {

        initTracks();
        lights();
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
