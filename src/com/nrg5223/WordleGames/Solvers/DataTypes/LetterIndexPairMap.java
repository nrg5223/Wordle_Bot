package com.nrg5223.WordleGames.Solvers.DataTypes;

import java.util.HashMap;

/**
 * A hashmap of LetterIndexPairs mapped to doubles.  It is used for storing
 * found letter-index-pairs and their corresponding scores as doubles.  The
 * benefit of this class is increased abstraction of some hashmap functionality.
 */
public class LetterIndexPairMap extends HashMap<LetterIndexPair, Double> {

    public void increment(LetterIndexPair lip){
        double count = this.getOrDefault(lip, (double) 0);
        this.put(lip, count + 1);
    }

    public void add(LetterIndexPair lip, double addition) {
        double value = this.getOrDefault(lip, (double) 0);
        this.put(lip, value + addition);
    }

    public double getScore(LetterIndexPair pair) {
        if (!this.containsKey(pair))
            return 0;
        return this.get(pair);
    }

    public void resetAllCounts() {
        this.replaceAll((pair, count) -> (double) 0);
    }

    public boolean contains(LetterIndexPair pair) {
        return this.containsKey(pair);
    }
}
