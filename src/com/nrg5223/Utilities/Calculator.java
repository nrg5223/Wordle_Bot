package com.nrg5223.Utilities;

/**
 * A container for calculation-related methods.  These methods are all used for
 * getting solver stats.
 */
public class Calculator {

    public double[] calcStats(int[] stats) {
        double gameCount = stats[0];
        double winCount = stats[1];
        double totalGuessCount = stats[2];
        double totalGuessTime = stats[3];
        double totalTestTime = stats[stats.length - 1];
        double[] listSizes = new double[6];

        double winPercentage = 100 * winCount / gameCount;
        double avgGuessCount = totalGuessCount / winCount;
        double avgGuessTime = totalGuessTime / totalGuessCount;
        double avgGameTime = totalGuessTime / gameCount;
        listSizes[0] = stats[4] / gameCount;
        for (int i = 1; i < 6; i++)
            listSizes[i] = stats[4 + i] / winCount;

        return new double[]
                {
                gameCount,
                winPercentage,
                avgGuessCount,
                avgGuessTime,
                avgGameTime,
                listSizes[0],
                listSizes[1],
                listSizes[2],
                listSizes[3],
                listSizes[4],
                listSizes[5],
                totalTestTime
                };
    }

    /**
     * Calculate an estimate for time left in getting running tests.  This
     * method is used by WordleTester subclasses for outputting status updates
     * to the user.
     *
     * @param timeSpent the amount of time the tests have taken so far
     * @param numberIterationsDone the number of iterations done so far
     * @param totalIterations the total number of iterations to do
     * @return an estimate for time left
     */
    public static double estimatedTimeLeft(double timeSpent, int numberIterationsDone, int totalIterations) {
        double timeOfIteration = timeSpent / numberIterationsDone;
        double totalTime = timeOfIteration * totalIterations;
        return totalTime - timeSpent;
    }
}
