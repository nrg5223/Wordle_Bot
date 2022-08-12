package com.nrg5223.Testers;

import com.nrg5223.Testers.TestSets.AllStatsGettingSet;
import com.nrg5223.Testers.TestSets.StatsGettingSet;
import com.nrg5223.Testers.TestSets.TimedAllStatsGettingSet;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;

/**
 * An object for running multithreaded wordle tests.  Each thread runs a set of
 * wordle games in a TestSet object.  Then their data is combined and recorded
 * to data/v_stats.txt where _ is the solver version.  Multithreading is used to
 * speed up testing.
 *
 * This subclass of StatsGetter creates TestSets from subsets of the solution
 * library.  Its purpose is to create sets such that all solution words are
 * tested.
 */
public class AllStatsGetter extends StatsGetter {

    private final WordList wordSolutionLib;
    private final int subSetSize;

    public AllStatsGetter(int solverVersion, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, solutionLib.size(), wordleLib, solutionLib);
        this.wordSolutionLib = solutionLib.toWordList();
        int libSize = solutionLib.size();
        subSetSize = libSize / numThreads;
    }

    // CREATING SETS **********************************************************

    @Override
    StatsGettingSet createSet(int setNumber) {
        int fromIndex = subSetSize * numSetsMade++; // fromIndex is inclusive
        int toIndex = fromIndex + subSetSize;       // toIndex is exclusive
        int remainder = remainder();
        if (setNumber == numThreads)
            return new TimedAllStatsGettingSet(solverVersion,
                                          subSetSize + remainder,
                                          wordSolutionLib.subList(fromIndex, toIndex + remainder),
                                          wordleLib,
                                          solutionLib);
        return new AllStatsGettingSet(solverVersion,
                                 subSetSize + remainder,
                                 wordSolutionLib.subList(fromIndex, toIndex + remainder),
                                 wordleLib,
                                 solutionLib);
    }
}
