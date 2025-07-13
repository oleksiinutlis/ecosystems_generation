package io.ecosystems_generation;


public class Prey extends Animal implements Entity{
    private boolean moveSuccess = false;
    private Entity[][] lastVision = null;

    @Override
    public EntityType getType() {
        return EntityType.PREY;
    }

    @Override
    public void sendRequests(EntityHandler handler, int x, int y) {
        // Request to sense surroundings with radius 2
        handler.submitRequest(new Request(RequestType.SENSE, this, x, y, 2));

        // Attempt to move right by 1 tile
        handler.submitRequest(new Request(RequestType.MOVE, this, x, y, x + 1, y));
    }

    @Override
    public void receiveResponse(Response response) {
        switch (response.getType()) {
            case MOVE:
                moveSuccess = (response.getStatus() == ResponseStatus.SUCCESS);
                if (!moveSuccess) {
                System.out.println("Prey " + getID() + " failed to move.");
            }
            break;

            case SENSE:
                if (response.getStatus() == ResponseStatus.SUCCESS) {
                    lastVision = (Entity[][]) response.getResult();
                    System.out.println("Prey " + getID() + " saw something.");
                } else {
                    lastVision = null;
                }
                break;
        }
    }

    public Prey(int animal_id, Gene gender_gene, Gene speed_gene, Gene strength_gene, Gene sexy_gene, Gene breeding_freq_gene, Gene cardio_gene, Gene sensing_strength_gene, Gene likability_gene, Gene aggresiveness_gene) {
        super(animal_id, gender_gene, speed_gene, strength_gene, sexy_gene, breeding_freq_gene, cardio_gene, sensing_strength_gene, likability_gene, aggresiveness_gene);

        // prey specific constructor components go here
    }

    public Prey(int animal_id) {
        super(animal_id);

        // prey specific constructor components go here
    }


}

