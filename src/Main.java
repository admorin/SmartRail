import components.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
    private Group root = new Group();
    private static GridPane pane = new GridPane();
    private static Component[] map = new Component[5];
    private static Shape[] guiMap = new Shape[5];
    private static Rectangle train = new Rectangle(50, 10);

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(pane);
        primaryStage.setTitle("Train Sim 2018");
        primaryStage.setScene(new Scene(root, 1280, 720));
        train.setTranslateY(80);
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                for (int i = 0; i < map.length; i++)
                {
                    if(((Track)map[i]).isAlive())
                        guiMap[i].setFill(Color.RED);
                    else
                        guiMap[i].setFill(Color.BLACK);

                    if (map[i].getClass().getSimpleName().equals("Track")) {
                        if (((Track) map[i]).isOpen) {
                            guiMap[i].setTranslateX((i * 60) + 50);
                            guiMap[i].setTranslateY(100);
                        } else {
                            guiMap[i].setTranslateX((i * 60) + 50);
                            guiMap[i].setTranslateY(100);
                            train.setTranslateX((i * 60) + 50);
                        }
                    }
                }
                redraw();
            }
        }.start();
        primaryStage.show();
    }

    private void redraw() {
        pane.getChildren().remove(train);
        for (int i = 0; i < guiMap.length; i++)
            pane.getChildren().remove(guiMap[i]);
        pane.getChildren().add(train);
        for (int j = 0; j < guiMap.length; j++)
            pane.getChildren().add(guiMap[j]);
    }

    public static void main(String[] args)
    {
        Track track1 = new Track();
        Track track2 = new Track();
        Track track3 = new Track();
        Track track4 = new Track();
        Track track5 = new Track();

        map[0] = track1;
        map[1] = track2;
        map[2] = track3;
        map[3] = track4;
        map[4] = track5;

        Station stationB = new Station("EndsHere", track5);
        Station stationA = new Station("AlexCantGithub", track1);

        TrainPrinter printer = new TrainPrinter(5, 0);
        printer.printTracks();
        track5.setTrackRStation(stationB, track4, "5", printer);
        track4.setTrack(track5, track3, "4", printer);
        track3.setTrack(track4, track2, "3", printer);
        track2.setTrack(track3, track1, "2", printer);
        track1.setTrackLStation(track2, stationA, "1", printer);

        for (int i = 0; i < guiMap.length; i++) {
            guiMap[i] = new Rectangle(50, 2);
        }

        stationA.finishLine(stationB);
        stationA.start();

        launch(args);
    }
}
