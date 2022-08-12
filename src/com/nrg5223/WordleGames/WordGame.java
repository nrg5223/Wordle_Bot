package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * Any type of word game.  Examples: Wordle, Antiwordle, Dordle.  This interface
 * is used when a new type of word game is implemented.
 */
public interface WordGame {

    static void main(String[] args) {}

    void play();

    Word getNextGuess();

    void submitGuess(Word guess);

    void log(Word guess);

    void printEndGameMsg();

    boolean isOver();

    boolean lastGuessWasCorrect();

    boolean isValid(Word guess);
}
