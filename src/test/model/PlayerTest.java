package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    private Player p;
    private Facility f1;
    private Facility f2;
    private Facility f3;

    @BeforeEach
    public void runBefore() {
        p = new Player();
        f1 = new Residence();
        f2 = new Security("Police office");
        f3 = new Security("Police office");
    }

    @Test
    public void testConstructor() {
        assertEquals(300, p.getAccount().getBalance());
        assertTrue(p.getLand().isEmpty());
    }

    @Test
    public void testBuildResidence() {
        assertTrue(p.buildResidence());
        assertTrue(p.buildResidence());
        assertTrue(p.buildResidence());
        assertEquals(0,p.getAccount().getBalance());
        assertFalse(p.buildResidence());
    }

    @Test
    public void testBuildBusiness() {
        p.getAccount().deposit(100);
        assertTrue(p.buildBusiness("Restaurant"));
        assertTrue(p.buildBusiness("Office building"));
        assertEquals(0,p.getAccount().getBalance());
        assertFalse(p.buildBusiness("Restaurant"));
    }

    @Test
    public void testBuildSecurity() {
        p.getAccount().deposit(300);
        assertTrue(p.buildSecurity("Police office"));
        assertTrue(p.buildSecurity("Fire department"));
        assertEquals(0,p.getAccount().getBalance());
        assertFalse(p.buildSecurity("Fire department"));
    }

    @Test
    public void testUpgradeHouse() {
        p.getAccount().deposit(100);
        p.buildResidence();
        assertEquals(1,p.getLand().get(0).getLevel());
        assertEquals(50,p.getLand().get(0).getRevenue());
        assertTrue(p.upgradeHouse(p.getLand().get(0)));
        assertEquals(2,p.getLand().get(0).getLevel());
        assertEquals(100,p.getLand().get(0).getRevenue());
        assertTrue(p.upgradeHouse(p.getLand().get(0)));
        assertEquals(3,p.getLand().get(0).getLevel());
        assertEquals(150,p.getLand().get(0).getRevenue());
        assertFalse(p.upgradeHouse(p.getLand().get(0)));
        assertEquals(3,p.getLand().get(0).getLevel());
        assertEquals(150,p.getLand().get(0).getRevenue());
    }

    @Test
    public void testRemoveHouse() {
        p.getAccount().deposit(700);
        assertEquals(0,p.getLand().size());
        p.buildResidence();
        assertEquals(1,p.getLand().size());
        assertEquals(1,p.getLand().contains("Residence"));
        p.buildBusiness("Restaurant");
        p.buildBusiness("Restaurant");
        p.buildBusiness("Restaurant");
        assertEquals(4,p.getLand().size());
        assertEquals(3,p.getLand().contains("Restaurant"));
        p.removeHouse(p.getLand().get(0));
        assertEquals(3,p.getLand().size());
        assertEquals(0,p.getLand().contains("Residence"));
        p.removeHouse(p.getLand().get(0));
        assertEquals(2,p.getLand().size());
        assertEquals(2,p.getLand().contains("Restaurant"));

    }

    @Test
    public void testAddRevenue() {
        assertTrue(p.getLand().isEmpty());
        p.getAccount().deposit(400);
        p.addRevenue();
        assertEquals(700, p.getAccount().getBalance());
        p.buildBusiness("Restaurant");
        p.addRevenue();
        assertEquals(600, p.getAccount().getBalance());
        p.buildResidence();
        assertEquals(500, p.getAccount().getBalance());
        p.addRevenue();
        assertEquals(750, p.getAccount().getBalance());
        p.buildSecurity("Police office");
        assertEquals(450, p.getAccount().getBalance());
        p.addRevenue();
        assertEquals(870, p.getAccount().getBalance());
    }

    @Test
    public void testDisplayHouse() {
        p.buildResidence();
        assertEquals("Type : Residence, Level: 1, Revenue: 50",p.displayHouse(p.getLand().get(0)));
        p.upgradeHouse(p.getLand().get(0));
        assertEquals("Type : Residence, Level: 2, Revenue: 100",p.displayHouse(p.getLand().get(0)));
    }

    @Test
    public void testCountEfficiency() {
        p.getAccount().deposit(1000);
        assertEquals(0.5,p.countEfficiency());
        p.buildResidence();
        assertEquals(1,p.countEfficiency());
        p.buildSecurity("Fire department");
        assertEquals(1.2,p.countEfficiency());
        p.upgradeHouse(p.getLand().get(1));
        assertEquals(1.3,p.countEfficiency());
        p.buildSecurity("Fire department");
        assertEquals(1.56,p.countEfficiency());
    }

    @Test
    public void testGetLand() {
        assertTrue(p.getLand().isEmpty());
        p.buildResidence();
        assertEquals(1,p.getLand().size());
    }

    @Test
    public void testGetAccount() {
        assertEquals(300,p.getAccount().getBalance());
        p.getAccount().deposit(300);
        assertEquals(600,p.getAccount().getBalance());
    }

    @Test
    public void testDisplayLand() {
        p.getAccount().deposit(1000);
        assertEquals("Land:", p.displayLand());
        p.buildResidence();
        assertEquals("Land: Residence.", p.displayLand());
        p.buildSecurity("Fire department");
        assertEquals("Land: Residence, Fire department.", p.displayLand());
        p.buildBusiness("Restaurant");
        assertEquals("Land: Residence, Fire department, Restaurant.", p.displayLand());
    }

    @Test
    public void testDisplayAccount() {
        assertEquals("Your Account balance: 300.0", p.displayAccount());
        p.buildResidence();
        assertEquals("Your Account balance: 200.0", p.displayAccount());
        p.buildResidence();
        p.buildResidence();
        assertEquals("Your Account balance: 0.0", p.displayAccount());
    }




}
