package io.ecosystems_generation;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;

import java.util.Random;

public class DrawTools {

    int worldSize;
    Random random;
    float[][] noise;
    Terrain[][] terrain;

    DrawTools(){
        this.worldSize = World.getWorldSize();
        this.random = World.getRandom();
        this.noise = World.getNoise();
        this.terrain = World.getTerrain();
    }

    public Pixmap getNoisePixmap(){
        Pixmap pixmap = new Pixmap(worldSize, worldSize, Pixmap.Format.RGBA8888);

        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                float f = noise[x][y];
                Color color;
                Material material = terrain[x][y].getMaterialType();
                switch (material){
                    case WATER:
                        float blueShade = 0.3f + 0.7f * (f / 0.30f); // deeper = darker
                        color = new Color(0f, 0f, blueShade, 1f);
                        pixmap.drawPixel(x, y, Color.rgba8888(color));
                        break;
                    case STONE:
                        color = new Color(255,0,0, 1f);
                        pixmap.drawPixel(x, y, Color.rgba8888(color));
                        break;
                    case TREE:
                        color = new Color(128,128,128, 0f);
                        pixmap.drawPixel(x, y, Color.rgba8888(color));
                        break;
                    case GROUND:
                        float greenShade = 1.0f - ((f - 0.30f) / 0.70f); // higher = darker
                        greenShade = 0.5f + 0.5f * greenShade;
                        color = new Color(0f, greenShade, 0f, 1f);
                        pixmap.drawPixel(x, y, Color.rgba8888(color));
                        break;
                }
            }
        }

        return pixmap;
    }
}
