import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.bobrovskaya.Point;
import ru.vsu.cs.bobrovskaya.Polygon;
import ru.vsu.cs.bobrovskaya.Section;

class polygonTest {
    @Test
    void areaTest() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(0,3);
        Point p3 = new Point(2,0);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3});
        float result = polygon.area();
        Assertions.assertEquals(3f, result);
    }
    @Test
    void areaTest2() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(4,0);
        Point p3 = new Point(4,3);
        Point p4 = new Point(0,3);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        float result = polygon.area();
        Assertions.assertEquals(12f, result);
    }
    @Test
    void areaTest3() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(4,0);
        Point p3 = new Point(4,4);
        Point p4 = new Point(0,4);
        Point p5 = new Point(-1,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4, p5});
        float result = polygon.area();
        Assertions.assertEquals(18f, result);
    }
    @Test
    void areaTest4() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Point p5 = new Point(0,4);
        Point p6 = new Point(-1,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4, p5, p6});
        float result = polygon.area();
        Assertions.assertEquals(12f, result);
    }
    @Test
    void areaTest5() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Point p5 = new Point(0,4);
        Point p6 = new Point(1,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4, p5, p6});
        float result = polygon.area();
        Assertions.assertEquals(8f, result);
    }
    @Test
    void isPointInsidePolygonTest() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(2,2);
        Assertions.assertTrue( polygon.isPointInsidePolygon(p5, polygon));
    }
    @Test
    void isPointInsidePolygonTest2() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(0,2);
        Assertions.assertFalse( polygon.isPointInsidePolygon(p5, polygon));
    }
    @Test
    void isPointInsidePolygonTest3() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(0,0);
        Assertions.assertTrue( polygon.isPointInsidePolygon(p5, polygon));
    }

    @Test
    void isPointInsidePolygonTest4() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Point p5 = new Point(2,3);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4, p5});
        Point p6 = new Point(1,2);
        Assertions.assertFalse(polygon.isPointInsidePolygon(p6, polygon));
    }
    @Test
    void isPointInsidePolygonTest5() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(3,2);
        Point p4 = new Point(2,4);
        Point p5 = new Point(2,3);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4, p5});
        Point p6 = new Point(1,1);
        Assertions.assertTrue(polygon.isPointInsidePolygon(p6, polygon));
    }
    @Test
    void getIntersectionPointsTest1() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(0,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(-1,1);
        Point p6 = new Point(1,1);
        Section section = new Section(p5, p6);
        Point res = new Point(0, 1);
        Point[] expected = new Point[]{res};
        Point[] actual = polygon.getIntersectionPoints(section, polygon);
        Assertions.assertEquals(expected[0], actual[0]);
    }
    @Test
    void getIntersectionPointsTest2() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(0,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(1,-1);
        Point p6 = new Point(1,-2);
        Section section = new Section(p5, p6);
        Point[] actual = polygon.getIntersectionPoints(section, polygon);
        Assertions.assertNull(actual);
    }
    @Test
    void getIntersectionPointsTest3() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(0,2);
        Polygon polygon = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(-1,1);
        Point p6 = new Point(3,1);
        Section section = new Section(p5, p6);
        Point p7 = new Point(2, 1);
        Point p8 = new Point(0,1);
        Point[] expected = new Point[]{p7, p8};
        Point[] actual = polygon.getIntersectionPoints(section, polygon);
        for (int i = 0; i < actual.length; i++) {
            Assertions.assertEquals(expected[i], actual[i]);
        }
    }
    @Test
    void getIntersectionOfPolygonsTest() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Polygon polygon1 = new Polygon(new Point[] {p1, p2, p3});
        Point p5 = new Point(1,1);
        Point p6 = new Point(3,1);
        Point p7 = new Point(3,3);
        Polygon polygon2 = new Polygon(new Point[] {p5, p6, p7});
        Polygon actual = polygon1.getIntersectionOfPolygons(polygon2);
        Point p8 = new Point(2,1);
        Polygon expected = new Polygon(new Point[]{p5, p3, p8});

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void getIntersectionOfPolygonsTest2() {
        Point p1 = new Point(0,0);
        Point p2 = new Point(2,0);
        Point p3 = new Point(2,2);
        Point p4 = new Point(0,2);
        Polygon polygon1 = new Polygon(new Point[] {p1, p2, p3, p4});
        Point p5 = new Point(-1,0);
        Point p6 = new Point(1,0);
        Point p7 = new Point(1,2);
        Point p8 = new Point(-1,2);
        Polygon polygon2 = new Polygon(new Point[] {p5, p6, p7, p8});
        Polygon actual = polygon1.getIntersectionOfPolygons(polygon2);
        Polygon expected = new Polygon(new Point[]{p1, p4, p7, p6});

        Assertions.assertEquals(expected.vertices[0].getX(), actual.vertices[0].getX());
    }
    @Test
    void getUnionOfPolygonsTest() {
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
        Point p9 = new Point(1, 0);
        Point p10 = new Point(2, 1);
        Polygon actual = polygon1.getUnionOfPolygons(polygon2);
        Polygon expected = new Polygon(new Point[]{p3, p10, p7, p6, p5, p9, p1, p4});

        Assertions.assertEquals(expected, actual);
    }
    @Test
    void getUnionOfPolygonAreaTest() {
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
        Polygon poly = polygon1.getUnionOfPolygons(polygon2);
        float actual = poly.area();
        float expected = 7f;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSubAreaOfPolygonsTest() {
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
        float actual = polygon1.getSubtractionAreaOfPolygons(polygon2);
        float expected = 3;
        Assertions.assertEquals(expected, actual);
    }
}
