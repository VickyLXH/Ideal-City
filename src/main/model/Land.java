package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represent a land that contains facilities. Cannot exceed MAX_SIZE
public class Land implements Writable {
    private static final int MAX_SIZE = 9;


    private ArrayList<Facility> facilities;

    // EFFECTS: create an empty land.
    public Land() {
        facilities = new ArrayList<>();
    }

    // REQUIRES: land is not full
    // MODIFIES: this
    // EFFECTS: add a house on land.
    public void addHouse(Facility h) {
        facilities.add(h);
    }

    // REQUIRES: h is in facilities and it only appears once
    // MODIFIES: this
    // EFFECTS: remove the given house from land.
    public void removeHouse(Facility h) {
        facilities.remove(h);
    }

    // EFFECTS: get the number of houses on the land.
    public int size() {
        return facilities.size();
    }

    // EFFECTS: get the index i house from land
    public Facility get(int i) {
        return facilities.get(i);
    }

    // EFFECTS: return true if size reached MAX_SIZE; else return false.
    public boolean isFull() {
        return size() == MAX_SIZE;
    }

    // EFFECTS: return true if land is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // REQUIRES: name.equals("Residence") || name.equals("Restaurant") || name.equals("Office building") ||
    // name.equals("Police office") || name.equals("Fire department")
    // EFFECTS: return number of the given facility on the land.
    public int contains(String name) {
        int count = 0;
        for (Facility i: facilities) {
            if (i.getName() == name) {
                count++;
            }
        }
        return count;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("facilities", toJson2());
        return json;
    }

    public JSONArray toJson2() {
        JSONArray jsonArray = new JSONArray();

        for (Facility t : facilities) {
            jsonArray.put(t.toJson());
        }

        return jsonArray;
    }


}
