package io.ecosystems_generation.EntityHandling;

import java.util.ArrayList; // Import the ArrayList class
import java.util.List;

public class EntityHandler {

    private final Entity[][] entities;
    private final int zoneStartY, zoneEndY;
    private final List<int[]> movementQueue = new ArrayList<>();

    public EntityHandler(Entity[][] entities, int zoneStartY, int zoneEndY) {
        this.entities = entities;
        this.zoneStartY = zoneStartY;
        this.zoneEndY = zoneEndY;
    }

    public void sendRequest(RequestType type, Entity entity, int... args) {
        if (type == RequestType.MOVE) {

                int fromX = args[0];
                int fromY = args[1];
                int toX = args[2];
                int toY = args[3];

                // TODO
                // THATS MY DRAWING MOVEMENT DONT TOUCH THAT
                if (entity instanceof Predator){
                    Predator predator = (Predator) entity;
                    predator.setDesiredCoordinates(toX, toY);
                }
                else if (entity instanceof Prey){
                    Prey prey = (Prey) entity;
                    prey.setDesiredCoordinates(toX, toY);
                }

                // Store move requests for later resolution
                movementQueue.add(new int[]{fromX, fromY, toX, toY});

            // Add more case types like SENSE, EAT, etc.
        }
    }

    public void resolveRequests() {
        for (int[] req : movementQueue) {
            int fromX = req[0];
            int fromY = req[1];
            int toX = req[2];
            int toY = req[3];

            Entity e = entities[fromX][fromY];
            if (e != null && entities[toX][toY] == null) {
                entities[toX][toY] = e;
                entities[fromX][fromY] = null;
            }
        }
        movementQueue.clear(); // Clear after applying
    }
}
