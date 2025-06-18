package io.github.evolutions;

import java.util.concurrent.ThreadLocalRandom;

public class GeneUtils {
    public static byte getRandomGeneByte(byte amplitude) {
        byte lower = (byte) -amplitude;
        byte random_byte = (byte) ThreadLocalRandom.current().nextInt(lower, amplitude + 1);
        if (random_byte == 0) {
            return (byte) (-1 + ThreadLocalRandom.current().nextInt(0, 2) * 2);
            // returns from -1 to 1
        }
        else {
            return random_byte;
        }
    }
}
