package com.nrg5223.Testers.TestSets;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.StatsGettingGame;

/**
 * Used to run a number of TestingWordleGames.  The games record stats during
 * tests.  Then they are accessed by a WordleTester object to be recorded to
 * data/v_stats.txt where _ is the solver version.
 *
 * The size of the set is a fraction of the total test count specified in the
 * run configuration.  If this number is not divisible by the number of threads,
 * the last thread's StatsGettingSet also has the remaining number of games to
 * run.
 */
public class StatsGettingSet extends TestSet {

    int[] stats = new int[10];
    StatsGettingGame game;
    final StringBuilder missedWordsStr = new StringBuilder();

    public StatsGettingSet(int solverVersion, int setSize, StringList wordleLib, StringList solutionLib) {
        super(solverVersion, setSize, wordleLib, solutionLib);
    }

    // RUNNING TESTS **********************************************************

    /**
     * Run a number of TestWordleGames and store stats from all games.
     *
     * All StatsGetters use this run method.  Is is not overridden in
     * subclasses EXCEPT for TimedStatsGettingSet, which is a small, niche
     * class.  TimedStatsGettingSet adds little functionality to run() for
     * saving time-related data; it does not change existing functionality.
     */
    @Override
    public void run() {
        for (int i = 0; i < setSize; i++) {
            runNextGame();
        }
    }

    /**
     * Create, play, and store stats from a game.  Store words from lost games.
     *
     * Note: exceptions are ignored to allow all tests to run.  ErrorFindingSet
     * is responsible for storing errors.
     */
    void runNextGame() {
        try {
            createNewGame();

            game.play();
            updateStats(game.getStats());

            if (!game.lastGuessWasCorrect())
                missedWordsStr.append(game.getSolutionStr()).append("\n");
        }
        catch (Exception ignored) {}
    }

    /**
     * This method is used by TimedStatsGettingSet and TimedLibrarySubSet in
     * their run() methods.  It is identical to this class's run(), and it adds
     * functionality for storing and printing time-related data to the user on
     * the terminal.
     */
    void timedRun() {
        double start = System.currentTimeMillis();

        for (int i = 0; i < setSize; i++) {
            runNextGame();

            int tenth = setSize / 10;
            if (setSize < 10 && i > 1)
                printTimeLeft(start, i);
            else if (tenth > 0 && i % tenth == 0 && i > 1)
                printTimeLeft(start, i);
        }
    }

    /**
     * This method is used so the same TestingWordleGame can be reused and
     * reset.  The purpose of this is for it to be seen outside of the try
     * block's scope.  Also, it takes the already-created Wordle library as an
     * argument to save time; it doesn't have to generate it itself.
     */
    void createNewGame() {
        game = new StatsGettingGame(solverVersion, wordleLib, solutionLib);
    }

    // STORING STATS **********************************************************

    /**
     * Increment the total stat values with the stats of the most recently
     * completed game.
     *
     * @param newStats the stats from the most recently completed Wordle game
     */
    void updateStats(int[] newStats) {
        for (int i = 0; i < newStats.length; i++) {
            stats[i] += newStats[i];
        }
    }

    // GETTERS ****************************************************************

    public int[] getStats() {
        return stats;
    }

    public String getMissedWordsStr() {
        return missedWordsStr.toString();
    }
}
