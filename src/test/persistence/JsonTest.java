package persistence;

import model.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkFacility(Facility f, String name, int level, int revenue) {
        assertEquals(name, f.getName());
        assertEquals(level, f.getLevel());
        assertEquals(revenue, f.getRevenue());
    }
}
