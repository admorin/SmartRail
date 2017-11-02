
import components.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
    private Group root = new Group();
    private static final int WIDTH = 7;
    private static final int HEIGHT = 2;
    private static Pane pane = new Pane();
    private static Component[][] map = new Component[WIDTH][HEIGHT];
    private static Shape[][] guiMap = new Shape[WIDTH][HEIGHT];
    private static Rectangle train = new Rectangle(50, 10);
    private static Rectangle station = new Rectangle(30, 40);
    private Canvas canvas = new Canvas();
    private GraphicsContext gc = canvas.getGraphicsContext2D();
   // private static Circle station = new Circle(25);


    @Override
    public void start(Stage primaryStage) throws Exception {

        MainThread mainT = new MainThread(pane);

        root.getChildren().add(pane);


        primaryStage.setTitle("Train Sim 2018");
        primaryStage.setScene(new Scene(root, 1280, 720));

        primaryStage.show();
        DisplayGUI GUI = new DisplayGUI(pane,mainT );
        GUI.start();

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



//        stationA.finishLine(stationB);
//        stationA.start();

        launch(args);
    }
}

