package io.ecosystems_generation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.*;

public class DrawTools {

    int worldSize;
    Random random;
    float[][] noise;
    Terrain[][] terrain;

    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    int GRID_WIDTH;
    int GRID_HEIGHT;
    int TILE_SIZE;

    private Texture tileset;
    private TextureRegion[][] textureTiles;
    private TextureRegion[][] extraTiles;
    private TextureRegion[][] terrainTiles;

        Map<TextureName, TextureRegion> tileLookup = new HashMap<>();

    private static final int TOP         = 1 << 0; // 0000 0001
    private static final int BOTTOM      = 1 << 1; // 0000 0010
    private static final int LEFT        = 1 << 2; // 0000 0100
    private static final int RIGHT       = 1 << 3; // 0000 1000
    private static final int TOP_LEFT    = 1 << 4; // 0001 0000
    private static final int TOP_RIGHT   = 1 << 5; // 0010 0000
    private static final int BOTTOM_LEFT = 1 << 6; // 0100 0000
    private static final int BOTTOM_RIGHT= 1 << 7; // 1000 0000

    DrawTools(SpriteBatch batch, ShapeRenderer shapeRenderer, int GRID_WIDTH, int GRID_HEIGHT, int TILE_SIZE){
        this.worldSize = World.getWorldSize();
        this.random = World.getRandom();
        this.noise = World.getNoise();
        this.terrain = World.getTerrain();

        this.shapeRenderer = shapeRenderer;
        this.batch = batch;
        this.GRID_WIDTH = GRID_WIDTH;
        this.GRID_HEIGHT = GRID_HEIGHT;
        this.TILE_SIZE = TILE_SIZE;

        loadTextures();
        setTerrainTextures();
        setExtraTextures();
        }

    private void loadTextures(){
        tileset = new Texture(Gdx.files.internal("Overworld.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        textureTiles = TextureRegion.split(tileset, 16, 16); // Slices the image into 16x16 pieces
        tileLookup.put(TextureName.GRASS_DEFAULT_1, textureTiles[0][0]);
        tileLookup.put(TextureName.GRASS_DEFAULT_2, textureTiles[9][7]);
        tileLookup.put(TextureName.GRASS_DEFAULT_3, textureTiles[10][7]);
        tileLookup.put(TextureName.GRASS_DEFAULT_4, textureTiles[9][8]);
        tileLookup.put(TextureName.GRASS_DEFAULT_5, textureTiles[10][8]);

        tileLookup.put(TextureName.GRASS_WATER_EDGE_LEFT, textureTiles[7][4]);
        tileLookup.put(TextureName.GRASS_WATER_EDGE_RIGHT, textureTiles[7][2]);
        tileLookup.put(TextureName.GRASS_WATER_EDGE_TOP, textureTiles[8][3]);
        tileLookup.put(TextureName.GRASS_WATER_EDGE_BOTTOM, textureTiles[6][3]);

        tileLookup.put(TextureName.GRASS_WATER_OUTER_TOP_LEFT, textureTiles[9][2]);
        tileLookup.put(TextureName.GRASS_WATER_OUTER_TOP_RIGHT, textureTiles[9][3]);
        tileLookup.put(TextureName.GRASS_WATER_OUTER_BOTTOM_LEFT, textureTiles[10][2]);
        tileLookup.put(TextureName.GRASS_WATER_OUTER_BOTTOM_RIGHT, textureTiles[10][3]);

        tileLookup.put(TextureName.GRASS_WATER_INNER_TOP_LEFT, textureTiles[8][4]);
        tileLookup.put(TextureName.GRASS_WATER_INNER_TOP_RIGHT, textureTiles[8][2]);
        tileLookup.put(TextureName.GRASS_WATER_INNER_BOTTOM_LEFT, textureTiles[6][4]);
        tileLookup.put(TextureName.GRASS_WATER_INNER_BOTTOM_RIGHT, textureTiles[6][2]);

        tileLookup.put(TextureName.STONE_DEFAULT_1, textureTiles[5][6]);
        tileLookup.put(TextureName.STONE_DEFAULT_2, textureTiles[5][7]);
        tileLookup.put(TextureName.STONE_DEFAULT_3, textureTiles[5][8]);
        tileLookup.put(TextureName.STONE_DEFAULT_4, textureTiles[5][9]);
        tileLookup.put(TextureName.STONE_DEFAULT_5, textureTiles[5][10]);

        tileLookup.put(TextureName.WATER_DEFAULT, textureTiles[7][3]);
    }

    public Pixmap getNoisePixmap(){
        Pixmap pixmap = new Pixmap(worldSize, worldSize, Pixmap.Format.RGBA8888);
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
               pixmap.drawPixel(x, y, Color.rgba8888(getGridColor(x,y)));
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

    public void drawPixmap(){
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

    public void drawTerrain(){
        batch.begin();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                batch.draw(terrainTiles[x][y], x * TILE_SIZE, y * TILE_SIZE);
            }
        }
        batch.end();
    }

    public void drawExtras(){
        batch.begin();
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                switch (terrain[x][y].getMaterialType()){
                    case STONE:
                        TextureRegion textureRegion = extraTiles[x][y];
                        batch.draw(textureRegion, x * TILE_SIZE, y * TILE_SIZE);
                    case TREE:
                        break;
                }
            }
        }
        batch.end();
    }

    public void setTerrainTextures(){
        terrainTiles = new TextureRegion[GRID_WIDTH][GRID_HEIGHT];
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                switch (terrain[x][y].getMaterialType()){
                    case STONE:
                        terrainTiles[x][y] = tileLookup.get(TextureName.grassFromInt(TerrainUtils.getRandomInt(1,6)));
                        break;
                    case GROUND:
                        setGroundTile(x,y);
                        break;
                    case WATER:
                        terrainTiles[x][y] = tileLookup.get(TextureName.WATER_DEFAULT);
                        break;
                }
            }
        }
    }

    private void setExtraTextures(){
        extraTiles = new TextureRegion[GRID_WIDTH][GRID_HEIGHT];
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                switch (terrain[x][y].getMaterialType()){
                    case STONE:
                        TextureName name = TextureName.stoneFromInt(TerrainUtils.getRandomInt(1,6));
                        TextureRegion textureRegion = tileLookup.get(name);
                        extraTiles[x][y] = textureRegion;
                }
            }
        }
    }

    private void setGroundTile(int x, int y){
        int mask = getWaterMask(x, y);
        terrainTiles[x][y] = getTileFromMask(mask);
    }

    private int getWaterMask(int x, int y){
        int mask = 0;

        // Check the adjusting tiles for water (for texture setup)
        if (x > 0 && terrain[x - 1][y].getMaterialType() == Material.WATER) mask |= LEFT;
        if (x < GRID_WIDTH - 1 && terrain[x + 1][y].getMaterialType() == Material.WATER) mask |= RIGHT;
        if (y > 0 && terrain[x][y - 1].getMaterialType() == Material.WATER) mask |= BOTTOM;
        if (y < GRID_HEIGHT - 1 && terrain[x][y + 1].getMaterialType() == Material.WATER) mask |= TOP;
        if (x > 0 && y > 0 && terrain[x - 1][y - 1].getMaterialType() == Material.WATER) mask |= BOTTOM_LEFT;
        if (x < GRID_WIDTH - 1 && y > 0 && terrain[x + 1][y - 1].getMaterialType() == Material.WATER) mask |= BOTTOM_RIGHT;
        if (x > 0 && y < GRID_HEIGHT - 1 && terrain[x - 1][y + 1].getMaterialType() == Material.WATER) mask |= TOP_LEFT;
        if (x < GRID_WIDTH - 1 && y < GRID_HEIGHT - 1 && terrain[x + 1][y + 1].getMaterialType() == Material.WATER) mask |= TOP_RIGHT;

        return mask;
    }

    private TextureRegion getTileFromMask(int mask) {
        // Outer corners
        if ((mask & (LEFT | TOP | TOP_LEFT)) == (LEFT | TOP | TOP_LEFT))
            return tileLookup.get(TextureName.GRASS_WATER_OUTER_TOP_LEFT);
        if ((mask & (LEFT | BOTTOM | BOTTOM_LEFT)) == (LEFT | BOTTOM | BOTTOM_LEFT))
            return tileLookup.get(TextureName.GRASS_WATER_OUTER_BOTTOM_LEFT);
        if ((mask & (RIGHT | TOP | TOP_RIGHT)) == (RIGHT | TOP | TOP_RIGHT))
            return tileLookup.get(TextureName.GRASS_WATER_OUTER_TOP_RIGHT);
        if ((mask & (RIGHT | BOTTOM | BOTTOM_RIGHT)) == (RIGHT | BOTTOM | BOTTOM_RIGHT))
            return tileLookup.get(TextureName.GRASS_WATER_OUTER_BOTTOM_RIGHT);

        // Edges
        if ((mask & LEFT) == LEFT)
            return tileLookup.get(TextureName.GRASS_WATER_EDGE_LEFT);
        if ((mask & RIGHT) == RIGHT)
            return tileLookup.get(TextureName.GRASS_WATER_EDGE_RIGHT);
        if ((mask & TOP) == TOP)
            return tileLookup.get(TextureName.GRASS_WATER_EDGE_TOP);
        if ((mask & BOTTOM) == BOTTOM)
            return tileLookup.get(TextureName.GRASS_WATER_EDGE_BOTTOM);

        // Inner corners
        if ((mask & TOP_LEFT) == TOP_LEFT)
            return tileLookup.get(TextureName.GRASS_WATER_INNER_TOP_LEFT);
        if ((mask & TOP_RIGHT) == TOP_RIGHT)
            return tileLookup.get(TextureName.GRASS_WATER_INNER_TOP_RIGHT);
        if ((mask & BOTTOM_LEFT) == BOTTOM_LEFT)
            return tileLookup.get(TextureName.GRASS_WATER_INNER_BOTTOM_LEFT);
        if ((mask & BOTTOM_RIGHT) == BOTTOM_RIGHT)
            return tileLookup.get(TextureName.GRASS_WATER_INNER_BOTTOM_RIGHT);

        // Default tile
        return tileLookup.get(TextureName.grassFromInt(TerrainUtils.getRandomInt(1, 6)));
    }
}
