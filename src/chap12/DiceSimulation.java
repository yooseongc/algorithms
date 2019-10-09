package chap12;

import utilities.StdRandom;

import java.util.Arrays;

/**
 * problem 1.1.35
 */
public class DiceSimulation {

    public static final int SIDE = 6;
    private static double[] exct = new double[SIDE * 2 + 1];
    private static double[] expr = new double[SIDE * 2 + 1];

    public static void main(String[] args) {
        calcExact();

        int n = 10;
        while (!isErrorBelow(0.001)) {
            calcExperiment(n);
            n = n * 10;
        }
        System.out.println("n= " + n);
    }

    private static boolean isErrorBelow(double tolerance) {
        double[] err = new double[SIDE * 2 + 1];
        for (int i = 0; i < err.length; i++) {
            err[i] = expr[i] - exct[i];
            if (Math.abs(err[i]) >= tolerance) return false;
        }
        return true;
    }

    private static void calcExperiment(int n) {
        // initialization
        for (int i = 0; i < expr.length; i++) {
            expr[i] = 0.0;
        }
        // try n times
        for (int k = 0; k < n; k++) {
            int diceA = StdRandom.uniform(1, 7);
            int diceB = StdRandom.uniform(1, 7);
            expr[diceA + diceB] += 1.0;
        }
        for (int k = 2; k <= 2 * SIDE; k++) {
            expr[k] /= n;
        }
        System.out.println("experiment using n= " + n + " --- " + Arrays.toString(expr));
    }

    private static void calcExact() {
        // initialization
        for (int i = 0; i < exct.length; i++) {
            exct[i] = 0.0;
        }
        // calc exact
        for (int i = 1; i <= SIDE; i++) {
            for (int j = 1; j <= SIDE; j++) {
                exct[i + j] += 1.0;
            }
        }
        for (int k = 2; k <= 2 * SIDE; k++) {
            exct[k] /= 36.0;
        }
        System.out.println("exact --- " + Arrays.toString(exct));
    }



}
