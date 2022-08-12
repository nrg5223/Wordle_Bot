package com.nrg5223.Utilities;

import com.nrg5223.Testers.*;
import com.nrg5223.WordleGames.Solvers.Solver;

/**
 * A container for printing methods.  All methods are static and are used for
 * printing updates to the user in the terminal while the program is running.
 */
public abstract class Print {

    public static void validRunConfigs() {
        System.out.println();
        System.out.println("Valid args strings:");
        System.out.println("===================");
        System.out.println("library                    v = version of solver (must be between 1-8)");
        System.out.println("manual                     f = first guess       (must be in Wordle library)");
        System.out.println("auto v f                   n = number of tests   (must be > 0)");
        System.out.println("quick_test v f n   ");
        System.out.println("test v f           ");
        System.out.println("test_all f         ");
        System.out.println("===================");
    }

    public static void webScrapingMsg() {
        System.out.println();
        System.out.println("Web scraping for Wordle data...");
    }

    public static void writingDataMsg(String fileName) {
        System.out.println();
        System.out.println("Writing to " + fileName + "...");
    }

    public static void manualGameMsg() {
        System.out.println("Starting manual Wordle Game...");
        System.out.println();
    }

    public static void autoGameMsg(int solverVersion) {
        System.out.println("Starting automatic Wordle Game with solver " + solverVersion + "...");
        System.out.println();
    }

    public static void findErrorsMsg(int numberOfTests, int solverVersion) {
        System.out.println("Set size: " + numberOfTests);
        System.out.println("Solver version: " + solverVersion);
        System.out.println("First guess: '" + Solver.firstGuess + "'");
        System.out.println();
        System.out.println("Searching for errors...");
    }

    public static void gettingStatsMsg(int solverVersion, int numOfTests, WordleTester tester) {
        System.out.println();
        if (tester instanceof AllStatsGetter) {
            System.out.println("Solver version: " + solverVersion);
            System.out.println("First guess: '" + Solver.firstGuess + "'");
            System.out.println();
            System.out.println("Getting stats for all solution words (count " + numOfTests + ")...");
        }
        else if (! (tester instanceof ErrorFinder)) {
            System.out.println("Solver version: " + solverVersion);
            System.out.println("First guess: '" + Solver.firstGuess + "'");
            System.out.println("Number of tests: " + numOfTests);
            System.out.println();
            System.out.println("Getting stats...");
        }
    }

    public static void timeLeft(double timeLeft) {
        System.out.println("  Estimated time left - " + Time.string(timeLeft));
    }

    public static void done() {
        System.out.println();
        System.out.println("Done.");
    }

    public static void executionTime(double time) {
        System.out.println();
        System.out.println("Execution time - " + Time.string(time));
    }
}
