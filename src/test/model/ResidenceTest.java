package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class ResidenceTest {
    Facility s1;
    Facility s2;


    @BeforeEach
    public void runBefore() {
        s1 = new Residence();
        s2 = new Residence(2,100);
    }

    @Test
    public void testConstructor() {
        assertEquals("Residence", s1.getName());
        assertEquals("Residence", s2.getName());
        assertEquals(1, s1.getLevel());
        assertEquals(2, s2.getLevel());
        assertEquals(50, s1.getRevenue());
        assertEquals(100, s2.getRevenue());
    }

    @Test
    public void testGetName() {
        assertEquals("Residence", s1.getName());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, s1.getLevel());
        s1.upgrade();
        assertEquals(2, s1.getLevel());
    }

    @Test
    public void testGetRevenue() {
        assertEquals(50, s1.getRevenue());
        s1.upgrade();
        assertEquals(100, s1.getRevenue());
    }

    @Test
    public void testUpgrade() {
        assertEquals(1, s1.getLevel());
        assertEquals(50, s1.getRevenue());
        s1.upgrade();
        assertEquals(2, s1.getLevel());
        assertEquals(100, s1.getRevenue());
        s1.upgrade();
        s1.upgrade();
        assertEquals(4, s1.getLevel());
        assertEquals(200, s1.getRevenue());
    }

    @Test
    public void testGetUpgradeFee() {
        assertEquals(100, s1.getUpgradeFee());
        s1.upgrade();
        assertEquals(200, s1.getUpgradeFee());
        s1.upgrade();
        assertEquals(300, s1.getUpgradeFee());
    }

    @Test
    public void testDisplayInformation() {
        assertEquals("Type : Residence, Level: 1, Revenue: 50", s1.displayInformation());
        s1.upgrade();
        assertEquals("Type : Residence, Level: 2, Revenue: 100", s1.displayInformation());
    }

    @Test
    public void testGetEfficiency() {
        assertEquals(1, s1.getEfficiency());
        s1.upgrade();
        assertEquals(1, s1.getEfficiency());
        s1.upgrade();
        s1.upgrade();
        assertEquals(1, s1.getEfficiency());
    }
}
