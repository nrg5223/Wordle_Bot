package com.nrg5223.Testers.TestSets;

import com.nrg5223.Utilities.Print;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.Utilities.Calculator;

/**
 * Used to run a number of automated wordle games for getting stats or finding
 * errors.
 */
public abstract class TestSet implements Runnable {

    int solverVersion;
    int setSize;

    StringList wordleLib;
    StringList solutionLib;

    public TestSet(int solverVersion, int setSize, StringList wordleLib, StringList solutionLib) {
        this.solverVersion = solverVersion;
        this.setSize = setSize;
        this.wordleLib = wordleLib;
        this.solutionLib = solutionLib;
    }

    // RUNNING TESTS **********************************************************

    @Override
    public abstract void run();

    abstract void createNewGame();

    // TIME UPDATES ***********************************************************

    void printTimeLeft(double startTime, int iterationNum) {
        double timeSpent = (System.currentTimeMillis() - startTime) / 1000;
        double timeLeft = Calculator.estimatedTimeLeft(timeSpent, iterationNum, setSize);
        Print.timeLeft(timeLeft);
    }
}
