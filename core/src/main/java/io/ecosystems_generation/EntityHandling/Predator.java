package io.ecosystems_generation.EntityHandling;


public class Predator extends Animal implements Entity {
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
}

