package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.Objects;

/**
 * A combination of a letter and an index representing the letter's position in
 * a 5-letter word.  It is used by Solvers V3 and up in calculating scores for
 * words.
 */
public class LetterIndexPair {
    String letter;
    int index;

    public LetterIndexPair(String letter, int index) {
        this.letter = letter;
        this.index = index;
    }

    public String letter() {
        return letter;
    }

    public int index() {
        return index;
    }

    @Override
    public boolean equals(Object obj) {
        if ( ! (obj instanceof LetterIndexPair))
            return false;

        LetterIndexPair other = (LetterIndexPair) obj;
        return this.letter().equals(other.letter()) &&
               this.index()      == other.index();
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, index);
    }
}
