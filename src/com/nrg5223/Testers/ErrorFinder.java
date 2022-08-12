package com.nrg5223.Testers;

import com.nrg5223.Testers.TestSets.ErrorFindingSet;
import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.Utilities.DataHandlers.DataWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * An object for running multithreaded wordle tests.  Each thread runs a set of
 * wordle games in an ErrorFindingSet object.  Then their data is combined and
 * recorded to data/errors.txt.  Multithreading is used to speed up testing.
 */
public class ErrorFinder extends WordleTester {

    private String errorsStr = "";
    private final List<ErrorFindingSet> errorFindingSets = new ArrayList<>();

    public ErrorFinder(int solverVersion, int totalTestCount, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, totalTestCount, wordleLib, solutionLib);
    }

    // MULTITHREADING HELPERS *************************************************

    /**
     * This method is exactly the same as in WordleTester, except it creates an
     * ErrorFindingSet in the beginning of the loop, instead of a TestSet.  An
     * ErrorFindingSet IS a TestSet, but it must be instantiated as an
     * ErrorFindingSet to use its full functionality.
     *
     * @param threads a list of threads
     */
    @Override
    void createAndStartAll(List<Thread> threads) {
        for (int i = 0; i < numThreads; i++) {
            ErrorFindingSet set = createSet(i + 1);
            errorFindingSets.add(set);

            Thread thread = new Thread(set);
            threads.add(thread);
            thread.start();
        }
    }

    // CREATING SETS **********************************************************

    @Override
    ErrorFindingSet createSet(int i) {
        return new ErrorFindingSet(solverVersion, setSize, wordleLib, solutionLib);
    }

    // DATA HELPERS ***********************************************************

    @Override
    void combineAllData() {
        for (ErrorFindingSet set : errorFindingSets) {
            updateErrorsStr(set.getErrorsStr());
        }
    }

    @Override
    void recordData() {
        try {
            (new DataWriter()).recordErrors(errorsStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void updateErrorsStr(String newErrorsStr) {
        errorsStr += newErrorsStr;
    }
}
