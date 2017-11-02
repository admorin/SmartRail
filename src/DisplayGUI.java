import components.Component;
import components.MainThread;
import components.Station;
import components.Track;
import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

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


    public DisplayGUI(Pane pane, MainThread mainT) {
        this.pane = pane;

        train.setTranslateY(80);
        map = mainT.initialize();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                guiMap[i][j] = new Rectangle(50, 2);
            }
        }

    }

    public void draw() {
        Rectangle x = new Rectangle(100, 100, 100, 100);
        Line line = new Line(150, 150, 500, 100);
        Rectangle y = new Rectangle(500, 100, 100, 100);


    }

    public void initTracks(){
        for(int i = 0; i < WIDTH; i++){
            for(int j = 0; j < HEIGHT; j++){

            }
        }
    }


    public void handle(long currentNanoTime) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (map[i][j].getClass().getSimpleName().equals("Track")) {
                    if (((Track) map[i][j]).isOpen) {
//                                if (i >= 5) {
//                                    guiMap[i].setTranslateX(((i - 5) * 60) + 50);
//                                    guiMap[i].setTranslateY(200);
//                                } else

                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);

                    } else {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY(100);
                        train.setTranslateX((i * 60) + 50);
                        //train.setTranslateY((j+1) *100);
                    }
                    if (((Track) map[i][j]).hasTrain)
                        guiMap[i][j].setFill(Color.RED);
                    else
                        guiMap[i][j].setFill(Color.BLACK);
                } else {
                    if (((Station) map[i][j]).isEnd()) {
//                            if(i == 7 || i == 13){
//                                guiMap[i].setTranslateX((i * 60) + 50);
//                                guiMap[i].setTranslateY(200);
//                            }

                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                        station.setTranslateX(50);
                        station.setTranslateY((j + 1) * 100);


                    } else {
                        guiMap[i][j].setTranslateX((i * 60) + 50);
                        guiMap[i][j].setTranslateY((j + 1) * 100);
                    }
                    if (((Station) map[i][j]).isEnd())
                        guiMap[i][j].setFill(Color.RED);
                    else
                        guiMap[i][j].setFill(Color.BLACK);
                }


            }
            redraw();

        }

    }


    private void redraw() {
        pane.getChildren().removeAll(train, station);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().remove(guiMap[i][j]);
            }
        }
        pane.getChildren().addAll(train, station);
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                pane.getChildren().add(guiMap[i][j]);
            }
        }
    }
}
