package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.LetterMap;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.*;

/**
 * A playable PTUI version of the online game Wordle.  A random 5-letter word is
 * generated from a library.  The user has 6 attempts to guess the word.  They
 * are prompted in the terminal and they type their guess.  After each guess, a
 * clue is printed in the terminal based on their guess.
 *
 * A link to the original game: https://www.nytimes.com/games/wordle/index.html
 */
public class WordleGame implements WordGame {

    private final StringList wordleLib;// = DataReader.getStrWordleLib();
    Word solution;// = new Word(solutionLib.get((new Random()).nextInt(solutionLib.size() - 1)));
    String[] clue = new String[]{"_", "_", "_", "_", "_"};
    int guessesLeft = 6;

    // CONSTRUCTOR(S) *********************************************************

    /**
     * A constructor that takes a given solution word instead of randomly
     * generating one.
     *
     * @param wordleLib the list of words in the Wordle library as strings
     * @param solution the given solution word
     */
    public WordleGame(StringList wordleLib, Word solution) {
        this.wordleLib = wordleLib;
        this.solution = solution;
    }

    public WordleGame(StringList wordleLib, StringList solutionLib) {
        this.wordleLib = wordleLib;
        solution = new Word(solutionLib.get((new Random()).nextInt(solutionLib.size() - 1)));
    }

    // GAMEPLAY ***************************************************************

    /**
     * Runs a complete game from start to finish.
     *
     * The solution is generated when the WordleGame is constructed.  The
     * guesses are submitted until one is correct or until there have been 6.
     */
    public void play() {
        while (!this.isOver()) {
            this.submitGuess(getNextGuess());
        }
        printEndGameMsg();
    }

    /**
     * The user is prompted to enter their next guess.  Their guess is scanned
     * and submitted to this game.
     *
     * @return a Word object for the guess to submit next
     */
    public Word getNextGuess() {
        Scanner in = new Scanner(System.in);
        System.out.print("Your guess: ");
        return (new Word(in.next().toLowerCase()));
    }

    /**
     * A guess is submitted and validated.  If it is valid, a clue is generated.
     * The guess is logged (printed to the terminal).
     *
     * @param guess the guess to submit
     */
    public void submitGuess(Word guess) {
        if (isValid(guess)) {
            updateClue(guess);
            guessesLeft--;
        }
        log(guess);
    }

    // CLUE *******************************************************************

    /**
     * The last submitted guess is assumed to be valid at this point.  It is
     * compared to the solution letter-by-letter to generate the clue.  The clue
     * is an array of strings.  Each string corresponds to a letter in the
     * guess.
     *
     * The possible strings:
     *      "_" indicates that the letter in this position in their guess is not
     *          in the solution.  If this letter is shown to be in the solution
     *          in another position, this indicates that the letter is not in
     *          the solution any more times than it already is shown to be.
     *
     *      "?" indicates that the letter in this position in their guess is in
     *          the solution, but not in the current position.
     *
     *      "!" indicates that the letter in this position in their guess is in
     *          the solution and is in this position.
     *
     * The algorithm:
     *      1. Iterate over the guess to find letters that match the solution
     *         and put '!' in the clue for each.
     *      2. Fill solutionMap.  The values are how many more times each letter
     *         is in the solution EXCLUDING instances of found location.  So,
     *         the map values are how many times '?' can appear for each letter.
     *      3. Iterate over the guess array
     *         a. If a guess letter is in the solution map and the value is > 0,
     *            put '?', then decrement the map value.
     *         b. Otherwise, put '_'.
     *
     * @param guess the last submitted guess
     */
    void updateClue(Word guess) {
        clue = new String[]{"_", "_", "_", "_", "_"};

        LetterMap solutionMap = new LetterMap();

        // Indicate correct-position letters
        for (int i = 0; i < 5; i++) {
            if (solution.list().get(i).equals(guess.arr()[i])) {
                clue[i] = "!";
            }
        }

        // Count non-correct-position letters
        for (int k = 0; k < 5; k++) {
            if (!clue[k].equals("!")) {
                solutionMap.increment(solution.arr()[k]);
            }
        }

        // Update clue for non-correct-position letters
        for (int i = 0; i < 5; i++) {
            if (!clue[i].equals("!")) {
                String letter = guess.arr()[i];
                if (solutionMap.hasMoreThanOne(letter)) {
                    clue[i] = "?";
                    solutionMap.decrement(letter);
                } else {
                    clue[i] = "_";
                }
            }
        }
    }

    // HELPERS/LOGIC **********************************************************

    public boolean isOver() {
        return guessesLeft == 0 || lastGuessWasCorrect();
    }

    public boolean lastGuessWasCorrect() {
        for (String str : clue) {
            if (!str.equals("!")) {
                return false;
            }
        }
        return true;
    }

    public boolean isValid(Word guess) {
        return wordleLib.contains(guess.str());
    }

    // PTUI FUNCTIONALITY *****************************************************

    /**
     * The guess is printed to the terminal.  If it is valid, the clue is
     * printed beneath it.  If not, a message is printed.
     *
     * @param guess the last submitted guess
     */
    public void log(Word guess) {
        System.out.println();
        System.out.println("Guess: " + guess.str());
        if (isValid(guess)) {
            System.out.println("     " + Arrays.toString(guess.arr()));
            System.out.println(6 - guessesLeft + "/6: " + Arrays.toString(clue));
        }
        else {
            System.out.println("Invalid guess.");
        }
    }

    public void printEndGameMsg() {
        System.out.println();
        if (lastGuessWasCorrect()) {
            System.out.println("You got it!");
        }
        else {
            System.out.println("The word was: " + solution.str());
        }
    }
}
