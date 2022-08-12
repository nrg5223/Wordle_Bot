package com.nrg5223.Testers.TestSets;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.ErrorFindingGame;

/**
 * Used to run a number of TestWordleGames.  The games record game details when
 * exceptions are thrown during tests.  Then they are accessed by an ErrorFinder
 * object to be recorded to data/errors.txt.
 */
public class ErrorFindingSet extends StatsGettingSet {

    StringBuilder errorsStr = new StringBuilder();
    ErrorFindingGame errorGame;

    public ErrorFindingSet(int solverVersion, int setSize, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, setSize, wordleLib, solutionLib);
    }

    // RUNNING TESTS **********************************************************

    /**
     * Run a number of ErrorFindingGames and store game details from all games.
     */
    @Override
    public void run() {
        for (int i = 0; i < setSize; i++) {
            try {
                createNewGame();
                errorGame.play();
            }
            catch (Exception e) {
                errorsStr.append(errorGame.getGameStr()).append("\n");
            }
        }
    }

    @Override
    void createNewGame() {
        errorGame = new ErrorFindingGame(solverVersion, wordleLib, solutionLib);
    }

    // GETTERS ****************************************************************

    public String getErrorsStr() {
        return errorsStr.toString();
    }
}
