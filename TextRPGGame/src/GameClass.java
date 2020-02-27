/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 23.02.2020
 */

public class GameClass {

    private Hero[] heroPattern = new Hero[3];
    private Monster[] monsterPattern = new Monster[3];

    private GameMap map;
    private InGameShop shop;

    private Hero mainHero;
    private Monster currentMonster;
    private int currentRound;
    private int monsterID = 0;

    private int inputInt;

    public GameClass() {
        initGame();
    }

    public void mainGameLoop() {
        map = new GameMap();
        shop = new InGameShop();

        inputInt = 0;
        System.out.println("Game started!");

        selectHero();
        mainHero.setPosXY(10, 3);

        map.buildDangerousMap(10, 3);
        map.updateMap(mainHero.getPosX(), mainHero.getPosY());
        map.showMap();

        while (true) {
            int x = getAction(1, 6, "Choose your action: [1.GO LEFT] | [2.GO RIGHT] | [3.GO UP] | [4.GO DOWN] | [5.FIND A MONSTER] | [6.RELAX]");

            switch (x) {
                case 1:
                    if (map.isCellEmpty(mainHero.getPosX() - 1, mainHero.getPosY())) {
                        mainHero.moveHero(-1, 0);
                    }
                    break;
                case 2:
                    if (map.isCellEmpty(mainHero.getPosX() + 1, mainHero.getPosY())) {
                        mainHero.moveHero(1, 0);
                    }
                    break;
                case 3:
                    if (map.isCellEmpty(mainHero.getPosX(), mainHero.getPosY() - 1)) {
                        mainHero.moveHero(0, -1);
                    }
                    break;
                case 4:
                    if (map.isCellEmpty(mainHero.getPosX(), mainHero.getPosY() + 1)) {
                        mainHero.moveHero(0, 1);
                    }
                    break;
                case 5:
                    currentMonster = (Monster) monsterPattern[Utils.random.nextInt(3)].clone();
                    currentMonster.lvlUp(map.getDangerousMap(mainHero.getPosX(), mainHero.getPosY()));
                    battle(mainHero, currentMonster);
                    break;
                case 6:
                    mainHero.fullHp();
                    break;
            }

            if (map.getObstMap(mainHero.getPosX(), mainHero.getPosY()) == 'S') {
                shopAction();
            }

            if (Utils.random.nextInt(100) < 3) {
                System.out.println("Monster attack!!!");
                currentMonster = (Monster) monsterPattern[Utils.random.nextInt(3)].clone();
                currentMonster.lvlUp(map.getDangerousMap(mainHero.getPosX(), mainHero.getPosY()));
                battle(mainHero, currentMonster);
            }

            map.updateMap(mainHero.getPosX(), mainHero.getPosY());
            map.showMap();

            if (!mainHero.isAlive()) {
                break;
            }
        }

        System.out.println("Game end!");

    }

    public void selectHero() {
        String s = "Choose your hero: ";
        for (int i = 0; i < 3; i++) {
            s += (i + 1) + "." + heroPattern[i].getName() + "   ";
        }
        inputInt = getAction(1,3, s);

        mainHero = (Hero) heroPattern[inputInt - 1].clone();
        System.out.println(mainHero.getName() + " ready for adventure");

    }

    public void battle(Hero hero, Monster monster) {

        currentRound = 1;
        System.out.println("Battle: " + hero.getName() + " vs. " + monster.getName());

        do {
            System.out.println("Now turn is: " + currentRound);
            hero.showInfo();
            monster.showInfo();

            hero.makeNewRound();

            inputInt = getAction(0,3, "Select [1.Attack] | [2.Defense] | [3.Inventory] | [0.Exit game] -> ");

            if (inputInt == 1) {
                monster.getDamage(hero.makeAttack());
                if (!monster.isAlive()) {
                    System.out.println(monster.getName() + "is dead");
                    hero.expGain(monster.getCharMaxHP() * 2);
                    hero.addKillCounter();
                    currentMonster.myInventory.transferAllItemsToAnotherInventory(hero.myInventory);
                    break;
                }
            }

            if (inputInt == 2) {
                hero.setBlockStance();
            }

            if (inputInt == 3) {
                hero.myInventory.showAllItems();
                int inputInv = getAction(0, hero.myInventory.getSizeInventory(), "Choose item to use: ");
                String usedItem = hero.myInventory.useItem(inputInv);
                if (usedItem != "") {
                    System.out.println(hero.getName() + " used " + usedItem);
                    hero.useItem(usedItem);
                } else {
                    System.out.println(hero.getName() + " closed inventory");
                }
            }

            if (inputInt == 0) {
                break;
            }

            monster.makeNewRound();

            if (Utils.random.nextInt(100) < 80) {
                hero.getDamage(monster.makeAttack());
            } else {
                hero.setBlockStance();
            }

            if (!hero.isAlive()) {
                break;
            }
            currentRound++;

        } while (true);
        if (monster.isAlive() && hero.isAlive()) {
            System.out.println(hero.getName() + " running away");
        }
        if (!monster.isAlive()) {
            System.out.println(hero.getName() + " win");
        }
        if (!hero.isAlive()) {
            System.out.println(monster.getName() + " win");
        }
    }

    public void shopAction() {
        shop.showItems();
        System.out.println("[0.Exit]");
        int x = getAction(0, shop.ITEMS_COUNT, "What's buy?");
        if (x == 0) return;
        shop.sellItem(x, mainHero);
    }

    public void initGame() {
        heroPattern[0] = new Hero("Knight", "Lancelot", 16,8 ,12);
        heroPattern[1] = new Hero("Barbarian", "Konan", 16, 8, 12);
        heroPattern[2] = new Hero("Dwarf", "Gimli", 16, 8, 12);

        monsterPattern[0] = new Monster("Humanoid", "Goblin", 12,  4, 4);
        monsterPattern[1] = new Monster("Humanoid", "Orc", 18, 6, 6);
        monsterPattern[2] = new Monster("Humanoid", "Troll", 32, 12, 10);

        currentRound = 1;
    }

    public int getAction(int min, int max, String msg) {
        int x = 0;
        do {
            if (msg != "") {
                System.out.print(msg + ": ");
            }
            x = Utils.sc.nextInt();
        } while (x < min || x > max);
        return x;
    }

}
