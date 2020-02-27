/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 23.02.2020
 */

public class Monster extends GameCharacter {

    public Monster (String monsterClass, String monsterName, int monsterStrength, int monsterDexterity, int monsterEndurance) {
        super(monsterClass, monsterName, monsterStrength, monsterDexterity, monsterEndurance);
        myInventory = new Inventory();
        myInventory.addItem(new Item("Small healing potion", Item.ItemType.CONSUMABLES));
        myInventory.addSomeCoins(100);
    }

    public void lvlUp(int level) {
        for (int i = 0; i < level; i++) {
            showInfo();
            currentStrength += basicStrength * 0.3f;
            currentDexterity += basicDexterity * 0.3f;
            currentEndurance += basicEndurance * 0.3f;
            calculateSecondaryParameters();
            charHp = charMaxHP;
            showInfo();
        }
    }

}
