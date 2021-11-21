package edu.csf.oop.java.seaBattle;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import edu.csf.oop.java.seaBattle.Board.Board;
import edu.csf.oop.java.seaBattle.Board.BoardEnemy;
import edu.csf.oop.java.seaBattle.Board.BoardPlayer;
import edu.csf.oop.java.seaBattle.Ship.Mine;
import edu.csf.oop.java.seaBattle.Ship.Minesweeper;
import edu.csf.oop.java.seaBattle.Ship.Ship;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Game {
    public BoardPlayer boardPlayer;
    public BoardEnemy boardEnemy;
    private boolean running = false;

    private int numShipsPlayer = 12;
    private int numShipsEnemy = 12;
    private int maxSizeShip;

    private boolean enemyTurn = false;

    private boolean demo = false;

    private final Random random = new Random();

    final MenuBar menu = new MenuBar();
    final Menu menuFile = new Menu("File");

    final BorderPane root = new BorderPane();

    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    public Parent createContent() {
        MenuItem save = new MenuItem("Save");
        MenuItem load = new MenuItem("Load");
        MenuItem demo = new MenuItem("Demo");
        menuFile.getItems().addAll(save);
        menuFile.getItems().addAll(load);
        menuFile.getItems().addAll(demo);
        menu.getMenus().add(menuFile);

        root.setPrefSize(600, 600);
        root.setLeft(new Text("Place ships, mine, minesweeper\n"
                + "Left click - vertical \n"
                + "Right - horizontal "));

        root.setTop(menu);
        boardPlayer = new BoardPlayer(event -> {

            if (running) {
                return;
            }
            Board.Cell cell = (Board.Cell) event.getSource();
            if (numShipsPlayer > 2) {
                if (boardPlayer.placeShip(new Ship(getType(numShipsPlayer), event.getButton() == MouseButton.PRIMARY, cell.x, cell.y))) {
                    logger.info("Coordinates place ship " + cell.x + " " + cell.y);
                    numShipsPlayer--;
                }
            }
            else if (numShipsPlayer > 1) {
                if (boardPlayer.placeMine(new Mine(cell.x, cell.y))) {
                    logger.info("Coordinates place mine " + cell.x + " " + cell.y);
                    numShipsPlayer--;
                }
            } else {
                if (boardPlayer.placeMinesweeper(new Minesweeper(cell.x, cell.y))) {
                    logger.info("Coordinates place minesweeper " + cell.x + " " + cell.y);
                    logger.info("Start game");
                    startGame();
                    numShipsPlayer--;
                }
            }
        });

        boardEnemy = new BoardEnemy(event -> {

            if (!running) {
                return;
            }

            Board.Cell cell = (Board.Cell) event.getSource();
            if (cell.wasShot) {
                return;
            }

            if (cell.mine != null) {
                isMine(boardPlayer);
                enemyTurn = !boardEnemy.shoot(cell.x, cell.y);
            }

            enemyTurn = !boardEnemy.shoot(cell.x, cell.y);
            logger.info("Coordinates shoot " + cell.x + " " + cell.y);

            if (boardEnemy.ships == 0) {
                logger.info("End game");
                root.setLeft(new Text("You won"));
                running = false;
            }

            if (enemyTurn) {
                enemyMove();
                if (boardPlayer.ships == 0) {
                    logger.info("End game");
                    root.setLeft(new Text("You lose"));
                    running = false;
                }
            }
        });

        save.setOnAction(t -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Save JSON (*.json)", "*.json"));
            fileChooser.setTitle("Save");

            File file = fileChooser.showSaveDialog(root.getScene().getWindow());
            ObjectMapper objectMapper = buildMapper();
            List<Board.Cell> cells = boardPlayer.getCellsShips();
            try {
                objectMapper.writeValue(file, cells);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        demo.setOnAction(t -> {
            placeShipPlayerRandom();
            startGame();
            while (running) {
                int x = random.nextInt(boardPlayer.sizeBoardX);
                int y = random.nextInt(boardPlayer.sizeBoardY);

                Board.Cell cell = boardEnemy.getCell(x, y);
                if (cell.wasShot) {
                    continue;
                }

                if (cell.mine != null) {
                    isMine(boardPlayer);
                    enemyTurn = !boardEnemy.shoot(cell.x, cell.y);
                }
                enemyTurn = !boardEnemy.shoot(cell.x, cell.y);

                logger.info("Coordinates shoot " + cell.x + " " + cell.y);

                if (boardEnemy.ships == 0) {
                    logger.info("End game");
                    root.setLeft(new Text("You won"));
                    running = false;
                }

                if (enemyTurn) {
                    enemyMove();
                    if (boardPlayer.ships == 0) {
                        logger.info("End game");
                        root.setLeft(new Text("You lose"));
                        running = false;
                    }
                }
            }
        });

        VBox vbox = new VBox(50, boardEnemy, boardPlayer);
        vbox.setAlignment(Pos.CENTER);

        root.setCenter(vbox);

        return root;
    }

    private static ObjectMapper buildMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.setVisibility(objectMapper.getSerializationConfig()
                .getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        return objectMapper;
    }

    private int getType(int num) {
        if (num == 12) {
            maxSizeShip = 4;
        }
        if (num == 11) {
            maxSizeShip = 3;
        }
        if (num == 9) {
            maxSizeShip = 2;
        }
        if (num == 6) {
            maxSizeShip = 1;
        }
        return maxSizeShip;
    }

    private void wait(int ms)
    {
        try
        {
            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }

    private void isMine(Board board) {
        ArrayList<Board.Cell> cells = board.getCellsShips();
        for (Board.Cell cell : cells ){
            if (!cell.wasShot){
                board.shoot(cell.x, cell.y);
                break;
            }
        }
    }

    private void isMinesweeper(Board board) {
        ArrayList<Board.Cell> cells = board.getCellsMines();
        for (Board.Cell cell : cells){
            if (!cell.wasShot){
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.YELLOW);
                break;
            }
        }
    }

    private void enemyMove() {
        while (enemyTurn) {
            wait(100);
            int x = random.nextInt(boardEnemy.sizeBoardX);
            int y = random.nextInt(boardEnemy.sizeBoardY);

            Board.Cell cell = boardPlayer.getCell(x, y);
            if (cell.wasShot)
                continue;
            if (cell.mine != null) {
                isMine(boardEnemy);
                enemyTurn = boardPlayer.shoot(cell.x, cell.y);
            }
            if (cell.minesweeper != null) {
                isMinesweeper(boardEnemy);
                enemyTurn = boardPlayer.shoot(cell.x, cell.y);
            }

            enemyTurn = boardPlayer.shoot(x, y);
        }
    }

    private void placeShipPlayerRandom() {
        while (numShipsEnemy > 0) {
            int x = random.nextInt(boardPlayer.sizeBoardX);
            int y = random.nextInt(boardPlayer.sizeBoardY);

            if (numShipsEnemy == 2) {
                if (boardPlayer.placeMine(new Mine(x, y))) {
                    --numShipsPlayer;
                    break;
                }
            }
            if (numShipsPlayer == 1) {
                if (boardPlayer.placeMinesweeper(new Minesweeper(x, y))) {
                    --numShipsPlayer;
                    break;
                }
            }
            if (boardPlayer.placeShip(new Ship(getType(numShipsPlayer), Math.random() < 0.5, x, y))) {
                --numShipsPlayer;
            }
        }
    }

    private void placeShipEnemy() {
        while (numShipsEnemy > 0) {
            int x = random.nextInt(boardEnemy.sizeBoardX);
            int y = random.nextInt(boardEnemy.sizeBoardY);

            if (numShipsEnemy == 2) {
                if (boardEnemy.placeMine(new Mine(x, y))) {
                    --numShipsEnemy;
                    break;
                }
            }
            if (numShipsEnemy == 1) {
                if (boardEnemy.placeMinesweeper(new Minesweeper(x, y))) {
                    --numShipsEnemy;
                    break;
                }
            }
            if (boardEnemy.placeShip(new Ship(getType(numShipsEnemy), Math.random() < 0.5, x, y))) {
                --numShipsEnemy;
            }
            VBox vbox = new VBox(50, boardEnemy, boardPlayer);
            vbox.setAlignment(Pos.CENTER);

            root.setCenter(vbox);
        }
    }

    private void startGame() {
        placeShipEnemy();
        root.setLeft(new Text("Attack the enemy"));
        running = true;
    }
}
