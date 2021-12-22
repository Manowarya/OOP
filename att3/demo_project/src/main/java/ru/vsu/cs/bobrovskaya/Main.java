package ru.vsu.cs.bobrovskaya;


public class Main {

    public static void main(String[] args) {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(0,2);
        Polygon polygon1 = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(1,-1);
        Point p6 = new Point(3,-1);
        Point p7 = new Point(3,1);
        Point p8 = new Point(1,1);
        Polygon polygon2 = new Polygon(new Point[] {p5, p6, p7, p8});
        Polygon result = polygon1.getIntersectionOfPolygons(polygon2);
        System.out.println("Координаты точек фигуры, полученной при пересечении фигур: ");
        for (int i = 0; i < result.vertices.length; i++) {
            System.out.println(result.vertices[i].getX() + " " + result.vertices[i].getY());
        }
        System.out.println("Площадь полученной фигуры: " + result.area());
        result = polygon1.getUnionOfPolygons(polygon2);
        System.out.println("Координаты точек фигуры, полученной при объединении фигур: ");
        for (int i = 0; i < result.vertices.length; i++) {
            System.out.println(result.vertices[i].getX() + " " + result.vertices[i].getY());
        }
        System.out.println("Площадь полученной фигуры: " + result.area());
        System.out.println("Площадь фигуры при вычитании: " +   polygon1.getSubtractionAreaOfPolygons(polygon2));

    }
}
