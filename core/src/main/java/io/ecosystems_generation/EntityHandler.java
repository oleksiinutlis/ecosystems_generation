package io.ecosystems_generation;


public class EntityHandler {
    private Entity[][] entities; // your shared or zoned grid
    private int zoneStartX, zoneEndX; // zone bounds
    private int zoneStartY, zoneEndY; // zone bounds


     public EntityHandler(Entity[][] entities, int zoneStartX, int zoneEndX, int zoneStartY, int zoneEndY) {
        this.entities = entities;
        this.zoneStartX = zoneStartX;
        this.zoneEndX = zoneEndX;
        this.zoneStartY = zoneStartY;
        this.zoneEndY = zoneEndY;
    }

    public void printZone() {
    for (int x = zoneStartX; x < zoneEndX; x++) {
        for (int y = zoneStartY; y < zoneEndY; y++) {
            Entity e = entities[x][y];
            if (e == null) {
                System.out.print("0 ");
            } else {
                
                    System.out.print("1 ");
                    
                    
                }
            }
        }
        System.out.println(); // Newline after each row
    }
}


