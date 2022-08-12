package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.HashSet;

/**
 * A solver version that chooses guesses by calculating reduction of each
 * potential guess.  It takes the union of the sets of words that could be
 * removed by each potential guess.  The size of the union represents the
 * average number of words removed by a given guess.
 *
 * Status: needs more testing and optimization.  Is extremely slow currently.
 */
public class SolverV8 extends SolverV4 {

    public SolverV8(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return 8;
    }

    // GUESSING ***************************************************************

    @Override
    void calcWordScores(WordList guessList) {
        for (Word word : guessList) {
            word.setScore(reduction(word));
        }
    }

    @Override
    WordList guessList() {
        return wordleLib;
    }

    // UNIQUE HELPERS *********************************************************

    /**
     * Calculate the average reduction of a word.  The reduction means the
     * number of words eliminated from the solution list when a word is guessed.
     * Reduction is calculated by finding the average number of words
     * eliminated in every possible clue-case when a word is guessed.
     * 
     * @param word a word in the Wordle library
     * @return the average reduction of the given word
     */
    private double reduction(Word word) {
        char[] clueOptions = new char[] {'!', '?', '_'};
        char[] clues = new char[5];
        HashSet<Word>[] sets = new HashSet[5];
        double reduction = 0;
        double total = possibleSolutions.size();

        double probability;
        HashSet<Word> removalSet;

        for (char clue0 : clueOptions) {
            clues[0] = clue0;
            for (char clue1 : clueOptions) {
                clues[1] = clue1;
                for (char clue2 : clueOptions) {
                    clues[2] = clue2;
                    for (char clue3 : clueOptions) {
                        clues[3] = clue3;
                        for (char clue4 : clueOptions) {
                            clues[4] = clue4;

                            // Calc reduction of this config
                            probability = 1;                // reset value
                            removalSet = new HashSet<>();   // reset value
                            double currReduction = 0;

                            for (int i = 0; i < 5; i++) {
                                sets[i] = removalSet(word.arr()[i], i, clues[i]);
                                probability *= (total - sets[i].size());// /total; // remove common factor 1/total to reduce # operations
                                removalSet.addAll(sets[i]);
                            }
                            // Only add if the clue configuration is possible.  Impossible clue configs
                            // will yield a removal set containing all solutions
                            if (removalSet.size() != possibleSolutions.size())
                                currReduction = probability * removalSet.size();

                            // Add to sum reduction of this config
                            reduction += currReduction;
                        }
                    }
                }
            }
        }

        return reduction;
    }

    /**
     * Get the set of words that would be removed resulting from a letter in a 
     * guess.  It depends on the clue, so this method returns the right set
     * for the given clue. Clue-cases:
     *      '!' / green : the given letter is in the solution in that position
     *      '?' / yellow: the given letter is in the solution not in that position
     *      '_' / gray  : the given letter is not in the solution any more than
     *                    it already is
     * @param letter a letter from a word whose reduction is being calculated
     * @param index the position of the given letter in the word
     * @param clue a theoretical clue resulting from a theoretical guess
     * @return the set of words that would be removed from the given letter-clue
     *         configuration in a theoretical guess
     */
    private HashSet<Word> removalSet(String letter, int index, char clue) {
        switch (clue) {
            case '!':
                return greenRemovalSet(letter, index);
            case '?':
                return yellowRemovalSet(letter, index);
            case '_':
                return grayRemovalSet(letter, index);
        }
        throw new IllegalArgumentException();
    }

    HashSet<Word> greenRemovalSet(String letter, int index) {
        HashSet<Word> set = new HashSet<>();
        for (Word word : possibleSolutions)
            if (!word.containsAt(letter, index)) {
                set.add(word);
            }
        return set;
    }

    HashSet<Word> yellowRemovalSet(String letter, int index) {
        HashSet<Word> set = new HashSet<>();
        for (Word word : possibleSolutions)
            if (!word.contains(letter) || word.containsAt(letter, index)) {
                set.add(word);
            }
        return set;
    }

    HashSet<Word> grayRemovalSet(String letter, int index) {
//        return possibleSolutions.size() - countWordsNoLetterAtIndex(letter, index);
        HashSet<Word> set = new HashSet<>();
        for (Word word : possibleSolutions)
            if (word.containsAt(letter, index)) {
                set.add(word);
            }
        return set;
    }
}
