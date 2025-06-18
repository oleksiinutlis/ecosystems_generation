package io.ecosystems_generation;
// NOTE: CURRENT IMPLEMENTATION OF MUTATION COULD HAVE ERRORS IF THE CHOSEN MUTATION VOLATILITY + RANDOM TRAVEL RANGES IS LARGER THAN BE ALLOWED FOR A BYTE
public class Gene {
    // == required input variables ==
    public byte geneId;               // id for what trait the gene encodes for
    public byte randomTravelRange;    // max number the gene value can travel to
    // == optional input variables ==
    public byte geneValue;            // the value of the gene
    byte mutationVolatility;           // max amount the gene can mutate in one mutation
    byte mutationProbability;          // probability the gene will mutate in byte form (out of 100)

    public Gene(geneBuilder builder) {
        this.geneId = builder.geneId;
        this.randomTravelRange = builder.randomTravelRange;
        this.geneValue = builder.geneValue;
        this.mutationVolatility = builder.mutationVolatility;
        this.mutationProbability = builder.mutationProbability;
    }

    public static class geneBuilder {
        public byte geneId;
        public byte randomTravelRange;

        public byte geneValue = GeneUtils.getRandomGeneByte(this.randomTravelRange);
        byte mutationVolatility;
        byte mutationProbability = 25;     // this means 25% of the time the gene will mutate

        public geneBuilder(byte geneId, byte randomTravelRange) {
            this.geneId = geneId;
            this.randomTravelRange = randomTravelRange;
            this.mutationProbability = (byte) (randomTravelRange / 20);
            // this means the value can change 5% of the travel range units per round of mutation
        }

        public geneBuilder setMutationProbability(byte mutationProbability) {
            this.mutationProbability = mutationProbability;
            return this;
        }

        public geneBuilder setMutationVolatility(byte mutationVolatility) {
            this.mutationVolatility = mutationVolatility;
            return this;
        }

        public geneBuilder setGeneValue(byte geneValue) {
            this.geneValue = geneValue;
            return this;
        }

        public Gene build() {
            return new Gene(this);
        }

    }


    public Gene recombination(Gene otherGene) {

        if (GeneUtils.getRandomGeneByte((byte) 0b1) > 0) {  // dads gene is chosen, 50% chance
            Gene newGene = new
            Gene.geneBuilder(this.geneId, this.randomTravelRange)
            .setMutationProbability(this.mutationProbability)
            .setMutationVolatility(this.mutationVolatility)
            .setGeneValue(mutate(this.geneValue))
            .build();
            return newGene;
        }
        else {                                             // moms gene is chosen
            Gene newGene = new
            Gene.geneBuilder(this.geneId, this.randomTravelRange)
            .setMutationProbability(this.mutationProbability)
            .setMutationVolatility(this.mutationVolatility)
            .setGeneValue(mutate(otherGene.geneValue))
            .build();
            return newGene;
        }
    }

    private byte mutate(byte currentGeneValue) {
        if (Math.abs(GeneUtils.getRandomGeneByte((byte) 100)) <= this.mutationProbability) {  // calculate if mutation is going to happen using mutation probability
            // System.err.println("gene mutated");
            byte mutationSum = GeneUtils.getRandomGeneByte(this.mutationVolatility);            // add random byte up to mutation volatility max to the gene
            if (mutationSum < -this.randomTravelRange) return this.randomTravelRange;
            if (mutationSum > this.randomTravelRange)  return this.randomTravelRange;

            if (mutationSum == 0) {
                return GeneUtils.getRandomGeneByte((byte) 0b0001);
            }

            return mutationSum;

        }
        else {
            return currentGeneValue;                                                      // if no mutation is occuring, return original gene
        }
    }
}
