package io.ecosystems_generation;

import java.util.Random;

public class World {
    private Entity[][] entities;
    private static Terrain[][] terrain;
    private static float[][] noise;

    private static int worldSize;

    private static final Random random = new Random(7777);

    public World(int size){
        worldSize = size;
        terrain = new Terrain[worldSize][worldSize];
        entities = new Entity[worldSize][worldSize];

        NoiseGenerator noiseGenerator = new NoiseGenerator();
        noise = noiseGenerator.generateSmoothNoise(worldSize);
        setTerrain();
        setEntities();
    }


    public static int getWorldSize(){
        return worldSize;
    }

    public static Random getRandom(){
        return random;
    }

    public static float[][] getNoise(){
        return noise;
    }

    public static Terrain[][] getTerrain(){
        return terrain;
    }

    public Entity[][] getEntities() {
        return entities;
    }

    private void setTerrain() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                float f = noise[x][y];

                if (f < 0.40f) {
                    // Water (darker blue for deeper)
                    terrain[x][y] = new Terrain(Material.WATER);
                    continue;
                }

                if (f >= 0.45f && f < 0.70f) {
                    // Tree generation, 1.5% generation chance
                    if (TerrainUtils.getRandomBoolean(1.5f)) {
                        terrain[x][y] = new Terrain(Material.TREE);
                        continue;
                    }
                }

                if (f >= 0.45f && f < 0.65f) {
                    // Stone generation, 1.5% generation chance
                    if (TerrainUtils.getRandomBoolean(1.5f)) {
                        terrain[x][y] = new Terrain(Material.STONE);
                        continue;
                    }
                }
                // Grass (darker green for higher terrain)
                terrain[x][y] = new Terrain(Material.GROUND);
            }
        }
    }

    private void setEntities() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                if (terrain[x][y].getMaterialType() == Material.GROUND) {
                boolean chance = TerrainUtils.getRandomBoolean(30);
                    if (chance) {
                        this.entities[x][y] = new Prey(0);
                    }
                }
            }
        }
    }
}
