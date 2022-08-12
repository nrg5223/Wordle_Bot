package com.nrg5223.WordleGames.Solvers.DataTypes;

/**
 * Represents a set of letters in the alphabet.  Used by SolverV2 to store
 * letter frequency values.
 */
public class Alphabet extends LetterMap {

    public int getScore(String letter) {
        return this.get(letter);
    }

    public void resetLetterCounts() {
        this.replaceAll((letter, count) -> 0);
    }
}
