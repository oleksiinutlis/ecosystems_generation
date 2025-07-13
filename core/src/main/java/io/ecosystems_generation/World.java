package io.ecosystems_generation;

import io.ecosystems_generation.EntityHandling.*;
import io.ecosystems_generation.TerrainHandling.Material;
import io.ecosystems_generation.TerrainHandling.NoiseGenerator;
import io.ecosystems_generation.TerrainHandling.Terrain;
import io.ecosystems_generation.TerrainHandling.TerrainUtils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class World {
    private static Entity[] entities;
    private static boolean[][] foodMap;
    private static Terrain[][] terrain;
    private static float[][] noise;
    private static Random random;
    private static int worldSize;
    private static DrawTools drawTool;

    public EntityHandler handler;
    public World(int size, int seed){
        worldSize = size;
        terrain = new Terrain[worldSize][worldSize];
        entities = new Entity[worldSize];
        foodMap = new boolean[worldSize][worldSize];

        // fully random if seed is set to 0
        random = seed == 0 ? new Random() : new Random(seed);

        NoiseGenerator noiseGenerator = new NoiseGenerator();
        noise = noiseGenerator.generateSmoothNoise(worldSize);
        setTerrain();
        setEntities();

//        int min_x = 0;
//        int max_x = entities.length;
//        int min_y = 0;
//        int max_y = entities[0].length;
//
//        // todo remove magic numbers
//        this.handler = new EntityHandler(entities, min_x, max_x, min_y, max_y);

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

    public static Entity[] getEntities() {
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
        for (int x = 0; x < worldSize; x++) {
            for (int y = 0; y < worldSize; y++) {
                if (terrain[x][y].getMaterialType() == Material.GROUND) {
                    boolean chance = TerrainUtils.getRandomBoolean(0.5f);
                    if (chance) {
                        Predator predator = addPredator(0, x, y);
                        entities[x] = predator;
                        break;
                    }

                    chance = TerrainUtils.getRandomBoolean(2f);
                    if (chance) {
                        Prey prey = addPrey(0, x, y);
                        entities[x] = prey;
                        break;
                    }
                }
            }
        }
    }

    public static void addFood(){
        int x = random.nextInt(0, worldSize);
        int y = random.nextInt(0, worldSize);
        foodMap[x][y] = true;
    }

    public static boolean checkForFood(int x, int y){
            return foodMap[x][y];
    }

    public static void eatFood(int x, int y){
        foodMap[x][y] = false;
    }

    private Predator addPredator(int id, int x, int y){
        Predator predator = new Predator(id);
        predator.setDrawnCoordinates(x * 16, y * 16);
        predator.setDesiredCoordinates(x, y);
        predator.setAnimationFrame(0);
        predator.setTextureAnimationsCount(6);

        return predator;
    }

    private Prey addPrey(int id, int x, int y){
        Prey prey = new Prey(id);
        prey.setDrawnCoordinates(x * 16, y * 16);
        prey.setDesiredCoordinates(x,  y);
        prey.setAnimationFrame(0);

        prey.setTextureAnimationsCount(4);

        return prey;
    }

    public void setDrawTool(DrawTools drawTool) {
        this.drawTool = drawTool;
    }
}
