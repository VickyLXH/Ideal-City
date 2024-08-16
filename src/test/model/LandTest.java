package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LandTest {
    private Land l1;
    private Facility f1;
    private Facility f2;
    private Facility f3;

    @BeforeEach
    public void runBefore() {
        l1 = new Land();
        f1 = new Residence();
        f2 = new Security("Police office");
        f3 = new Security("Police office");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, l1.size());
    }

    @Test
    public void testAddHouse() {
        l1.addHouse(f1);
        assertEquals(1,l1.size());
        assertEquals(1,l1.contains("Residence"));
        l1.addHouse(f1);
        assertEquals(2,l1.size());
        assertEquals(2,l1.contains("Residence"));
        l1.addHouse(f2);
        assertEquals(3,l1.size());
        assertEquals(1,l1.contains("Police office"));
    }

    @Test
    public void testRemoveHouse() {
        l1.addHouse(f1);
        assertEquals(1,l1.contains("Residence"));
        l1.addHouse(f2);
        assertEquals(1,l1.contains("Police office"));
        assertEquals(2,l1.size());
        l1.removeHouse(f2);
        assertEquals(0,l1.contains("Police office"));
        assertEquals(1,l1.size());
        l1.removeHouse(f1);
        assertEquals(0,l1.contains("Residence"));
        assertEquals(0,l1.size());
    }

    @Test
    public void testSize() {
        assertEquals(0, l1.size());
        l1.addHouse(f1);
        assertEquals(1, l1.size());
        l1.addHouse(f2);
        assertEquals(2, l1.size());
        l1.addHouse(f3);
        assertEquals(3, l1.size());
    }

    @Test
    public void testGet() {
        assertEquals(0, l1.size());
        l1.addHouse(f1);
        assertEquals(1, l1.size());
        l1.addHouse(f2);
        assertEquals(2, l1.size());
        l1.addHouse(f3);
        assertEquals(3, l1.size());
        assertEquals(f1, l1.get(0));
        assertEquals(f2, l1.get(1));
        assertEquals(f3, l1.get(2));
    }

    @Test
    public void testIsFull() {
        assertFalse(l1.isFull());
        assertTrue(l1.isEmpty());
        assertEquals(0, l1.size());
        l1.addHouse(f1);
        l1.addHouse(f2);
        l1.addHouse(f3);
        l1.addHouse(f3);
        l1.addHouse(f3);
        l1.addHouse(f3);
        assertFalse(l1.isFull());
        l1.addHouse(f3);
        l1.addHouse(f3);
        l1.addHouse(f3);
        assertTrue(l1.isFull());
        assertEquals(9, l1.size());

    }
    @Test
    public void testIsEmpty() {
        assertFalse(l1.isFull());
        assertTrue(l1.isEmpty());
        assertEquals(0, l1.size());
        l1.addHouse(f1);
        l1.addHouse(f2);
        l1.addHouse(f3);
        l1.addHouse(f3);
        l1.addHouse(f3);
        l1.addHouse(f3);
        assertFalse(l1.isEmpty());
    }

    @Test
    public void testContains() {
        assertEquals(0,l1.contains("Residence"));
        l1.addHouse(f1);
        assertEquals(1,l1.contains("Residence"));
        l1.addHouse(f2);
        l1.addHouse(f3);
        assertEquals(2,l1.contains("Police office"));
    }


}
