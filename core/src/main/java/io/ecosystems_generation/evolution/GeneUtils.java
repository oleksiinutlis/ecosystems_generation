package io.ecosystems_generation.evolution;

import java.util.concurrent.ThreadLocalRandom;

public class GeneUtils {
    public static byte getRandomGeneByte(byte amplitude) {
        if (amplitude == 0) {
            return (byte) (-1 + ThreadLocalRandom.current().nextInt(0, 2) * 2);
            // returns from -1 to 1
        }
        byte lower = (byte) -amplitude;
        return (byte) ThreadLocalRandom.current().nextInt(lower, amplitude + 1);
    }
}
