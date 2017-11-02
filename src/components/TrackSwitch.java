package components;

/**
 * Created by alexschmidt-gonzales on 10/31/17.
 */
public class TrackSwitch extends Thread implements Component {
    public Track upTrack;
    public Track rTrack;
    public Track dTrack;
    public Track lTrack;

    public TrackSwitch(){

    }

    public void setTrackSwitchDown(String name, Track trackR, Track trackL , Track trackDown){
        setName(name);
        this.lTrack = trackL;
        this.rTrack = trackR;
        this.dTrack = trackDown;

    }

    public void setTrackSwitchL(Track trackL, Track trackUp, Track trackDown){
        this.lTrack = trackL;
        this.upTrack = trackUp;
        this.dTrack = trackDown;

    }
    public Track triggerSwitch(){
        return dTrack;

    }

}
