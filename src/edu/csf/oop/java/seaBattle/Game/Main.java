package edu.csf.oop.java.seaBattle.Game;

import edu.csf.oop.java.seaBattle.logging.Log;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    Game game = new Game();

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(game.createContent());
        primaryStage.setTitle("Battleship");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Log.startGame();
        launch(args);
    }
}
