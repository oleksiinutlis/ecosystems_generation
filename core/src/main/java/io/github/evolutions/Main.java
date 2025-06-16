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

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    private static final int worldSize = 1000;

    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture texture;

    @Override
    public void create() {
        worldSetup(); // generate terrain

        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();
        Pixmap pixmap = new Pixmap(worldSize, worldSize, Pixmap.Format.RGBA8888);
        //
        // noise generation visualisation
        //
        for (int x = 0; x < worldSize; x++){
            for (int y = 0; y < worldSize; y++){
                float[][] noise = world.getWhiteNoise();
                float f = noise[x][y];
                Color color = new Color(f, f, f,1f);
                pixmap.drawPixel(x, y, Color.rgba8888(color));
            }
        }

        texture = new Texture(pixmap);
        pixmap.dispose();
    }

    @Override
    public void resize(int width, int height){
        viewport.update(width, height,true);
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

    private void worldSetup(){
        this.world = new World(worldSize);
    }

}
