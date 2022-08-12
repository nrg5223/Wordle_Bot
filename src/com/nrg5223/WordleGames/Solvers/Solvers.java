package com.nrg5223.WordleGames.Solvers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;

/**
 * A container for static methods and data related to Solvers.
 */
public abstract class Solvers {

    public final static int numberOfVersions = 8;

    /**
     * A generic method for instantiating a solver.  It takes the solver version
     * as an argument and returns a solver of the given version number.
     *
     * @param version the version number as an int for the solver to instantiate
     * @param wordleLib the wordle library; it is needed by all solver constructors
     * @param solutionLib the solution library; it is needed by all solver constructors
     * @return a subclass of Solver
     * @throws IllegalArgumentException when the version parameter is not a
     *                                  version number of an existing solver.
     */
    public static Solver instantiate(int version, StringList wordleLib, StringList solutionLib) {
        if      (version == 1)
               return new SolverV1(wordleLib, solutionLib);
        else if (version == 2)
               return new SolverV2(wordleLib, solutionLib);
        else if (version == 3)
               return new SolverV3(wordleLib, solutionLib);
        else if (version == 4)
               return new SolverV4(wordleLib, solutionLib);
        else if (version == 5)
               return new SolverV5(wordleLib, solutionLib);
        else if (version == 6)
               return new SolverV6(wordleLib, solutionLib);
        else if (version == 7)
               return new SolverV7(wordleLib, solutionLib);
        else if (version == 8)
               return new SolverV8(wordleLib, solutionLib);

        // The argument validation functionality in Application.main() should
        // prevent this from being reached; it is here for the compiler
        throw new IllegalArgumentException("The solver number must be between 1 and " + numberOfVersions + ".");
    }

    public static boolean isValidVersion(int versionNumber) {
        return versionNumber > 0 && versionNumber <= numberOfVersions;
    }
}
