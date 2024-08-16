package model;

import org.json.JSONObject;
import persistence.Writable;

// represents business facility with only two types of name("Police office" or "Fire department"), level and revenue
public class Security implements Facility, Writable {
    private static final int DEFAULT_LEVEL = 1;
    private static final int DEFAULT_REVENUE = 100;
    private static final int INCREMENT_REVENUE = 100;
    private static final int UPGRADE_FEE = 400;
    private static final double INITIAL_EFFICIENCY = 1.20;
    private static final double INCREMENT_EFFICIENCY = 0.1;


    private String name;
    private int level;
    private int revenue;

    // REQUIRES: name.equals("Police office") || name.equals("Fire department")
    // EFFECTS: initiate a new house with given name and default level and revenue.
    public Security(String name) {
        this.name = name;
        this.level = DEFAULT_LEVEL;
        this.revenue = DEFAULT_REVENUE;
    }

    // REQUIRES: name.equals("Police office") || name.equals("Fire department")
    // EFFECTS: initiate a new house with given name, level and revenue.
    public Security(String name, int l, int r) {
        this.name = name;
        this.level = l;
        this.revenue = r;
    }

    @Override
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
        return "Name: " + getName() + ", Type : Security" + ", Level: " + getLevel() + ", Revenue: " + getRevenue();
    }

    // EFFECTS: return efficiency of the security building
    public double getEfficiency() {
        if (level == 1) {
            return INITIAL_EFFICIENCY;
        }
        return ((level - 1) *  INCREMENT_EFFICIENCY) + INITIAL_EFFICIENCY;
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
