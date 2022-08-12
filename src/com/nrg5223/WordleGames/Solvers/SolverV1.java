package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.util.Random;

/**
 * A solver version that chooses a random word from the list of possible
 * solutions.
 *
 * It is essentially the base version, so there is no additional
 * implementation.
 */
public class SolverV1 extends Solver {

    public SolverV1(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public Word generateGuess() {
        return (possibleSolutions.get((new Random()).nextInt(possibleSolutions.size())));
    }
}
