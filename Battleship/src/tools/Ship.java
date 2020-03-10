package tools;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */

import java.awt.*;
import java.util.ArrayList;

public class Ship {
    private ArrayList<Cell> cells = new ArrayList<>();

    public Ship(int x, int y, int length, int position) {
        for (int i = 0; i < length; i++) {
            cells.add(new Cell(x + i * ((position == 1) ? 0 : 1), y + i * ((position == 1) ? 0 : 1)));
        }
    }

    public boolean isOutOfField (int bottom, int top) { //корабль вышел за определенные границы
        for (Cell cell : cells) {
            if (cell.getX() < bottom || cell.getX() > top || cell.getY() < bottom || cell.getY() > top) {
                return true;
            }
        }
        return false;
    }

    public boolean isOverlayOrTouch(Ship currentShip) { //прикосновение или перекрытие корабля
        for (Cell cell : cells) {
            if (currentShip.isOverlayOrTouchCell(cell)) {
                return true;
            }
        }
        return false;
    }

    public boolean isOverlayOrTouchCell(Cell currentCell) {
        for (Cell cell : cells) {
            for (int dx = -1; dx < 2; dx++) {
                for (int dy = -1; dy < 2; dy++) {
                    if (currentCell.getX() == cell.getX() + dx && currentCell.getY() == cell.getY() + dy) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean checkHit(int x, int y) {
        for (Cell cell : cells) {
            if (cell.checkHit(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isAlive() {
        for (Cell cell : cells) {
            if (cell.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public void paint(Graphics g, int cellSize, boolean hide) {
        for (Cell cell : cells) {
            cell.paint(g, cellSize, hide);
        }
    }

}
