package com.nrg5223.Testers;

import com.nrg5223.Testers.TestSets.StatsGettingSet;
import com.nrg5223.Testers.TestSets.TimedStatsGettingSet;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.Utilities.DataHandlers.DataWriter;

import java.io.IOException;

/**
 * An object for running multithreaded wordle tests.  Each thread runs a set of
 * wordle games in a TestSet object.  Then their data is combined and recorded
 * to data/v_stats.txt where _ is the solver version.  Multithreading is used to
 * speed up testing.
 *
 * The test sets created are StatsGettingSets.  The size of each set is a
 * fraction of the total set count specified in the run configuration.  If it is
 * not divisible by the number of threads, the last thread also runs the
 * remaining number of tests.  Solution words are randomly generated for these
 * tests.
 */
public class StatsGetter extends WordleTester {

    int[] stats = new int[11];
    StringBuilder missedWordsStr = new StringBuilder();

    public StatsGetter(int solverVersion, int totalTestCount, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, totalTestCount, wordleLib, solutionLib);
    }

    // CREATING SETS **********************************************************

    /**
     * This method is meant to be overridden by AllStatsGetter where the
     * set created is a specified subset of the Wordle library.
     *
     * @return a TestSet object to be used for running a test
     * @param setNumber the number set that is being made (first set is set #1)
     */
    @Override
    StatsGettingSet createSet(int setNumber) {
        numSetsMade++;
        if (setNumber == numThreads)
            return new TimedStatsGettingSet(solverVersion, setSize + remainder(), wordleLib, solutionLib);
        return new StatsGettingSet(solverVersion, setSize + remainder(), wordleLib, solutionLib);
    }

    // STATS HELPERS **********************************************************

    private void updateStats(int[] newStats) {
        for (int i = 0; i < newStats.length; i++) {
            stats[i] += newStats[i];
        }
    }

    // DATA HELPERS ***********************************************************

    @Override
    void combineAllData() {
        for (StatsGettingSet set : testSets) {
            updateStats(set.getStats());
            missedWordsStr.append(set.getMissedWordsStr());
        }
    }

    /**
     * The StatsGetter must put the 'time' value in the stats array before the
     * stats are recorded.  The superclass cannot do this because StatsGetter is
     * the only subclass that uses the time.
     */
    @Override
    void recordData() {
        stats[stats.length - 1] = time;

        try {
            DataWriter writer = new DataWriter();
            writer.recordStats(stats);
            writer.recordMissedWords(missedWordsStr.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
