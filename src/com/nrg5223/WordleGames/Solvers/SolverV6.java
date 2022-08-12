package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.DataTypes.LetterIndexPair;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * A solver version that chooses guesses using the "brick-flame-podgy-shunt"
 * method.  This combination of 4 words contains the 20 most common letters.
 * This solver guesses these 4 words first then uses SolverV4's method for the
 * rest.
 *
 * Potential improvement: there is code in-progress for calculating the best
 * 4-word combination with the 20 most common letters.  It may be added to this
 * solver later.
 *
 * Status: has at least one unidentified bugs.
 */
public class SolverV6 extends SolverV4 {

    WordList first4Guesses = new WordList();

    public SolverV6(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        first4Guesses.add(new Word("brick"));
        first4Guesses.add(new Word("flame"));
        first4Guesses.add(new Word("podgy"));
        first4Guesses.add(new Word("shunt"));
    }

    @Override
    public int getVersion() {
        return 6;
    }

    // GUESSING ***************************************************************

    @Override
    Word thisVersionGuess() {
        if (guessCount < 5)
            return wordOfNextHighestScore();
        else {
            return v4Guess();
        }
    }

    @Override
    void calcWordScores(WordList guessList) {
        // Calculate average reduction of possible solutions of every word in
        // the library
        pairSet.resetAllCounts();
        for (Word word : guessList) {
            for (int i = 0; i < 5; i++) {
                String letter = word.arr()[i];

                double value = calcAvgReductionOfLIP(letter, i);

                pairSet.add(new LetterIndexPair(letter, i), value);
            }
            // Calculate scores for words in the Wordle library
            word.resetScore();
            word.calcAndSetScore(pairSet);
        }
    }

    @Override
    WordList guessList() {
        if (guessCount < 5)
            return first4Guesses;
        else
            return possibleSolutions;
    }

    // UNIQUE HELPERS *********************************************************

    /**
     * Get the word of the 4-word combo (brick, flame, podgy, shunt) that has
     * the highest score using SolverV4's method.
     *
     * @return brick, flame, podgy, or shunt; the one with the highest score
     */
    private Word wordOfNextHighestScore() {
        double[] scores = new double[4];

        WordList guessList = guessList();
        scores[0] = guessList.getWord("brick").score();
        scores[1] = guessList.getWord("flame").score();
        scores[2] = guessList.getWord("podgy").score();
        scores[3] = guessList.getWord("shunt").score();

        double highest = scores[0];
        int highestIndex = 0;
        double score;
        for (int i = 0; i < 4; i++) {
            score = scores[i];
            if (score > highest) {
                highest = score;
                highestIndex = i;
            }
        }
        return first4Guesses.get(highestIndex);
    }
}
