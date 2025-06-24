package io.ecosystems_generation;

import java.util.concurrent.ThreadLocalRandom;

public class TerrainUtils {
    public static boolean getRandomBoolean(float chance){
        return ThreadLocalRandom.current().nextFloat(0f, 100f) < chance;
    }

    public static int getRandomInt(int lower, int upper){
        return ThreadLocalRandom.current().nextInt(lower, upper);
    }
}
