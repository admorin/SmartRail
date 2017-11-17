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
    private static Pane pane = new Pane();

    /**
     * This is our primary stage. We set up the window and start the main game thread from here.
     * @param primaryStage
     * @throws Exception
     */

    @Override
    public void start(Stage primaryStage) throws Exception {

        MainThread mainT = new MainThread(pane);
        root.getChildren().add(pane);
        primaryStage.setTitle("Train Simulator 2017");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.setResizable(false);
        DisplayGUI GUI = new DisplayGUI(pane, mainT);
        GUI.start();
        primaryStage.show();


    }

    public static void main(String[] args) {


        launch(args);
    }
}

