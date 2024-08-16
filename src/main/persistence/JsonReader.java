package persistence;

import model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Represents a reader that reads workroom from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Player read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parsePlayer(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        Player p = new Player();
        getAccount(p, jsonObject);
        getLand(p, jsonObject);
        return p;
    }

    public void getAccount(Player p, JSONObject jsonObject) {
        Double balance = jsonObject.getJSONObject("account").getDouble("balance");
        p.getAccount().setBalance(balance);
    }

    // MODIFIES: p
    // EFFECTS: parses facilities from JSON object and adds them to player land
    public void getLand(Player p, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONObject("land").getJSONArray("facilities");
        for (Object json : jsonArray) {
            JSONObject nextFacility = (JSONObject) json;
            addFacility(p, nextFacility);
        }
    }

    // MODIFIES: p
    // EFFECTS: parses facility from JSON object and adds it to player land
    public void addFacility(Player p, JSONObject jsonObject) {
        Facility facility;
        String name = jsonObject.getString("name");
        int level = jsonObject.getInt("level");
        int revenue = jsonObject.getInt("revenue");
        if (name.equals("Residence")) {
            facility = new Residence(level,revenue);
        } else if (name.equals("Restaurant") || name.equals("Office building")) {
            facility = new Business(name, level, revenue);
        } else {
            facility = new Security(name, level, revenue);
        }
        p.getLand().addHouse(facility);
    }
}
