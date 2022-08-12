package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.Arrays;

/**
 * A wordle game that stores additional data.  After a game is complete, this
 * data is passed on to a DataHandler to be recorded in a text file.  The data
 * stored:
 *      Words - the solution word and guess sequence for each game are stored in
 *              a string.  If an exception is caught during a game, the string
 *              is recorded in a text file for debugging.  This allows errors to
 *              be recreated and ensures they are all handled.
 */
public class ErrorFindingGame extends AutomatedGame {

    /** Data used to store faulty solutions and guess sequences */
    private final StringBuilder gameStr = new StringBuilder("solution: " + solution.str() + "\n");
    private Word currGuess;

    public ErrorFindingGame(int solverVersion, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, wordleLib, solutionLib);
    }

    // GAMEPLAY ***************************************************************

    /**
     * In TestingWordleGame, getNextGuess() is passed directly to submitGuess().
     * In this class, this method changes the currGuess field and passes that to
     * submitGuess().  The guesses all need to be saved to find faulty guess
     * sequences.
     */
    @Override
    public void play() {
        while (!this.isOver()) {
            currGuess = getNextGuess();
            this.submitGuess(currGuess);
        }
    }

    /**
     * This functions the same way as it does in WordleGame.  In addition, it
     * updates the game string by appending the current guess.
     *
     * @param guess the guess to submit
     */
    @Override
    public void submitGuess(Word guess) {
        updateClue(guess);
        guessesLeft--;
        solver.analyzeClue(guess, clue);

        updateGameStr();
    }

    // GAME DATA **************************************************************

    /**
     * gameStr is a StringBuilder that represents this game.  Initially it
     * contains a line with the solution word.  Throughout the game, each guess
     * and clue is appended to gameStr.  The guesses and clues match the format
     * of what is printed to the terminal in a WordleGame for readability.
     */
    private void updateGameStr() {
        gameStr.append(currGuessStr());
    }

    /**
     * Generate the string that would be printed to the terminal in a WordleGame
     * after each guess.  Use the current guess and its corresponding hints.
     *
     * @return a string that represents a guess and its corresponding hints.  It
     * is meant to be appended to gameStr.
     */
    private String currGuessStr() {
        return "Guess: " + currGuess.str() + "\n" +
                "     " + Arrays.toString(currGuess.arr()) + "\n" +
                (6 - guessesLeft) + "/6: " + Arrays.toString(clue) + "\n";
    }

    // GETTERS ****************************************************************

    /**
     * gameStr is a StringBuilder object because it needs to be appended to
     * several times.  It is converted to a string then returned.
     *
     * @return a string that represents this game.  It follows the format of
     * what would be printed to the terminal in a WordleGame.  It also shows
     * the solution.  This string meant to be is written to a text file to be
     * used for debugging.
     */
    public String getGameStr() {
        return gameStr.toString();
    }
}
