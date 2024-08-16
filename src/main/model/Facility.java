package model;

import org.json.JSONObject;

// represent all facilities that can be build on land
public interface Facility {

    String getName();

    // EFFECTS: get the level of house.
    int getLevel();

    // EFFECTS: get revenue of house.
    int getRevenue();

    // EFFECTS: get the upgrade fee for the house, which is UPGRADE_FEE times level.
    int getUpgradeFee();

    // MODIFIES: this.
    // EFFECTS: upgrade the house by adding 1 to level and adding increment to revenue.
    void upgrade();

    // EFFECTS: display the information of the house, including name, level and revenue.
    String displayInformation();

    // EFFECTS: return efficiency
    double getEfficiency();

    JSONObject toJson();

}




