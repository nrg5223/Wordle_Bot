package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.Solver;
import com.nrg5223.WordleGames.Solvers.Solvers;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * An automated Wordle game.  Instead of getting user input from terminal, this
 * type of WordleGame uses a Solver object to generate guesses.  The solver
 * reads the clue and uses an algorithm to generate smart guesses.
 */
public class AutomatedGame extends WordleGame {
    Solver solver;

    // CONSTRUCTOR(S) *********************************************************

    /**
     * A constructor that takes a given solution word instead of randomly
     * generating one.
     *
     * @param version the solver version this game uses
     * @param solution the given solution word
     * @param wordleLib the list of words in the Wordle library as strings
     * @param solutionLib the list of words in the solution library as strings
     */
    public AutomatedGame(int version, Word solution, StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solution);
        solver = Solvers.instantiate(version, wordleLib, solutionLib);
    }

    public AutomatedGame(int version, StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        solver = Solvers.instantiate(version, wordleLib, solutionLib);
    }

    // GAMEPLAY ***************************************************************

    /**
     * Instead of getting a guess from the user in the terminal, the solver
     * generates a guess using an algorithm.
     *
     * @return the next guess to submit
     */
    @Override
    public Word getNextGuess() throws IllegalArgumentException {
        return solver.generateGuess();
    }

    /**
     * First, the guess is submitted the same way as it is in WordleGame.  Then,
     * the solver uses the guess and corresponding clue to update its list of
     * possible solution words.
     *
     * @param guess the guess to submit
     */
    @Override
    public void submitGuess(Word guess) {
        updateClue(guess);
        guessesLeft--;
        log(guess);

        solver.analyzeClue(guess, clue);
    }
}
