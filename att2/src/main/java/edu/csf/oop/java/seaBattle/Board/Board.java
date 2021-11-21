package edu.csf.oop.java.seaBattle.Board;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.csf.oop.java.seaBattle.Ship.IShip;
import edu.csf.oop.java.seaBattle.Ship.Mine;
import edu.csf.oop.java.seaBattle.Ship.Minesweeper;
import edu.csf.oop.java.seaBattle.Ship.Ship;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Board extends Parent implements IBoard {
    private final VBox rows = new VBox();
    @JsonIgnore
    public int ships = 10;
    @JsonIgnore
    final public int sizeBoardX = 16;
    @JsonIgnore
    final public int sizeBoardY = 16;

    @JsonSerialize
    public ArrayList<Cell> cellsShips = new ArrayList<>();

    @JsonSerialize
    public ArrayList<Cell> getCellsShips() {
        return cellsShips;
    }

    public ArrayList<Cell> cellsMines = new ArrayList<>();

    public ArrayList<Cell> getCellsMines() {
        return cellsMines;
    }

    public Board(EventHandler<? super MouseEvent> handler) {
        for (int y = 0; y < sizeBoardY; y++) {
            HBox row = new HBox();
            for (int x = 0; x < sizeBoardX; x++) {
                Cell c = new Cell(x, y, this);
                c.setOnMouseClicked(handler);
                row.getChildren().add(c);
            }
            rows.getChildren().add(row);
        }
        getChildren().add(rows);
    }

    public boolean shoot(int x, int y) {
        Cell cell = getCell(x, y);
        cell.wasShot = true;
        cell.setFill(Color.BLACK);
        if (cell.ship != null) {
            cell.ship.hit();
            cell.setFill(Color.RED);
            if (!cell.ship.isAlive()) {
                Cell[] neighbors = getNeighborsCell(cell.ship);
                for (Cell n : neighbors) {
                    if (!n.wasShot) {
                        n.setFill(Color.BLACK);
                        n.wasShot = true;
                    }
                }
                 cell.board.ships--;
            }
            return true;
        }
        else if (cell.mine != null) {
            cell.mine.hit();
            cell.setFill(Color.RED);
            cell.setStroke(Color.YELLOW);
            Cell[] neighbors = getNeighborsCell(cell.mine);
            for (Cell n : neighbors) {
                if (!n.wasShot) {
                    n.setFill(Color.BLACK);
                    n.wasShot = true;
                }
            }
        }
        else if (cell.minesweeper != null) {
            cell.minesweeper.hit();
            cell.setFill(Color.RED);
            cell.setStroke(Color.BLUE);
            Cell[] neighbors = getNeighborsCell(cell.minesweeper);
            for (Cell n : neighbors) {
                if (!n.wasShot) {
                    n.setFill(Color.BLACK);
                    n.wasShot = true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean placeShip(Ship ship) {
        return false;
    }

    @Override
    public boolean placeMine(Mine mine) {
        return false;
    }

    public boolean canPlaceShip(IShip ship) {
        Point2D[] point = ship.getPoints();
        for (int i = 0; i < ship.getPoints().length; i++) {
            if (!isValidPoint(point[i])) {
                return false;
            }
            Cell cell = getCell(point[i]);
            if (cell.ship != null || cell.mine != null) {
                return false;
            }
            Cell[] neighbors = getNeighborsCell(ship);
            for (Cell n : neighbors) {
                if (n.ship != null || n.mine != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Cell[] getNeighborsCell(IShip ship) {
        Point2D[] nei = ship.getNeighbors();
        List<Cell> neighbors = new ArrayList<>();
        for (Point2D p : nei) {
            if (isValidPoint(p)) {
                neighbors.add(getCell((int)p.getX(), (int)p.getY()));
            }
        }
        return neighbors.toArray(new Cell[0]);
    }

    protected Cell getCell(Point2D point2D) {
        return getCell((int)point2D.getX(), (int)point2D.getY());
    }

    private boolean isValidPoint(Point2D point) {
        return isValidPoint(point.getX(), point.getY());
    }

    private boolean isValidPoint(double x, double y) {
        return x >= 0 && x < sizeBoardX && y >= 0 && y < sizeBoardY;
    }


    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    public static class Cell extends Rectangle {
        @JsonSerialize
        public int x, y;
        @JsonSerialize
        public Ship ship = null;
        @JsonSerialize
        public Mine mine = null;
        public Minesweeper minesweeper = null;
        @JsonSerialize
        public boolean wasShot = false;

        private final Board board;

        public Cell(int x, int y, Board board) {
            super(15, 15);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }

        public Mine getMine() {
            return mine;
        }
    }

}
