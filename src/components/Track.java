package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Track extends Rectangle implements Component
{
    public Track()
    {
        super(75, 2);
        setFill(Color.BLACK);
    }

    @Override
    public void run()
    {

    }
}
