import components.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PathTransition;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    double newX = 0;
    double newY = 0;


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

    public boolean trainListener(){
        boolean flag = false;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).hasTrain) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public void trackListener(){
        String dir = "";
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).hasTrain) {
                        Train train = ((Track) map[i][j]).train;
                        dir = train.returnCurrentDirection();
                        if (newX == -1 && newY == -1) {
                            newX = (i * SIZEX + (SIZEX / 2) + 50);
                        } else {
                            if (dir.equals("Down")) {
                                newX = newX + 2;
                                newY = newY + 1;
                                testTrain.setTranslateX(newX);
                                testTrain.setTranslateY(newY);
                            } else if (dir.equals("Right")) {

                                newX = newX + 2;
                                newY = ((j + 1) * SIZEY);
                                testTrain.setTranslateX(newX);
                                testTrain.setTranslateY(newY);

                            } else if (dir.equals("Left")) {

                            } else if (dir.equals("Up")) {

                            }
                        }
                    }



                }
            }
        }
        if(!trainListener()){
            newX = -1;
            newY = -1;
        }

    }



    public void initTracks() {

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {

                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isSwitch) {
                        Line line = new Line();
                        line.setStartX(i*SIZEX);
                        line.setStartY((j+1) * SIZEY);
                        line.setEndX((i*SIZEX) + (SIZEY));
                        line.setEndY((j+1) * SIZEY + (SIZEY));
                        pane.getChildren().add(line);

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
                    if(((Station)map[i][j]).hasArrived){
                        ((Station)map[i][j]).hasArrived = true;

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
                    rect.setTranslateX((i * SIZEX) + 50);
                    rect.setTranslateY((j + 1) * SIZEY);
                    rect.setFill(Color.AQUA);

                    text.setTranslateX(rect.getTranslateX());
                    text.setTranslateY(rect.getTranslateY());
                    text.setFont(Font.font(20));


                    train.setVisible(true);

                    pane.getChildren().add(text);

                    rect.setOnMousePressed(event -> {
                        rect.setFill(Color.RED);
                        pickedStations.add(t);

                        if(pickedStations.size() == 2) {
                            mainT.setStartStation(pickedStations);
                            pickedStations.getFirst();

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
