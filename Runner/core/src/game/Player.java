/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 27.02.2020
 */

package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Player {
    private GameScreen gameScreen;

    private Texture texture;
    private Vector2 position;
    private Vector2 velocity;
    private Rectangle rectangle;

    private Sound jumpSound;

    private float score;

    private float time;

    private final int HEIGHT = 100;
    private final int WIDTH = 80;

    public Player(GameScreen gameScreen, Sound jump) {
        this.gameScreen = gameScreen;
        this.texture = new Texture("runner.png");
        this.position = new Vector2(0, 128);
        this.velocity = new Vector2(240.0f, 0.0f);
        this.score = 0;
        this.rectangle = new Rectangle(position.x + WIDTH / 4, position.y, WIDTH / 2, HEIGHT);
        this.jumpSound = jump;
    }

    public void render (SpriteBatch batch) {
        int frame = (int) (time / 0.1f);
        frame = frame % 6;
        batch.draw(texture, gameScreen.getPlayerAnchor(), position.y, frame * 90, 0, WIDTH, HEIGHT);
    }

    public void update (float dt) {
        if (position.y > gameScreen.getGroundHeight()) {
            velocity.y -= 720.0 * dt;
        } else {
            position.y = gameScreen.getGroundHeight();
            velocity.y = 0.0f;
            time += velocity.x * dt / 300.0f;
            if (Gdx.input.justTouched()) {
                velocity.y = 520.0f;
                jumpSound.play();
            }
        }
        position.mulAdd(velocity, dt);
        velocity.x += 15.0f * dt;
        score += velocity.x * dt / 5.0f;
        rectangle.setPosition(position.x + WIDTH / 4, position.y);
    }

    public void restart() {
        position.set(0, gameScreen.getGroundHeight());
        score = 0;
        velocity.set(240.0f, 0.0f);
        rectangle.setPosition(position);
    }

    public Vector2 getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public float getScore() {
        return score;
    }
}
