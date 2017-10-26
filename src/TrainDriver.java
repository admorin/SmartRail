import components.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import util.MapFromXML;


public class TrainDriver {
    public GridPane pane;
    public Component[] map;
    public Train train;
    public Station station;
    public Track track;
    public TrainDriver(GridPane pane, Component[] map){
        this.pane = pane;
        this.map = map;

    }
    public void initializeMap() {
        System.out.println("Initializing map...");
        Train trainX;
        map = new MapFromXML().getMap();
        Controller control = new Controller(map);
        for (int i = 0; i < map.length; i++) {
            if (map[i].getClass().getSimpleName().equals("Track")) {
                trackInit(i, control);
            }
            else if (map[i].getClass().getSimpleName().equals("Station")){
                stationInit(i, control);
            }

            map[i].run();
        }
    }

    public void stationInit(int i, Controller control){
        station = (Station) map[i];
        pane.getChildren().add(station);
        station.setTranslateX((i * 85) + 100);
        station.setTranslateY(50);
        station.setOnMousePressed(event ->
        {
            //TODO somehow spawn a train from here.
            station.setFill(Color.BLUE);
            train = new Train(station, control.returnTrack(), null, pane, "A");
            control.returnTrack();
            control.printTracks();
            pane.getChildren().add(train.train);
            train.train.setTranslateX(((1 * 85) + 100));
            train.train.setTranslateY(35);

            train.start();
            //train.test();

            System.out.println("Spawning train from station " + station.getName());
        });

        station.setOnMouseReleased(event ->
        {
            station.setFill(Color.RED);


        });
    }

    public void trackInit(int i, Controller control){
        track = (Track) map[i];
        control.initTracks(track);
        //pane.getChildren().add(track.display);
//        track.display.setTranslateX((i * 85) + 100);
//        track.display.setTranslateY(50);
    }
}
