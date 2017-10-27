import components.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Main extends Application
{
    private Group root = new Group();
    private GridPane pane = new GridPane();
    private Component[] map;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        root.getChildren().add(pane);
        primaryStage.setTitle("Train Sim 2018");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
        TrainDriver driver = new TrainDriver(pane, map);
        driver.initializeMap();
        //Test new XML reader class
    }


    public static void main(String[] args)
    {
        Track track1 = new Track();
        Track track2 = new Track();
        Track track3 = new Track();
        Track track4 = new Track();
        Track track5 = new Track();

        Station stationB = new Station("EndsHere", track5);
        Station stationA = new Station("AlexCantGithub", track1);

        TrainPrinter printer = new TrainPrinter(5, 0);
        printer.printTracks();
        track5.setTrackRStation(stationB, track4, "5", printer);
        track4.setTrack(track5, track3, "4", printer);
        track3.setTrack(track4, track2, "3", printer);
        track2.setTrack(track3, track1, "2", printer);
        track1.setTrackLStation(track2, stationA, "1", printer);

        stationA.finishLine(stationB);
        stationA.start();

        //launch(args);
    }
}
