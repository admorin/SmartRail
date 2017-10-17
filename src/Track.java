import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Created by alexschmidt-gonzales on 10/17/17.
 */
public class Track extends Line {

    public Track(int x, int y, boolean flag){
        setFill(Color.BLACK);
        setOpacity(10.0);
        setStartX(x);
        setStartY(y);
        setEndX(x + 25);
        setEndY(y);
        setLayoutX(200);
        setLayoutY(200);
    }
}
