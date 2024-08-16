package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

// Represent a player that has land and bank account
public class Player implements Writable {
    private static final double PUNISHMENT_FOR_NO_RESIDENCE = 0.5;
    private static final int RESIDENCE_BUILD_FEE = 100;
    private static final int BUSINESS_BUILD_FEE = 200;
    private static final int SECURITY_BUILD_FEE = 300;

    private Land land;
    private Account account;

    // EFFECTS: create a new player
    public Player() {
        land = new Land();
        account = new Account();
    }

    // MODIFIES: this
    // EFFECTS: return true and build a new Residence if account balance enough;
    // else return false and do nothing;
    public boolean buildResidence() {
        if (account.withdraw(RESIDENCE_BUILD_FEE)) {
            Facility r = new Residence();
            land.addHouse(r);
            Event e = new Event("Residence added!");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    // REQUIRES: name.equals("Restaurant") || name.equals("Office building")
    // MODIFIES: this
    // EFFECTS: return true and build a new Security if account balance enough;
    // else return false and do nothing;
    public boolean buildBusiness(String name) {
        if (account.withdraw(BUSINESS_BUILD_FEE)) {
            Facility e = new Business(name);
            land.addHouse(e);
            Event l = new Event(name + " added!");
            EventLog.getInstance().logEvent(l);
            return true;
        }
        return false;
    }

    // REQUIRES: name.equals("Police office") || name.equals("Fire department")
    // MODIFIES: this
    // EFFECTS: return true and build a new Business if account balance enough;
    // else return false and do nothing;
    public boolean buildSecurity(String name) {
        if (account.withdraw(SECURITY_BUILD_FEE)) {
            Facility s = new Security(name);
            land.addHouse(s);
            Event e = new Event(name + " added!");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: return true and upgrade the given house if treasure enough; else return false.
    public boolean upgradeHouse(Facility h) {
        if (account.withdraw(h.getUpgradeFee())) {
            h.upgrade();
            Event e = new Event(h.getName() + " upgraded!");
            EventLog.getInstance().logEvent(e);
            return true;
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: remove the ith facility from land.
    public void removeHouse(Facility h) {
        land.removeHouse(h);
        Event e = new Event(h.getName() + " removed!");
        EventLog.getInstance().logEvent(e);
    }

    // MODIFIES: this
    // EFFECTS: add revenue of all houses on the land to account.
    public void addRevenue() {
        for (int i = 0; i < land.size(); i++) {
            Facility h = land.get(i);
            account.deposit(countEfficiency() * h.getRevenue());
        }
        Event e = new Event("Revenue added!");
        EventLog.getInstance().logEvent(e);
    }

    // EFFECTS: display the information of given house.
    public String displayHouse(Facility h) {
        Event e = new Event(h.getName() + " displayed!");
        EventLog.getInstance().logEvent(e);
        return h.displayInformation();
    }

    // EFFECTS: count for efficiency
    public double countEfficiency() {
        double bonus = 1;

        if (land.contains("Residence") == 0) {
            bonus = PUNISHMENT_FOR_NO_RESIDENCE;
        }

        for (int i = 0; i < land.size(); i++) {
            Facility h = land.get(i);
            bonus = bonus * h.getEfficiency();
        }

        return bonus;
    }

    // EFFECTS: return land
    public Land getLand() {
        return land;
    }

    // EFFECTS: return account
    public Account getAccount() {
        return account;
    }

    // EFFECTS: return a string that display land information
    public String displayLand() {
        String s = "Land:";
        for (int i = 0; i < land.size(); i++) {
            if (i == land.size() - 1) {
                s += " " + land.get(i).getName() + ".";
            } else {
                s += " " + land.get(i).getName() + ",";
            }
        }
        return s;
    }

    // EFFECTS: return string that shows account information
    public String displayAccount() {
        return "Your Account balance: " + account.getBalance();
    }


    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("land", land.toJson());
        json.put("account", account.toJson());
        return json;
    }

}

