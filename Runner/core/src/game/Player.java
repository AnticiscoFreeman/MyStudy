/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 27.02.2020
 */

package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private GameScreen gameScreen;

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;

    private float score;

    private final int HEIGHT = 100;
    private final int WIDTH = 100;

    public Player(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("runner.png");
        this.position = new Vector2(0, 128);
        this.velocity = new Vector2(240.0f, 0.0f);
        this.score = 0;
    }

    public void render (SpriteBatch batch) {
        batch.draw(texture, gameScreen.getPlayerAnchor(), position.y);
    }

    public Vector2 getPosition() {
        return position;
    }
}
