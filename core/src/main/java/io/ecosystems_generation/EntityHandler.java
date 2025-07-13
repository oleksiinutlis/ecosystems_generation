package io.ecosystems_generation;
import java.util.ArrayList; // Import the ArrayList class
import java.util.List;

public class EntityHandler {

    private final Entity[][] entities;
    private final int zoneStartY, zoneEndY;
    private final int zoneStartX, zoneEndX;
    
    private final List<Request> requestQueue = new ArrayList<>();
    private final List<Response> responseQueue = new ArrayList<>();

    public EntityHandler(Entity[][] entities, int zoneStartX, int zoneStartY, int zoneEndX, int zoneEndY) {
        this.entities = entities;
        this.zoneStartY = zoneStartY;
        this.zoneStartX = zoneStartX;
        this.zoneEndX = zoneEndX;
        this.zoneEndY = zoneEndY;
    }

    // Called every tick
    public void stepZone() {
        requestQueue.clear();
        responseQueue.clear();

        // Phase 1: Gather all intents
        for (int y = zoneStartY; y < zoneEndY; y++) {
            for (int x = zoneStartX; x < zoneEndX; x++) {
                Entity e = entities[x][y];
                if (e != null) {
                    e.sendRequests(this, x, y);  // animals only submit requests
                }
            }
        }

        // Phase 2: Resolve requests


        for (Request request : requestQueue) {
            int[] request_arguments = request.getArgs();
            Entity entity_arguments = request.getEntity();
            switch (request.getType()) {
                case MOVE:
                    int fromX = request_arguments[0];
                    int fromY = request_arguments[1];
                    int toX = request_arguments[2];
                    int toY = request_arguments[3];

                    if (inBounds(toX, toY) && entities[toX][toY] == null) {
                        entities[toX][toY] = entity_arguments;
                        entities[fromX][fromY] = null;
                        responseQueue.add(new Response(RequestType.MOVE, entity_arguments, ResponseStatus.SUCCESS, null));
                    } else {
                        responseQueue.add(new Response(RequestType.MOVE, entity_arguments, ResponseStatus.DENIED, null));
                    }
                break;
                case SENSE:
                    int x = request_arguments[0];
                    int y = request_arguments[1];
                    int radius = request_arguments[2];
                    Entity[][] view = extractSubgrid(x, y, radius);
                    responseQueue.add(new Response(RequestType.SENSE, entity_arguments, ResponseStatus.SUCCESS, view));
                break;
            }
        }

        // Phase 3: Deliver feedback
        for (Response result : responseQueue) {
            result.getEntity().receiveResponse(result);
        }
    }

    public void submitRequest(Request r) {
        requestQueue.add(r);
    }

    private Entity[][] extractSubgrid(int cx, int cy, int r) {
        int size = 2 * r + 1;
        Entity[][] result = new Entity[size][size];
        for (int dx = -r; dx <= r; dx++) {
            for (int dy = -r; dy <= r; dy++) {
                int x = cx + dx;
                int y = cy + dy;
                if (inBounds(x, y)) {
                    result[dx + r][dy + r] = entities[x][y];
                }
            }
        }
        return result;
    }

    private boolean inBounds(int x, int y) {
        return x >= 0 && y >= 0 && x < entities.length && y < entities[0].length;
    }
}