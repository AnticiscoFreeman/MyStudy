/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 26.02.2020
 */

public class InGameShop {

    public final int ITEMS_COUNT = 4;
    private Item[] items = new Item[ITEMS_COUNT];
    private int[] itemCost = new int[ITEMS_COUNT];
    private int goldAmount;
    
    public InGameShop() {
        goldAmount = 2000;
        items[0] = new Item("Small healing potion", Item.ItemType.CONSUMABLES);
        itemCost[0] = 100;
        items[1] = new Item("Medium healing potion", Item.ItemType.CONSUMABLES);
        itemCost[1] = 200;
        items[2] = new Item("Small shield", Item.ItemType.ARMOR);
        itemCost[2] = 300;
        items[3] = new Item("Lucky rock", Item.ItemType.INFINITY);
        itemCost[3] = 600;
    }
    
    public void showItems() {
        System.out.println("Shop:");
        for (int i = 0; i < ITEMS_COUNT; i++) {
            System.out.println((i + 1) + " " + items[i].getName() + " = " + itemCost[i]);
        }
    }

    public void sellItem (int indexItem, Hero hero) {
        if (hero.myInventory.isCoinsEnough(itemCost[indexItem - 1])) {
            hero.myInventory.spendSomeCoins(itemCost[indexItem -1]);
            goldAmount += itemCost[indexItem - 1];
            hero.myInventory.addItem(items[indexItem - 1]);
            System.out.println("Buy this: " + items[indexItem - 1].getName());
        } else {
            System.out.println("Need more gold!");
        }
    }

}
