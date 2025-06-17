package io.github.evolutions;

public class Animal {
    public int animal_id;
    public byte animal_age;
    public Gene gender_gene;
    public Gene speed_gene;
    public Gene strength_gene;
    public Gene sexy_gene;
    public Gene breeding_freq_gene;
    public Gene cardio_gene;
    public Gene sensing_strength_gene;
    public Gene likability_gene;
    public Gene aggresiveness_gene;
    // animal constructor with randomised genetic information, animal id derived through input
    public Animal(int animal_id) {
        this.animal_id = animal_id;
        this.animal_age = 0;    // animal age is set to 0 as the young adult
        // gender has gene id 0b0000
        this.gender_gene = new Gene.gene_builder((byte) 0b0000, (byte) 1).set_mutation_probability((byte) 0).set_mutation_volatility((byte) 0).build();   // gender implemented with gene with amplitude of 1, can be 1 or -1. it also cant mutate, because probability and volatility is 0
        // speed has gene id 0b0001
        this.speed_gene = new Gene.gene_builder((byte) 0b0001, (byte) 50).build();
        // strength has gene id 0b0010
        this.strength_gene = new Gene.gene_builder((byte) 0b0010, (byte) 50).build();
        // sexy has gene id 0b0011
        this.sexy_gene = new Gene.gene_builder((byte) 0b0011, (byte) 50).build();
        // breeding freq has gene id 0b0100
        this.breeding_freq_gene = new Gene.gene_builder((byte) 0b0100, (byte) 50).build();
        // cardio has gene id 0b0101
        this.cardio_gene = new Gene.gene_builder((byte) 0b0101, (byte) 50).build();
        // sensing strength has gene id 0b0110
        this.sensing_strength_gene = new Gene.gene_builder((byte) 0b0110, (byte) 50).build();
        // likability has gene id 0b0111
        this.likability_gene = new Gene.gene_builder((byte) 0b0111, (byte) 50).build();
        // aggresiveness has gene id 0b1000
        this.aggresiveness_gene = new Gene.gene_builder((byte) 0b1000, (byte) 50).build();
    }
    // version of constructor where the genes are inputted, used for child animal in breeding, where genes are inputted as product of recombination
    public Animal(int animal_id, Gene gender_gene, Gene speed_gene, Gene strength_gene, Gene sexy_gene, Gene breeding_freq_gene, Gene cardio_gene, Gene sensing_strength_gene, Gene likability_gene, Gene aggresiveness_gene) {
        this.animal_id = animal_id;
        this.animal_age = -128; // animal age is set at maximum negative value to communicate this an infant animal 
        // gender has gene id 0b0000
        this.gender_gene = gender_gene;   // gender implemented with gene with amplitude of 1, can be 1 or -1. it also cant mutate, because probability and volatility is 0
        // speed has gene id 0b0001
        this.speed_gene = speed_gene;
        // strength has gene id 0b0010
        this.strength_gene = strength_gene;
        // sexy has gene id 0b0011
        this.sexy_gene = sexy_gene;
        // breeding freq has gene id 0b0100
        this.breeding_freq_gene = breeding_freq_gene;
        // cardio has gene id 0b0101
        this.cardio_gene = cardio_gene;
        // sensing strength has gene id 0b0110
        this.sensing_strength_gene = sensing_strength_gene;
        // likability has gene id 0b0111
        this.likability_gene = likability_gene;
        // aggresiveness has gene id 0b1000
        this.aggresiveness_gene = aggresiveness_gene;
    }

    public Animal genetic_exchange(Animal parentAnimal) {
        Animal new_animal = new Animal(
            assign_new_animal_id(this.animal_id, parentAnimal.animal_id),
            this.gender_gene.recombination(parentAnimal.gender_gene), 
            this.strength_gene.recombination(parentAnimal.strength_gene),
            this.strength_gene.recombination(parentAnimal.strength_gene),
            this.sexy_gene.recombination(parentAnimal.sexy_gene),
            this.breeding_freq_gene.recombination(parentAnimal.breeding_freq_gene), 
            this.cardio_gene.recombination(parentAnimal.cardio_gene),
            this.sensing_strength_gene.recombination(parentAnimal.sensing_strength_gene),
            this.likability_gene.recombination(parentAnimal.likability_gene),
            this.aggresiveness_gene.recombination(parentAnimal.aggresiveness_gene)
            );

        return new_animal;
    }
    // A(x,y) = (x^2 + x + 2xy + 3y + y^2) / 2 
    // Cantor pairing function
    public int assign_new_animal_id(int parent_id, int other_parent_id) {
        return (parent_id * parent_id + parent_id + 2 * parent_id * other_parent_id + 3 * other_parent_id + other_parent_id * other_parent_id) / 2;
        
    }
}