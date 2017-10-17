import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * Created by alexschmidt-gonzales on 10/17/17.
 */
public class Station extends Rectangle implements Runnable{

    public String name;
    public int location;
    public boolean click = false;
    public Station(String name, int location){
        this.name = name;
        this.location = location;
        setFill(Color.RED);
        setHeight(100);
        setWidth(50);

        setOnMousePressed(event -> {
            setFill(Color.BLUE);
            click = true;
        });
        setOnMouseReleased(event -> {
            setFill(Color.RED);
        });
    }


    @Override
    public void run() {
        Timeline timeline = new Timeline();
        timeline.setAutoReverse(true);
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(5000),
                new KeyValue(translateXProperty(), 25)));

        timeline.play();
    }
}
