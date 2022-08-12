package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;

/**
 * A class that is used to simulate solver functionality in a simulated Wordle
 * game.  Its only purpose is to reuse functionality in the abstract Solver
 * class and set the possibleSolutions variable.
 */
public class SimulationSolver extends Solver {
    public SimulationSolver(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
    }

    @Override
    public int getVersion() {
        return -1; // This is a placeholder because this method is never called
                   // for a simulation solver
    }

    /**
     * This simulation sovler is used by a SolverV7 to test potential guesses.
     * This method sets possibleSolutions to the value it has in the game the
     * SolverV7 is trying to solve.
     *
     * @param possibleSolutions the value of possibleSolutions in the game a
     *                          SolverV7 is trying to solve
     */
    public void setPossibleSolutions(WordList possibleSolutions) {
        this.possibleSolutions = possibleSolutions;
    }
}
