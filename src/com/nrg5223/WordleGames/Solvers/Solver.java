package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.HashMap;
import java.util.Random;

/**
 * The class with the algorithms for solving a Wordle game.  The solver does not
 * have access to the game; it is given the clues to be used for the algorithm.
 * Its purpose is to generate guesses.
 *
 * The base level algorithm:
 * It has a list of words that may be the solution to a wordle game.  Initially,
 * the list is the full Wordle library.  When it is given a clue, the solver
 * removes all words that cannot be the solution based on the clue.  Some
 * subclasses may reuse parts of the base level algorithm.
 *
 * This is the base version with the simplest algorithm.  It is abstract and is
 * meant to be extended by several concrete subclasses.  These subclasses have
 * varying, more complicated Wordle-solving algorithms.
 */
public abstract class Solver {

    WordList possibleSolutions;
    WordList wordleLib;
    int guessCount = 0;
    public static Word firstGuess;

    public Solver(StringList strWordleLib, StringList strSolutionLib) {
        possibleSolutions = strSolutionLib.toWordList();
        this.wordleLib = strWordleLib.toWordList();
    }

    public abstract int getVersion();

    // GENERAL FUNCTIONALITY **************************************************

    public void analyzeClue(Word guess, String[] clue) {
        possibleSolutions.removeIf(word -> !isPossibleSolution(word, guess, clue));
    }

    /**
     * Can the given word be a solution?
     *
     * @param word a word from the list of possible solutions.  It may still be
     *             a possible solution, and it may not.
     * @param guess the last submitted guess
     * @param clue the clue that corresponds to the last submitted guess
     * @return true if the word can be a solution; false otherwise.
     */
    boolean isPossibleSolution(Word word, Word guess, String[] clue) {
        HashMap<String, Integer> unknownPosCountMap = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            String letter = guess.arr()[i];

            // Check for presence of letters not in solution
            switch (clue[i]) {
                case "_":
                    for (int k = 0; k < 5; k++) {
                        if (word.arr()[k].equals(letter) &&
                                (!(clue[k].equals("!") || unknownPosCountMap.containsKey(letter)))) {
                            return false;
                        }
                    }
                    break;

                // Check for absence of necessary letters of known position in those
                // positions
                case "!":
                    if (!word.arr()[i].equals(guess.arr()[i]))
                        return false;
                    break;

                // Check for presence of necessary letters with unknown position
                case "?":
                    if (word.arr()[i].equals(guess.arr()[i]))
                        return false;

                    // Update count map
                    int count = unknownPosCountMap.getOrDefault(letter, 0);
                    unknownPosCountMap.put(letter, count + 1);
                    break;
            }
        }

        // Check for presence of necessary letters with unknown position in an
        // invalid position
        for (int i = 0; i < 5; i++) {
            // For every '?' letter in the guess
            if (clue[i].equals("?")) {
                String letter = guess.arr()[i];
                int count = 0;

                // For every letter in the word
                for (int j = 0; j < 5; j++) {
                    // If the necessary letter appears in a valid position
                    if (!clue[j].equals("!") && !guess.arr()[j].equals(letter) && word.arr()[j].equals(letter)) {
                        count++;
                    }
                }
                if (count < unknownPosCountMap.get(letter)) {
                    return false;
                }
            }
        }

        return true;
    }

    boolean allWordsHaveRepeatLetters(WordList possibleSolutions) {
        for (Word word : possibleSolutions) {
            if (word.allUniqueLetters()) {
                return false;
            }
        }
        return true;
    }

    // GUESSING ***************************************************************

    public Word generateGuess() {
        return (possibleSolutions.get((new Random()).nextInt(possibleSolutions.size())));
    }

    /**
     * Get the best guess with all unique letters.  Guesses with all unique
     * letters are generally better.  If all guesses left have repeat letters,
     * return the best guess with repeat letters.
     *
     * @return the best guess with all unique letters if there is one; the best
     *         guess with repeat letters otherwise.
     */
    Word bestGuessWithUniqueLetters(WordList guessList) {
        Word guess = guessList.get(0);

        if (allWordsHaveRepeatLetters(guessList))
            return guess;

        int index = 0;
        while (!guess.allUniqueLetters()) {
            guess = guessList.get(++index);
        }
        return guess;
    }

    // GETTERS ****************************************************************

    public int getPossibleSolutionsSize() {
        return possibleSolutions.size();
    }
}
