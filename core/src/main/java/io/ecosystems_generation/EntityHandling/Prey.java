package io.ecosystems_generation.EntityHandling;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Prey extends Animal implements Entity {
    private boolean moveSuccess = false;
    private Entity[][] lastVision = null;
    private int ticksSinceLastEaten = 0;
    private int ticksSinceLastBreed = 0;
    private boolean predatorNearby = false;
    private int[] nearestPredator = null;
    private int[] nearestFood = null;
    private int[] nearestMate = null;
    // private int[] desire_counter = {0,0,0,0};

    @Override
    public EntityType getType() {
        return EntityType.PREY;
    }

    public void sendRequests(EntityHandler handler, int x, int y) {
        ticksSinceLastEaten++;
        ticksSinceLastBreed++;
        


        // Request to sense surroundings with radius 2
        if (lastVision == null) {
            handler.submitRequest(new Request(RequestType.SENSE, this, x, y, 6));
        }
        else {
            updateKnownThreatsAndTargets(lastVision);
            int mid = (int) Math.floor((float) lastVision.length / 2f);
            
            
            int[] target = {0,0};
            int[] next = getRandomStepTowards(x, y, target[0], target[0], 1);
            System.out.println("tryingggg");
            handler.submitRequest(new Request(RequestType.MOVE, this, x, y, next[0], next[1]));

            
        }
        // Attempt to move right by 1 tile
    }

    @Override
    public void receiveResponse(Response response) {
        switch (response.getType()) {
            case MOVE:
                moveSuccess = (response.getStatus() == ResponseStatus.SUCCESS);
                System.out.println(moveSuccess);
                if (!moveSuccess) {
                    System.out.println("Prey " + getID() + " failed to move.");
                }
                break;

            case SENSE:
                if (response.getStatus() == ResponseStatus.SUCCESS) {
                    lastVision = (Entity[][]) response.getResult();
                } else {
                    lastVision = null;
                }
                break;
        }
    }


    private void updateKnownThreatsAndTargets(Entity[][] vision) {
        nearestPredator = null;
        nearestFood = null;
        nearestMate = null;
        predatorNearby = false;

        int minPredDist = Integer.MAX_VALUE;
        int minFoodDist = Integer.MAX_VALUE;
        int minMateDist = Integer.MAX_VALUE;

        for (int i = 0; i < vision.length; i++) {
            for (int j = 0; j < vision[0].length; j++) {
                Entity e = vision[i][j];
                if (e == null || e == this) continue;

                int dist = Math.abs(i - vision.length / 2) + Math.abs(j - vision[0].length / 2); // Manhattan distance

                switch (e.getType()) {
                    case PREDATOR: 
                        predatorNearby = true;
                        if (dist < minPredDist) {
                            minPredDist = dist;
                            nearestPredator = new int[]{i, j};
                        }
                    break;
                    case FOOD:
                        if (dist < minFoodDist) {
                            minFoodDist = dist;
                            nearestFood = new int[]{i, j};
                        }
                    break;
                    case PREY:
                        if (dist < minMateDist) {
                            minMateDist = dist;
                            nearestMate = new int[]{i, j};
                        }
                    break;
                }
            }
        }
}

    public Prey(int animal_id, Gene gender_gene, Gene speed_gene, Gene strength_gene, Gene sexy_gene, Gene breeding_freq_gene, Gene cardio_gene, Gene sensing_strength_gene, Gene likability_gene, Gene aggresiveness_gene) {
        super(animal_id, gender_gene, speed_gene, strength_gene, sexy_gene, breeding_freq_gene, cardio_gene, sensing_strength_gene, likability_gene, aggresiveness_gene);
        // prey specific constructor components go here
    }

    public Prey(int id){
        super(id);
        // prey specific constructor components go here
    }


    @Override
    public void draw(SpriteBatch batch, TextureRegion[] textures) {
        batch.begin();
        if (isMoving()) {
            int drawnX = getDrawnX();
            int drawnY = getDrawnY();

            int desiredX = getDesiredX();
            int desiredY = getDesiredY();

            // TODO REPLACE THE -1 / 1 with -speed / speed
            // speed has to be reasonable, make it 1 to 5
            if (drawnX > desiredX) {
                drawnX += -1;
            } else if (drawnX < desiredX) {
                drawnX += 1;
            }

            if (drawnY > desiredY) {
                drawnY += -1;
            } else if (drawnY < desiredY) {
                drawnY += 1;
            }


            setDrawnCoordinates(drawnX, drawnY);

            int frame = getAnimationFrame();

            if (getDrawnX() >= getDesiredX()) {
                // We want to move left so we
                frame += 4;
            }
            batch.draw(textures[frame], getDrawnX(), getDrawnY(), 24, 24);
            changeTexture();
        } else {
            batch.draw(textures[getAnimationFrame()], getDrawnX(), getDrawnY(), 24, 24);
        }
        batch.end();
    }

    private double calculateDistance (int x, int y, int mid){
        double distanceX = Math.abs(mid - x);
        double distanceY = Math.abs(mid - y);
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }
}
