package io.ecosystems_generation.EntityHandling;


public class Predator extends Animal  implements Entity{
    private boolean moveSuccess = false;
    private Entity[][] lastVision = null;

    @Override
    public EntityType getType() {
        return EntityType.PREDATOR;
    }

    @Override
    public void act(EntityHandler handler, int x, int y) {
        // TODO I INVENTED THIS
    }


    public Predator(int animalId, Gene genderGene, Gene speedGene, Gene strengthGene, Gene sexyGene, Gene breeding_freqGene, Gene cardioGene, Gene sensing_strengthGene, Gene likabilityGene, Gene aggresivenessGene) {
        super(animalId, genderGene, speedGene, strengthGene, sexyGene, breeding_freqGene, cardioGene, sensing_strengthGene, likabilityGene, aggresivenessGene);

        // predator specific constructor components go here
    }

    public Predator(int animalId){
        super(animalId);

        // TODO I INVENTED THIS
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
                frame += 6;
            }
            batch.draw(textures[frame], getDrawnX(), getDrawnY(), 48, 36);;
            changeTexture();
        } else {
            batch.draw(textures[getAnimationFrame()], getDrawnX(), getDrawnY(), 48, 36);
        }
        batch.end();
    }
}

