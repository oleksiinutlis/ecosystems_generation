package io.github.evolutions;

public class World {
    private Entity[][] entities;
    private Terrain[][] terrain;

    private final int mapSize;

    public int getMapSize(){
        return mapSize;
    }

    public World(int size){
        mapSize = size;
        terrain = new Terrain[mapSize][mapSize];

        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                terrain[i][j] = new Terrain(Material.GROUND);
            }
        }
    }
}
