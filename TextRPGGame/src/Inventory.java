/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 24.02.2020
 */

import java.util.ArrayList;

public class Inventory {

    private int gold;
    ArrayList<Item> myInventory;

    public Inventory() {
        gold = 0;
        myInventory = new ArrayList<>();
    }

    public void addSomeCoins(int value) {
        gold += value;
    }

    public void spendSomeCoins(int value) {
        gold -= value;
    }

    public boolean isCoinsEnough(int value) {
        if (gold >= value) return true;
        return false;
    }

    public void addItem(Item newItem) {
        myInventory.add(newItem);
    }

    public void showAllItems() {
        System.out.println("Inventory:");
        System.out.println("0. Exit from Inventory");
        if (myInventory.size() > 0) {
            for (int i = 0; i < myInventory.size(); i++) {
                System.out.println((i + 1) + ". " + myInventory.get(i).getName());
            }
        } else {
            System.out.println("Inventory is empty");
        }
        System.out.println("Gold: " + gold);
    }

    public String useItem(int itemID) {
        if (itemID == 0) {
            return "";
        }
        String item = myInventory.get(itemID - 1).getName();
        if (myInventory.get(itemID - 1).getType() == Item.ItemType.CONSUMABLES) {
            myInventory.remove(itemID - 1);
        }
        return item;
    }

    public int getSizeInventory() {
        return myInventory.size();
    }

    public void transferAllItemsToAnotherInventory(Inventory inventory){
        for (int i = 0; i < myInventory.size(); i++) {
            inventory.addItem(myInventory.get(i));
            inventory.addSomeCoins(gold);
        }
    }

}
