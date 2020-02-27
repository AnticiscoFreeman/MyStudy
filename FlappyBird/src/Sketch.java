import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 16.02.2020
 */

public class Sketch extends PApplet {
    private final int SIZE_X = 800;
    private final int SIZE_Y = 600;

    private float posXPlayer = 200f;
    private float posYPlayer = 50f;
    private float speedY = 0f;
    private float gravity = 0.50f;
    private float[] wallOffset = new float[4];
    private WallPair[] walls = new WallPair[4];
    private boolean endGame = false;
    private int score = 0;
    private PFont gamefont;

    private int countBck = 300;

    PImage player;
    PImage background;
    PImage wall;
    PImage resetBtn;

    public class WallPair {
        float x;
        float y1;
        float y2;

        public WallPair(float x, float y1, float y2) {
            this.x = x;
            this.y1 = y1;
            this.y2 = y2;
        }
    }

    public void settings() {
        size(SIZE_X, SIZE_Y);
    }

    public void setup() {
        smooth();
        noStroke();
        player = loadImage("player.png");
        background = loadImage("background.png");
        wall = loadImage("wall.png");
        resetBtn = loadImage("restartBtn.png");
        gamefont = createFont("font.TTF", 38.0f, true);
        fill(255);
        textFont(gamefont);
        textAlign(LEFT, TOP);
        for (int i = 0; i < wallOffset.length; i++) {
            wallOffset[i] = random(-100, 100);
        }
    }

    public void draw() {
        if (!endGame) {
            countBck++;
        }
        if (countBck > width * 4) {
            countBck = 0;
        }

        drawBackground(countBck % width);
        image(player, posXPlayer, posYPlayer);
        speedY += gravity;
        posYPlayer += speedY;
        if (posYPlayer > height - 50) {
            endGame = true;
        }
        if (posYPlayer < 0) {
            posYPlayer = 50;
        }
        checkTouch();
        text("SCORE: " + score, width - 200, 40);
        if (endGame) {
            imageMode(CENTER);
            image(resetBtn, width / 2, height / 2);
            imageMode(CORNER);
        }
    }

    private void drawBackground(int offset) {
        background(0);
        image(background, -offset, 0);
        image(background, width - offset, 0);
        drawWalls(width - offset);
        if (countBck > SIZE_X) {
            drawWalls(-offset);
        }
    }

    public void drawWalls(int offset) {
        for (int i = 0; i < 4; i++) {
            drawPairOfWalls(offset + 200 * i, i, wallOffset[i]);
        }
    }

    public void drawPairOfWalls(int posX, int i, float offset) {
        pushMatrix();
        rotate(PI);
        image(wall, -posX - 50, -200 - offset);
        popMatrix();
        image(wall, posX, height / 2 + 150 + offset);
        if (posX > 0 && posX < width) {
            walls[i] = new WallPair(posX, 200 + offset, height / 2 + 150 + offset);
        }
        if (posX == posXPlayer - wall.width && !endGame) {
            score++;
        }
    }

    public void checkTouch() {
        for (WallPair pair : walls) {
            if (pair != null) {
                if ((posYPlayer < pair.y1 && posXPlayer > pair.x && posXPlayer < pair.x + wall.width) ||
                        (posYPlayer > pair.y2 && posXPlayer > pair.x && posXPlayer < pair.x + wall.width)) {
                    endGame = true;
                }
            }
        }
    }

    public void reset() {
        endGame = false;
        countBck = 300;
        posYPlayer = 50;
        score = 0;
        speedY = 0;
        for (int i = 0; i < wallOffset.length; i++) {
            wallOffset[i] = random(-100, 100);
        }
    }

    @Override
    public void keyPressed() {
        if (!endGame) {
            speedY *= -2.0f;
        } else {
            reset();
        }
    }

    @Override
    public void keyReleased() {
        if (!endGame) {
            speedY = gravity;
        }
    }

    @Override
    public void mousePressed() {
        keyPressed();
    }

    @Override
    public void mouseReleased() {
        keyReleased();
    }
}
