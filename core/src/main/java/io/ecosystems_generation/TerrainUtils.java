package io.ecosystems_generation;

import java.util.concurrent.ThreadLocalRandom;

public class TerrainUtils {
    public static byte getRandomInt(int chance) {
        if (chance == 0) {
            return (byte) (-1 + ThreadLocalRandom.current().nextInt(0, 2) * 2);
            // returns from -1 to 1
        }
        byte lower = (byte) -chance;
        return (byte) ThreadLocalRandom.current().nextInt(lower, chance + 1);
    }
}
