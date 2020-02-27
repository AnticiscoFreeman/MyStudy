/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 25.02.2020
 */

public class Item {

    public enum ItemType {
        CONSUMABLES,
        QUEST,
        ARMOR,
        WEAPON,
        INFINITY
    }

    private String name;
    public ItemType type;

    public Item(String name, ItemType type) {
        this.name = name;
        this.type = type;
    }

    public String getName(){
        return name;
    }

    public ItemType getType() {
        return type;
    }
}
