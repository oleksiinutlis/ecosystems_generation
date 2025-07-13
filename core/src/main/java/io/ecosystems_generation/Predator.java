package io.ecosystems_generation;


public class Predator extends Animal  implements Entity{
    private boolean moveSuccess = false;
    private Entity[][] lastVision = null;

    @Override
    public EntityType getType() {
        return EntityType.PREDATOR;
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


    public Predator(int animalId, Gene genderGene, Gene speedGene, Gene strengthGene, Gene sexyGene, Gene breeding_freqGene, Gene cardioGene, Gene sensing_strengthGene, Gene likabilityGene, Gene aggresivenessGene) {
        super(animalId, genderGene, speedGene, strengthGene, sexyGene, breeding_freqGene, cardioGene, sensing_strengthGene, likabilityGene, aggresivenessGene);

        // predator specific constructor components go here
    }

    
}

