/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 23.02.2020
 */

public class Hero extends GameCharacter implements Cloneable {

    private int currentExp;
    private int expToNextLevel;
    private int killedMonsters;
    private int currentZone;

    private int posX;
    private int posY;

    public Hero(String heroClass, String heroName, int heroStrength, int heroDexterity, int heroEndurance) {
        super(heroClass, heroName, heroStrength, heroDexterity, heroEndurance);
        currentZone = 0;
        currentExp = 0;
        expToNextLevel = 1000;
        killedMonsters = 0;
        myInventory = new Inventory();
        myInventory.addItem(new Item("Small healing potion", Item.ItemType.CONSUMABLES));
        myInventory.addItem(new Item("Small healing stone", Item.ItemType.INFINITY));
        myInventory.addSomeCoins(1000);
    }

    public void expGain(int inputExp) {
        currentExp += inputExp;
        System.out.println(charName + " obtain " + inputExp + " exp");
        if (currentExp >= expToNextLevel) {
            currentExp -= expToNextLevel;
            expToNextLevel *= 2;
            heroLevel++;
            charAttack += 5;
            currentStrength += 2;
            currentDexterity += 2;
            currentEndurance += 1;
            calculateSecondaryParameters();
            charHp = charMaxHP;
            System.out.println(charName + " level up to " + heroLevel);
        }
    }

    public void addKillCounter() {
        killedMonsters++;
    }

    public void showInfo() {
        System.out.println("Name: " + charName + " Hp: " + charHp + "/" + charMaxHP + " Level: " + heroLevel + " [" + currentExp + "/" + expToNextLevel + "]");
    }

    public void goToDangerousZone() {
        currentZone++;
        System.out.println("Go to zone " + currentZone);
    }

    public int getZoneDangerous() {
        return currentZone;
    }

    public void setPosXY(int x, int y) {
        posX = x;
        posY = y;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void moveHero(int vX, int vY) {
        posX += vX;
        posY += vY;
    }
}
