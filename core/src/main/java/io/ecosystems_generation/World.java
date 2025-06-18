package io.ecosystems_generation;

import java.util.Random;

public class World {
    private Entity[][] entities;
    private static Terrain[][] terrain;
    private static float[][] noise;

    private static int worldSize;

    private static final Random random = new Random();

    public World(int size){
        worldSize = size;
        terrain = new Terrain[worldSize][worldSize];

        NoiseGenerator noiseGenerator = new NoiseGenerator();
        noise = noiseGenerator.generateSmoothNoise(worldSize);

        setTerrain();
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

    private void setTerrain() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                float f = noise[x][y];

                if (f < 0.30f) {
                    // Water (darker blue for deeper)
                    terrain[x][y] = new Terrain(Material.WATER);
                    continue;
                }

                if (f >= 0.45f && f < 0.70f) {
                    // Tree generation, 1.5% generation chance
                    if (random.nextFloat() < 0.015f) {
                        terrain[x][y] = new Terrain(Material.TREE);
                        continue;
                    }
                }

                if (f >= 0.35f && f < 0.65f) {
                    // Stone generation, 1.5% generation chance
                    if (random.nextFloat() < 0.015f) {
                        terrain[x][y] = new Terrain(Material.STONE);
                        continue;
                    }
                }
                // Grass (darker green for higher terrain)
                terrain[x][y] =
                    terrain[x][y] = new Terrain(Material.GROUND);
            }
        }
    }
}
