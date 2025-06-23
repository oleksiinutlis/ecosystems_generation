package io.ecosystems_generation;

import java.util.Random;

public class NoiseGenerator {

    // Seeded random for reproducible results
    private Random random = World.getRandom();
    // Smoothstep (fade) function for smooth interpolation
    private static float fade(float t) {
        return t * t * (3 - 2 * t);
    }

    // Linear interpolation
    // https://en.wikipedia.org/wiki/Linear_interpolation
    private static float lerp(float a, float b, float t) {
        return a + t * (b - a);
    }

    public float[][] generateSmoothNoise(int outputSize){
        // Three noises with different weights to smooth out the result
        float[][] whiteNoiseSmall = generateWhiteNoise(outputSize/16);
        float[][] whiteNoiseMid = generateWhiteNoise(outputSize/8);
        float[][] whiteNoiseBig = generateWhiteNoise(outputSize/4);

        float[][] smoothNoise = new float[outputSize][outputSize];

        for (int y = 0; y < outputSize; y++) {
            for (int x = 0; x < outputSize; x++) {
                smoothNoise[y][x] = calculateValueNoise(x, y,  whiteNoiseSmall.length, outputSize, whiteNoiseSmall) * 1f;
                smoothNoise[y][x] += calculateValueNoise(x, y, whiteNoiseMid.length, outputSize, whiteNoiseMid) * 0.5f;
                smoothNoise[y][x] += calculateValueNoise(x, y, whiteNoiseBig.length, outputSize, whiteNoiseBig) * 0.25f;
                smoothNoise[y][x] /= 1.75f; // because we add the noises the sum of weights is 1.75, and it has to be in range [0;1)
            }
        }
        return smoothNoise;
    }

    private float calculateValueNoise(int x, int y,  int inputSize, int outputSize, float[][] whiteNoise){
        float gy = ( (float) y / (outputSize - 1) ) * (inputSize - 1);
        int y0 = (int) Math.floor(gy);
        int y1 = Math.min(y0 + 1, inputSize - 1);
        float ty = fade(gy - y0);


        // Map x coordinate to fractional white noise grid coordinate
        float gx = ( (float) x / (outputSize - 1) ) * (inputSize - 1);
        int x0 = (int) Math.floor(gx);
        int x1 = Math.min(x0 + 1, inputSize - 1);
        float tx = fade(gx - x0);

        // Fetch four neighboring white noise values
        float v00 = whiteNoise[y0][x0];
        float v10 = whiteNoise[y0][x1];
        float v01 = whiteNoise[y1][x0];
        float v11 = whiteNoise[y1][x1];

        // Interpolate horizontally
        float ix0 = lerp(v00, v10, tx);
        float ix1 = lerp(v01, v11, tx);

        // Interpolate vertically
        return lerp(ix0, ix1, ty);
    }

    public float[][] generateWhiteNoise(int size){
        float[][] whiteNoise = new float[size][size];
        for (int i = 0; i < whiteNoise.length; i++) {
            for (int j = 0; j < whiteNoise.length; j++) {
                whiteNoise[i][j] = random.nextFloat();
            }
        }
        return whiteNoise;
    }
}
