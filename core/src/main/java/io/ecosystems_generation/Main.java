package io.ecosystems_generation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
    private static final float ticksPerSecond = 600f;
    private static final float TICK_INTERVAL = 1 / ticksPerSecond;

    private static final int worldSize = 32; // has to be a power of 2

    private static final int GRID_WIDTH = 32;
    private static final int GRID_HEIGHT = 32;
    private static final int TILE_SIZE = 32;

    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture texture;
    private ShapeRenderer shapeRenderer;

    @Override
    public void create() {
        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        // generates a world and terrain
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height,false);
    }

    @Override
    public void render() {
        this.world = new World(worldSize);
        this.drawTool = new DrawTools();

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        handleTickLogic();
        drawTiles();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
        shapeRenderer.dispose();
    }


    public void drawTiles(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                Color color = drawTool.getGridColor(x, y);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(x * TILE_SIZE,y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();
    }

    public void drawPixmap(){
        Pixmap pixmap = drawTool.getNoisePixmap();
        texture = new Texture(pixmap);
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
    }

    // Tick logic
    public void handleTickLogic(){
        float delta = Gdx.graphics.getDeltaTime();
        tickTimer += delta;
        while (tickTimer >= TICK_INTERVAL){
            tick();
            tickTimer -= TICK_INTERVAL;
        }
    }

    public void tick(){
        tickCount++;
    }
}
