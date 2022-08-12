package com.nrg5223.Testers;

import com.nrg5223.Testers.TestSets.StatsGettingSet;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.Utilities.Print;

import java.util.ArrayList;
import java.util.List;

/**
 * An object for running multithreaded wordle tests.  Each thread runs a set of
 * wordle games in a TestSet object.  Subclasses of WordleTester records various
 * forms of data relating to the games in text files.
 */
public abstract class WordleTester {

    int solverVersion;

    final int setSize;
    final int totalTestCount;
    int numSetsMade = 0;
    int time;
    public final static int numThreads = Runtime.getRuntime().availableProcessors();
    final List<StatsGettingSet> testSets = new ArrayList<>();

    StringList wordleLib;
    StringList solutionLib;

    public WordleTester(int solverVersion, int totalTestCount, StringList wordleLib, StringList solutionLib) {
        this.solverVersion = solverVersion;
        this.totalTestCount = totalTestCount;
        setSize = totalTestCount / numThreads;

        this.wordleLib = wordleLib;
        this.solutionLib = solutionLib;
    }

    /**
     * Run tests using TestSet objects.  Create threads with TestSets, start
     * them all, join them all. When tests have ran, combine the stats from each
     * TestSet and record the data in a text files using DataHandler.
     */
    public void runTests() throws NullPointerException {
        Print.gettingStatsMsg(solverVersion, totalTestCount, this);

        List<Thread> threads = new ArrayList<>();

        double start = System.currentTimeMillis();

        createAndStartAll(threads);
        joinAll(threads);
        combineAllData();

        time = (int) (System.currentTimeMillis() - start) / 1000;

        recordData();
    }

    // MULTITHREADING HELPERS *************************************************

    void createAndStartAll(List<Thread> threads) {
        for (int i = 0; i < numThreads; i++) {
            StatsGettingSet set = createSet(i + 1);
            testSets.add(set);

            Thread thread = new Thread(set);
            threads.add(thread);
            thread.start();
        }
    }

    private void joinAll(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // CREATING SETS **********************************************************

    abstract StatsGettingSet createSet(int i);

    /**
     * WordleTester's constructor calculates test set (subset) size with integer
     * division.  If the given total number of tests is not divisible by the
     * number of threads used, the number of tests will be wrong.  This method
     * calculates the extra number of tests that need to be done.  This number
     * is only added to the last thread's set size.
     *
     * @return the remainder value if the current set is the last one; 0 otherwise.
     */
    int remainder() {
        if (numSetsMade == numThreads)
            return totalTestCount % numThreads;
        return 0;
    }

    // DATA HELPERS ***********************************************************

    abstract void combineAllData();

    abstract void recordData();
}
