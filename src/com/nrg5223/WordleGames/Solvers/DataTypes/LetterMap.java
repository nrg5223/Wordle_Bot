package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.HashMap;

/**
 * A hashmap of Letters mapped to integers.  It is used by WordleGame in
 * updateCluesArray().  It is also extended by Alphabet and used to map found
 * letters to their scores as integers.  The benefit of this class is increased
 * abstraction of some hashmap functionality.
 */
public class LetterMap extends HashMap<String, Integer> {

    /**
     * Is the count of this letter greater than zero?
     *
     * The purpose in context:
     *      Is there more of the given letter in the solution from the current
     *      point in the solution on?  This method is used in WordleGame in
     *      updateCluesArray() in step 3a.
     *
     * @param letter a letter in the solution to a Wordle game
     *
     * @return true if the count of this letter is greater than zero;
     *         false otherwise.
     */
    public boolean hasMoreThanOne(String letter) {
        return this.containsKey(letter) && this.get(letter) > 0;
    }

    public void increment(String letter){
        int count = this.getOrDefault(letter, 0);
        this.put(letter, count + 1);
    }

    public void decrement(String letter) {
        int previousCount = this.get(letter);
        this.put(letter, previousCount - 1);
    }
}
