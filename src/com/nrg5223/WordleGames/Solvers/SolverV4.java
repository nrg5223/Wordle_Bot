package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.*;
import com.nrg5223.WordleGames.Solvers.DataTypes.LetterIndexPairMap;

/**
 * This is the one.
 *
 * A solver version that chooses guesses by calculating average reduction of
 * each potential guess.  To do so, it calculates the average reduction of each
 * letter-index in each guess, and sums the 5 letter-index reductions.  It
 * assumes guesses with all unique letters are best.
 *
 * Status: methods tested and verified.  Performs well.
 */
public class SolverV4 extends SolverV3 {

    public SolverV4(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return 4;
    }

    // GUESSING ***************************************************************

    @Override
    Word thisVersionGuess() {
        return bestGuessWithUniqueLetters(guessList());
    }

    /**
     * The v4 guessing method is reusing in some cases in subclasses of v4.
     * This method exists for those subclasses to use.
     *
     * @return the guess word v4 would return if it was the solver for a game
     */
    Word v4Guess() {
        return bestGuessWithUniqueLetters(guessList());
    }

    @Override
    void calcWordScores(WordList guessList) {
        pairSet = new LetterIndexPairMap();
        for (Word word : guessList) {
            for (int i = 0; i < 5; i++) {
                String letter = word.arr()[i];

                LetterIndexPair lip = new LetterIndexPair(letter, i);
                if (!pairSet.contains(lip)) {
                    double value = calcAvgReductionOfLIP(letter, i);
                    pairSet.add(lip, value);
                }
            }
            word.resetScore();
            word.calcAndSetScore(pairSet);
        }
    }

    @Override
    WordList guessList() {
        return wordleLib;
    }

    // UNIQUE HELPERS *********************************************************

    double calcAvgReductionOfLIP(String letter, int i) {
        double totalCount = possibleSolutions.size();
        double value = 0;

        double x = countGreenWords(letter, i);
        double y = countYellowWords(letter, i);
        double z = countGrayWords(letter, i);

        // The following right-sides of '=' all had the common factor 1/libSize.
        // It is removed to improve speed because the values are relative.

        value += x * (totalCount - x);
        value += y * (totalCount - y);
        value += z * (totalCount - z);

        return value;
    }

    // Words without the letter in that spot
    int countGreenWords(String letter, int index) {
        int count = 0;
        for (Word word : possibleSolutions)
            if (!word.containsAt(letter, index)) { count++; }
        return count;
    }

    // Words with the letter at the index
    int countGrayWords(String letter, int index) {
        return possibleSolutions.size() - countGreenWords(letter, index);
    }

    // Words without the letter or with the letter in wrong index
    int countYellowWords(String letter, int index) {
        int count = 0;
        for (Word word : possibleSolutions)
            if (!word.contains(letter) || word.containsAt(letter, index)) { count++; }
        return count;
    }
}
