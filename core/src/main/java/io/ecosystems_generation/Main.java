package io.ecosystems_generation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.HashMap;
import java.util.Map;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter{
    private World world;
    private DrawTools drawTool;
    // tick logic variables
    private float tickTimer = 0f;
    private int tickCount = 0;

    // constants
    private static final float ticksPerSecond = 600f;
    private static final float TICK_INTERVAL = 1 / ticksPerSecond;

    private static final int worldSize = 1024;
    private static final int WORLD_SEED = 0; // leave 0 for random seed

    float speed = 400f;

    private static int GRID_WIDTH;
    private static int GRID_HEIGHT;
    private static final int TILE_SIZE = 16;

    private OrthographicCamera camera;

    private Texture texture;
    private FitViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        this.world = new World(worldSize, WORLD_SEED);
        this.drawTool = new DrawTools(batch, shapeRenderer, GRID_WIDTH, GRID_HEIGHT, TILE_SIZE);
        // generates a world and terrain
    }

    @Override
    public void resize(int width, int height){
        //viewport.update(width, height, true);
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.translate(-speed * delta, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.translate(speed * delta, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.translate(0, speed * delta);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.translate(0, -speed * delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        drawTool.drawTerrain();

        tick();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        shapeRenderer.dispose();
    }

    // tick mechanism
    private void tick(){
        float delta = Gdx.graphics.getDeltaTime();
        tickTimer += delta;
        while (tickTimer >= TICK_INTERVAL){
            tickTimer -= TICK_INTERVAL;
            handleTickLogic(); // the tick logic itself
        }
    }

    private void handleTickLogic(){
        tickCount++;
        System.out.println(TILE_SIZE);
        // Render the screen every third tick (20 times a second)
        if (tickCount % 3 == 0){
        }
    }

    public static void setScreenSize(int screenWidth, int screenHeight){
        GRID_WIDTH = (int) Math.ceil(screenWidth / (float) TILE_SIZE);
        GRID_HEIGHT = (int) Math.ceil(screenHeight / (float) TILE_SIZE);
    }
}
