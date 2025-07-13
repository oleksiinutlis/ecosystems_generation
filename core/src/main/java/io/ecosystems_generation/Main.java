package io.ecosystems_generation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import io.ecosystems_generation.EntityHandling.Entity;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter{
    private World world;
    private DrawTools drawTool;

    // tick logic variables
    private float tickTimer = 0f;
    private static int tickCount = 0;

    // constants
    private static final float ticksPerSecond = 1f;
    private static final float TICK_INTERVAL = 1 / ticksPerSecond;

    // Game parameters
    private static final int foodGenPerSecond = 1;

    private static final int worldSize = 250;

    private static final int WORLD_SEED = 1278; // leave 0 for random seed

    // camera panning speed
    float speed = 300f;


    private static int WORLD_WIDTH = 250;
    private static int WORLD_HEIGHT = 140;

    private static int GRID_VISIBLE_WIDTH = 250;
    private static int GRID_VISIBLE_HEIGHT = 140;


    private static final int TILE_SIZE = 16;

    private OrthographicCamera camera;

    private Texture texture;
    private FitViewport viewport;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;

    private float deltaTime = 0;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.zoom -= 0.7f;

        viewport = new FitViewport(GRID_VISIBLE_WIDTH * 16, GRID_VISIBLE_HEIGHT * 16, camera); // attach camera
        viewport.apply();  // set up camera correctly
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        this.world = new World(worldSize, WORLD_SEED);
        this.drawTool = new DrawTools(batch, shapeRenderer, WORLD_WIDTH, WORLD_HEIGHT, TILE_SIZE);
        // generates a world and terrain

        world.setDrawTool(drawTool);
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height);
    }

    @Override
    public void render() {
        handleCameraMovement();
        handleCameraZoom();
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Entity[] entities = World.getEntities();
        drawTool.drawTerrain();
        drawTool.drawExtras();
        drawTool.drawEntities(entities);
        // world tick, everything synced to this

        addFood();
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
        tickTimer += deltaTime;
        while (tickTimer >= TICK_INTERVAL){
            tickTimer -= TICK_INTERVAL;
            handleTickLogic(); // the tick logic itself
        }
    }

    private void handleTickLogic(){
        tickCount++;
//        this.world.handler.stepZone();

    randomMove();
    }

    private void handleCameraMovement(){
        deltaTime = Gdx.graphics.getDeltaTime();

        // camera movement
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) camera.translate(-speed * deltaTime, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) camera.translate(speed * deltaTime, 0);
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) camera.translate(0, speed * deltaTime);
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) camera.translate(0, -speed * deltaTime);
    }

    private void handleCameraZoom(){
        // camera clamp logic to make sure it is in bounds
        camera.position.x = Math.max(camera.position.x, camera.zoom * camera.viewportWidth / 2f);
        camera.position.x = Math.min(camera.position.x, camera.viewportWidth - camera.zoom * camera.viewportWidth / 2f);
        camera.position.y = Math.max(camera.position.y, camera.zoom * camera.viewportHeight / 2f);
        camera.position.y = Math.min(camera.position.y, camera.viewportHeight - camera.zoom * camera.viewportHeight / 2f);

        // zoom logic
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) camera.zoom += 0.005f;
        if (Gdx.input.isKeyPressed(Input.Keys.E)) camera.zoom -= 0.005f;

        // zoom clamp
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 1.0f);
    }

    private void addFood(){
        World.addFood();
    }

    public static int getTickCount() {
        return tickCount;
    }

    public static int getTileSize(){
        return TILE_SIZE;
    }

    public void randomMove(){
        Entity[] entities = World.getEntities();
            for (Entity entity : entities) {
                if (entity != null && !entity.isMoving()) {
                        Random random = World.getRandom();
                        int[] coords = entity.randomStep(entity.getDesiredX(), entity.getDesiredY());
                        entity.setDesiredCoordinates(World.getRandom().nextInt(0, WORLD_WIDTH),
                                                     World.getRandom().nextInt(0, WORLD_HEIGHT));

                }
            }
    }

}
