package edu.csf.oop.java.seaBattle.Ship;

import javafx.geometry.Point2D;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public class Mine extends Parent implements IShip {
    public int x;
    public int y;
    public int health = 1;

    public Mine(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Point2D[] getPoints() {
        List<Point2D> point = new ArrayList<>();
        point.add(new Point2D(this.x, this.y));
        return point.toArray(new Point2D[0]);
    }

    @Override
    public Point2D[] getNeighbors() {
        List<Point2D> pointN = new ArrayList<>();
        Point2D[] point = getPoints();
        for (Point2D p : point) {
            pointN.add(new Point2D(p.getX() - 1, p.getY()));
            pointN.add(new Point2D(p.getX() + 1, p.getY()));
            pointN.add(new Point2D(p.getX(), p.getY() - 1));
            pointN.add(new Point2D(p.getX(), p.getY() + 1));
            pointN.add(new Point2D(p.getX() - 1, p.getY() - 1));
            pointN.add(new Point2D(p.getX() - 1, p.getY() + 1));
            pointN.add(new Point2D(p.getX() + 1, p.getY() - 1));
            pointN.add(new Point2D(p.getX() + 1, p.getY() + 1));
        }
        return pointN.toArray(new Point2D[0]);
    }
    public void hit() {
        health--;
    }
}
