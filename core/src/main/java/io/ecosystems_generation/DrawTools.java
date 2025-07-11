package io.ecosystems_generation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import io.ecosystems_generation.EntityHandling.Entity;
import io.ecosystems_generation.EntityHandling.EntityType;
import io.ecosystems_generation.TerrainHandling.Material;
import io.ecosystems_generation.TerrainHandling.Terrain;
import io.ecosystems_generation.TerrainHandling.TerrainUtils;
import io.ecosystems_generation.TerrainHandling.TextureName;

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

        loadTerrainTextures();
        loadEntityTextures();

        setTerrainTextures();
        setExtraTextures();
        }

    private void loadTerrainTextures(){
        Texture tileset = new Texture(Gdx.files.internal("Overworld.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        // Slices the image into 16x16 pieces
        TextureRegion[][] textureTiles = TextureRegion.split(tileset, 16, 16);

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

        loadDecorationStructures();

        tileLookup.put(TextureName.WATER_DEFAULT, textureTiles[7][3]);

        // TODO: REPLACE TREE
        // stone for now, no tree textures yet
        tileLookup.put(TextureName.TREE_PLACEHOLDER, textureTiles[5][6]);

        tileLookup.put(TextureName.DECORATION_LILY_PAD_0, textureTiles[0][2]);
        tileLookup.put(TextureName.DECORATION_LILY_PAD_1, textureTiles[0][3]);
        tileLookup.put(TextureName.DECORATION_LILY_PAD_2, textureTiles[0][4]);
        tileLookup.put(TextureName.DECORATION_LILY_PAD_3, textureTiles[0][5]);
    }

    private void loadDecorationStructures(){
        Texture tileset = new Texture(Gdx.files.internal("tileset.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        TextureName[] decorations = TextureName.getDecorations();
        for (int x = 0; x < 5; x++) {
            tileLookup.put(decorations[x], new TextureRegion(tileset, x * 16, 80, 16, 16));
        }

        tileLookup.put(TextureName.DECORATION_DEFAULT_5, new TextureRegion(tileset, 128, 80, 16, 16));
        tileLookup.put(TextureName.DECORATION_DEFAULT_6, new TextureRegion(tileset, 160, 80, 16, 16));
        tileLookup.put(TextureName.BUSH_0, new TextureRegion(tileset, 224, 80, 16, 16));
        tileLookup.put(TextureName.BUSH_1, new TextureRegion(tileset, 240, 80, 16, 16));
    }

    private void loadEntityTextures(){
        // boar
        Texture tileset = new Texture(Gdx.files.internal("boar_animations.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion boarRegion = new TextureRegion(tileset, 0, 0, 48, 32);
        tileLookup.put(TextureName.BOAR, boarRegion);

        // chicken
        tileset = new Texture(Gdx.files.internal("food.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureRegion carrotRegion = new TextureRegion(tileset, 112, 128, 16, 16);
        tileLookup.put(TextureName.CARROT, carrotRegion);

        // chicken
        tileset = new Texture(Gdx.files.internal("chicken.png"));
        tileset.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        TextureName[] chickenTextures = TextureName.getChickenTextures();
        for (int i = 0; i < 4; i++) {
            TextureRegion chickenTexture = new TextureRegion(tileset, i * 16, 128, 16, 16);
            tileLookup.put(chickenTextures[i], chickenTexture);
        }
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
            case DECORATION:
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
        batch.begin();
        for (int x = 0; x < entities.length; x++) {
            if (entities[x] != null) {
                for (int y = 0; y < entities[x].length; y++) {
                    if (entities[x][y] != null) {
                        EntityType entityType = entities[x][y].getType();
                        switch (entityType) {
                            case PREDATOR:
                                batch.draw(tileLookup.get(TextureName.CHICKEN_0), x * TILE_SIZE, y * TILE_SIZE, 24, 24);
                                break;
                            case PREY:
                                batch.draw(tileLookup.get(TextureName.BOAR), x * TILE_SIZE - 10, y * TILE_SIZE - 10, 48, 32);
                                break;
                        }
                    }
                }
            }
        }
        batch.end();
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
                TextureRegion textureRegion;
                switch (terrain[x][y].getMaterialType()){
                    case DECORATION:
                        if (!isNearWater(x,y)){
                            textureRegion = extraTiles[x][y];
                            batch.draw(textureRegion, x * TILE_SIZE, y * TILE_SIZE);
                        }
                        else replaceTile(x, y, Material.GROUND);
                        break;
                    case TREE:
                        break;
                }

                if (World.checkForFood(x,y) && !isNearWater(x,y)){
                    textureRegion = tileLookup.get(TextureName.CARROT);
                    batch.draw(textureRegion, x * TILE_SIZE, y * TILE_SIZE);
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
                    case DECORATION:
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
                TextureRegion textureRegion;
                TextureName decoration;
                switch (terrain[x][y].getMaterialType()){
                    case DECORATION:
                        decoration = TextureName.decorationFromInt(TerrainUtils.getRandomInt(1,TextureName.getDecorationSize()));
                        textureRegion = tileLookup.get(decoration);
                        extraTiles[x][y] = textureRegion;
                        break;
                    case WATER:
                        if (TerrainUtils.getRandomBoolean(1f)){
                            replaceTile(x,y, Material.DECORATION);
                            decoration = TextureName.waterDecorationFromInt(TerrainUtils.getRandomInt(1,TextureName.getWaterDecorationSize()));
                            textureRegion = tileLookup.get(decoration);
                            extraTiles[x][y] = textureRegion;
                            break;
                        }
                }
            }
        }
    }

    private void setGroundTile(int x, int y){
        int mask = getWaterMask(x, y);
        if (!isValidTexture(mask)){
            // We don't have textures for ground surrounded by 4+ water tiles, hence replace the texture
            replaceTile(x, y, Material.WATER);
            terrainTiles[x][y] = tileLookup.get(TextureName.WATER_DEFAULT);
            return;
        }
        terrainTiles[x][y] = getTileFromMask(mask, x, y);

    }


    private void replaceTile(int x, int y, Material replaceMaterial){
        World.setTerrainTile(x, y, replaceMaterial);
        terrain[x][y].setMaterialType(replaceMaterial);
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

    private TextureRegion getTileFromMask(int mask, int x, int y) {
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

    private boolean isValidTexture(int mask){
        if (countBits(mask) > 5) return false;

        int TOP_RIGHT_SIDE = TOP_RIGHT | RIGHT | TOP;
        int TOP_LEFT_SIDE = TOP_LEFT | LEFT | TOP;
        int BOTTOM_RIGHT_SIDE = BOTTOM_RIGHT | RIGHT | BOTTOM;
        int BOTTOM_LEFT_SIDE = BOTTOM_LEFT | LEFT | BOTTOM;

        if ((mask & (TOP_RIGHT_SIDE | TOP_LEFT_SIDE)) == (TOP_RIGHT_SIDE | TOP_LEFT_SIDE)){
            return false;
        }

        if ((mask & (TOP_RIGHT_SIDE | BOTTOM_RIGHT_SIDE)) == (TOP_RIGHT_SIDE | BOTTOM_RIGHT_SIDE)){
            return false;
        }

        if ((mask & (BOTTOM_RIGHT_SIDE | BOTTOM_LEFT_SIDE)) == (BOTTOM_RIGHT_SIDE | BOTTOM_LEFT_SIDE)){
            return false;
        }

        if ((mask & (BOTTOM_LEFT_SIDE | TOP_LEFT_SIDE)) == (BOTTOM_LEFT_SIDE | TOP_LEFT_SIDE)){
            return false;
        }

        return true;
    }

    private int countBits (int mask){
        int res = 0;
        while (mask != 0){
            if ((mask & 1) == 1) res++;
            mask >>= 1;
        }
        return res;
    }

    public boolean isNearWater(int x, int y){
        return countBits(getWaterMask(x,y)) != 0;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < worldSize && y >= 0 && y < worldSize;
    }



}
