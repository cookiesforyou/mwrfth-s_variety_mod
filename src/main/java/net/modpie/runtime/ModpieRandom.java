package net.modpie.runtime;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Beginner-friendly random number helpers.
 * <p>Modpie code like {@code random(3, 7)} gets translated to calls on this class.</p>
 */
public final class ModpieRandom {

    private ModpieRandom() {}

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public static int randomInt(int max) {
        return ThreadLocalRandom.current().nextInt(0, max + 1);
    }

    public static double randomDouble(double min, double max) {
        return ThreadLocalRandom.current().nextDouble(min, max);
    }

    public static double randomDouble(double max) {
        return ThreadLocalRandom.current().nextDouble(0, max);
    }

    public static boolean chance(double probability) {
        return ThreadLocalRandom.current().nextDouble() < probability;
    }

    public static <T> T pick(T[] options) {
        if (options.length == 0) return null;
        return options[ThreadLocalRandom.current().nextInt(options.length)];
    }

    public static <T> T pick(java.util.List<T> options) {
        if (options.isEmpty()) return null;
        return options.get(ThreadLocalRandom.current().nextInt(options.size()));
    }
}
