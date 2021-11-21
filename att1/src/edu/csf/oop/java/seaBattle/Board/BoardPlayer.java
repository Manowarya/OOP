package edu.csf.oop.java.seaBattle.Board;

import edu.csf.oop.java.seaBattle.Ship.Ship;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class BoardPlayer extends Board{

    public BoardPlayer(EventHandler<? super MouseEvent> handler) {
        super(handler);
    }
    public boolean placeShip(Ship ship) {
        if (canPlaceShip(ship)) {
            Point2D[] point = ship.getPoints();
            for (Point2D point2D : point) {
                Cell cell = getCell(point2D);
                cell.ship = ship;
                cell.setFill(Color.WHITE);
                cell.setStroke(Color.GREEN);
            }
            return true;
        }
        return false;
    }
}
