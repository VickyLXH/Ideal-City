package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import model.*;
import model.Event;
import persistence.JsonReader;
import persistence.JsonWriter;

// Ideal City Application (graphic form)
public class IdealCityGUI {
    private static final String JSON_STORE = "./data/IdealCity.json";
    private static final int RESIDENCE_BUILD_FEE = 100;
    private static final int BUSINESS_BUILD_FEE = 200;
    private static final int SECURITY_BUILD_FEE = 300;

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Player player;
    private JFrame frame;
    private JButton menu;
    private JButton income;
    private JLabel efficiency;
    private HashMap<JButton,Facility> houseButton;
    private ArrayList<Integer> xs;
    private ArrayList<Integer> ys;


    public static void main(String[] args) {
        new IdealCityGUI();
    }

    // EFFECTS: initiate all the fields
    public IdealCityGUI() {
        player = new Player();
        houseButton = new HashMap<>();
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        xs = new ArrayList<>();
        ys = new ArrayList<>();
        initFrame();
        initX();
        initY();
    }

    // MODIFIES: this
    // EFFECTS: initiate all the y-axis of the houseButtons
    private void initY() {
        ys.add(80);
        ys.add(300);
        ys.add(350);
        ys.add(90);
        ys.add(295);
        ys.add(200);
        ys.add(240);
        ys.add(20);
        ys.add(180);
    }

    // MODIFIES: this
    // EFFECTS: initiate all the x-axis of the houseButtons
    private void initX() {
        xs.add(290);
        xs.add(270);
        xs.add(80);
        xs.add(430);
        xs.add(495);
        xs.add(305);
        xs.add(100);
        xs.add(420);
        xs.add(405);
    }

    // MODIFIES: this
    // EFFECTS: initiate JFrame including background image, menu button and income button.
    private void initFrame() {
        frame = new JFrame();
        frame.addWindowListener(new MyWindowListener());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon imageIcon = new ImageIcon("./data/CityMap2.jpg");
        JLabel backgroundLabel = new JLabel(imageIcon);
        frame.setLayout(new BorderLayout());
        frame.setContentPane(backgroundLabel);
        frame.setSize(576,480);


        efficiency = new JLabel("Efficiency:" + String.format("%.3f", player.countEfficiency()));
        efficiency.setBounds(450,30,100,30);
        efficiency.setOpaque(false);
        frame.add(efficiency);

        menu = new JButton("Menu");
        menu.setBounds(450,400,80, 30);
        frame.add(menu);//adding button in JFrame
        addMenuEvent();

        income = new JButton("$:" + player.getAccount().getBalance());
        income.setBounds(450,10,80,30);
        frame.add(income);
        addIncomeEvent(income);
        frame.setVisible(true);//making the frame visible

    }

    // MODIFIES: this
    // EFFECTS: clicking income button will add the revenue to player.
    public void addIncomeEvent(JButton income) {
        income.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.addRevenue();
                income.setText("$:" + player.getAccount().getBalance());
            }
        });
    }

    // EFFECTS: clicking menu button will pop up build, save and load buttons.
    public void addMenuEvent() {
        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem buildMenuItem = new JMenuItem("Build");
                JMenuItem saveMenuItem = new JMenuItem("Save");
                JMenuItem loadMenuItem = new JMenuItem("Load");
                popupMenu.add(buildMenuItem);
                popupMenu.add(saveMenuItem);
                popupMenu.add(loadMenuItem);

                popupMenu.show(menu, menu.getWidth(), 0);
                addBuildEvent(buildMenuItem);
                addSaveEvent(saveMenuItem);
                addLoadEvent(loadMenuItem);
            }
        });
    }

    // EFFECTS: loading the game stored in IdealCity.json.
    private void addLoadEvent(JMenuItem loadMenuItem) {
        loadMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    restoreFrame();
                    houseButton.clear();
                    player = jsonReader.read();
                    restoreStatus();

                    frame.revalidate(); // Update the JFrame's layout
                    frame.repaint(); // Repaint the JFrame
                } catch (IOException e2) {
                    JOptionPane.showMessageDialog(null,
                            "Unable to read from file: " + JSON_STORE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: restore the JFrame to initial status (all the house button will be removed).
    private void restoreFrame() {
        Component[] components = frame.getComponents();
        for (Component component : components) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                if (houseButton.keySet().contains(button)) {
                    frame.remove(button);
                }
            }
        }
    }

    // REQUIRES: JFrame has been reseted to initial state.
    // MODIFIES: this
    // EFFECTS: restore all the houseButtons back to JFrame.
    private void restoreStatus() {
        income.setText("$:" + player.getAccount().getBalance());
        for (int i = 0; i < player.getLand().size(); i++) {
            Facility h = player.getLand().get(i);
            if (h instanceof Residence) {
                buildResidence(i);
            } else if (h instanceof Business) {
                if (h.getName().equals("Restaurant")) {
                    buildRestaurant(i);
                } else {
                    buildOffice(i);
                }
            } else if (h instanceof Security) {
                if (h.getName().equals("Police office")) {
                    buildPolice(i);
                } else {
                    buildFire(i);
                }
            }
        }
        efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
        frame.revalidate();
        frame.repaint();
    }

    // EFFECTS: save the game into IdealCity.json
    private void addSaveEvent(JMenuItem save) {
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jsonWriter.open();
                    jsonWriter.write(player);
                    jsonWriter.close();
                    JOptionPane.showMessageDialog(null, "Saved to " + JSON_STORE);
                } catch (FileNotFoundException e2) {
                    JOptionPane.showMessageDialog(null,
                            "Unable to write to file: " + JSON_STORE);
                }
            }
        });
    }

    // EFFECTS: clicking build button will either pop up full message (the land is full), or pop up facility type button
    public void addBuildEvent(JMenuItem build) {
        build.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.getLand().isFull()) {
                    JOptionPane.showMessageDialog(null, "Failed! The land is full!");
                } else {
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem residence = new JMenuItem("Residence: $" + RESIDENCE_BUILD_FEE);
                    JMenuItem business = new JMenuItem("Business: $" + BUSINESS_BUILD_FEE);
                    JMenuItem security = new JMenuItem("Security: $" + SECURITY_BUILD_FEE);
                    popupMenu.add(residence);
                    popupMenu.add(business);
                    popupMenu.add(security);
                    popupMenu.show(menu, menu.getWidth(), 0);
                    addResidenceEvent(residence);
                    addBusinessEvent(business);
                    addSecurityEvent(security);
                }

            }
        });
    }

    // EFFECTS: clicking security button will pop up police office and fire department button.
    private void addSecurityEvent(JMenuItem security) {
        security.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem police = new JMenuItem("Police office");
                JMenuItem fire = new JMenuItem("Fire department");
                popupMenu.add(police);
                popupMenu.add(fire);
                popupMenu.show(menu, menu.getWidth(), 0);
                addPoliceEvent(police);
                addFireEvent(fire);

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: clicking fire department button will build a fire department on land, presenting as a button on JFrame.
    private void addFireEvent(JMenuItem fire) {
        fire.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.buildSecurity("Fire department")) {
                    buildFire(player.getLand().size() - 1);
                    income.setText("$:" + player.getAccount().getBalance());
                    JOptionPane.showMessageDialog(null,
                            "Success! Your account balance is:" + player.getAccount().getBalance());
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No money! Your account balance is:" + player.getAccount().getBalance());
                }
            }
        });
    }

    // EFFECTS: clicking a facility button will pop up a menu with upgrade, remove and display buttons.
    private void addSelectEvent(JButton facility) {
        facility.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem upgrade = new JMenuItem("Upgrade");
                JMenuItem remove = new JMenuItem("Remove");
                JMenuItem display = new JMenuItem("Display");
                popupMenu.add(upgrade);
                popupMenu.add(remove);
                popupMenu.add(display);
                popupMenu.show(facility, facility.getWidth(), 0);
                addRemoveEvent(remove);
                addUpgradeEvent(upgrade);
                addDisplayEvent(display);
            }
        });
    }

    // EFFECTS: clicking display button will pop up information of that facility.
    private void addDisplayEvent(JMenuItem display) {
        display.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                JPopupMenu popup = (JPopupMenu) source.getParent();
                Component invoker = popup.getInvoker();
                if (invoker instanceof JButton) {
                    JButton button = (JButton) invoker;
                    String message = player.displayHouse(houseButton.get(button));
                    JOptionPane.showMessageDialog(frame, message, "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: upgrade the selected facility
    private void addUpgradeEvent(JMenuItem upgrade) {
        upgrade.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                JPopupMenu popup = (JPopupMenu) source.getParent();
                Component invoker = popup.getInvoker();
                if (invoker instanceof JButton) {
                    JButton button = (JButton) invoker;
                    if (player.upgradeHouse(houseButton.get(button))) {
                        JOptionPane.showMessageDialog(null,
                                "Upgraded! Your account balance is:" + player.getAccount().getBalance());
                        income.setText("$:" + player.getAccount().getBalance());
                        efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                        frame.revalidate();
                        frame.repaint();
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "No money! Your account balance is:" + player.getAccount().getBalance());
                    }
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: remove the selected facility
    private void addRemoveEvent(JMenuItem remove) {
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JMenuItem source = (JMenuItem) e.getSource();
                JPopupMenu popup = (JPopupMenu) source.getParent();
                Component invoker = popup.getInvoker();
                if (invoker instanceof JButton) {
                    JButton button = (JButton) invoker;
                    frame.remove(button);
                    player.removeHouse(houseButton.get(button));
                    houseButton.remove(button);
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                    frame.revalidate();
                    frame.repaint();

                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: build a police office on land and represent it as a button on JFrame.
    private void addPoliceEvent(JMenuItem police) {
        police.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.buildSecurity("Police office")) {
                    buildPolice(player.getLand().size() - 1);
                    income.setText("$:" + player.getAccount().getBalance());
                    JOptionPane.showMessageDialog(null,
                            "Success! Your account balance is:" + player.getAccount().getBalance());
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No money! Your account balance is:" + player.getAccount().getBalance());
                }
            }
        });
    }

    // EFFECTS: clicking business button will pop up a menu with Restaurant and Office building buttons.
    public void addBusinessEvent(JMenuItem business) {
        business.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPopupMenu popupMenu = new JPopupMenu();
                JMenuItem restaurant = new JMenuItem("Restaurant");
                JMenuItem office = new JMenuItem("Office building");
                popupMenu.add(restaurant);
                popupMenu.add(office);
                popupMenu.show(menu, menu.getWidth(), 0);
                addRestaurantEvent(restaurant);
                addOfficeEvent(office);

            }
        });
    }

    // MODIFIES: this
    // EFFECTS: build office building on land and represent it as a button on JFrame.
    private void addOfficeEvent(JMenuItem office) {
        office.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.buildBusiness("Office building")) {
                    buildOffice(player.getLand().size() - 1);
                    income.setText("$:" + player.getAccount().getBalance());
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                    JOptionPane.showMessageDialog(null,
                            "Success! Your account balance is:" + player.getAccount().getBalance());

                } else {
                    JOptionPane.showMessageDialog(null,
                            "No money! Your account balance is:" + player.getAccount().getBalance());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: build restaurant on land and represent it as a button on JFrame.
    private void addRestaurantEvent(JMenuItem restaurant) {
        restaurant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (player.buildBusiness("Restaurant")) {
                    buildRestaurant(player.getLand().size() - 1);
                    income.setText("$:" + player.getAccount().getBalance());
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                    JOptionPane.showMessageDialog(null,
                            "Success! Your account balance is:" + player.getAccount().getBalance());

                } else {
                    JOptionPane.showMessageDialog(null,
                            "No money! Your account balance is:" + player.getAccount().getBalance());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: build residence on land and represent it as a button on JFrame.
    public void addResidenceEvent(JMenuItem residence) {
        residence.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // if money enough
                if (player.buildResidence()) {

                    buildResidence(player.getLand().size() - 1);
                    income.setText("$:" + player.getAccount().getBalance());
                    efficiency.setText("Efficiency:" + String.format("%.3f", player.countEfficiency()));
                    JOptionPane.showMessageDialog(null,
                            "Success! Your account balance is:" + player.getAccount().getBalance());
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No money! Your account balance is:" + player.getAccount().getBalance());
                }
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: choose a available position(no button position) to locate the facility button.
    public void setPosition(JButton b) {
        for (int i = 0; i < xs.size(); i++) {
            if (!haveButtonThere(xs.get(i),ys.get(i))) {
                b.setBounds(xs.get(i),ys.get(i),50,50);
                return;
            }
        }

    }

    // EFFECTS: check if the given position already had a button there.
    private boolean haveButtonThere(int x, int y) {
        Set<JButton> buttons = houseButton.keySet();
        for (JButton button : buttons) {
            if (button.getX() == x && button.getY() == y) {
                return true;
            }
        }
        return false;
    }

    // MODIFIES: this
    // EFFECTS: create a fire department button on JFrame and store it into houseButton.
    private void buildFire(int i) {
        ImageIcon icon = new ImageIcon("./data/fire department.png");
        ImageIcon icon2 = new ImageIcon(
                icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton fire = new JButton(icon2);

        setPosition(fire);
        frame.add(fire);
        addSelectEvent(fire);
        frame.revalidate(); // Update the JFrame's layout
        frame.repaint(); // Repaint the JFrame
        houseButton.put(fire,player.getLand().get(i));
    }

    // MODIFIES: this
    // EFFECTS: create a police office button on JFrame and store it into houseButton.
    private void buildPolice(int i) {
        ImageIcon icon = new ImageIcon("./data/police office.png");
        ImageIcon icon2 = new ImageIcon(
                icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton police = new JButton(icon2);
        setPosition(police);
        frame.add(police);
        addSelectEvent(police);
        frame.revalidate(); // Update the JFrame's layout
        frame.repaint(); // Repaint the JFrame
        houseButton.put(police,player.getLand().get(i));
    }

    // MODIFIES: this
    // EFFECTS: create a office building button on JFrame and store it into houseButton.
    private void buildOffice(int i) {
        ImageIcon icon = new ImageIcon("./data/office building.png");
        ImageIcon icon2 = new ImageIcon(
                icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton office = new JButton(icon2);
        setPosition(office);
        houseButton.put(office,player.getLand().get(i));
        frame.add(office);
        addSelectEvent(office);
        frame.revalidate(); // Update the JFrame's layout
        frame.repaint(); // Repaint the JFrame
        houseButton.put(office,player.getLand().get(i));
    }

    // MODIFIES: this
    // EFFECTS: create a restaurant button on JFrame and store it into houseButton.
    private void buildRestaurant(int i) {
        ImageIcon icon = new ImageIcon("./data/restaurant.png");
        ImageIcon icon2 = new ImageIcon(
                icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton restaurant = new JButton(icon2);
        setPosition(restaurant);
        houseButton.put(restaurant,player.getLand().get(i));
        frame.add(restaurant);
        addSelectEvent(restaurant);
        frame.revalidate(); // Update the JFrame's layout
        frame.repaint(); // Repaint the JFrame

    }

    // MODIFIES: this
    // EFFECTS: create a residence button on JFrame and store it into houseButton.
    private void buildResidence(int i) {
        ImageIcon icon = new ImageIcon("./data/Residence.png");
        ImageIcon icon2 = new ImageIcon(
                icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
        JButton residence = new JButton(icon2);
        setPosition(residence);
        houseButton.put(residence,player.getLand().get(i));
        frame.add(residence);
        addSelectEvent(residence);
        frame.revalidate(); // Update the JFrame's layout
        frame.repaint(); // Repaint the JFrame00

    }

}
