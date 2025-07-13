package io.ecosystems_generation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import io.ecosystems_generation.EntityHandling.Entity;
import io.ecosystems_generation.EntityHandling.EntityType;
import io.ecosystems_generation.TerrainHandling.Material;
import io.ecosystems_generation.TerrainHandling.Terrain;

import java.util.Random;

public class DrawTools {

    int worldSize;
    Random random;
    float[][] noise;
    Terrain[][] terrain;

    ShapeRenderer shapeRenderer;
    int GRID_WIDTH;
    int GRID_HEIGHT;
    int TILE_SIZE;


    DrawTools(ShapeRenderer shapeRenderer, int GRID_WIDTH, int GRID_HEIGHT, int TILE_SIZE){
        this.worldSize = World.getWorldSize();
        this.random = World.getRandom();
        this.noise = World.getNoise();
        this.terrain = World.getTerrain();
        this.shapeRenderer = shapeRenderer;
        this.GRID_WIDTH = GRID_WIDTH;
        this.GRID_HEIGHT = GRID_HEIGHT;
        this.TILE_SIZE = TILE_SIZE;
    }

    public Pixmap getNoisePixmap(){
        Pixmap pixmap = new Pixmap(worldSize, worldSize, Pixmap.Format.RGBA8888);
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
               pixmap.drawPixel(x,y, Color.rgba8888(getGridColor(x,y)));
            }
        }
        return pixmap;
    }

    public Color getGridColor(int x, int y){
        Material material = terrain[x][y].getMaterialType();
        float f = noise[x][y];
        switch (material){
            case WATER:
                float blueShade = 0.3f + 0.7f * (f / 0.30f); // deeper = darker
                return new Color(0f, 0f, blueShade, 1f);
            case TREE:
                return new Color(0.59f, 0.29f, 0.0f, 1f); // Reddish brown
            case STONE:
                return new Color(0.502f, 0.502f, 0.502f, 1f); // SlateGray
            case GROUND:
                float greenShade = 1.0f - ((f - 0.30f) / 0.70f); // higher = darker
                greenShade = 0.5f + 0.5f * greenShade;
                return new Color(0f, greenShade, 0f, 1f);
        }
        return new Color(0,0,0,0f);
    }

    public void drawTiles(){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                Color color = getGridColor(x, y);
                shapeRenderer.setColor(color);
                shapeRenderer.rect(x * TILE_SIZE,y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
            }
        }
        shapeRenderer.end();
    }

    public void drawPixmap(SpriteBatch batch){
        Pixmap pixmap = getNoisePixmap();
        Texture texture = new Texture(pixmap);
        batch.begin();
        batch.draw(texture,0,0);
        batch.end();
    }

    public void drawEntities(Entity[][] entities){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for (int x = 0; x < entities.length; x++) {
            if (entities[x] != null) {
                for (int y = 0; y < entities[x].length; y++) {
                    if (entities[x][y] != null) {
                        EntityType entityType = entities[x][y].getType();
                        switch (entityType) {
                            case PREY:
                                shapeRenderer.setColor(1f, 1f, 0f, 1f);
                                shapeRenderer.circle(x * TILE_SIZE + (float) TILE_SIZE / 2, y * TILE_SIZE + (float) TILE_SIZE / 2, (float) TILE_SIZE / 2);
                                break;
                            case PREDATOR:
                                shapeRenderer.setColor(1f, 0f, 0f, 1f);
                                shapeRenderer.circle(x * TILE_SIZE + (float) TILE_SIZE / 2, y * TILE_SIZE + (float) TILE_SIZE / 2, (float) TILE_SIZE / 2);
                                break;
                        }
                    }
                }
            }
        }
        shapeRenderer.end();
    }
}
