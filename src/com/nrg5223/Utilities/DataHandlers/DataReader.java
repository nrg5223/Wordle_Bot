package com.nrg5223.Utilities.DataHandlers;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import com.nrg5223.WordleGames.Solvers.DataTypes.WordList;
import com.nrg5223.WordleGames.Solvers.DataTypes.Word;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * A container of static methods used for file reading and parsing.
 */
public class DataReader extends DataHandler {

    // GETTING LIBRARIES

    public static StringList getStrWordleLib() throws IOException {
        return getLibFrom(wordleLibFileName);
    }

    public static StringList getStrSolutionLib() throws IOException {
        return getLibFrom(solutionLibFileName);
    }

    /**
     * The testing library is a manually-created library of words used for
     * testing solvers.  This method is usually unused due to its purpose; it is
     * also meant for developer use.
     *
     * @return a StringList of the words from the testing library
     * @throws IOException can occur during file reading/writing
     */
    public static StringList getStrTestingLib() throws IOException {
        return getLibFrom(testingLibFileName);
    }

    /**
     * Parse a StringList from the given file, which contains the wordle library
     * or the solution library.  This method is used by getStrWordleLib() and
     * getStrSolutionLib().
     *
     * Lines are parsed by splitting lines with " " and taking the first
     * element.  This is to allow users to add notes next to words in the
     * library text files without affecting the library parsing.
     *
     * @param fileName the name of the file with the wordle library or the
     *                 solution library
     * @return a StringList of the words from the library of choice
     * @throws IOException can occur during file reading/writing
     */
    private static StringList getLibFrom(String fileName) throws IOException {
        StringList wordleLib = new StringList();

        BufferedReader reader = new BufferedReader((new FileReader(fileName)));

        String line = reader.readLine();
        while (line != null && !line.equals("")) {
            wordleLib.add(line.split(" ")[0]);
            line = reader.readLine();
        }
        return wordleLib;
    }

    // DEBUGGING **************************************************************

    /**
     * data/errors.txt contains text representations of all Wordle games up to
     * this point that have caused errors.  This method parses the content of
     * data/errors.txt that represents the first of those games.  The result is
     * a Word[]; the first element is the solution to that game; the rest are
     * the guesses, in order, that lead to the error.
     *
     * @return a Word[] that contains the solution word and in-order guesses
     *         that have caused an error
     * @throws IOException can occur during any file reading/writing
     */
    public static Word[] parseFaultyGame() throws IOException, NullPointerException {
        if (!fileExists(errorsFileName)) throw new FileNotFoundException();

        WordList gameDetails = new WordList();
        BufferedReader reader = new BufferedReader((new FileReader(errorsFileName)));

        String line = reader.readLine();
        gameDetails.add(new Word(line.split("solution: ")[1]));

        while (!line.equals("")) {
            line = reader.readLine();
            if (line.equals(""))
                break;
            gameDetails.add(new Word(line.split("Guess: ")[1]));

            reader.readLine();
            reader.readLine();
        }

        return gameDetails.toArray();
    }
}
