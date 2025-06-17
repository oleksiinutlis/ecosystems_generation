package io.github.evolutions;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Random;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    private static final int worldSize = 1024; // has to be a power of 2

    private Random random = new Random();
    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture texture;

    @Override
    public void create() {

        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();
        Pixmap pixmap = new Pixmap(worldSize, worldSize, Pixmap.Format.RGBA8888);
        //
        // noise generation visualisation
        //

        this.world = new World(worldSize);
        float[][] noise = world.getNoise();

        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                float f = noise[x][y];
                Color color;
                if (f < 0.35f) {
                    // Water (darker blue for deeper)
                    float blueShade = 0.3f + 0.7f * (f / 0.30f); // deeper = darker
                    color = new Color(0f, 0f, blueShade, 1f);
                    pixmap.drawPixel(x, y, Color.rgba8888(color));
                    continue;
                }

                if (f >= 0.45f && f < 0.70f) {
                    if (random.nextFloat() < 0.015f){
                        color = new Color(255,0,0, 1f);
                        pixmap.drawPixel(x, y, Color.rgba8888(color));
                        continue;
                    }
                }

                // Grass (darker green for higher terrain)
                float greenShade = 1.0f - ((f - 0.30f) / 0.70f); // higher = darker
                greenShade = 0.5f + 0.5f * greenShade;
                color = new Color(0f, greenShade, 0f, 1f);
                pixmap.drawPixel(x, y, Color.rgba8888(color));
            }
        }


        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height,false);
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        texture.dispose();
    }

    public static int getWorldSize(){
        return worldSize;
    }


}
