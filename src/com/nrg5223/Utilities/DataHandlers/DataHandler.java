package com.nrg5223.Utilities.DataHandlers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * An abstract class for handing data.  It is extended by a class used for
 * reading files, and a class used for writing to files.  This class is
 * essentially a container for file-handling methods used by its subclasses.
 */
public abstract class DataHandler {

    /**
     * Directories as strings.  Each includes the directory path starting from
     * data/, ending at the final directory.
     */
    private final static String DATA_DIR = "data/";
    private final static String STATS_DIR = DATA_DIR + "Stats/";
    private final static String LIB_DIR = DATA_DIR + "Libraries/";
    private final static String ERRORS_DIR = DATA_DIR + "Errors/";
    private final static String MISSED_DIR = DATA_DIR + "Missed/";

    /**
     * The stats, errors, and missedWords file names are assigned in the
     * constructor of TestingWordleGame using assignStatsFileName().  It depends
     * on the version of the solver used because stats are recorded separately
     * for each version of Solver.
     *
     * All files are written in the data/ directory.  Different files go in
     * different subdirectories of data/.
     */
    static String statsFileName;
    static String errorsFileName;
    static String missedWordsFileName;
    final static String wordleLibFileName = LIB_DIR + "wordle_lib.txt";
    final static String solutionLibFileName = LIB_DIR + "solution_lib.txt";
    final static String testingLibFileName = LIB_DIR + "testing_lib.txt";

    // FILE NAMES *************************************************************

    public static void assignFileNames(int version) {
        statsFileName = STATS_DIR + "v" + version + "stats.txt";
        errorsFileName = ERRORS_DIR + "v" + version + "errors.txt";
        missedWordsFileName = MISSED_DIR + "v" + version + "missed_words.txt";
    }

    public static String errorsFileName() {
        return errorsFileName;
    }

    // FILE HANDLING **********************************************************

    /**
     * Search the data directory for a text file and delete it if it exists.
     * This method is used by record() in step 2 of its process to add content
     * to an existing file.
     *
     * @param fileName the name of a file to delete
     */
    static void deleteFile(String fileName) {
        File directory = new File(directoryOf(fileName));
        File[] files = directory.listFiles();
        assert files != null;
        for (File f : files) {
            String fName = f.getName();
            fName = fName.substring(0, fName.length() - ".txt".length());
            if (fName.equals(fileName)) {
                f.delete();
                break;
            }
        }
    }

    /**
     * Get the entire content of a text file as a string.  This method is used
     * by record() in step 1 of its process to add content to an existing file.
     *
     * @param fileName a text file to get content from
     * @return the entire content of a text file as a string if it exists; an
     *          empty string otherwise.
     * @throws IOException can occur during any file reading/writing
     */
    static String contentOf(String fileName) throws IOException {
        if (!fileExists(fileName))
            return "";

        BufferedReader reader = new BufferedReader((new FileReader(fileName)));
        StringBuilder str = new StringBuilder();

        String line = reader.readLine();
        while (line != null) {
            str.append(line).append("\n");
            line = reader.readLine();
        }
        return str.toString();
    }

    /**
     * Does the data/ directory contain a file with the given name?  This method
     * is used by record() to determine whether it needs to add content to an
     * existing file or create a new file to write to.
     *
     * @param fileName the name of a text file
     * @return true if the file exists in the data/ directory; false otherwise.
     */
    static boolean fileExists(String fileName) {
        String dir = directoryOf(fileName);

        File directory = new File(dir);
        File[] files = directory.listFiles();
        assert files != null;
        for (File f : files) {
            String fName = dir + f.getName();
            if (fName.equals(fileName)) return true;
        }
        return false;
    }

    /**
     * Get the directory of a file.  File names include their directories, so
     * this method can parse the directory from a given file name.
     *
     * @param fileName the name of a file
     * @return the directory of a file as a string
     */
    private static String directoryOf(String fileName) {
        String[] characters = fileName.split("");
        int length = characters.length;
        int i;
        for (i = length - 1; i >=0; i--) {
            if (characters[i].equals("/"))
                break;
        }
        return fileName.substring(0, i + 1);
    }
}
