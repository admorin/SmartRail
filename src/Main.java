import components.Component;
import components.Station;
import components.Track;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
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
            }
            map[i].run();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
