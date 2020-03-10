package tools;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */

public class Ships {

    private final int CELL_SIZE;
    private ArrayList<Ship> ships = new ArrayList<>();
    private final int[] TYPE_SHIP = {4, 3, 3, 2, 2, 2, 1, 1, 1, 1};
    private Random random;
    private boolean hide;

    public Ships(int fieldSize, int cellSize, boolean hide) {
        random = new Random();
        for (int i = 0; i < TYPE_SHIP.length; i++) {
            Ship ship;
            do {
                int x = random.nextInt(fieldSize);
                int y = random.nextInt(fieldSize);
                int position = random.nextInt(2);
                ship = new Ship(x, y, TYPE_SHIP[i], position);
            } while (ship.isOutOfField(0, fieldSize - 1) || ship.isOverlayOrTouch(ship));
            ships.add(ship);
        }
        CELL_SIZE = cellSize;
        this.hide = hide;
    }

    public boolean isOverlayOrTouch(Ship currentShip) { //прикосновение или перекрытие корабля
        for (Ship ship : ships) {
            if (ship.isOverlayOrTouch(currentShip)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkHit(int x, int y) {
        for (Ship ship : ships) {
            if (ship.checkHit(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSurvivors() {
        for (Ship ship : ships) {
            if (ship.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g) {
        for (Ship ship : ships) {
            ship.paint(g, CELL_SIZE, hide);
        }
    }
}
