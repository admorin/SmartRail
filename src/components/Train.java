package components;

import javafx.animation.AnimationTimer;
import javafx.scene.shape.Rectangle;
import util.Logger;

public class Train extends Rectangle implements Component
{
    Component curComponent;
    Station startDest;
    Station endDest;

    static private int width = 75;
    static private int height = 20;

    private int secsPassed;

    public Train(Component startComp, Component endComp)
    {
        super(width, height);
        startDest = (Station) startComp;
        endDest = (Station) endComp;
    }

    @Override
    public void run()
    {
        System.out.println("New route request : Train ID [0] from Station[" + startDest.getName() + "] to Station[B]");
        new AnimationTimer()
        {
            @Override
            public void handle(long now)
            {
                secsPassed++;
                if (secsPassed % 180 == 0)
                    new Logger(this,"Can I move yet?");
                //TODO ask the rail or main system if it can travel, somehow broadcast a message upwards
            }
        }.start();
    }
}
