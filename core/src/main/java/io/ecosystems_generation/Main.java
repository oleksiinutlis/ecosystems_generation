package io.ecosystems_generation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter{
    private World world;
    private DrawTools drawTool;
    // tick logic variables
    private float tickTimer = 0f;
    private int tickCount = 0;

    // constants
    private static final float ticksPerSecond = 5f;
    private static final float TICK_INTERVAL = 1 / ticksPerSecond;

    private static final int worldSize = 128; // has to be a power of 2

    private static final int GRID_WIDTH = 96;
    private static final int GRID_HEIGHT = 64;
    private static final int TILE_SIZE = 32;

    private Texture texture;
    private FitViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        this.world = new World(worldSize);
        this.drawTool = new DrawTools(shapeRenderer, GRID_WIDTH, GRID_HEIGHT, TILE_SIZE);
        // generates a world and terrain
    }

    @Override
    public void resize(int width, int height){
        //viewport.update(width, height,true);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

        tick();
        drawTool.drawTiles();
        drawTool.drawEntities(world.getEntities());
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        shapeRenderer.dispose();
    }

    // Tick logic
    public void tick(){
        float delta = Gdx.graphics.getDeltaTime();
        tickTimer += delta;
        while (tickTimer >= TICK_INTERVAL){
            handleTickLogic();
            tickTimer -= TICK_INTERVAL;
        }
    }

    public void handleTickLogic(){
        tickCount++;
    }
}
