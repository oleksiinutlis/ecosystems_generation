package io.github.evolutions;
// NOTE: CURRENT IMPLEMENTATION OF MUTATION COULD HAVE ERRORS IF THE CHOSEN MUTATION VOLATILITY + RANDOM TRAVEL RANGES IS LARGER THAN BE ALLOWED FOR A BYTE
public class Gene {
    // == required input variables ==
    public byte gene_id;               // id for what trait the gene encodes for
    public byte random_travel_range;    // max number the gene value can travel to
    // == optional input variables ==
    public byte gene_value;            // the value of the gene
    byte mutation_volatility;           // max amount the gene can mutate in one mutation
    byte mutation_probability;          // probability the gene will mutate in byte form (out of 100)

    public Gene(gene_builder builder) {
        this.gene_id = builder.gene_id;
        this.random_travel_range = builder.random_travel_range;
        this.gene_value = builder.gene_value;
        this.mutation_volatility = builder.mutation_volatility;
        this.mutation_probability = builder.mutation_probability;
    }

    public static class gene_builder {
        public byte gene_id;
        public byte random_travel_range;

        public byte gene_value = GeneUtils.getRandomGeneByte(this.random_travel_range);
        byte mutation_volatility;
        byte mutation_probability = 25;     // this means 25% of the time the gene will mutate

        public gene_builder(byte gene_id, byte random_travel_range) {
            this.gene_id = gene_id;
            this.random_travel_range = random_travel_range;
            this.mutation_probability = (byte) (random_travel_range / 20);
            // this means the value can change 5% of the travel range units per round of mutation
        }

        public gene_builder set_mutation_probability(byte mutation_probability) {
            this.mutation_probability = mutation_probability;
            return this;
        }

        public gene_builder set_mutation_volatility(byte mutation_volatility) {
            this.mutation_volatility = mutation_volatility;
            return this;
        }

        public gene_builder set_gene_value(byte gene_value) {
            this.gene_value = gene_value;
            return this;
        }

        public Gene build() {
            return new Gene(this);
        }

    }


    public Gene recombination(Gene otherGene) {

        if (GeneUtils.getRandomGeneByte((byte) 0b1) > 0) {  // dads gene is chosen, 50% chance
            Gene new_gene = new
            Gene.gene_builder(this.gene_id, this.random_travel_range)
            .set_mutation_probability(this.mutation_probability)
            .set_mutation_volatility(this.mutation_volatility)
            .set_gene_value(mutate(this.gene_value))
            .build();
            return new_gene;
        }
        else {                                             // moms gene is chosen
            Gene new_gene = new
            Gene.gene_builder(this.gene_id, this.random_travel_range)
            .set_mutation_probability(this.mutation_probability)
            .set_mutation_volatility(this.mutation_volatility)
            .set_gene_value(mutate(otherGene.gene_value))
            .build();
            return new_gene;
        }
    }

    private byte mutate(byte current_gene_value) {
        if (Math.abs(GeneUtils.getRandomGeneByte((byte) 100)) <= this.mutation_probability) {  // calculate if mutation is going to happen using mutation probability
            // System.err.println("gene mutated");
            byte mutation_sum = GeneUtils.getRandomGeneByte(this.mutation_volatility);            // add random byte up to mutation volatility max to the gene
            if (mutation_sum < -this.random_travel_range) return this.random_travel_range;
            if (mutation_sum > this.random_travel_range)  return this.random_travel_range;

            if (mutation_sum == 0) {
                return GeneUtils.getRandomGeneByte((byte) 0b0001);
            }

            return mutation_sum;

        }
        else {
            return current_gene_value;                                                      // if no mutation is occuring, return original gene
        }
    }
}
