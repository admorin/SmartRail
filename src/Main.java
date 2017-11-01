
import components.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
    private Group root = new Group();
    private static GridPane pane = new GridPane();
    private static Component[][] map = new Component[5][2];
    private static Shape[][] guiMap = new Shape[5][2];
    private static Rectangle train = new Rectangle(50, 10);
    private static Circle station = new Circle(25);

    @Override
    public void start(Stage primaryStage) throws Exception {
        root.getChildren().add(pane);
        primaryStage.setTitle("Train Sim 2018");
        primaryStage.setScene(new Scene(root, 1280, 720));
        train.setTranslateY(80);
        new AnimationTimer() {
            public void handle(long currentNanoTime) {
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < 2; j++) {
                        if (map[i][j].getClass().getSimpleName().equals("Track")) {
                            if (((Track) map[i][j]).isOpen) {
//                                if (i >= 5) {
//                                    guiMap[i].setTranslateX(((i - 5) * 60) + 50);
//                                    guiMap[i].setTranslateY(200);
//                                } else

                                guiMap[i][j].setTranslateX((i * 60) + 50);
                                guiMap[i][j].setTranslateY((j+1) * 100);

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
                        }
                        else {
                            if (((Station)map[i][j]).isEnd()) {
//                            if(i == 7 || i == 13){
//                                guiMap[i].setTranslateX((i * 60) + 50);
//                                guiMap[i].setTranslateY(200);
//                            }

                                guiMap[i][j].setTranslateX((i * 60) + 50);
                                guiMap[i][j].setTranslateY(100);

                            } else {
                                guiMap[i][j].setTranslateX((i * 60) + 50);
                                guiMap[i][j].setTranslateY(100);
                            }
                            if (((Station) map[i][j]).isEnd())
                                guiMap[i][j].setFill(Color.RED);
                            else
                                guiMap[i][j].setFill(Color.BLACK);
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                pane.getChildren().remove(guiMap[i][j]);
            }
        }
        pane.getChildren().add(train);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                pane.getChildren().add(guiMap[i][j]);
            }
        }

//        pane.getChildren().remove(train);
//        for (int i = 0; i < guiMap.length; i++)
//            pane.getChildren().remove(guiMap[i]);
//        pane.getChildren().add(train);
//        for (int j = 0; j < guiMap.length; j++)
//            pane.getChildren().add(guiMap[j]);
    }

    public static void main(String[] args) {
//        Station station1 = new Station();
//        Track track1 = new Track();
//        Track track2 = new Track();
//        Track track3 = new Track();
//        Track track4 = new Track();
//        Track track5 = new Track();
//        Station station2 = new Station();
//
//        map[0] = station1;
//        map[1] = track1;
//        map[2] = track2;
//        map[3] = track3;
//        map[4] = track4;
//        map[5] = track5;
//        map[6] = station2;
//
//        Station stationB = new Station("EndsHere", track5);
//        Station stationA = new Station("AlexCantGithub", track1);

//        TrainPrinter printer = new TrainPrinter(5, 0);
//        printer.printTracks();
//        track5.setTrackRStation(stationB, track4, "5", printer);
//        track4.setTrack(track5, track3, "4", printer);
//        track3.setTrack(track4, track2, "3", printer);
//        track2.setTrack(track3, track1, "2", printer);
//        track1.setTrackLStation(track2, stationA, "1", printer);

        MainThread mainT = new MainThread();
        map = mainT.initialize();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 2; j++) {
                guiMap[i][j] = new Rectangle(50, 2);
            }
        }

//        stationA.finishLine(stationB);
//        stationA.start();

        launch(args);
    }
}

