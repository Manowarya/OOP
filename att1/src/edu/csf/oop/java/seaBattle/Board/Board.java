package edu.csf.oop.java.seaBattle.Board;

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

public class Board extends Parent implements IBoard {
    private final VBox rows = new VBox();
    public int ships = 10;

    public Board(EventHandler<? super MouseEvent> handler) {
        for (int y = 0; y < 10; y++) {
            HBox row = new HBox();
            for (int x = 0; x < 10; x++) {
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
        return false;
    }

    @Override
    public boolean placeShip(Ship ship) {
        return false;
    }

    protected boolean canPlaceShip(Ship ship) {
        Point2D[] point = ship.getPoints();
        for (int i = 0; i < ship.getPoints().length; i++) {
            if (!isValidPoint(point[i])) {
                return false;
            }
            Cell cell = getCell(point[i]);
            if (cell.ship != null) {
                return false;
            }
            Cell[] neighbors = getNeighborsCell(ship);
            for (Cell n : neighbors) {
                if (n.ship != null) {
                    return false;
                }
            }
        }
        return true;
    }

    private Cell[] getNeighborsCell(Ship ship) {
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
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    public Cell getCell(int x, int y) {
        return (Cell)((HBox)rows.getChildren().get(y)).getChildren().get(x);
    }

    public static class Cell extends Rectangle {
        public int x, y;
        public Ship ship = null;
        public boolean wasShot = false;

        private final Board board;

        public Cell(int x, int y, Board board) {
            super(25, 25);
            this.x = x;
            this.y = y;
            this.board = board;
            setFill(Color.LIGHTGRAY);
            setStroke(Color.BLACK);
        }
    }
}
