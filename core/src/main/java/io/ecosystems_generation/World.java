package io.ecosystems_generation;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class World {
    private Entity[][] entities;
    private static Terrain[][] terrain;
    private static float[][] noise;

    private static int worldSize;
    private static Random random;

    private Queue<Terrain> terrainQueue = new LinkedList<>();

    public World(int size, int seed){
        worldSize = size;
        terrain = new Terrain[worldSize][worldSize];
        entities = new Entity[worldSize][worldSize];

        // fully random if seed is set to 0
        random = seed == 0 ? new Random() : new Random(seed);

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
                Material material = Material.GROUND; // default value ground
                if (f < 0.40f) {
                    material = Material.WATER;
                }
                else if (f >= 0.45f && f < 0.65f) {
                    // Stone generation, 1.5% generation chance
                    if (TerrainUtils.getRandomBoolean(2f)) {
                        material = Material.STONE;
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
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                if (terrain[x][y].getMaterialType() == Material.GROUND) {
                boolean chance = TerrainUtils.getRandomBoolean(5);
                    if (chance) {
                        this.entities[x][y] = new Prey(0);
                    }
                }
            }
        }
    }

    public static void setTerrainTile(int x, int y, Material materialType){
        terrain[x][y].setMaterialType(materialType);
    }
}
