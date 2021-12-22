import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.vsu.cs.bobrovskaya.Section;

class sectionTest {
    @Test
    void intersectsTest() {
        Section section1 = new Section(0, 0, 3, 3);
        Section section2 = new Section(0, 3, 3, 1);
        boolean result = section1.intersects(section2);
        Assertions.assertTrue(result);
    }
    @Test
    void intersectsTest2() {
        Section section1 = new Section(0, 0, 0, 3);
        Section section2 = new Section(2, 2, 3, 3);
        boolean result = section1.intersects(section2);
        Assertions.assertFalse(result);
    }
    @Test
    void intersectsTest3() {
        Section section1 = new Section(0, 0, 0, 3);
        Section section2 = new Section(0, 0, 3, 3);
        boolean result = section1.intersects(section2);
        Assertions.assertTrue(result);
    }
}