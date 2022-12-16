package cz.polacek.game.utils;

import cz.polacek.game.config.Config;

import java.util.Random;

public class Utils {
    public static int randomNumberBetween(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }

    public static double randomDoubleBetween(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public static boolean randomBoolean() {
        return Math.random() < 0.5;
    }

    public static int[] randomSpawnLocation() {
        int[] ints = new int[2];
        if (randomBoolean()) {
            if (randomBoolean()) { ints[0] = -Config.tileComputed; }
            else { ints[0] = Config.windowWidth; }
            ints[1] = randomNumberBetween(0, Config.windowHeight);
        } else {
            if (randomBoolean()) { ints[1] = -Config.tileComputed; }
            else { ints[1] = Config.windowWidth; }
            ints[0] = randomNumberBetween(0, Config.windowWidth);
        }
        return ints;
    }

    public static int[] randomArray(int length, int min, int max) {
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = randomNumberBetween(min, max);
        }
        return ints;
    }
}
