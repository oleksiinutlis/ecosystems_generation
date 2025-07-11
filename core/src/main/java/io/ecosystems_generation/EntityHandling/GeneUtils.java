package io.ecosystems_generation.EntityHandling;

import java.util.concurrent.ThreadLocalRandom;

public class GeneUtils {
    public static byte getRandomGeneByte(byte amplitude) {
        byte lower = (byte) -amplitude;
        byte upper = amplitude;
        byte random_byte = (byte) ThreadLocalRandom.current().nextInt(lower, upper + 1);

        if (random_byte == 0) {
            byte new_random_byte = (byte) (-1 + ThreadLocalRandom.current().nextInt(0, 2) * 2);

            return new_random_byte;
        }
        else {
            return random_byte;
        }
    }
}
