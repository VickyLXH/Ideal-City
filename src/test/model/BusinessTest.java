package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessTest {
    Facility b1;
    Facility b2;
    Facility b3;

    @BeforeEach
    public void runBefore() {
        b1 = new Business("Restaurant");
        b2 = new Business("Office building");
        b3 = new Business("Restaurant", 2, 400);
    }

    @Test
    public void testConstructor() {
        assertEquals("Restaurant", b1.getName());
        assertEquals("Office building", b2.getName());
        assertEquals("Restaurant", b3.getName());
        assertEquals(1, b1.getLevel());
        assertEquals(1, b2.getLevel());
        assertEquals(2, b3.getLevel());
        assertEquals(200, b1.getRevenue());
        assertEquals(200, b2.getRevenue());
        assertEquals(400, b3.getRevenue());
    }

    @Test
    public void testGetName() {
        assertEquals("Restaurant", b1.getName());
        assertEquals("Office building", b2.getName());
    }

    @Test
    public void testGetLevel() {
        assertEquals(1, b1.getLevel());
        b1.upgrade();
        assertEquals(2, b1.getLevel());
    }

    @Test
    public void testGetRevenue() {
        assertEquals(200, b2.getRevenue());
        b2.upgrade();
        assertEquals(400, b2.getRevenue());
    }

    @Test
    public void testUpgrade() {
        assertEquals(1, b1.getLevel());
        assertEquals(200, b1.getRevenue());
        b1.upgrade();
        assertEquals(2, b1.getLevel());
        assertEquals(400, b1.getRevenue());
        b1.upgrade();
        b1.upgrade();
        assertEquals(4, b1.getLevel());
        assertEquals(800, b1.getRevenue());
    }

    @Test
    public void testGetUpgradeFee() {
        assertEquals(200, b1.getUpgradeFee());
        b1.upgrade();
        assertEquals(400, b1.getUpgradeFee());
    }

    @Test
    public void testDisplayInformation() {
        assertEquals("Name: Restaurant, Type : Business, Level: 1, Revenue: 200", b1.displayInformation());
        b1.upgrade();
        assertEquals("Name: Restaurant, Type : Business, Level: 2, Revenue: 400", b1.displayInformation());
        assertEquals("Name: Office building, Type : Business, Level: 1, Revenue: 200",
                b2.displayInformation());
    }

    @Test
    public void testGetEfficiency() {
        assertEquals(1,b1.getEfficiency());
        b1.upgrade();
        assertEquals(1,b1.getEfficiency());
        assertEquals(1,b2.getEfficiency());
    }
}
