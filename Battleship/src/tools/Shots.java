package tools;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */

public class Shots {
    private final int CELL_SIZE;
    private ArrayList<Shot> shots;

    public Shots(int cellSize) {
        CELL_SIZE = cellSize;
        shots = new ArrayList<>();
    }

    public void add(int x, int y, boolean shot) {
        shots.add(new Shot(x, y, shot));
    }

    public boolean hitSamePlace(int x, int y) {
        for (Shot shot : shots) {
            if (shot.getX() == x && shot.getY() == y && shot.isShot()) {
                return true;
            }
        }
        return false;
    }

    public Shot getLabel(int x, int y) {
        for (Shot label : shots) {
            if (label.getX() == x && label.getY() == y && (!label.isShot())) {
                return label;
            }
        }
        return null;
    }

    public void removeLabel(Shot label) {
        shots.remove(label);
    }

    public void paint(Graphics g) {
        for (Shot shot : shots) {
            shot.paint(g, CELL_SIZE);
        }
    }

}
