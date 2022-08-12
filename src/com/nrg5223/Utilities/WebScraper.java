package com.nrg5223.Utilities;

import com.nrg5223.WordleGames.Solvers.DataTypes.StringList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.*;

/**
 * Used to web scrape for data used by WordleGame and Solver.
 */
public class WebScraper {

    // LIBRARIES **************************************************************

    /**
     * Web scrape the page with the Wordle source code to store the complete
     * Wordle library.
     *
     * @return the Wordle library as a list of strings
     */
    public StringList getStrWordleLib() {
        /*
         * Initially, getStrWordleLib() parses a list of 5-letter words from the
         * Wordle library.  But the algorithm parses extra strings beyond the
         * library.  This number, 12974 (which is the size of the Wordle library) is
         * the index of the last actual 5-letter word in the array.  It is used to
         * cut off the extra parsed strings so the result of getStrWordleLib() is
         * only the Wordle library.
         */
        return scrapeForStringsUpToIndex(12974);
    }

    public StringList getStrSolutionLib() {
        /*
         * Initially, getStrWordleLib() parses a list of 5-letter words from the
         * Wordle library.  But the algorithm parses extra strings beyond the
         * library.  This number, 2309 (which is the size of the Wordle solution library) is
         * the index of the last actual solution word in the array.  It is used to
         * cut off the extra strings so the result of getStrWordleLib() is
         * only the Wordle solution library.
         */
        return scrapeForStringsUpToIndex(2309);
    }

    // WEB SCRAPING ***********************************************************

    /**
     * Web scrape the page with the Wordle source code to store the complete
     * Wordle library.
     *
     * @param sizeOfList is 12974 when getting the whole Wordle library, and
     *                   is 2309 when getting the Wordle solution library
     * @return the set of words from the Wordle library up to the given index
     */
    private StringList scrapeForStringsUpToIndex(int sizeOfList) {
        Print.webScrapingMsg();

        StringList wordleLib = new StringList();
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                String wordleSourceCodeUrl = "https://www.nytimes.com/games-assets/v2/wordle.a0a92b97854ca00493069c304e9bc893eee6c118.js";
                final Document doc = Jsoup.connect(wordleSourceCodeUrl).get();
                String htmlStr = doc.html();

                /*
                 * The Wordle library is found in the source code page; words on the page
                 * are separated by this string, so it is used in getStrWordleLib() to
                 * identify the 5-letter words in the html.
                 */
                String wordDivider = "&quot;";
                String[] uncutArray = htmlStr.split(wordDivider);
                StringList uncutList = new StringList(Arrays.asList(uncutArray));
                uncutList.removeIf(word -> word.length() != 5);

                wordleLib.addAll(uncutList.subList(0, sizeOfList)); // sizeOfList is exclusive
                break;
            } catch (IOException ignored) {} // includes ConnectException
        }
        return wordleLib;
    }
}
