/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 23.02.2020
 */

public class GameCharacter {

    protected String charName;
    protected String charClass;

    protected int basicStrength;
    protected int basicDexterity;
    protected int basicEndurance;
    protected int currentStrength;
    protected int currentDexterity;
    protected int currentEndurance;

    protected int charHp;
    protected int charMaxHP;

    protected int charAttack;
    protected int charDefense;
    protected int critChance;
    protected float critMultiplier;
    protected int avoidChance;

    protected int heroLevel;
    protected boolean isBlockAttack;

    private boolean isAlive;

    protected Inventory myInventory;


    public GameCharacter(String characterClass, String characterName, int strength, int dexterity, int endurance) {
        charClass = characterClass;
        charName = characterName;
        basicStrength = strength;
        basicDexterity = dexterity;
        basicEndurance = endurance;
        currentStrength = basicStrength;
        currentDexterity = basicDexterity;
        currentEndurance = basicEndurance;
        calculateSecondaryParameters();
        heroLevel = 1;
        charHp = charMaxHP;
        isAlive = true;
        isBlockAttack = false;
    }

    public void calculateSecondaryParameters() {
        charAttack = basicStrength * 2;
        charMaxHP = basicEndurance * 50;
        charDefense = (int)((basicStrength + basicDexterity) / 4.0f);
        critChance = basicDexterity * 1;
        critMultiplier = 1.2f + (float)(basicDexterity / 20.0f);
        avoidChance = 3 + (int)(basicDexterity / 5.0f);
    }

    public void getDamage(int inputDamage) {
        if (Utils.random.nextInt(100) < avoidChance) {
            System.out.println(charName + " avoid attack");
        } else {
            inputDamage -= Utils.random.nextInt(charDefense);
            if (isBlockAttack) {
                System.out.println(charName + " blocking " + charDefense + " damage");
                inputDamage -= Utils.random.nextInt(charDefense);
            }
            if (inputDamage < 0) {
                inputDamage = 0;
            }
            System.out.println(charName + " take " + inputDamage + " damage");
            charHp -= inputDamage;
            if (charHp < 1) {
                isAlive = false;
            }
        }
    }

    public int makeAttack() {
        // 20 - 16...24
        int minAttack = (int) (charAttack * 0.8f);
        int deltaAttack = (int) (charAttack * 0.4f);
        int currentAttack = minAttack + Utils.random.nextInt(deltaAttack);
        if (Utils.random.nextInt(100) < critChance) {
            System.out.println(charName + " critical attack " + currentAttack + " damage");
            currentAttack *= (int)(currentAttack * critMultiplier);
        } else {
            System.out.println(charName + " attack " + currentAttack + " damage");
        }
        return currentAttack;
    }

    public void setBlockStance() {
        isBlockAttack = true;
        System.out.println(charName + " set block");
    }

    public void cure(int value) {
        charHp += value;
        if (charHp > charMaxHP) {
            charHp = charMaxHP;
        }
    }

    public void fullHp() {
        charHp = charMaxHP;
    }

    public void makeNewRound() {
        isBlockAttack = false;
    }

    public String getName() {
        return charName;
    }

    public int getCharMaxHP() {
        return charMaxHP;
    }

    public void showInfo() {
        System.out.println("Name: " + charName + " Hp: " + charHp + "/" + charMaxHP);
    }

    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println("Clone fail");
            return this;
        }
    }

    public void useItem(String inputItem) {
        switch (inputItem) {
            case "Small healing potion":
                cure(120);
                System.out.println(charName + " healing on 50");
                break;
            case "Small healing stone":
                cure(60);
                System.out.println(charName + " healing on 10");
                break;
        }
    }

    public boolean isAlive() {
        return isAlive;
    }
}
