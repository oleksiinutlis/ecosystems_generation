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

    private static int worldSize;
    private static Random random;
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
                    material = Material.WATER;
                }
                else if (f >= 0.45f && f < 0.65f) {
                    // Decoration generation, 5% generation chance
                    if (TerrainUtils.getRandomBoolean(5f)) {
                        material = Material.DECORATION;
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
                boolean chance = TerrainUtils.getRandomBoolean(0.5f);
                    if (chance) {
                        this.entities[x][y] = new Predator(0);
                    }
                }
            }
        }
    }

    public static void setTerrainTile(int x, int y, Material materialType){
        terrain[x][y].setMaterialType(materialType);
    }

    public static void addFood(){
        int x = random.nextInt(0, worldSize);
        int y = random.nextInt(0, worldSize);
//        System.out.println("Trying to add food");
        if (terrain[x][y].getMaterialType() == Material.GROUND){
            foodMap[x][y] = true;
//            System.out.println("Adding food");
        }

    }

    public static boolean checkForFood(int x, int y){
        return foodMap[x][y];
    }

    public static void eatFood(int x, int y){
        foodMap[x][y] = false;
    }
}
