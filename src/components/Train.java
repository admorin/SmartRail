package components;

import javafx.scene.shape.Rectangle;

public class Train extends Rectangle implements Component
{
    Component curComponent;
    Station startDest;
    Station endDest;

    public Train()
    {
        super(75, 20);
    }

    @Override
    public void run()
    {


    }
}
