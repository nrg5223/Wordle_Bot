package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;

/**
 * A solver version that uses the same method as SolverV3, except it chooses
 * guesses from the whole Wordle library.  SolverV3 chooses guesses from the
 * solution library, meaning it plays hard mode.  This solver assumes guesses
 * with all unique letters are best.
 *
 * Status: methods tested and verified.  Performs well.
 */
public class SolverV5 extends SolverV3 {

    public SolverV5(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return 5;
    }

    // GUESSING ***************************************************************

    @Override
    WordList guessList() {
        return wordleLib;
    }
}
