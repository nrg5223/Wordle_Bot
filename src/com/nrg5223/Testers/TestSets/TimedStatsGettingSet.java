package com.nrg5223.Testers.TestSets;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;

/**
 * A subclass of StatsGettingSet that calls timedRun() in place of run().  It is
 * used once per StatsGetter object.  It is used by the last thread of tests to
 * update the user on the terminal while tests are running.
 */
public class TimedStatsGettingSet extends StatsGettingSet {

    public TimedStatsGettingSet(int solverVersion, int setSize, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, setSize, wordleLib, solutionLib);
    }

    @Override
    public void run() {
        timedRun();
    }
}
