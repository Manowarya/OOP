package edu.csf.oop.java.seaBattle.Board;

import edu.csf.oop.java.seaBattle.Ship.Mine;
import edu.csf.oop.java.seaBattle.Ship.Minesweeper;
import edu.csf.oop.java.seaBattle.Ship.Ship;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoardEnemy extends Board {
    public BoardEnemy( EventHandler<? super MouseEvent> handler) {
        super(handler);
    }
    public boolean placeShip(Ship ship) {
        if (canPlaceShip(ship)) {
            Point2D[] point = ship.getPoints();
            for (Point2D point2D : point) {
                Cell cell = getCell(point2D);
                cell.ship = ship;
                cellsShips.add(cell);
            }
            return true;
        }
        return false;
    }
    public boolean placeMine(Mine mine) {
        if (canPlaceShip(mine)) {
            Point2D[] point = mine.getPoints();
            for (Point2D point2D : point) {
                Cell cell = getCell(point2D);
                cell.mine = mine;
                cellsMines.add(cell);
            }
            return true;
        }
        return false;
    }
    public boolean placeMinesweeper(Minesweeper minesweeper) {
        if (canPlaceShip(minesweeper)) {
            Point2D[] point = minesweeper.getPoints();
            for (Point2D point2D : point) {
                Cell cell = getCell(point2D);
                cell.minesweeper = minesweeper;
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.BLUE);
            }
            return true;
        }
        return false;
    }
}
