package io.github.evolutions;

public class World {
    private Entity[][] entities;
    private Terrain[][] terrain;
    private float[][] noise;

    private final int worldSize;

    public int getWorldSize(){
        return worldSize;
    }

    public float[][] getNoise(){
        return noise;
    }

    public World(int size){
        this.worldSize = size;
        this.terrain = new Terrain[worldSize][worldSize];

        NoiseGenerator noiseGenerator = new NoiseGenerator();
        noise = noiseGenerator.generateSmoothNoise(worldSize);

        for (int i = 0; i < worldSize; i++) {
            for (int j = 0; j < worldSize; j++) {
                terrain[i][j] = new Terrain(Material.GROUND);
            }
        }
    }
}
