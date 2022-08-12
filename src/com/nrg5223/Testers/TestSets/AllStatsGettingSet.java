package com.nrg5223.Testers.TestSets;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.StatsGettingGame;

/**
 * Used to run a number of TestingWordleGames.  The games record stats during
 * tests.  Then they are accessed by a WordleTester object to be recorded to
 * data/v_stats.txt where _ is the solver version.
 *
 * The set created is a subset of the solution library. It is created in an
 * AllWordsStatsGetter and passed to this class.
 */
public class AllStatsGettingSet extends StatsGettingSet {

    int index = 0;
    WordList libSubSet;

    public AllStatsGettingSet(int solverVersion, int setSize, WordList libSubSet, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, setSize, wordleLib, solutionLib);
        this.libSubSet = libSubSet;
        this.setSize = setSize;
    }

    // RUNNING TESTS **********************************************************

    /**
     * Create the next game to be tested.  It functions the same as in the
     * superclass except it chooses the next word in the subset for the solution
     * instead of a random word.
     */
    @Override
    void createNewGame() {
        game = new StatsGettingGame(solverVersion, libSubSet.get(index++), wordleLib, solutionLib);
    }
}
