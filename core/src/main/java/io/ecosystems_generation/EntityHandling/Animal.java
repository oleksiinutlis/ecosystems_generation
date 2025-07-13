package io.ecosystems_generation.EntityHandling;

import io.ecosystems_generation.Main;
import io.ecosystems_generation.TerrainHandling.TerrainUtils;

public class Animal {
    public int animalId;
    public int getID() { return animalId; }
    public byte animalAge;
    public Gene genderGene;
    // gender has gene id 0b0000
    public Gene speedGene;
    // speed has gene id 0b0001
    public Gene strengthGene;
    // strength has gene id 0b0010
    public Gene sexyGene;
    // sexy has gene id 0b0011
    public Gene breedingFreqGene;
    // breeding freq has gene id 0b0100
    public Gene cardioGene;
    // cardio has gene id 0b0101
    public Gene sensingStrengthGene;
    // sensing strength has gene id 0b0110
    public Gene likabilityGene;
    // likability has gene id 0b0111
    public Gene aggresivenessGene;
    // aggressiveness has gene id 0b1000

    int x;
    int y;

    int desiredX;
    int desiredY;

    int drawnX;
    int drawnY;
    int animationFrame;
    int textureAnimationsCount;
    int rendersSinceTextureChange;

    //
    // animal constructor with randomised genetic information, animal id derived through input
    //

    public Animal(int animal_id) {
        this.animalId = animal_id;
        this.animalAge = 0;    // animal age is set to 0 as the young adult
        this.genderGene = new Gene.geneBuilder((byte) 0b0000, (byte) 1).set_mutation_probability((byte) 0).set_mutation_volatility((byte) 0).build();   // gender implemented with gene with amplitude of 1, can be 1 or -1. it also cant mutate, because probability and volatility is 0
        this.speedGene = new Gene.geneBuilder((byte) 0b0001, (byte) 50).build();
        this.strengthGene = new Gene.geneBuilder((byte) 0b0010, (byte) 50).build();
        this.sexyGene = new Gene.geneBuilder((byte) 0b0011, (byte) 50).build();
        this.breedingFreqGene = new Gene.geneBuilder((byte) 0b0100, (byte) 50).build();
        this.cardioGene = new Gene.geneBuilder((byte) 0b0101, (byte) 50).build();
        this.sensingStrengthGene = new Gene.geneBuilder((byte) 0b0110, (byte) 50).build();
        this.likabilityGene = new Gene.geneBuilder((byte) 0b0111, (byte) 50).build();
        this.aggresivenessGene = new Gene.geneBuilder((byte) 0b1000, (byte) 50).build();
    }
    //
    // version of constructor where the genes are inputted, used for child animal in breeding, where genes are inputted as product of recombination
    //
    public Animal(int animal_id, Gene genderGene, Gene speedGene, Gene strengthGene, Gene sexyGene, Gene breeding_freqGene, Gene cardioGene, Gene sensing_strengthGene, Gene likabilityGene, Gene aggresivenessGene) {
        this.animalId = animal_id;
        this.animalAge = -128; // animal age is set at maximum negative value to communicate this an infant animal
        this.genderGene = genderGene;   // gender implemented with gene with amplitude of 1, can be 1 or -1. it also cant mutate, because probability and volatility is 0
        this.speedGene = speedGene;
        this.strengthGene = strengthGene;
        this.sexyGene = sexyGene;
        this.breedingFreqGene = breeding_freqGene;
        this.cardioGene = cardioGene;
        this.sensingStrengthGene = sensing_strengthGene;
        this.likabilityGene = likabilityGene;
        this.aggresivenessGene = aggresivenessGene;
    }

    public Animal geneticExchange(Animal parentAnimal) {
        Animal new_animal = new Animal(
            assignNewAnimalId(this.animalId, parentAnimal.animalId),
            this.genderGene.recombination(parentAnimal.genderGene),
            this.strengthGene.recombination(parentAnimal.strengthGene),
            this.strengthGene.recombination(parentAnimal.strengthGene),
            this.sexyGene.recombination(parentAnimal.sexyGene),
            this.breedingFreqGene.recombination(parentAnimal.breedingFreqGene),
            this.cardioGene.recombination(parentAnimal.cardioGene),
            this.sensingStrengthGene.recombination(parentAnimal.sensingStrengthGene),
            this.likabilityGene.recombination(parentAnimal.likabilityGene),
            this.aggresivenessGene.recombination(parentAnimal.aggresivenessGene)
            );

        return new_animal;
    }

    public int assignNewAnimalId(int parentId, int other_parentId) {
        return cantor(parentId, other_parentId);
    }


    public static int[] getRandomStepTowards(int current_x, int current_y, int target_x, int target_y, double randomness) {
        int dx = target_x - current_x;
        int dy = target_y - current_y;

        int stepX = Integer.compare(dx, 0); // -1, 0, or 1
        int stepY = Integer.compare(dy, 0);

        // Add jitter with probability
        if (Math.random() < randomness) {
            stepX += (int)(Math.round((Math.random() - 0.5) * 2)); // -1, 0, or 1
            stepY += (int)(Math.round((Math.random() - 0.5) * 2));
        }

        // Clamp to valid step range
        stepX = Math.max(-1, Math.min(1, stepX));
        stepY = Math.max(-1, Math.min(1, stepY));

        return new int[] { target_x + stepX, target_y + stepY };
    }

    // A(x,y) = (x^2 + x + 2xy + 3y + y^2) / 2
    // Cantor pairing function
    public int cantor(int x, int y){
        int res = x * x + x + 2 * x * y + 3 * y + y * y;
        return res / 2;
    }

    public void setTextureAnimationsCount(int count) {
        this.textureAnimationsCount = count;
    }

    public int getAnimationFrame() {
        return animationFrame;
    }

    public void setAnimationFrame(int animationFrame) {
        this.animationFrame = animationFrame;
    }

    public void setNextFrame(){
        this.animationFrame++;
        this.animationFrame %= (textureAnimationsCount);
    }

    public void setDrawnCoordinates(int x, int y){
        this.drawnX = x;
        this.drawnY = y;
    }

    public int getDrawnX() {
        return drawnX;
    }

    public int getDrawnY() {
        return drawnY;
    }

    public boolean isMoving() {
        return desiredX != drawnX || desiredY != drawnY;
    }

    public int getDesiredX() {
        return desiredX;
    }

    public void setDesiredX(int desiredX) {
        this.desiredX = desiredX;
    }

    public int getDesiredY() {
        return desiredY;
    }

    public void setDesiredY(int desiredY) {
        this.desiredY = desiredY;
    }

    public void setDesiredCoordinates(int x, int y){
        setDesiredX(Main.getTileSize() * x);
        setDesiredY(Main.getTileSize() * y);
    }

    public int getRendersSinceTextureChange() {
        return rendersSinceTextureChange;
    }

    public void setRendersSinceTextureChange(int rendersSinceTextureChange) {
        this.rendersSinceTextureChange = rendersSinceTextureChange;
    }

    void changeTexture(){
        int rendersSinceTextureChange = getRendersSinceTextureChange();
        if (rendersSinceTextureChange % TerrainUtils.getRandomInt(1, 31) == 0) {
            setNextFrame();
        }
        setRendersSinceTextureChange(rendersSinceTextureChange + 1);
    }
}
