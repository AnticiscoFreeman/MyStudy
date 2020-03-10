/**
 *  vX = 5px/frame
 *
 *  1 s -> 60px = 60 * 5 = 300px
 *  1 s -> 20px = 20 * 5 = 100px
 *
 *  xV = 300px/sed
 *  60 -> dt = 1/fps = 1/60
 *  20 -> dt = 1/fps = 1/20
 *
 *  !!! x += vX * dt !!!
 *  fps=60fps, vX=300 -> x = 300 * 1/60 * 60 = 300 px/sec
 *  fps=20fps, vX=300 -> x = 300 * 1/20 * 20 = 300 px/sec
 *
 */

package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RunnerGame extends Game {
	public static final int WINDOW_X = 1280;
	public static final int WINDOW_Y = 720;

	private SpriteBatch batch;
	private GameScreen gameScreen;
	private Viewport viewport;


	@Override
	public void create () {
		batch = new SpriteBatch();
		gameScreen = new GameScreen(this, batch);
		viewport = new FitViewport(WINDOW_X, WINDOW_Y);
		setScreen(gameScreen);
	}

	@Override
	public void render () {
		float dt = Gdx.graphics.getDeltaTime();
		getScreen().render(dt);
	}

	@Override
	public void dispose () {
		batch.dispose();
		getScreen().dispose();

	}

	public Viewport getViewport() {
		return viewport;
	}
}
