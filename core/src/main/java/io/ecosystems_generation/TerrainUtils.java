package io.ecosystems_generation;

import java.util.concurrent.ThreadLocalRandom;

public class TerrainUtils {
    public static boolean getChanceCheck(int chance) {
        
        
        return 100 - ThreadLocalRandom.current().nextInt(0, chance + 1) > 0;
    }
}
