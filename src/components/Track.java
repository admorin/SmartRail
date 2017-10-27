package components;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Track extends Thread implements Component
{
    static private int width = 75;
    static private int height = 2;
    public boolean hasTrain;
    public Track next;
    public Rectangle display = new Rectangle();
    public Train train;
    public boolean isOpen = true;
    public String NAME;
    int index = 1;
    TrainPrinter printer;

    public Track(){

    }


    public Track(Track next, String name)
    {
        this.NAME = name;
        setName(name);
        train = new Train(null, this, null, null, "ALEX");
        this.next = next;
        //super(width, height);
        //setFill(Color.BLACK);
        hasTrain = false;


    }

    public void moveTrain(){

        try {
            train.changeTrack(this);
            isOpen = false;
            System.out.println("Train is on track "+ this.getName());
            Thread.sleep(1000);
//               System.out.println("Current Track = " +
//                       Thread.currentThread().getName() +
//                       " Next Track = " + next.getName());
            System.out.println("Attempting to move Train from Track " + getName() +
                    " to Track " + next.getName());



        }
        catch (Exception e){

        }

    }

    public void setDisplay(){
        display.setWidth(width);
        display.setHeight(height);
        display.setFill(Color.BLACK);
    }


    public void setOccupied(){
        this.next.isOpen = false;

    }

    public void run()
    {
        while(isOpen) {
            try {
                if (this.next.isOpen) {
                    moveTrain();
                    //printer.moveTrain(index+1);
                    next.start();
                }
                else{

                    System.out.println("Track " + getName() + " is occupied...");
                    System.out.println("Waiting...");
                    Thread.sleep(2000);



                }
            }
            catch(Exception e){
                isOpen = false;
                System.exit(0);
            }
        }
        isOpen = false;

    }


}
