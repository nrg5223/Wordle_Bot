package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.SimulationGame;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.Collections;

/**
 * A solver version that chooses guesses by calculating average reduction of
 * each potential guess.  To do so, it creates a copy of the current game for
 * each word, guesses each word, and gets the reduction of the solution list for
 * each one.  This takes a long time, so it uses SolverV4's method for the first
 * 2 guesses.  Then it uses its method to re-rank the best X guesses from
 * SolverV4's method.
 *
 * Status: methods tested and verified.  Performs well, but is slow.
 */
public class SolverV7 extends SolverV4 {

    SimulationGame simulationGame;
    int[] subSetSizes = {1, 1, 1000, 1000, 1000, 1000};

    public SolverV7(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        simulationGame = new SimulationGame(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return 7;
    }

    // GUESSING ***************************************************************

    @Override
    Word thisVersionGuess() {
        if (guessCount == 2)
            return v4Guess();
        WordList topGuesses = wordleLib.subList(0, subSetSizes[guessCount]); // best x guesses from v4's algorithm
        scoreTopGuesses(topGuesses); // calculate perfectly accurate ranking using v7's algorithm
        Collections.sort(topGuesses); // sort then return the best one
        return topGuesses.get(0);
    }

    // UNIQUE HELPERS *********************************************************

    /**
     * Calculate the scores of the words in the given list.  To do so, create a
     * simulation game for each potential guess and calculate the reduction of
     * each word by averaging the actual reductions of each guess in the
     * simulation games.
     *
     * @param guesses a list of potential guesses.  It is not the whole wordle
     *                library because that takes too long.  SolverV7 calculates
     *                the scores using its method for the best X guesses
     *                calculated using SolverV4's method.
     */
    public void scoreTopGuesses(WordList guesses) {
        int score;
        int reduction;
        for (Word word : guesses) {
            score = 0;
            for (Word theoreticalSolution : possibleSolutions) {
                // simulate guess and calculate reduction of guess in this simulation
                WordList possibleSolutionsCopy = new WordList(possibleSolutions);
                reduction = possibleSolutionsCopy.size() - simulationGame.getPossibleSolutionsSize(possibleSolutionsCopy, word, theoreticalSolution);
                score += reduction;
            }
            word.setScore(score);
        }
    }
}
