package io.ecosystems_generation;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {

    private World world;
    private static final int worldSize = 1024; // has to be a power of 2

    private FitViewport viewport;
    private SpriteBatch batch;
    private Texture texture;

    @Override
    public void create() {
        viewport = new FitViewport(worldSize, worldSize);
        batch = new SpriteBatch();

        this.world = new World(worldSize);

        //
        // noise generation visualisation
        //

        DrawTools drawTools = new DrawTools();

        Pixmap pixmap = drawTools.getNoisePixmap();
        texture = new Texture(pixmap);
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
}
