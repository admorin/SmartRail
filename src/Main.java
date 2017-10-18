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
        Station test = new Station("A", 0);
        Track track = new Track(100, 100, false);
        pane.getChildren().addAll(test, track);
        test.run();
        root.getChildren().add(pane);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 1000, 1000));
        primaryStage.show();

        //Test new XML reader class
        new MapFromXML();
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
