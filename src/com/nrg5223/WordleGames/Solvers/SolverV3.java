package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.*;
import com.nrg5223.WordleGames.Solvers.DataTypes.LetterIndexPairMap;

/**
 * A solver version that uses letter-index frequency to choose the best guess.
 * It assumes guesses with all unique letters are best.
 *
 * Note: this solver plays hard mode, meaning it only chooses guesses that can
 * be a solution and makes use of the clue from all previous guesses.
 *
 * Status: methods tested and verified.  Performs well.
 */
public class SolverV3 extends SolverV2 {

    LetterIndexPairMap pairSet;

    public SolverV3(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        pairSet = new LetterIndexPairMap();
    }

    public int getVersion() {
        return 3;
    }

    // GUESSING ***************************************************************

    @Override
    void calcWordScores(WordList guessList) {
        // Count all letter-index-pair counts using words in possibleSolutions
        pairSet.resetAllCounts();
        for (Word word : possibleSolutions) {
            for (int i = 0; i < 5; i++) {
                pairSet.increment(new LetterIndexPair(word.arr()[i], i));
            }
        }

        for (Word word : guessList) {
            word.calcAndSetScore(pairSet);
        }
    }
}
