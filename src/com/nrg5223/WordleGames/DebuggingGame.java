package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * A type of WordleGame that is used for debugging. It runs like an
 * AutomatedWordleGame except it uses pre-determined details.  When a Wordle
 * game configuration (combination of a solution and guess sequence) causes an
 * exception to be thrown during testing, it is written to a text file.  This
 * type of WordleGame uses those game details to recreate the error for
 * debugging.
 */
public class DebuggingGame extends AutomatedGame {

    /** An in-order array of guesses that previously caused an error */
    private final Word[] guesses;
    private int index = 0;

    // CONSTRUCTOR(S) *********************************************************

    /**
     * A DebuggingWordleGame is meant to run a Wordle game that has caused an
     * error.  The constructor gets the data from a faulty game and assigns it
     * to its fields so the game can be re-created.
     *
     * @param version the solver version
     * @param faultyGameDetails the solution and guess sequence as a Word[] from
     *                          a game that has caused an error.  The first
     *                          element is the solution; the rest are in-order
     *                          guesses from the game.
     * @param wordleLib the list of words in the Wordle library as strings
     * @param solutionLib the list of words in the solution library as strings
     */
    public DebuggingGame(int version, Word[] faultyGameDetails, StringList wordleLib, StringList solutionLib) {
        super(version, wordleLib, solutionLib);
        solution = faultyGameDetails[0];
        guesses = new Word[faultyGameDetails.length - 1];
        System.arraycopy(faultyGameDetails, 1, guesses, 0, guesses.length);
    }

    // DEBUGGING **************************************************************

    /**
     * The guesses from a game that caused an error are stored as a field.  The
     * next guess to submit is simply the next guess in that sequence.
     *
     * If the sequence of guesses is exhausted, a guess is generated from the
     * solver to avoid having an exception thrown.
     *
     * @return the next guess in the faulty sequence of guesses
     */
    @Override
    public Word getNextGuess() {
        if (index < guesses.length)
            return guesses[index++];
        // This should cause the IllegalArgumentException
        return solver.generateGuess();
    }
}
