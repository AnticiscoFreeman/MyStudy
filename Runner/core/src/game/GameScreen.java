/**
 * Created by Aleksandr Gladkov [Anticisco]
 * Date of creation: 27.02.2020
 */

package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class GameScreen implements Screen {
    private RunnerGame runnerGame;
    private SpriteBatch batch;

    private Texture textureBackground;
    private Texture textureGround;
    private Texture textureBlock;

    private BitmapFont font48;
    private BitmapFont font96;

    private Music worldSound;
    private Music gameOverSound;
    private Sound jumpSound;

    private float groundHeight = 128.0f;
    private float playerAnchor = 128.0f;

    private Player player;
    private Block[] enemies;

    private float time;

    private boolean gameOver;

    public GameScreen (RunnerGame runnerGame, SpriteBatch batch) {
        this.runnerGame = runnerGame;
        this.batch = batch;
    }

    @Override
    public void show() {
        textureBackground = new Texture("sky.png");
        textureGround = new Texture("ground.png");
        textureBlock = new Texture("enemy.png");

        worldSound = Gdx.audio.newMusic(Gdx.files.internal("world.mp3"));
        worldSound.setLooping(true);
        worldSound.setVolume(0.5f);

        gameOverSound = Gdx.audio.newMusic(Gdx.files.internal("gameOver.ogg"));
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("pook.ogg"));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.borderColor = Color.BLACK;
        parameter.borderWidth = 2;
        parameter.shadowOffsetX = 3;
        parameter.shadowOffsetY = -3;
        parameter.shadowColor = Color.BLACK;
        font48 = generator.generateFont(parameter);
        parameter.size = 96;
        font96 = generator.generateFont(parameter);
        generator.dispose();

        player = new Player(this, jumpSound);
        enemies = new Block[10];
        enemies[0] = new Block(textureBlock, new Vector2(1400, groundHeight));
        for (int i = 1; i < 10; i++) {
            enemies[i] = new Block(textureBlock, new Vector2(enemies[i - 1].getPosition().x + MathUtils.random(500, 1100), groundHeight));
        }
        gameOver = false;
    }

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        batch.draw(textureBackground,0,0);
        for (int i = 0; i < (RunnerGame.WINDOW_X / textureGround.getWidth()) + 1; i++) {
            batch.draw(textureGround, i * textureGround.getWidth() - player.getPosition().x % textureGround.getWidth(), 0);
        }
        player.render(batch);

        for (int i = 0; i < enemies.length; i++) {
            enemies[i].render(batch, player.getPosition().x - playerAnchor);
        }
        font48.draw(batch, "SCORE: " + (int)player.getScore(), 22, 702);
        if (gameOver) {
            font96.draw(batch , "GAME OVER", 360, 382);
            font48.setColor(1,1,1, 0.5f + 0.5f * (float)Math.sin(time * 5.0f));
            font48.draw(batch , "Tap to RESTART", 450, 282);
            font48.setColor(1,1,1,1);
        }
        batch.end();
    }

    private void update (float dt) {
        time += dt;
        if (!gameOver) {
            player.update(dt);
            worldSound.play();

            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getPosition().x < player.getPosition().x - playerAnchor - 80) {
                    enemies[i].setPosition(getRightestEnemies() + MathUtils.random(500, 1100), groundHeight);
                }
            }

            for (int i = 0; i < enemies.length; i++) {
                if (enemies[i].getRectangle().overlaps(player.getRectangle())) {
                    gameOver = true;
                    worldSound.stop();
                    gameOverSound.play();
                    break;
                }
            }

        } else {
            if (Gdx.input.justTouched()) {
                restart();
            }
        }
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

    public void restart() {
        gameOver = false;
        enemies[0].setPosition(1400, groundHeight);
        for (int i = 1; i < 10; i++) {
            enemies[i].setPosition(enemies[i - 1].getPosition().x + MathUtils.random(500, 1100), groundHeight);
        }
        player.restart();
    }

    public float getRightestEnemies() {
        float maxValue = 0.0f;
        for (int i = 0; i < enemies.length; i++) {
            if(enemies[i].getPosition().x > maxValue) {
                maxValue = enemies[i].getPosition().x;
            }
        }
        return maxValue;
    }

    public float getPlayerAnchor() {
        return playerAnchor;
    }

    public float getGroundHeight() {
        return groundHeight;
    }

    @Override
    public void dispose() {
        textureBackground.dispose();
        textureGround.dispose();
        textureBlock.dispose();
        worldSound.dispose();
        jumpSound.dispose();
        gameOverSound.dispose();
    }

}