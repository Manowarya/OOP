package edu.csf.oop.java.seaBattle.Ship;

import javafx.geometry.Point2D;

public interface IShip {
    Point2D[] getPoints();
    Point2D[] getNeighbors();
}
