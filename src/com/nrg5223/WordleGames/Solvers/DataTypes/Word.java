package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * A Word object represents a word.  The word takes 3 forms: string, native
 * array, and arraylist.
 *
 * This class is meant to combine the functions of a native array and of a
 * string, and possibly of other data structures.  The benefit of this is the
 * ability to do more word/guess analysis with a single class. This simplifies
 * the functionality in Solver.
 */
public class Word implements Comparable<Word> {

    String str;
    String[] arr;
    ArrayList<String> list;

    double score;

    // CONSTRUCTOR(S) *********************************************************

    /**
     * A Word object is created using a string.  All strings are converted to
     * lowercase by default.  Its array form and list form are generated from
     * the string.
     *
     * @param str the string used to create the Word
     */
    public Word(String str) {
        this.str = str.toLowerCase();
        arr = str.split("");
        list = new ArrayList<>();
        list.addAll(Arrays.asList(str.split("")));
    }

    // DATA TYPES *************************************************************

    public String str() {
        return str;
    }

    public String[] arr() {
        return str.split("");
    }

    public ArrayList<String> list() {
        return list;
    }

    // UTILITIES **************************************************************

    public boolean contains(String letter) {
        return str.contains(letter);
    }

    public boolean containsAt(String letter, int index) {
        return arr[index].equals(letter);
    }

    /**
     * Does this word have all unique letters? In other words, does every word
     * only appear once in this word?
     *
     * @return true if all letters appear once; false otherwise.
     */
    public boolean allUniqueLetters() {
        HashMap<String, Integer> map = new HashMap<>();
        for (String letter : arr) {
            int count = map.getOrDefault(letter, 0);
            if (count > 0)
                return false;
            map.put(letter, count + 1);
        }
        return true;
    }

    // SCORING ****************************************************************

    /**
     * Calculate the score of this word using letter frequencies.  This
     * overloaded version of this method is used by SolverV2.
     *
     * @param alphabet a set of all letters in the alphabet.  It contains all of
     *               them, therefore, it contains the letters in this word,
     *                and can be used to calculate this word's score.
     */
    public void calcAndSetScore(Alphabet alphabet) {
        score = 0;
        for (String letter : arr) {
            score += alphabet.getScore(letter);
        }
    }

    /**
     * Calculate the score of this word using LetterIndexPair frequencies.  This
     * overloaded version of this method is used by SolverV3.
     *
     * @param pairSet a set of all LetterIndexPair's.  It contains all of them,
     *                therefore, it contains the LetterIndexPair's in this word,
     *                and can be used to calculate this word's score.
     */
    public void calcAndSetScore(LetterIndexPairMap pairSet) {
        resetScore();
        for (int i = 0; i < 5; i++) {
            score += pairSet.getScore(new LetterIndexPair(arr[i], i));
        }
    }

    public double score() {
        return score;
    }

    public void resetScore() {
        score = 0;
    }

    /**
     * Used by SolverV7 and SolverV8
     *
     * @param score a score for this word that was calculated by a solver
     */
    public void setScore(double score) {
        this.score = score;
    }

    // OVERRIDES **************************************************************

    /**
     * Note: want to reverse the list.  Collections.sort() puts ints smallest
     * to largest in sorting, and I want higher scores to go first.
     *
     * @param other a Word object
     *
     * @return negative number if this word has a higher score than the other;
     *         positive word if the other word has a higher score; 0 otherwise.
     */
    @Override
    public int compareTo(Word other) {
        return (int) (other.score() - this.score());
    }

    @Override
    public boolean equals(Object obj) {
        if ( ! (obj instanceof Word))
            return false;
        Word other = (Word) obj;
        return this.str().equals(other.str());
    }

    @Override
    public int hashCode() {
        return Objects.hash(str);
    }

    @Override
    public String toString() {
        return str;
    }
}
