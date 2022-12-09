package cz.polacek.game.utils;

import java.util.Random;

public class Utils {
    public static int randomNumberBetween(int min, int max) {
        Random r = new Random();
        return r.nextInt(max - min) + min;
    }
    public static double randomDoubleBetween(double min, double max) {
        Random r = new Random();
        return r.nextDouble(max - min) + min;
    }

    public static int[] randomArray(int length, int min, int max) {
        int[] ints = new int[length];
        for (int i = 0; i < length; i++) {
            ints[i] = randomNumberBetween(min, max);
        }
        return ints;
    }
}
