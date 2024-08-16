package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a bank account with balance inside
public class Account implements Writable {
    private static final int DEFAULT_TREASURE = 300;

    private double balance;

    // EFFECTS: creates a new account with DEFAULT_TREASURE in balance.
    public Account() {
        balance = DEFAULT_TREASURE;
    }

    // MODIFIES: this
    // Effects: set the balance (used for tests only)
    public void setBalance(Double d) {
        balance = d;
    }

    // EFFECTS: get balance of account
    public double getBalance() {
        return balance;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: add amount to balance
    public void deposit(double amount) {
        balance += amount;
    }

    // REQUIRES: amount >= 0
    // MODIFIES: this
    // EFFECTS: return true and withdraw money if balance - amount >= 0; false otherwise
    public boolean withdraw(double amount) {
        if (balance - amount >= 0) {
            balance = balance - amount;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("balance", balance);
        return json;
    }
}
