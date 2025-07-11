package io.ecosystems_generation.EntityHandling;


public class Prey extends Animal implements Entity {

    @Override
    public EntityType getType() {
        return EntityType.PREY;
    }

    @Override
    public void act(EntityHandler handler, int x, int y) {
        // TODO I INVENTED THIS
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

