package com.nrg5223.Testers.TestSets;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;

/**
 * A subclass of LibrarySubSet that calls timedRun() in place of run().  It is
 * used once per AllWordsStatsGetter object.  It is used by the last thread of
 * tests to update the user on the terminal while tests are running.
 */
public class TimedAllStatsGettingSet extends AllStatsGettingSet {

    public TimedAllStatsGettingSet(int solverVersion, int setSize, WordList libSubSet, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, setSize, libSubSet, wordleLib, solutionLib);
    }

    @Override
    public void run() {
        timedRun();
    }
}
