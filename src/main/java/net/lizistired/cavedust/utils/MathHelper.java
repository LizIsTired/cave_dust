package net.lizistired.cavedust.utils;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MathHelper {
    /**
     * Normalizes a value to between a range, eg. you want to map a value of 100 to 0 - 1, with 50 being 0.5.
     * @param min Minimum value
     * @param max Maximum value
     * @param val Value to be normalized
     * @return Normalized value (double)
     */
    public static double normalize(double min, double max, double val) {
        return 1 - ((val - min) / (max - min));
    }

    /**
     * Generates a random int between min and max
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number (int)
     */
    public static int generateRandomInt(int min, int max) {
        Random random = new Random();
        return random.ints(min, max)
                .findFirst()
                .getAsInt();
    }

    /**
     * Generates a random double between min and max
     * @param min Minimum value
     * @param max Maximum value
     * @return Random number (double)
     */
    public static double generateRandomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }
}
