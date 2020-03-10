/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 09.03.2020
 */

package tools;

import java.awt.*;

public class Shot {
    private int x;
    private int y;
    private boolean shot;

    public Shot(int x, int y, boolean shot) {
        this.x = x;
        this.y = y;
        this.shot = shot;
    }

    public void paint(Graphics g, int cellSize) {
        g.setColor(Color.gray);
        if (shot) {
            g.fillRect(x * cellSize + cellSize / 2 - 3, y * cellSize + cellSize / 2 - 3, 8, 8);
        } else {
            g.drawRect(x * cellSize + cellSize / 2 - 3, y * cellSize + cellSize / 2 - 3, 8, 8);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isShot() {
        return shot;
    }
}