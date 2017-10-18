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
        MapFromXML map = new MapFromXML();
        for (int i = 0; i < map.getMap().length; i++)
        {
            if (map.getMap()[i].contains("station"))
            {
                String[] name = map.getMap()[i].split(":");
                Station station = new Station(name[1]);
                pane.getChildren().add(station);
                station.setTranslateX((i * 85) + 100);
                station.setTranslateY(50);
                station.run();
            }
            if (map.getMap()[i].equals("track"))
            {
                Track track = new Track();
                track.setTranslateX((i * 85) + 100);
                track.setTranslateY(50);
                pane.getChildren().add(track);
                //track.run();
            }
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
