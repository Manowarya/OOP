package edu.csf.oop.java.seaBattle.Ship;

import javafx.geometry.Point2D;
import javafx.scene.Parent;

import java.util.ArrayList;
import java.util.List;

public class Ship extends Parent implements IShip{

    public int maxSizeShip;
    public boolean vertical;
    public int x;
    public int y;

    public int health;

    public Ship(int maxSizeShip, boolean vertical, int x, int y) {
        this.maxSizeShip = maxSizeShip;
        this.vertical = vertical;
        health = maxSizeShip;
        this.x = x;
        this.y = y;
    }

    public void hit() {
        health--;
    }

    public boolean isAlive() {
        return health > 0;
    }

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

    public Point2D[] getPoints() {
        List<Point2D> point = new ArrayList<>();
        if (this.vertical) {
            for (int i = this.y; i < this.y + maxSizeShip; i++) {
                point.add(new Point2D(this.x, i));
                }
            }
        else {
            for (int i = this.x; i < this.x + maxSizeShip; i++) {
                point.add(new Point2D(i, this.y));
            }
        }
        return point.toArray(new Point2D[0]);
    }
}
