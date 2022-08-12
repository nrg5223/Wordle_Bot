package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * A wordle game that stores additional data.  After a game is complete, this
 * data is passed on to a DataHandler to be recorded in a text file.  The data
 * stored:
 *      Stats - used to calculate other stats like average number of guesses per
 *              game, win percentage, and average time to generate a guess.
 */
public class StatsGettingGame extends AutomatedGame {

    /** Data used to calculate stats */
    private int gameCount = 0;
    private int totalGuessCount = 0;
    private int currGuessCount = 0;
    private int winCount = 0;
    private int totalGuessTime = 0;
    private final int[] possibleSolutionsSizes = new int[]{1, 1, 1, 1, 1, 1};

    // CONSTRUCTOR(S) *********************************************************

    /**
     * A constructor that takes a given solution instead of randomly generating
     * one.
     *
     * @param version the solver version
     * @param solution the word to be the solution.
     * @param wordleLib the list of words in the Wordle library as strings
     * @param solutionLib the list of words in the solution library as strings
     */
    public StatsGettingGame(int version, Word solution, StringList wordleLib, StringList solutionLib) {
        super(version, solution, wordleLib, solutionLib);
    }

    public StatsGettingGame(int version, StringList wordleLib, StringList solutionLib) {
        super(version, wordleLib, solutionLib);
    }

    // GAMEPLAY ***************************************************************

    /**
     * This functions the same way as it does in WordleGame.  In addition, it
     * updates data including: win count, guess count, and game count.
     *
     * currGuessCount is essentially the total guess count for the game.  But
     * only totalGuessCount is saved in stats[] and accessed by other classes.
     * totalGuessCount is updated by adding currGuessCount to it only if a game
     * is won.  This makes the average number of guesses only based on games
     * that were won.
     */
    @Override
    public void play() {
        while (!this.isOver()) {
            double start = System.currentTimeMillis();

            possibleSolutionsSizes[6 - guessesLeft] = solver.getPossibleSolutionsSize();
            this.submitGuess(getNextGuess());

            double guessTime = (System.currentTimeMillis() - start);
            totalGuessTime += guessTime;
        }
        if (lastGuessWasCorrect()) {
            winCount++;
            totalGuessCount += currGuessCount;
        }
        gameCount++;
        currGuessCount = 0;
    }

    /**
     * This functions the same way as it does in WordleGame.  In addition, it
     * updates the game string by appending the current guess.  It also
     * increments the guess count and records the time it takes to submit the
     * guess.
     *
     * @param guess the guess to submit
     */
    @Override
    public void submitGuess(Word guess) {
        updateClue(guess);
        guessesLeft--;
        solver.analyzeClue(guess, clue);
        currGuessCount++;
    }

    // GETTERS ****************************************************************

    public int[] getStats() {
        return new int[]
                {
                        gameCount,
                        winCount,
                        totalGuessCount,
                        totalGuessTime,
                        possibleSolutionsSizes[0],
                        possibleSolutionsSizes[1],
                        possibleSolutionsSizes[2],
                        possibleSolutionsSizes[3],
                        possibleSolutionsSizes[4],
                        possibleSolutionsSizes[5],
                };
    }

    public String getSolutionStr() {
        return solution.str();
    }
}
