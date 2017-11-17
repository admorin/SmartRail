/**
 * Andrew Morin
 * Tyson Craner
 * Alex Schmidt
 *
 * Train System
 * Project 3
 * 11/15/2017
 */

import components.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

public class Main extends Application {
    private Group root = new Group();
    private static final int WIDTH = 7;
    private static final int HEIGHT = 2;
    private static Pane pane = new Pane();
    private static Component[][] map = new Component[WIDTH][HEIGHT];
    private static Shape[][] guiMap = new Shape[WIDTH][HEIGHT];
    private static Rectangle train = new Rectangle(50, 10);
    private static Rectangle station = new Rectangle(30, 40);
    private Canvas canvas = new Canvas();
    private GraphicsContext gc = canvas.getGraphicsContext2D();

    /**
     * This is our primary stage. We set up the window and start the main game thread from here.
     * @param primaryStage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainThread mainT = new MainThread(pane);

        root.getChildren().add(pane);

        primaryStage.setTitle("Train Sim 2018");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        DisplayGUI GUI = new DisplayGUI(pane,mainT );
        GUI.start();
        primaryStage.show();


    }

    public static void main(String[] args) {


        launch(args);
    }
}

