package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Alphabet;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.Collections;

/**
 * A solver version that uses letter frequency to choose the best guess. It
 * assumes guesses with all unique letters are best.
 *
 * Note: this solver plays hard mode, meaning it only chooses guesses that can
 * be a solution and makes use of the clue from all previous guesses.
 *
 * Status: methods tested and verified.  Performs well.
 */
public class SolverV2 extends SolverV1 {

    Alphabet alphabet;

    public SolverV2(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        alphabet = new Alphabet();
    }

    public int getVersion() {
        return 2;
    }

    // GUESSING ***************************************************************

    /**
     * The first guess generated is always the same.  It is a word that has been
     * found to be effective.  For the rest of the guesses, the solver chooses a
     * random word from the list of possible solutions.
     *
     * @return the next guess to submit
     */
    public Word generateGuess() {
        if (++guessCount == 1)
            return firstGuess;

        if (possibleSolutions.size() <= 2)
            return possibleSolutions.get(0);

        WordList guessList = guessList();
        calcWordScores(guessList);
        guessList.removeIf(word -> word.score() == 0);
        Collections.sort(guessList);

        return thisVersionGuess();
    }

    Word thisVersionGuess() {
        return bestGuessWithUniqueLetters(possibleSolutions);
    }

    WordList guessList() {
        return possibleSolutions;
    }

    /**
     * Calculate word scores.  A word's score correlates directly to the sum of
     * letter frequencies of a word's letters.
     */
    void calcWordScores(WordList guessList) {
        countLettersInPossibleSolutions();

        for (Word word : guessList) {
            word.calcAndSetScore(alphabet);
        }
    }

    // UNIQUE HELPERS *********************************************************

    /**
     * Count letter instances in words in possible solutions.  Then set those
     * letters' values in an alphabet object.
     */
    private void countLettersInPossibleSolutions() {
        alphabet.resetLetterCounts();
        for (Word word : possibleSolutions) {
            for (String letter : word.arr()) {
                alphabet.increment(letter);
            }
        }
    }
}
