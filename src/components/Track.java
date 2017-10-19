package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Track extends Rectangle implements Component
{
    static private int width = 75;
    static private int height = 2;

    public Track()
    {
        super(width, height);
        setFill(Color.BLACK);
    }

    @Override
    public void run()
    {

    }

}
