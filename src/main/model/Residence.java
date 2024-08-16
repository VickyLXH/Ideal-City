package model;

import org.json.JSONObject;
import persistence.Writable;

// Represent houses with DEFAULT_NAME, level and revenue
public class Residence implements Facility, Writable {
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_REVENUE = 50;
    private static final int INCREMENT_REVENUE = 50;
    private static final int UPGRADE_FEE = 100;
    private static final double DEFAULT_EFFICIENCY = 1;
    private static final String NAME = "Residence";


    private String name;
    private int level;
    private int revenue;


    // EFFECTS: initiate a new house with given name and default level and revenue.
    public Residence() {
        this.name = NAME;
        this.level = DEFAULT_LEVEL;
        this.revenue = DEFAULT_REVENUE;
    }

    // EFFECTS: initiate a new house with given name, level and revenue.
    public Residence(int l, int r) {
        this.name = NAME;
        this.level = l;
        this.revenue = r;
    }

    // EFFECTS: get the name of Residence.
    public String getName() {
        return name;
    }


    // EFFECTS: get the level of house.
    @Override
    public int getLevel() {
        return level;
    }

    // EFFECTS: get revenue of house.
    @Override
    public int getRevenue() {
        return revenue;
    }

    // EFFECTS: get the upgrade fee for the house, which is 100 times level.
    @Override
    public int getUpgradeFee() {
        return level * UPGRADE_FEE;
    }


    // MODIFIES: this.
    // EFFECTS: upgrade the house by adding 1 to level and adding increment to revenue.
    @Override
    public void upgrade() {
        this.level += 1;
        this.revenue += INCREMENT_REVENUE;
    }

    // EFFECTS: display the information of the house, including name, level and revenue.
    @Override
    public String displayInformation() {
        return "Type : Residence" + ", Level: " + getLevel() + ", Revenue: " + getRevenue();
    }

    // EFFECTS: get DEFAULT_EFFICIENCY
    public double getEfficiency() {
        return DEFAULT_EFFICIENCY;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("level", level);
        json.put("revenue", revenue);
        return json;
    }
}


