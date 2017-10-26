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


    //Comment

    public static void main(String[] args)
    {
        TrainPrinter printer = new TrainPrinter(5, 0);
        printer.printTracks();
        Track track5 = new Track(null, "5", printer);
        Track track4 = new Track(track5, "4", printer);
        Track track3 = new Track(track4, "3", printer);
        Track track2 = new Track(track3,"2", printer);
        Track track1 = new Track(track2, "1", printer);



        track2.start();
        track4.start();




        //Comment Andrew


        //launch(args);
    }
}
