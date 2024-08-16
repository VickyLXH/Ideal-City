package persistence;

import model.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JsonWriterTest extends JsonTest {
    //NOTE TO CPSC 210 STUDENTS: the strategy in designing tests for the JsonWriter is to
    //write data to a file and then use the reader to read it back in and check that we
    //read in a copy of what was written out.

    @Test
    void testWriterInvalidFile() {
        try {
            Player wr = new Player();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            Player p = new Player();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyIdealCity.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyIdealCity.json");
            p = reader.read();
            assertEquals(300, p.getAccount().getBalance());
            assertEquals(0,p.getLand().size());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            Player p = new Player();
            p.getAccount().setBalance(200.0);
            p.getLand().addHouse(new Residence(1,50));
            p.getLand().addHouse(new Business("Restaurant", 2,400));
            p.getLand().addHouse(new Security("Police office", 3,300));
            JsonWriter writer = new JsonWriter("./data/testWriterIdealCity.json");
            writer.open();
            writer.write(p);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterIdealCity.json");
            p = reader.read();
            assertEquals(200.0, p.getAccount().getBalance());
            assertEquals(3,p.getLand().size());
            checkFacility(p.getLand().get(0), "Residence", 1,50);
            checkFacility(p.getLand().get(1), "Restaurant", 2,400);
            checkFacility(p.getLand().get(2), "Police office", 3,300);

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}