package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SecurityTest {
    Facility s1;
    Facility s2;
    Facility s3;

    @BeforeEach
    public void runBefore() {
        s1 = new Security("Police office");
        s2 = new Security("Fire department");
        s3 = new Security("Fire department",2,150);
    }

    @Test
    public void testConstructor() {
        assertEquals("Police office", s1.getName());
        assertEquals("Fire department", s2.getName());
        assertEquals("Fire department", s3.getName());
        assertEquals(1, s1.getLevel());
        assertEquals(1, s2.getLevel());
        assertEquals(2, s3.getLevel());
        assertEquals(100, s1.getRevenue());
        assertEquals(100, s2.getRevenue());
        assertEquals(150, s3.getRevenue());
    }

    @Test
    public void testGetName() {
        assertEquals("Police office", s1.getName());
        assertEquals("Fire department", s2.getName());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, s1.getLevel());
        s1.upgrade();
        assertEquals(2, s1.getLevel());
    }

    @Test
    public void testGetRevenue() {
        assertEquals(100, s2.getRevenue());
        s2.upgrade();
        assertEquals(200, s2.getRevenue());
    }

    @Test
    public void testUpgrade() {
        assertEquals(1, s1.getLevel());
        assertEquals(100, s1.getRevenue());
        s1.upgrade();
        assertEquals(2, s1.getLevel());
        assertEquals(200, s1.getRevenue());
        s1.upgrade();
        s1.upgrade();
        assertEquals(4, s1.getLevel());
        assertEquals(400, s1.getRevenue());
    }

    @Test
    public void testGetUpgradeFee() {
        assertEquals(400, s1.getUpgradeFee());
        s1.upgrade();
        assertEquals(800, s1.getUpgradeFee());
        s1.upgrade();
        assertEquals(1200, s1.getUpgradeFee());
    }

    @Test
    public void testDisplayInformation() {
        assertEquals("Name: Police office, Type : Security, Level: 1, Revenue: 100", s1.displayInformation());
        s1.upgrade();
        assertEquals("Name: Police office, Type : Security, Level: 2, Revenue: 200", s1.displayInformation());
        assertEquals("Name: Fire department, Type : Security, Level: 1, Revenue: 100",
                s2.displayInformation());
    }

    @Test
    public void testGetEfficiency() {
        assertEquals(1.2, s1.getEfficiency());
        s1.upgrade();
        assertEquals(1.3, s1.getEfficiency());
        s1.upgrade();
        s1.upgrade();
        assertEquals(1.5, s1.getEfficiency());
        assertEquals(1.2, s2.getEfficiency());
    }
}
