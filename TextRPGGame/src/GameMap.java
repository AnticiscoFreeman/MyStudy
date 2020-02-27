/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 26.02.2020
 */

public class GameMap {

    private final int MSX = 20;
    private final int MSY = 6;

    private String[][] map = new String[MSY][MSX];
    private int[][] dangerousMap = new int[MSY][MSX];
    private char[][] obstMap = new char[MSY][MSX];

    public GameMap() {
        obstMap[3][7] = 'S';
        obstMap[4][8] = 'X';
    }

    public char getObstMap(int x, int y) {
        return obstMap[y][x];
    }

    public void updateMap(int heroX, int heroY) {
        for (int i = 0; i < MSY; i++) {
            for (int j = 0; j < MSX; j++) {
                map[i][j] = "*";
                if (obstMap[i][j] == 'S') {
                    map[i][j] = "S";
                }
                if (obstMap[i][j] == 'X') {
                    map[i][j] = "X";
                }
            }
        }
        map[heroY][heroX] = "H";
    }

    public void showMap() {
        for (int i = 0; i < MSY; i++) {
            for (int j = 0; j < MSX; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    public void buildDangerousMap(int cX, int cY) {
        for (int i = 0; i < MSY; i++) {
            for (int j = 0; j < MSX; j++) {
                int dng = (int) Math.sqrt(Math.pow(cY - i, 2) + Math.pow(cX - j, 2));
                dangerousMap[i][j] = dng;
            }
        }
    }

    public int getDangerousMap(int x, int y) {
        return dangerousMap[y][x];
    }

    public boolean isCellEmpty (int x, int y) {
        if (x < 0 || y < 0 || x > MSX || y > MSY) {
            return false;
        }
        if (obstMap[y][x] == ('X')) {
            return false;
        }
        else {
            return true;
        }
    }
}
