/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 27.02.2020
 */

package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Texture textureBackground;
    private Texture textureGround;

    private float worldX;
    private float groundHeight = 128.0f;
    private float playerAnchor = 128.0f;

    private Player player;

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() {
        textureBackground = new Texture("sky.png");
        textureGround = new Texture("ground.png");
        player = new Player(this);
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(textureBackground,0,0);
        for (int i = 0; i < (RunnerGame.WINDOW_X / textureGround.getWidth()) + 1; i++) {
            batch.draw(textureGround, i * textureGround.getWidth() - worldX % textureGround.getWidth(), 0);
        }
        player.render(batch);

        batch.end();
    }

    private void update (float delta) {
        worldX += 128 * delta;
    }

    @Override
    public void resize(int width, int height) {
        runnerGame.getViewport().update(width, height,true);
        runnerGame.getViewport().apply();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        textureGround.dispose();

    }

    public float getPlayerAnchor() {
        return playerAnchor;
    }
}
