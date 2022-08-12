package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.ArrayList;

/**
 * A subclass of ArrayList<Word> whose purpose is to slightly modify or add some
 * ArrayList functionality.
 */
public class WordList extends ArrayList<Word> {

    public WordList(WordList possibleSolutions) {
        super(possibleSolutions);
    }

    public WordList() {}

    // GETTERS ****************************************************************

    public Word getWord(String targetString) {
        for (Word word : this) {
            if (word.str().equals(targetString)) {
                return word;
            }
        }
        throw new IllegalStateException();
    }

    // CONVERSION *************************************************************

    @Override
    public Word[] toArray() {
        Word[] arr = new Word[this.size()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = this.get(i);
        }
        return arr;
    }

    @Override
    public WordList subList(int fromIndex, int toIndex) {
        WordList newList = new WordList();
        for (int i = fromIndex; i < toIndex; i++) {
            newList.add(this.get(i));
        }
        return newList;
    }
}
