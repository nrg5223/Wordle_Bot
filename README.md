# Wordle Bot

## Nicholas Grosskopf

Personal project - started 6/27/2022.

### Purpose
>   Create a program that plays Wordle as optimally as possible.

### Features
> #### Done
> - Playable Wordle PTUI
>    - Play an implementation of the online game, Wordle.
> - Automated Wordle PTUI
>    - Watch a solver play the Wordle PTUI.
> - Wordle Tester
>    - Run automated, multithreaded Wordle games.  The program calculates solver
>       stats and records them as proof of algorithm improvement.
> - Error Finder
>    - Run automated, multithreaded Wordle games.  The program catches 
>      exceptions and records the game configurations that caused the 
>      exceptions.  The configurations can be used for debugging.
>    - The debugging run configuration gets the faulty game configurations
>      found by the error finder, and runs them to easily recreate errors.
>    - This is for developer use.
> - General Features
>    - Calculate and print execution time estimates to the terminal for the user
>      during stats-gathering.
>    - Print functionality status updates to the terminal for the user.
>    - Use web scraping to get the official Wordle guess library and solution
>      library from online.
> 
> #### In Progress
> - none currently
> 
> #### Planned
> - Program that plays the daily Wordle using the solver.
>    - Daily texts to my phone or discord messages to show me results.
>    - Use a raspberry pi (most likely).
> - Import stats into matlab, plot them, then import the plot into the project
>   to illustrate algorithm improvement.
> - Antiwordle
>    - Playable PTUI
>    - Automated PTUI
> - Dordle
>    - Playable PTUI
>    - Automated PTUI

### How to run/use the program
> #### Prereqs
> - To update the library:
>    - have internet connection.
>    - install the Jsoup library.

> #### To run it
> 
> These are valid args strings:
>
>     ==================
>     library               v = version of solver (must be between 1-8)
>     manual                f = first guess       (must be in Wordle library)
>     auto v f              n = number of tests   (must be > 0)
>     quick_test v f n      
>     test v f           
>     test_all f         
>     ==================
>
> This is what they each do:
> 
>      library     - executes web scraping and writes the Wordle library and
>                    the solution library to text files.
>                  - additional arguments: none
>
>      manual      - plays a PTUI version of Wordle in the terminal.  The user
>                    types their guesses, and clues are printed to the
>                    terminal.
>                  - additional arguments: none
>
>      auto        - plays an automated PTUI version of Wordle in the terminal.
>                    The Solver class generates guesses and they are printed in
>                    the terminal.
>                  - additional arguments:
>                        args[1] - the solver version
>                        args[2] - the first guess word
>
>      quick_test  - runs a number of automated Wordle games and calculates
>                    stats like win percentage, avg num of guesses, and avg
>                    guess time.  The stats are written to a text file in data/Stats/
>                    called vXstats.txt where X is the solver version.
>                  - additional arguments:
>                        args[1] - the solver version
>                        args[2] - the first guess word
>                        args[3] - the number of tests to run
>
>      test        - runs a Wordle game for every word in the solution library
>                    for a given solver version.  Stats are calculated and
>                    written to a text file in data/Stats/ called vXstats.txt 
>                    where X is the solver version.
>                  - additional arguments:
>                        args[1] - the solver version
>                        args[2] - the first guess word
>
>      test_all    - runs a Wordle game for every word in the solution library
>                    for every solver version.  Stats are calculated and
>                    written to a text file in data/Stats/ called vXstats.txt 
>                    where X is the solver version.
>                  - additional arguments:
>                        args[1] - the first guess word

### Design
> #### Documents
> - See UML Class Diagram for system design here: https://github.com/nrg5223/Wordle_Bot/blob/3452c56630faa655ba46d3a79446f0fa86d50a7a/Documents/UML%20Class%20Diagram.pdf

### Solver Versions
> ### V1 
> A solver version that chooses a random word from the list of possible
> solutions.
> 
>  - Status: methods tested and verified. Performs well.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 97.75%
>     - Avg guesses: 4.07
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: 84.38%
>     - Avg guesses: 4.62
> 
> ### V2
> A solver version that uses letter frequency to choose the best guess. It
> chooses the guess with the most common letters among what is left in the 
> list of possible solutions.  It assumes guesses with all unique letters
> are best.
> 
>  - Status: methods tested and verified.  Performs well.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 98.96%
>     - Avg guesses: 3.70
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: 87.87%
>     - Avg guesses: 4.35
> 
> ### V3
> A solver version that uses letter-index frequency to choose the best guess.
> It chooses the guess with the most common letters in the most common positions
> among what is left in the list of possible solutions.  It assumes guesses with
> all unique letters are best.
> 
>  - Status: methods tested and verified.  Performs well.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 99.22%
>     - Avg guesses: 3.62
>  - With Wordle lib as solution lib (size 12974)
>     - Solver rate: 88.72%
>     - Avg guesses: 4.34
> 
> ### V4
> A solver version that chooses guesses by calculating average reduction of
> each potential guess.  To do so, it calculates the average reduction of each
> letter-index in each guess, and sums the 5 letter-index reductions.  It
> assumes guesses with all unique letters are best.
> 
> This is the best solver.  See how it works here: https://github.com/nrg5223/Wordle_Bot/blob/3452c56630faa655ba46d3a79446f0fa86d50a7a/Documents/Wordle%20Bot%20SolverV4.pdf
> 
>  - Status: methods tested and verified.  Performs well.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 100.00%
>     - Avg guesses: 3.59
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: 99.50
>     - Avg guesses: 4.27
> 
> ### V5
> A solver version that uses the same method as SolverV3, except it chooses
> guesses from the whole Wordle library.  SolverV3 chooses guesses from the
> solution library, meaning it plays hard mode.  This solver assumes guesses
> with all unique letters are best.
>
>  - Status: methods tested and verified.  Performs well.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 98.57%
>     - Avg guesses: 3.83
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: 85.35%
>     - Avg guesses: 4.47
> 
> ### V6
> A solver version that chooses guesses using the "brick-flame-podgy-shunt"
> method.  This combination of 4 words contains the 20 most common letters.
> This solver guesses these 4 words first then uses SolverV4's method for the
> rest.
> 
>  - Status: has at least one unidentified bug.
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 98.83%
>     - Avg guesses: 3.94
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: needs testing
>     - Avg guesses: needs testing
> 
> ### V7
> A solver version that chooses guesses by calculating average reduction of
> each potential guess.  To do so, it creates a copy of the current game for
> each word, guesses each word, and gets the reduction of the solution list for
> each one.  This takes a long time, so it uses SolverV4's method for the first
> 2 guesses.  Then it uses its method to re-rank the best X guesses from
> SolverV4's method.
> 
>  - Status:  methods tested and verified.  Performs well, but is slow.
>
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: 100.00%
>     - Avg guesses: 3.56
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: 99.78%
>     - Avg guesses: 4.19
> 
> ### V8 
> A solver version that chooses guesses by calculating reduction of each
> potential guess.  It stores a set of words that could be potentially removed
> by each letter in the guess.  Then it takes the union of the sets to remove
> repeat words.  This gives words more accurate scores.  The size of the union
> represents the average number of words removed by a given guess.
> 
>  - Status: needs more testing and optimization.  It is very slow. 
> 
> 
>  - With solution lib as solution lib (size 2309)
>     - Solve rate: needs testing
>     - Avg guesses: needs testing
>  - With Wordle lib as solution lib (size 12974)
>     - Solve rate: needs testing
>     - Avg guesses: needs testing

### Known Bugs
> - There is at least one unidentified bug in SolverV6.

README last updated on: 8/12/2022
