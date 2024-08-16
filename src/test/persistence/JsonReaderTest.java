package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Player p = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyFile() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyIdealCity.json");
        try {
            Player p = reader.read();
            assertEquals(300, p.getAccount().getBalance());
            assertEquals(0,p.getLand().size());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralFile() {
        JsonReader reader = new JsonReader("./data/testReaderIdealCity.json");
        try {
            Player p = reader.read();
            assertEquals(1100, p.getAccount().getBalance());
            assertEquals(4,p.getLand().size());
            checkFacility(p.getLand().get(0),"Residence", 1, 50);
            checkFacility(p.getLand().get(1),"Restaurant", 2, 400);
            checkFacility(p.getLand().get(2),"Police office", 2, 200);
            checkFacility(p.getLand().get(3),"Office building", 3, 600);
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}