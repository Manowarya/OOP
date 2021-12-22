package ru.vsu.cs.bobrovskaya;

import java.util.Objects;

public class Point {
    private float x, y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    final float equityTolerance = 0.000000001f;

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return (isEqual(point.x, x) && isEqual(point.y, y));
    }

    public boolean isEqual(float d1, float d2) {
        return Math.abs(d1-d2) <= equityTolerance;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static float distance(Point p1, Point p2) {
        float dx = p1.x - p2.x;
        float dy = p1.y - p2.y;

        return (float) Math.sqrt(dx * dx + dy * dy);
    }

}
