package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by alexschmidt-gonzales on 10/17/17.
 */
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
        setOnMousePressed(event ->
        {
            //TODO somehow spawn a train from here.
            setFill(Color.BLUE);
            System.out.println("Spawning train from station " + getName());
        });

        setOnMouseReleased(event ->
        {
            setFill(Color.RED);
        });
    }

    public String getName()
    {
        return name;
    }
}
