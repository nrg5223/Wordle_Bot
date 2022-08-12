package com.nrg5223.WordleGames;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.SimulationSolver;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

/**
 * A class that is used to simulate a part of a Wordle game.  It is used by
 * solver v7 to get the average solution-list-reduction value of each possible
 * guess.
 */
public class SimulationGame extends WordleGame {
    private final SimulationSolver solver;

    public SimulationGame(StringList wordleLib, StringList solutionLib) {
        super(wordleLib, solutionLib);
        solver = new SimulationSolver(wordleLib, solutionLib);
    }

    /**
     * A method that is called by SolverV7 once for each possible guess.  It
     * functions as if the guess was guessed; it gets the clue array, reduces
     * the theoretical solutionList, and returns the size of it.
     *
     * @param theoreticalPossibleSolutions the given list of possible solutions.
     *                                     It is the actual possibleSolutions in
     *                                     a Wordle game using SolverV7
     * @param theoreticalGuess a word that may be a guess
     * @param theoreticalSolution a word that may be a solution.  This method
     *                            assumes it is the solution to see how many
     *                            words would be eliminated from
     *                            possibleSolutions using the given guess word
     * @return the size of possibleSolutions after the theoretical guess is
     *         analyzed
     */
    public int getPossibleSolutionsSize(
                                        WordList theoreticalPossibleSolutions,
                                        Word theoreticalGuess,
                                        Word theoreticalSolution
                                       ) {
        // Setup
        solution = theoreticalSolution;
        solver.setPossibleSolutions(theoreticalPossibleSolutions);

        // Calculate new (theoretical) size of possibleSolutions
        updateClue(theoreticalGuess);
        solver.analyzeClue(theoreticalGuess, clue);
        return solver.getPossibleSolutionsSize();
    }
}
