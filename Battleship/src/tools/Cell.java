package tools;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */
import java.awt.*;

public class Cell {
    private final Color RED = Color.RED;
    private int x;
    private int y;
    private Color color;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        color = Color.gray;
    }

    public boolean checkHit (int xHit, int yHit) {
        if (this.x == xHit && this.y == yHit) {
            color = RED;
            return true;
        }
        return false;
    }

    public boolean isAlive() {
        return color != RED;
    }

    public void paint(Graphics g, int cellSize, boolean hide) {
        if (!hide || (hide && color == RED)) {
            g.setColor(color);
            g.fill3DRect(x * cellSize, y * cellSize, cellSize - 2, cellSize - 2, true);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
