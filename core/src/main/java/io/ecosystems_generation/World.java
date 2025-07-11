package io.ecosystems_generation;

import io.ecosystems_generation.EntityHandling.Entity;
import io.ecosystems_generation.EntityHandling.Predator;
import io.ecosystems_generation.TerrainHandling.Material;
import io.ecosystems_generation.TerrainHandling.NoiseGenerator;
import io.ecosystems_generation.TerrainHandling.Terrain;
import io.ecosystems_generation.TerrainHandling.TerrainUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class World {
    private static Entity[][] entities;
    private static boolean[][] foodMap;
    private static Terrain[][] terrain;
    private static float[][] noise;
    private static Random random;
    private static int worldSize;
    private Queue<Terrain> terrainQueue = new LinkedList<>();

    public World(int size, int seed){
        worldSize = size;
        terrain = new Terrain[worldSize][worldSize];
        entities = new Entity[worldSize][worldSize];
        foodMap = new boolean[worldSize][worldSize];

        // fully random if seed is set to 0
        random = seed == 0 ? new Random() : new Random(seed);

        NoiseGenerator noiseGenerator = new NoiseGenerator();
        noise = noiseGenerator.generateSmoothNoise(worldSize);
        setTerrain();
        setEntities();
        EntityHandler handler = new EntityHandler(entities, 0, 59, 0, 36);
        handler.printZone();
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

    public static Entity[][] getEntities() {
        return entities;
    }

    private void setTerrain() {
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                float f = noise[x][y];
                Material material = Material.GROUND; // default value ground
                if (f < 0.40f) {
                    // Water (darker blue for deeper)
                    terrain[x][y] = new Terrain(Material.WATER);
                    continue;
                }
                else if (f >= 0.45f && f < 0.65f) {
                    // Decoration generation, 5% generation chance
                    if (TerrainUtils.getRandomBoolean(5f)) {
                        material = Material.DECORATION;
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
                terrain[x][y] = new Terrain(material, x, y);
            }
        }
        terrainCleanup();
    }

    private void terrainCleanup(){

    }

    private int getChunkArea(Terrain terrain){
        terrain.markAsChecked();
        return 0;
    }

    private void setEntities() {



        this.entities[59 / 2][36 / 2] = new Prey(0);
        int min_x = 59 / 2 - 9;
        int max_x = 59 / 2 + 9 + 1;
        int min_y = 36 / 2 - 9;
        int max_y = 36 / 2 + 9 + 1;
        for (int x = min_x; x < max_x; x++) {
            for (int y = min_y; y < max_y; y++) {
                if (terrain[x][y].getMaterialType() == Material.GROUND) {
                boolean chance = TerrainUtils.getRandomBoolean(1);
                    if (chance) {
                        this.entities[x][y] = new Prey(0);
                    }
                }
            }
        }
    }
}
