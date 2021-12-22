package ru.vsu.cs.bobrovskaya;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Polygon {
    public Point[] vertices;

    public Polygon(Point[] vertices) {
        this.vertices = vertices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Polygon polygon = (Polygon) o;
        return Arrays.equals(vertices, polygon.vertices);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(vertices);
    }

    public float area() {
        float sum = 0;
        for (int i = 0; i < vertices.length ; i++) {
            if (i == 0) {
                sum += vertices[i].getX() * (vertices[i + 1].getY() - vertices[vertices.length - 1].getY());
            }
            else if (i == vertices.length - 1) {
                sum += vertices[i].getX() * (vertices[0].getY() - vertices[i - 1].getY());
            }
            else {
                sum += vertices[i].getX() * (vertices[i + 1].getY() - vertices[i - 1].getY());
            }
        }
        return (float) (0.5 * Math.abs(sum));
    }

    public boolean isPointInsidePolygon(Point point, Polygon polygon) {
        int i;
        int j;
        boolean result = false;
        for (i = 0, j = polygon.vertices.length - 1; i < polygon.vertices.length; j = i++)  {
            if ((polygon.vertices[i].getY() > point.getY()) != (polygon.vertices[j].getY() > point.getY()) &&
                    (point.getX() < (polygon.vertices[j].getX() - polygon.vertices[i].getX()) *
                            (point.getY() - polygon.vertices[i].getY()) /
                            (polygon.vertices[j].getY() - polygon.vertices[i].getY()) + polygon.vertices[i].getX())) {
                result = !result;
            }
        }
        return result;
    }

    public Point[] getIntersectionPoints(Section section, Polygon poly) {
        ArrayList<Point> intersectionPoints = new ArrayList<>();
        for (int i = 0; i < poly.vertices.length; i++) {
            int next = (i + 1 == poly.vertices.length) ? 0 : i + 1;

            Point ip = section.getIntersectionPoint(poly.vertices[i], poly.vertices[next]);
            if (ip != null) {
                intersectionPoints.add(ip);
            }
        }
        if (intersectionPoints.isEmpty()) {
            return null;
        }
        return intersectionPoints.toArray(new Point[0]);
    }

    public Point findCentroid(List<Point> points) {
        int x = 0;
        int y = 0;
        for (Point p : points) {
            x += p.getX();
            y += p.getY();
        }
        Point center = new Point(0, 0);
        x /= points.size();
        y /= points.size();
        center.setX(x);
        center.setY(y);
        return center;
    }

    public void sortVertices(List<Point> points) {
        Point center = findCentroid(points);
        points.sort((a, b) -> {
            double a1 = (Math.toDegrees(Math.atan2(a.getX() - center.getX(), a.getY() - center.getY())) + 360) % 360;
            double a2 = (Math.toDegrees(Math.atan2(b.getX() - center.getX(), b.getY() - center.getX())) + 360) % 360;
            return (int) (a1 - a2);
        });
        points.toArray(new Point[0]);
    }

    private void addPoints(ArrayList<Point> pool, Point[] newPoints) {
        for (Point np : newPoints) {
            boolean found = false;
            for (Point p : pool) {
                if (p.isEqual(p.getX(), np.getX()) && p.isEqual(p.getY(), np.getY())) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                pool.add(np);
            }
        }
    }

    public Polygon getIntersectionOfPolygons(Polygon polygon) {
        ArrayList<Point> clippedCorners = new ArrayList<>();
        for (Point vertex : this.vertices) {
            if (isPointInsidePolygon(vertex, polygon)) {
                addPoints(clippedCorners, new Point[]{vertex});
            }
        }
        for (int i = 0; i < polygon.vertices.length; i++) {
            if (isPointInsidePolygon(polygon.vertices[i],this)) {
                addPoints(clippedCorners, new Point[]{polygon.vertices[i]});
            }
        }
        for (int i = 0, next = 1; i < this.vertices.length; i++, next = (i + 1 == this.vertices.length) ? 0 : i + 1) {
            Section section = new Section(this.vertices[i], this.vertices[next]);
            Point[] points = getIntersectionPoints(section, polygon);
            if (points != null)
            addPoints(clippedCorners, points);
        }
        sortVertices(clippedCorners);
        return new Polygon(clippedCorners.toArray(new Point[0]));
    }

    public Polygon getUnionOfPolygons(Polygon polygon) {
        ArrayList<Point> clippedCorners = new ArrayList<>();

        for (Point vertex : this.vertices) {
            addPoints(clippedCorners, new Point[]{vertex});
        }
        for (int i = 0; i < polygon.vertices.length; i++) {
            addPoints(clippedCorners, new Point[]{polygon.vertices[i]});
        }
        for (Point vertex : this.vertices) {
            if (isPointInsidePolygon(vertex, polygon)) {
                clippedCorners.remove(vertex);
            }
        }
        for (int i = 0; i < polygon.vertices.length; i++) {
            if (isPointInsidePolygon(polygon.vertices[i],this)) {
                clippedCorners.remove(polygon.vertices[i]);
            }
        }
        for (int i = 0, next = 1; i < this.vertices.length; i++, next = (i + 1 == this.vertices.length) ? 0 : i + 1) {
            Section section = new Section(this.vertices[i], this.vertices[next]);
            Point[] points = getIntersectionPoints(section, polygon);
            if (points != null)
                addPoints(clippedCorners, points);
        }
        sortVertices(clippedCorners);
        return new Polygon(clippedCorners.toArray(new Point[0]));
    }

    public float getSubtractionAreaOfPolygons(Polygon polygon) {
        return this.area() - getIntersectionOfPolygons(polygon).area();
    }
}
