package ui;

import model.*;
import persistence.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// Ideal City Application (non-graphic form)
public class IdealCityApp {
    private static final String JSON_STORE = "./data/IdealCity.json";
    private Player player;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: run a new game
    public IdealCityApp() {
        input = new Scanner(System.in);
        player = new Player();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runIdealCity();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runIdealCity() {
        boolean keepGoing = true;
        String command = null;
        init();

        while (keepGoing) {
            doDisplay();
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("\nGoodbye!");
    }

    // MODIFIES: this
    // EFFECTS: initializes player
    private void init() {
        input.useDelimiter("\n");
        System.out.println("Welcome to Ideal City! This is a game that allows you operate your own city");
        System.out.println("\nHere is an explanation for all action you can take:");
        System.out.println("\n->build: you are allowed to build three types of facilities: ");
        System.out.println("\t-Residence: residence has relatively low income but it' s necessary! ");
        System.out.print("\t\t\t\tIf people can't find a place to live, they will be less productive!");
        System.out.println("\n\t-Business: business building can bring high income!");
        System.out.println("\t-Security: Security facility would bring higher efficiency!");
        System.out.print("\t\t\t\tYour total income will increase with your efficiency.");
        System.out.println("\n->select: Select a house to remove/upgrade it!");
        System.out.println("->getRevenue: Deposit the total revenue to your account!\n");

    }

    // MODIFIES: this
    // EFFECTS: process user command
    private void processCommand(String command) {
        if (command.equals("b")) {
            doBuild();
        } else if (command.equals("e")) {
            doSelect();
        } else if (command.equals("d")) {
            doDisplay();
        } else if (command.equals("g")) {
            dogetRevenue();
        } else if (command.equals("s")) {
            saveIdealCity();
        } else if (command.equals("l")) {
            loadIdealCity();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void saveIdealCity() {
        try {
            jsonWriter.open();
            jsonWriter.write(player);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads workroom from file
    private void loadIdealCity() {
        try {
            player = jsonReader.read();
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: add revenue to player
    private void dogetRevenue() {
        player.addRevenue();
    }

    // EFFECTS: display player information
    private void doDisplay() {
        System.out.println("Display city information: ");
        System.out.println(player.displayLand());
        System.out.println(player.displayAccount());
        System.out.println("Efficiency: " + player.countEfficiency());
    }

    // EFFECTS: select a specific facility on land
    private void doSelect() {
        int n = 0;
        String command = null;
        boolean keepGoing1 = true;
        boolean keepGoing2 = true;
        if (player.getLand().isEmpty()) {
            System.out.println("\nThe land is empty. Return to main menu.");
            return;
        }
        while (keepGoing1) {
            System.out.println("\nInput # of the house(start from 1):");
            n = input.nextInt();
            if (n >= 1 && n <= player.getLand().size()) {
                keepGoing1 = false;
            } else {
                System.out.println("\nFailed. Invalid input. Please try again.");
            }
        }
        n--;
        selectNext(player.getLand().get(n));
    }

    // MODIFIES: this
    // EFFECTS: take actions to selected house.
    private void selectNext(Facility h) {
        String command = null;
        boolean keepGoing = true;
        while (keepGoing) {
            System.out.println(player.displayHouse(h));
            selectMenu(h);
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("r") || command.equals("u")) {
                processSelectNext(command, h);
            } else if (command.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("\nFailed. Invalid input. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: process user command after select
    private void processSelectNext(String command, Facility h) {

        if (command.equals("r")) {
            player.removeHouse(h);
            System.out.println("\nHouse has been removed.");
        } else if (command.equals("u")) {
            if (player.upgradeHouse(h)) {
                System.out.println("\nHouse has been upgraded.");
            } else {
                System.out.println("\nFailed. Insufficient Money.");
                System.out.println(player.displayAccount());
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: create new building on land
    private void doBuild() {
        String command = null;
        boolean keepGoing = true;
        while (keepGoing) {
            buildMenu();
            command = input.next();
            command = command.toLowerCase();
            if (player.getLand().isFull()) {
                System.out.println("Failed. Land is Full.\n");
                keepGoing = false;
            } else if (command.equals("r") || command.equals("e") || command.equals("s")) {
                processBuild(command);
                keepGoing = false;
            } else if (command.equals("q")) {
                keepGoing = false;
            } else {
                System.out.println("Failed. Invalid input. Please try again.\n");
            }
        }

    }

    // REQUIRES: command.equals("r") || command.equals("e") || command.equals("s")
    // MODIFIES: this
    // EFFECTS: precess build with given command
    private void processBuild(String command) {
        if (command.equals("r")) {
            if (player.buildResidence()) {
                System.out.println("House has been built!\n");
            } else {
                System.out.println("Failed. Insufficient Money.\n");
            }
        } else if (command.equals("e")) {
            processBuildBusiness();
        } else {
            processBuildSecurity();
        }
    }

    // MODIFIES: this
    // EFFECTS: build business facility
    private void processBuildBusiness() {
        String command;
        boolean keepGoing = true;
        while (keepGoing) {
            buildBusinessMenu();
            command = input.next();
            if (command.equals("r")) {
                if (player.buildBusiness("Restaurant")) {
                    System.out.println("Restaurant has been built!\n");
                } else {
                    System.out.println("Failed. Insufficient Money.\n");
                }
                keepGoing = false;
            } else if (command.equals("o")) {
                if (player.buildBusiness("Office building")) {
                    System.out.println("Office building has been built!\n");
                } else {
                    System.out.println("Failed. Insufficient Money.\n");
                }
                keepGoing = false;
            } else {
                System.out.println("Failed. Invalid input. Please try again.");
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: build security
    private void processBuildSecurity() {
        String command;
        boolean keepGoing = true;
        while (keepGoing) {
            buildSecurityMenu();
            command = input.next();
            if (command.equals("p")) {
                if (player.buildSecurity("Police office")) {
                    System.out.println("Police office has been built!\n");
                } else {
                    System.out.println("Failed. Insufficient Money.\n");
                }
                keepGoing = false;
            } else if (command.equals("f")) {
                if (player.buildSecurity("Fire department")) {
                    System.out.println("Fire department has been built!\n");
                } else {
                    System.out.println("Failed. Insufficient Money.\n");
                }
                keepGoing = false;
            } else {
                System.out.println("Failed. Invalid input. Please try again.");
            }
        }
    }

    // EFFECTS: display security menu
    private void buildSecurityMenu() {
        System.out.println("\nSelect one of these:");
        System.out.println("\tp -> Police office");
        System.out.println("\tf -> Fire department");
    }

    // EFFECTS: display business menu
    private void buildBusinessMenu() {
        System.out.println("\nSelect one of these:");
        System.out.println("\tr -> Restaurant");
        System.out.println("\to -> Office building");
    }

    // EFFECTS: displays build menu of options to user
    private void buildMenu() {
        System.out.println("\nSelect the type of House:");
        System.out.println("\tr -> Residence: $100");
        System.out.println("\te -> Business: $200");
        System.out.println("\ts -> Security: $300");
        System.out.println("\tq -> Quit");
    }

    // EFFECTS: displays the main menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\tb -> build");
        System.out.println("\te -> select");
        System.out.println("\td -> display");
        System.out.println("\tg -> getRevenue");
        System.out.println("\ts -> save the game to file");
        System.out.println("\tl -> load the game from file");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: displays select menu of options to user
    private void selectMenu(Facility h) {
        System.out.println("\nSelect action:");
        System.out.println("\tr -> remove");
        System.out.println("\tu -> upgrade $" + h.getUpgradeFee());
        System.out.println("\tq -> quit");
    }
}
