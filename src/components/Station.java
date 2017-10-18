package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Station extends Circle implements Component
{
    public String name;

    public Station(String name)
    {
        super(25);
        this.name = name;
        setFill(Color.RED);
    }

    @Override
    public void run()
    {
        //TODO add a timer so it can't be spam clicked

    }

    public String getName()
    {
        return name;
    }
}
