package ru.vsu.cs.bobrovskaya;

import static ru.vsu.cs.bobrovskaya.Point.distance;

public class Section {
    Point p1, p2;

    public Section(Point p1, Point p2) {
        this.p1 = p1;
        this.p2 = p2;
    }
    public Section(float x1, float y1, float x2, float y2) {
        p1 = new Point(x1, y1);
        p2 = new Point(x2, y2);
    }

    final float equityTolerance = 0.000000001f;


    public boolean isEqual(float d1, float d2) {
        return Math.abs(d1-d2) <= equityTolerance;
    }

    public Point getP1() {
        return p1;
    }

    public void setP1(Point p1) {
        this.p1 = p1;
    }

    public Point getP2() {
        return p2;
    }

    public void setP2(Point p2) {
        this.p2 = p2;
    }

    public Point getIntersectionPoint(Point l2p1, Point l2p2) {
        float A1 = this.p2.getY() - this.p1.getY();
        float B1 = this.p1.getX() - this.p2.getX();
        float C1 = A1 * this.p1.getX() + B1 * this.p1.getY();
        float A2 = l2p2.getY() - l2p1.getY();
        float B2 = l2p1.getX() - l2p2.getX();
        float C2 = A2 * l2p1.getX() + B2 * l2p1.getY();
        float det = A1 * B2 - A2 * B1;
        if (det == 0) {
            return null;
        }
        else {
            float x = (B2 * C1 - B1 * C2) / det;
            float y = (A1 * C2 - A2 * C1) / det;
            boolean onLine1 = ((Math.min(this.p1.getX(), this.p2.getX()) < x || isEqual(Math.min(this.p1.getX(), this.p2.getX()), x))
                    && (Math.max(this.p1.getX(), this.p2.getX()) > x || isEqual(Math.max(this.p1.getX(), this.p2.getX()), x))
                    && (Math.min(this.p1.getY(), this.p2.getY()) < y || isEqual(Math.min(this.p1.getY(), this.p2.getY()), y))
                    && (Math.max(this.p1.getY(), this.p2.getY()) > y || isEqual(Math.max(this.p1.getY(), this.p2.getY()), y))
            );
            boolean onLine2 = ((Math.min(l2p1.getX(), l2p2.getX()) < x || isEqual(Math.min(l2p1.getX(), l2p2.getX()), x))
                    && (Math.max(l2p1.getX(), l2p2.getX()) > x || isEqual(Math.max(l2p1.getX(), l2p2.getX()), x))
                    && (Math.min(l2p1.getY(), l2p2.getY()) < y || isEqual(Math.min(l2p1.getY(), l2p2.getY()), y))
                    && (Math.max(l2p1.getY(), l2p2.getY()) > y || isEqual(Math.max(l2p1.getY(), l2p2.getY()), y))
            );
            if (onLine1 && onLine2) {
                return new Point(x, y);
            }
        }
        return null;
    }

    public boolean intersects(Section section) {
        float x1 = section.p1.getX();
        float x2 = section.p2.getX();
        float y1 = section.p1.getY();
        float y2 = section.p2.getY();
        float x3 = this.p1.getX();
        float x4 = this.p2.getX();
        float y3 = this.p1.getY();
        float y4 = this.p2.getY();

        float determinant = (y4-y3)*(x1-x2)-(x4-x3)*(y1-y2);
        if (determinant  == 0) {
            return (x1 * y2 - x2 * y1) * (x4 - x3) - (x3 * y4 - x4 * y3) * (x2 - x1) == 0
                    && (x1 * y2 - x2 * y1) * (y4 - y3) - (x3 * y4 - x4 * y3) * (y2 - y1) == 0;
        }
        else {
            float numerator_a = (x4-x2)*(y4-y3)-(x4-x3)*(y4-y2);
            float numerator_b = (x1-x2)*(y4-y2)-(x4-x2)*(y1-y2);
            float Ua = numerator_a/determinant ;
            float Ub = numerator_b/determinant ;
            return Ua >= 0 && Ua <= 1 && Ub >= 0 && Ub <= 1;
        }
    }
    public float length(){
        return distance(this.p1, this.p2);
    }
}
