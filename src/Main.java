import components.Component;
import components.Station;
import components.Track;
import components.Train;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import util.MapFromXML;

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
        initializeMap();
        //Test new XML reader class
    }

    private void initializeMap()
    {
        System.out.println("Initializing map...");
        map = new MapFromXML().getMap();
        for (int i = 0; i < map.length; i++)
        {
            if (map[i].getClass().getSimpleName().equals("Track"))
            {
                Track track = (Track) map[i];
                pane.getChildren().add(track);
                track.setTranslateX((i * 85) + 100);
                track.setTranslateY(50);
            } else if (map[i].getClass().getSimpleName().equals("Station"))
            {
                Station station = (Station) map[i];
                pane.getChildren().add(station);
                station.setTranslateX((i * 85) + 100);
                station.setTranslateY(50);
                station.setOnMousePressed(event ->
                {
                    //TODO somehow spawn a train from here.
                    station.setFill(Color.BLUE);
                    Train train = new Train();
                    train.run();
                    pane.getChildren().add(train);
                    train.setTranslateX(((1 * 85) + 100));
                    train.setTranslateY(35);
                    System.out.println("Spawning train from station " + station.getName());
                });

                station.setOnMouseReleased(event ->
                {
                    station.setFill(Color.RED);
                });
            }

            map[i].run();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
