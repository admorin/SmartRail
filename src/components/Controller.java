package components;

import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Created by alexschmidt-gonzales on 10/19/17.
 */
public class Controller extends ArrayList<Track> {
    public Component[] comps;

    public Controller(Component[] comps){
        this.comps = comps;

    }

    public void initTracks(Track tracks){

        add(tracks);
        System.out.println(tracks.toString());

    }

    public Track returnTrack(){
        Track current = get(0);
        current.hasTrain = true;
        current.display.setId("true");
        return current;
    }

    public void printTracks(){
        for(Track t : this){
            //System.out.println(t.toString());
        }
    }

    public void addTrain(Train train){

    }



}
