package com.nrg5223.Utilities.DataHandlers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.Utilities.Calculator;
import com.nrg5223.Utilities.Print;
import com.nrg5223.Utilities.Time;
import com.nrg5223.Utilities.WebScraper;
import com.nrg5223.WordleGames.Solvers.Solver;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * A container for methods used to write data to text files.
 */
public class DataWriter extends DataHandler {

    /** Strings that are reused in string generation methods */
    private final static String newLine = "\n";
    private final static String timeUnits = "ms";

    // ERRORS RECORDING *******************************************************

    public void recordErrors(String errorsStr) throws IOException {
        record(errorsStr, errorsFileName);
    }

    /**
     * Use the given string of missed words to write all missed words to a file.
     * These are words not solved by the solver.  This method is different from
     * record() because it only records new words.  It first checks the given
     * string for words that are already in the file; then it records the new
     * words.
     *
     * @param missedWordsStr a string with one word missed by a solver per line
     * @throws IOException can occur during file reading/writing
     */
    public void recordMissedWords(String missedWordsStr) throws IOException {
        String[] newMissedWords = missedWordsStr.split(newLine);
        StringBuilder newMissedWordsStr = new StringBuilder("");

        String existingMissedWordsStr = contentOf(missedWordsFileName);
        for (String word : newMissedWords)
            if (!existingMissedWordsStr.contains(word) &&
                !newMissedWordsStr.toString().contains(word))
                newMissedWordsStr.append(word).append(newLine);

        record(newMissedWordsStr.toString(), missedWordsFileName);
    }

    // STATS RECORDING ********************************************************

    public void recordStats(int[] stats) throws IOException {
        record(statsToString(stats), statsFileName);
    }

    /**
     * Convert the int[] of stats received by TestingWordleGame into a single
     * string that is usable by record().  This method handles formatting only.
     * A Calculator object is used for stat calculations.
     *
     * @param rawStats an int[] of stats received by a TestingWordleGame
     * @return the string representation of a Wordle game's stats to be written
     *         to a text file
     */
    private String statsToString(int[] rawStats) {
        Calculator calc = new Calculator();
        double[] calculations = calc.calcStats(rawStats);
        String timeString = Time.string(rawStats[rawStats.length - 1]);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy @HH:mm");
        String currentDate = dtf.format(LocalDateTime.now());

        return currentDate + " - " + (int) calculations[0] + " trials run in " + timeString +  " (first guess '" + Solver.firstGuess + "')" + newLine +
                "win percentage : " + calculations[1] + "%" + newLine +
                "avg num guesses: " + calculations[2] + newLine +
                "avg guess time : " + calculations[3] + " " + timeUnits + newLine +
                "avg game time  : " + calculations[4] + " " + timeUnits + newLine +
                "avg list size before each guess: " + newLine +
                (int) calculations[5] + " > " + (int) calculations[6] + " > " +
                (int) calculations[7] + " > " + (int) calculations[8] + " > " +
                (int) calculations[9] + " > " + (int) calculations[10] + newLine +
                newLine;
    }

    // LIBRARY WRITING ********************************************************

    public static void writeWordleLibToFile() throws IOException {
        StringList libList = (new WebScraper().getStrWordleLib());
        writeLibToFile(libList, wordleLibFileName);
    }

    public static void writeSolutionLibToFile() throws IOException {
        StringList libList = (new WebScraper()).getStrSolutionLib();
        writeLibToFile(libList, solutionLibFileName);
    }

    /**
     * Write a given library of words to a text file with one word per line.
     * This is used for updating the files that contain the wordle library and
     * solution library.  This method is used by writeSolutionLibToFile() and
     * writeWordleLibToFile()
     *
     * @param wordleLib a library of words as an arraylist.  It is either the
     *                  full wordle library or the wordle solution library.
     * @throws IOException can occur when reading/writing files
     */
    private static void writeLibToFile(ArrayList<String> wordleLib, String fileName) throws IOException {
        StringBuilder str = new StringBuilder();
        for (String word : wordleLib)
            str.append(word).append(newLine);

        overwrite(str.toString(), fileName);
    }

    // GENERAL WRITING ********************************************************

    /**
     * This method is used by recordErrors() and recordStats().  They simply
     * call this method using the appropriate parameters.  Those two methods are
     * separated (1) to better communicate functionality/purpose to classes that
     * use them and (2) because error data is received from TestingWordleGame as
     * a single string and stats data is received from TestingWordleGame as an
     * int[].  recordStats() has the additional step of converting the int[] to
     * a single string.
     *
     * This method creates and writes a single string to a text file if it
     * doesn't exist.  If it exists, this method adds to that file using this
     * process:
     *      1. Temporarily save its content
     *      2. Delete it
     *      3. Create a new file (same name, same location)
     *      4. Re-write the existing content
     *      5. Write the new content
     *
     * @param newContent a string representation of data that will be written to a file
     *
     * @param fileName the name of a file that will be written to.  It will be
     *                 either data/errors.txt or data/v_stats.txt where _ is the
     *                 version of the solver used
     *
     * @throws IOException can occur during any file reading/writing
     */
    private static void record(String newContent, String fileName) throws IOException {
        Print.writingDataMsg(fileName);

        if (!fileExists(fileName)) {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.close();
        }
        String existingContent = contentOf(fileName);
        deleteFile(fileName);

        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(existingContent);
        writer.write(newContent);
        writer.close();
    }

    /**
     * Overwrite the given file with the given string as content.  If the file
     * doesn't exist, create it.  This is used for updating the wordle and
     * solution libraries.
     *
     * @param newContent a string to be written to a file
     * @param fileName the name of a file to be written to
     * @throws IOException can occur when reading/writing to files
     */
    private static void overwrite(String newContent, String fileName) throws IOException {
        Print.writingDataMsg(fileName);

        if (fileExists(fileName)) {
            deleteFile(fileName);
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        writer.write(newContent);
        writer.close();
    }
}
