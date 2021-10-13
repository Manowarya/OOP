package edu.csf.oop.java.seaBattle.Game;

import edu.csf.oop.java.seaBattle.Board.Board;
import edu.csf.oop.java.seaBattle.Board.BoardEnemy;
import edu.csf.oop.java.seaBattle.Board.BoardPlayer;
import edu.csf.oop.java.seaBattle.Ship.Ship;
import edu.csf.oop.java.seaBattle.logging.Log;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.util.Random;

public class Game {
    private BoardPlayer boardPlayer;
    private BoardEnemy boardEnemy;
    private boolean running = false;

    private int numShipsPlayer = 10;
    private int numShipsEnemy = 10;
    private int maxSizeShip = 4;

    private boolean enemyTurn = false;

    private final Random random = new Random();

    public Parent createContent() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 600);
        root.setTop(new Text("Расставьте корабли\n"
                + "Левая кнопка мыши - вертикальный \n"
                + "Правая - горизонтальный"));

        boardEnemy = new BoardEnemy(event -> {
            if (!running)
                return;

            Board.Cell cell = (Board.Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !boardEnemy.shoot(cell.x, cell.y);
            Log.infoShoot(cell.x, cell.y);

            if (boardEnemy.ships == 0) {
                Log.endGame();
                root.setTop(new Text("Вы победили"));
                running = false;
            }

            if (enemyTurn) {
                enemyMove();
                if (boardPlayer.ships == 0) {
                    Log.endGame();
                    root.setTop(new Text("Вы проиграли"));
                    running = false;
                }
            }
        });

        boardPlayer = new BoardPlayer(event -> {
            if (running)
                return;
            Board.Cell cell = (Board.Cell) event.getSource();
            if (boardPlayer.placeShip(new Ship(getType(numShipsPlayer), event.getButton() == MouseButton.PRIMARY, cell.x, cell.y))) {
                Log.infoPlace(cell.x, cell.y);
                if (--numShipsPlayer == 0) {
                    startGame(root);
                }
            }
        });

        VBox vbox = new VBox(50, boardEnemy, boardPlayer);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);

        return root;
    }

    private int getType(int num) {
        if (num == 10) {
            maxSizeShip = 4;
        }
        if (num == 9) {
            maxSizeShip = 3;
        }
        if (num == 7) {
            maxSizeShip = 2;
        }
        if (num == 4) {
            maxSizeShip = 1;
        }
        return maxSizeShip;
    }

    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Board.Cell cell = boardPlayer.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = boardPlayer.shoot(x, y);
        }
    }

    private void startGame(BorderPane root) {
        root.setTop(new Text("Атакуйте противника"));

        while (numShipsEnemy > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (boardEnemy.placeShip(new Ship(getType(numShipsEnemy), Math.random() < 0.5, x, y))) {
                --numShipsEnemy;
            }
        }
        running = true;
    }
}
