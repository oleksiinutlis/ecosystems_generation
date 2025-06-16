package io.github.evolutions;

public class World {
    private Entity[][] entities;
    private Terrain[][] terrain;

    private final int worldSize;
    private float[][] whiteNoise;

    public float[][] getWhiteNoise() {
        return whiteNoise;
    }

    public int getWorldSize(){
        return worldSize;
    }

    private void generateWhiteNoise(){
        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                whiteNoise[i][j] = (float) Math.random();
            }
        }
    }

    public World(int size){
        this.worldSize = size;

        this.terrain = new Terrain[worldSize][worldSize];
        this.whiteNoise = new float[worldSize][worldSize];
        generateWhiteNoise();

        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                terrain[i][j] = new Terrain(Material.GROUND);
            }
        }
    }
}
