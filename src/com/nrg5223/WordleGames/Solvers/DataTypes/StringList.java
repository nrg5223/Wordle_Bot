package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of ArrayList<String> whose purpose is to simplify conversion to a
 * WordList object.
 */
public class StringList extends ArrayList<String> {

    public StringList(List<String> asList) {
        super(asList);
    }

    public StringList() {}

    // CONVERSION *************************************************************

    public WordList toWordList() {
        WordList list = new WordList();
        for (String str : this)
            list.add(new Word(str));
        return list;
    }
}
