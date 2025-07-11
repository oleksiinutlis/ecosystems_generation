package io.ecosystems_generation;

import io.ecosystems_generation.EntityHandling.Entity;
import io.ecosystems_generation.EntityHandling.EntityHandler;
import io.ecosystems_generation.EntityHandling.Predator;
import io.ecosystems_generation.EntityHandling.Prey;
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

        // TODO FIX
//        EntityHandler handler = new EntityHandler(entities, 0, 59, 0, 36);
//        handler.printZone();
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

    public static void setTerrainTile(int x, int y, Material materialType){
        terrain[x][y].setMaterialType(materialType);
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

        // Clean up small chunks of material (< 20 tiles)
        terrainCleanup();

    }

    private void terrainCleanup() {

        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                Material currentType = terrain[x][y].getMaterialType();
                if (currentType == Material.GROUND || currentType == Material.WATER) {
                    if (!terrain[x][y].checked()) {
                        // Get all tiles in connected area
                        LinkedList<Terrain> chunk = new LinkedList<>();
                        int areaSize = getChunkArea(x, y, currentType, chunk);

                        // If small area, flip material type
                        if (areaSize < 20) {
                            Material newType = (currentType == Material.WATER) ? Material.GROUND : Material.WATER;
                            for (Terrain t : chunk) {
                                t.setMaterialType(newType);
                            }
                        }
                    }
                }
            }
        }
    }

    private int getChunkArea (int startX, int startY, Material type, LinkedList < Terrain > chunk){
        int count = 0;
        Queue<Terrain> queue = new LinkedList<>();
        queue.add(terrain[startX][startY]);

        while (!queue.isEmpty()) {
            Terrain current = queue.poll();
            int x = current.getX();
            int y = current.getY();

            if (x < 0 || x >= worldSize || y < 0 || y >= worldSize) continue;
            Terrain t = terrain[x][y];

            if (t.checked() || t.getMaterialType() != type) continue;

            t.markAsChecked();
            chunk.add(t);
            count++;

            // Add 4 neighbors (up, down, left, right)
            if (x > 0) queue.add(terrain[x - 1][y]);
            if (x < worldSize - 1) queue.add(terrain[x + 1][y]);
            if (y > 0) queue.add(terrain[x][y - 1]);
            if (y < worldSize - 1) queue.add(terrain[x][y + 1]);
        }
        return count;
    }

    private void setEntities() {
        // TODO I REMOVED YOUR MAGIC NUMBERS
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                if (terrain[x][y].getMaterialType() == Material.GROUND) {
                    boolean chance = TerrainUtils.getRandomBoolean(0.5f);
                    if (chance) {
                        entities[x][y] = new Predator(0);
                    }

                    //TODO entity setting
                    chance = TerrainUtils.getRandomBoolean(0.5f);
                    if (chance) {
                        entities[x][y] = new Prey(0);
                    }
                }
            }
        }
    }

    public static void addFood(){
        int x = random.nextInt(0, worldSize);
        int y = random.nextInt(0, worldSize);
        if (terrain[x][y].getMaterialType() == Material.GROUND){
            foodMap[x][y] = true;
        }

    }

    public static boolean checkForFood(int x, int y){
        return foodMap[x][y];
    }

    public static void eatFood(int x, int y){
        foodMap[x][y] = false;
    }
}
