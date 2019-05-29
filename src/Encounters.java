import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Encounters {
    private String[] weaponTypes = {"Sword", "Bow", "Wand"};
    private String[] catalysts = {"Healing Catalyst", "Attack Catalyst", "Defense Catalyst"};
    private String[] armorTypes = {"Helmet", "Chestplate", "Leggings", "Boots"};

    public int generateInn(){
        return (int)(Math.random() * 10) + 5;
    }

    public Object[] generateQuest(){
        int amnt = (int)(Math.random() * 30) + 20; //amount to kill
        int enemy = (int)(Math.random() * 5); //type of enemy
        int reward = (int)(Math.random() * (3 * ((Math.random() * amnt) + enemy))) + amnt; //reward on completion
        Object[] names = Enemies.getNames();
        Object[] quest = {names[enemy], amnt, reward, enemy};
        return quest;
    }

    public Object[][] generateCityShop(){
        int shopLvl = (int)(Math.random() * 5) + 1;
        System.out.println("Shoplvl: "+ shopLvl);
        int amountOfItems = (int) (Math.random() * 5) + 5;
        Object[][] shop = new Object[amountOfItems][3];
        System.out.println(amountOfItems + " in generated shop");
        for (int i = 0; i < amountOfItems; i++){
            int itemDecider = (int)(Math.random() * 23); //weapon, catalyst or armor decider
            if (itemDecider < 10){ //choose weapon
                int type = (int)(Math.random() * 3);
                int attack = (int)(Math.random() * (15 * shopLvl)) + 5;
                int cost = (int)(Math.random() * (shopLvl * 10)) + attack;
                Object[] temp = {weaponTypes[type], attack, cost};
                shop[i] = temp;
            } else if (itemDecider < 13){ //catalyst
                int type = (int)(Math.random() * 3);
                int potency;
                int cost;
                if (type == 0){
                    potency = (int) (Math.random() * (5 * shopLvl)) + 3;
                    cost = (int)(Math.random() * (shopLvl * 5)) + (potency * 4);
                } else {
                    potency = (int) (Math.random() * shopLvl) + 1;
                    cost = (int)(Math.random() * (shopLvl * 5)) + (potency * 20);
                }
                Object[] temp = {catalysts[type], potency, cost};
                shop[i] = temp;
            } else { //armor
            int type = (int) (Math.random() * 4);
            int prot = (int) (Math.random() * (5 * shopLvl)) + 5;
            int cost = (int) (Math.random() * (shopLvl * 20)) + (prot * 2);
            Object[] temp = {armorTypes[type], prot, cost};
            shop[i] = temp;
        }
        }
        return shop;
    }

    public void generateWandererShop(){ //same as city except better items and lower prices
        int shopLvl = (int)(Math.random() * 5) + 3;
        System.out.println("Shoplvl: "+ shopLvl);
        int amountOfItems = (int) (Math.random() * 5) + 5;
        Object[][] shop = new Object[amountOfItems][3];
        System.out.println(amountOfItems + " in generated shop");
        for (int i = 0; i < amountOfItems; i++){
            int itemDecider = (int)(Math.random() * 23); //weapon, catalyst, or armor decider
            int discount;
            if (itemDecider < 10){ //choose weapon
                discount = (int)(Math.random() * (5 * (shopLvl - 2))) + 5;
                int random = (int)(Math.random() * 3);
                int attack = (int)(Math.random() * (10 * shopLvl)) + 5;
                int cost = (int)((Math.random() * (shopLvl * 10)) + attack) - discount;
                if (cost <= 0){
                    cost = attack - discount;
                }
                if (cost <= 0){
                    cost = attack / 2;
                }
                Object[] temp = {weaponTypes[random], attack, cost};
                shop[i] = temp;
            } else if (itemDecider < 13){ //choose catalyst
                discount = (int)(Math.random() * (3 * (shopLvl - 2))) + 10;
                int random = (int)(Math.random() * 3);
                int potency;
                int cost;
                if (random == 0){
                    potency = (int) (Math.random() * (5 * shopLvl)) + 3;
                    cost = (int)((Math.random() * (shopLvl * 5)) + (potency * 4)) - discount;
                } else {
                    potency = (int) (Math.random() * shopLvl) + 1;
                    cost = (int)((Math.random() * (shopLvl * 5)) + (potency * 20)) - discount;
                }

                Object[] temp = {catalysts[random], potency, cost};
                shop[i] = temp;
            } else { //armor
                discount = (int)(Math.random() * (2 * (shopLvl - 2))) + 5;
                int type = (int) (Math.random() * 4);
                int prot = (int) (Math.random() * (10 * shopLvl)) + 5;
                int cost = (int) ((Math.random() * (shopLvl * 15)) + (prot * 2)) - discount;
                Object[] temp = {armorTypes[type], prot, cost};
                shop[i] = temp;
            }
            System.out.println("Discount: " + discount);
        }
        int choice;
        do {
            String[] options1 = {"Shop", "Inventory", "Continue"};
            choice = JOptionPane.showOptionDialog(null, "You've met a wandering traveler!", "Traveler",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options1, null);
            if (choice == 0) {
                shop = shopDialog(shop);
            } else if (choice == 1) {
                Start.player.displayInv();
            }
        } while (choice == 0);
    }

    public void generateCity(){
        int genInn = (int)(Math.random() * 10);
        int genShop = (int)(Math.random() * 10);
        int genQuest = (int)(Math.random() * 10);
        int innCost = generateInn();
        Object[][] shop = null;
        Object[] quest = null;
        ArrayList<String> options = new ArrayList<>();
        boolean generated = false;
        if (genInn > 1){
            options.add("Stay at inn");
            generated = true;
        }
        if (genShop > 3){
            shop = generateCityShop();
            options.add("Shop");
            options.add("Sell");
            generated = true;
        }
        if (genQuest > 6){
            quest = generateQuest();
            options.add("See quest");
            generated = true;
        }
        options.add("Inventory");
        options.add("Continue");
        if (!generated){
            System.out.println("Empty city");
            generateCity();
        } else {
            Object[] cityDetails = {shop, quest, innCost, options};
            Save.setMapAtPos(Movement.getX(), Movement.getY(), "City", cityDetails);
            loadCity();
        }
    }

    public void loadCity(){
        Object[][] details = Save.getMapAtPos(Movement.getX(), Movement.getY());
        Object[] cityDetails = null;
        Object[][] shop = null;
        Object[] quest = null;
        int innCost = -1;
        try {
            cityDetails = details[1];
            shop = (Object[][]) cityDetails[0];
            quest = (Object[]) cityDetails[1];
            innCost = (int) cityDetails[2];
        } catch (NullPointerException e){
            System.out.println("Property returned null, should not cause an issue: " + e);
        }
        ArrayList<String> options = (ArrayList<String>) cityDetails[3];
        Object[] opt = options.toArray();
        String uCString;

        do {
            int userChoice = JOptionPane.showOptionDialog(null, "You've found a city! What would you like to do?" +
                            "\n(Staying at an inn recovers your health!) You have " + Start.player.getCoins() +
                            " coins", "City", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    opt, null);
            if (userChoice == -1){
                uCString = "Continue";
            } else {
                uCString = opt[userChoice].toString();
            }
            switch (uCString) {
                case "Stay at inn":
                    int sure = JOptionPane.showConfirmDialog(null, "Are you sure? This will cost " + innCost +
                            " coins\nYou have " + Start.player.getCoins() + " coins; "
                            + Start.player.getHitPoints() + "/" + Start.player.getMaxHp() + " hp", "Stay?",
                            JOptionPane.YES_NO_OPTION);
                    if (sure == 0) {
                        if (Start.player.getMaxHp() == Start.player.getHitPoints()){
                            JOptionPane.showMessageDialog(null, "Ahem, you have full health, there's nothing to heal...");
                        } else {
                            if (Start.player.getCoins() < innCost) {
                                JOptionPane.showMessageDialog(null, "You don't have enough coins to stay at the inn");
                            } else {
                                int random = (int) (Math.random() * (Start.player.getMaxHp()
                                        - Start.player.getHitPoints())) + 20;
                                Start.player.removeCoins(innCost);
                                Start.player.addHp(random);
                                JOptionPane.showMessageDialog(null, "Healed " + random + " hp!");
                            }
                        }
                    }
                    break;
                case "Shop":
                    shop = shopDialog(shop);
                    break;
                case "See quest":
                    int[] questDeets = {(int)quest[3], (int)quest[1], (int)quest[2],
                            Start.player.getKills((int)quest[3]), Movement.getX(), Movement.getY()};
                    //enemy type, amnt to kill, reward, start amount, city x, city y
                    String questString = "Kill " + quest[1] + " " + quest[0] + "s for " + quest[2] + " coins";
                    int[] playerQuest = Start.player.getActiveQuest();
                    if (playerQuest[4] == Movement.getX() && playerQuest[5] == Movement.getY()){
                        if (Start.player.getKills(playerQuest[0]) - playerQuest[3] == playerQuest[1]){
                            int claim = JOptionPane.showConfirmDialog(null, "Claim reward? (" + questString + ")", "Quest",
                                JOptionPane.YES_NO_OPTION);
                            if (claim == 0){
                                JOptionPane.showMessageDialog(null, "You have claimed the quest reward!\n" +
                                        "Received " + playerQuest[2] + " coins");
                                Start.player.addCoins(playerQuest[2]);
                                int[] defaultQuest = {-1, 0, 0, 0, 0, 0};
                                Start.player.setActiveQuest(defaultQuest);
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Quest progress:\n" +
                                    "Killed: " + Start.player.getKills(playerQuest[0]) + "/" + playerQuest[1]);
                        }
                    } else {
                        int acceptQuest = JOptionPane.showConfirmDialog(null, "The offered quest is:\n" + questString +
                                        "\nAccept for 10 coins? You have " + Start.player.getCoins(), "Quest",
                                JOptionPane.YES_NO_OPTION);
                        if (acceptQuest == 0) {
                            if (Start.player.getCoins() < 10) {
                                JOptionPane.showMessageDialog(null, "You don't have enough coins to accept the quest!");
                            } else if (Start.player.activeQuest()) {
                                JOptionPane.showMessageDialog(null, "You already have an active quest!");
                            } else {
                                JOptionPane.showMessageDialog(null, "Quest accepted!");
                                Start.player.removeCoins(10);
                                Start.player.setActiveQuest(questDeets);
                            }
                        }
                    }
                    break;
                case "Sell":
                    Object[] sellItems = Start.player.listToObject(Start.player.getAllInv());
                    try {
                        String sellChoice = JOptionPane.showInputDialog(null, "Choose the item you wish to sell:\n" +
                                        "This cannot be undone, and the item cannot be bought again!", "Sell",
                                JOptionPane.QUESTION_MESSAGE, null, sellItems, null).toString();
                        boolean found = false;
                        int eqNum = 0;
                        while (!found) { //until item is found,
                            if (sellChoice.equals(sellItems[eqNum])){ //if selected is same as current item in object array
                                found = true; //item location is found
                            } else {
                                eqNum++;
                            }
                        }
                        Object[][] inventory = Start.player.getInventory();
                        Object[] item = {inventory[eqNum][0].toString(), inventory[eqNum][1]};
                        Start.player.addCoins((int)(Math.random() * (int)inventory[eqNum][1]) + (int)inventory[eqNum][1]);
                        Start.player.removeFromInv(item);
                    } catch (NullPointerException e){
                        System.out.println("Sell cancelled: " + e);
                    }
                    break;
                case "Inventory":
                    Start.player.displayInv();
            }
        } while (!uCString.equals("Continue"));
        Object[] cityDetails2 = {shop, quest, innCost, options};
        Save.setMapAtPos(Movement.getX(), Movement.getY(), "City", cityDetails2);
    }

    public void generateBattle(){
        int level = Start.player.getLevel();
        if (level > 5) {
            level = 5;
        }
        int amount = (int)(Math.random() * level) + 1;
        if (amount == 0){
            System.out.println("Amount of enemy encounters = 0... That shouldn't happen");
            amount = 1;
        }
        for (int i = 0; i < amount; i++) {
            Enemies enemy = new Enemies();
            enemy.enemyGenerator();
            while (Start.player.isAlive() && enemy.isAlive()) {
                String[] battleOptions = {"Attack", "Items"};
                String message = "Fighting " + enemy.MonName() + ". " + enemy.getStatus() +
                        "\nPlayer hp: " + Start.player.getHitPoints();
                int result = JOptionPane.showOptionDialog(null, message, "Choose what to do",
                        JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, battleOptions, null);
                boolean itemUsed = true;
                if (result == 0) {
                    enemy.defend();
                } else {
                    itemUsed = Start.player.displayInv();
                }
                if (enemy.isAlive() && itemUsed) {
                    Start.player.defend(enemy);
                }
            }
        }
    }

    public String[] convertShopToString(Object[][] shop){
        String[] s = new String[shop.length];
        int i = 0;
        for (Object[] o : shop) {
            s[i] = (o[0].toString() + " [" + o[1].toString() + "]; Cost: " + o[2].toString());
            i++;
        }
        return s;
    }

    public Object[][] shopDialog(Object[][] shop){
        String[] shopItems = convertShopToString(shop);
        if (!Start.player.getInvStatus()){ //if inv is full
            JOptionPane.showMessageDialog(null, "Your inventory is full, you can't buy anything!");
            return shop;
        }
        Object shopChoice = JOptionPane.showInputDialog(null, "Shop: You have " + Start.player.getCoins() +
                " coins.", "Shop", JOptionPane.QUESTION_MESSAGE, null, shopItems, null);
        boolean found = false;
        int eqNum = 0;
        while (!found) { //until item is found,
            try {
                if (shopChoice.equals(shopItems[eqNum])) { //if selected is same as current item in object array
                    found = true; //item location is found
                } else {
                    eqNum++;
                }
            } catch (NullPointerException e){
                System.out.println("User selected cancel: " + e);
                return shop; //return the new shop once the user selects cancel
            }
        }
        if ((int)shop[eqNum][2] > Start.player.getCoins()){
            JOptionPane.showMessageDialog(null, "You don't have enough coins to buy that!");
            shopDialog(shop);
            return shop;
        }
        else {
            Start.player.removeCoins((int)shop[eqNum][2]); //remove the cost of item form player's coins
            ArrayList<Object[]> list = new ArrayList<>(Arrays.asList(shop)); //turn shop into arraylist
            list.remove(eqNum); //remove the bought item
            Start.player.addInventory(shop[eqNum][0].toString(), (int)shop[eqNum][1]);
            Object[][] newShop = list.toArray(new Object[][]{}); //turn back into object array
            shopDialog(newShop); //new dialog with new shop
            return newShop;
        }
    }

    public void generateBossBattle(){
        Enemies dragon = new Enemies();
        dragon.newDragon();

        while (Start.player.isAlive() && dragon.isAlive()) {
            String[] battleOptions = {"Attack", "Items"};
            String message = "Fighting " + dragon.MonName() + ". " + dragon.getStatus() +
                    "\nPlayer hp: " + Start.player.getHitPoints();
            int result = JOptionPane.showOptionDialog(null, message, "Choose what to do",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, battleOptions, null);
            boolean itemUsed = true;
            if (result == 0) {
                dragon.defend();
            } else {
                itemUsed = Start.player.displayInv();
            }
            if (dragon.isAlive() && itemUsed) {
                Start.player.defend(dragon);
            }
        }
    }
}
