import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Rhu Paatan (rpaatan)
 * @version 0.1.0
 * @Since 1/29/26
 **/
public class Jotto {
    private static final int WORD_SIZE = 5;
    private static final boolean DEBUG = true;
    // variable used to change the display when the program is being debugged.

    private int score;
    private String currentWord;
    private String filename;
    private ArrayList<String> playGuesses;
    // holds all the guesses of the player.
    private ArrayList<String> playWords;
    // stores the words that have been selected from word list during the current game. (1 run)
    private ArrayList<String> wordList;
    // holds the bank of words read in from a text file.

    // CONSTRUCTOR(S)
    public Jotto(String filename) {
        this.filename = filename;
        readWords();
    }

    // GETTERS & SETTERS

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getCurrentWord() {
        return currentWord;
    }

    public void setCurrentWord(String currentWord) {
        this.currentWord = currentWord;
    }

    public ArrayList<String> getPlayGuesses() {
        return playGuesses;
    }

    public void setPlayGuesses(ArrayList<String> playGuesses) {
        this.playGuesses = playGuesses;
    }

    public ArrayList<String> getWordList() {
        return wordList;
    }

    public void setWordList(ArrayList<String> wordList) {
        this.wordList = wordList;
    }

    public ArrayList<String> getPlayWords() {
        return playWords;
    }

    public void setPlayWords(ArrayList<String> playWords) {
        this.playWords = playWords;
    }

    // OTHER METHODS
    public ArrayList<String> readWords() {
        // PASSED TEST CASES
        ArrayList<String> readInWords = new ArrayList<>();
        String currentWord = "";

        try {
            File fr = new File(filename);
            Scanner fs = new Scanner(fr);

            while(fs.hasNext()) {
                boolean isNotDuplicate = true;
                currentWord = fs.next();

                for (String readInWord : readInWords) {
                    if (readInWord.equals(currentWord)) {
                        isNotDuplicate = false;
                        break;
                    }
                }

                if(isNotDuplicate) {
                    readInWords.add(currentWord);
                }
            }
        } catch (Exception e) {
            System.out.println("Couldn't open " + filename);
        }

        wordList = readInWords;

        return wordList;
    } // PASSED

    public void play() { // CODE FINISHED, UNTESTED.
        Scanner in = new Scanner(System.in);
        String playerInput = "";
        String exitGame = "zz";


        System.out.println("Welcome to the game.");

        do {
            System.out.println("Current Score: " + score + "\n" +
                    "=-=-=-=-=-=-=-=-=-=-=\n" +
                    "Choose one of the following:\n" +
                    "1:\t Start the game\n" +
                    "2:\t See the word list\n" +
                    "3:\t See the chosen words\n" +
                    "4:\t Show Player guesses\n" +
                    "zz to exit\n" +
                    "=-=-=-=-=-=-=-=-=-=-=");

            System.out.print("What is your choice: ");
            playerInput = in.next();

            if(playerInput.equals("one") || playerInput.equals("1")) {
                boolean wordPicked = pickWord();
                if(wordPicked) {
                    score = guess();
                } else {
                    showPlayerGuesses();
                }
            } else if(playerInput.equals("two") || playerInput.equals("2")) {
                showWordList();
            } else if(playerInput.equals("three") || playerInput.equals("3")) {
                showPlayedWords();
            } else if(playerInput.equals("four") || playerInput.equals("4")) {
                showPlayerGuesses();
            } else if((playerInput.toLowerCase()).equals(exitGame)) {
                System.out.println("Final Score: " + getScore() + "\n" +
                        "Thank you for playing");
            } else {
                System.out.println("I don't know what " + playerInput + " is.");
            }

            System.out.print("Press enter to continue.");
            playerInput = in.nextLine();

        } while(!(playerInput.equalsIgnoreCase(exitGame)));

    } // UNFINISHED -- NEEDS ADD. METHODS

    public String showPlayedWords() {
        if(playWords == null) {
            return "No words have been played.";
        }

        StringBuilder sb = new StringBuilder("Current list of played words:");

        for (String playWord : playWords) {
            //   for(int i = 0; i < playWords.size(); i++) {
            sb.append("\n").append(playWord);
        }

        return sb.toString();
    } // METHOD NEEDS: pickWord(), getPlayedWords

    public String showWordList() {
        StringBuilder sb = new StringBuilder("Current word list: ");

        for(int i = 0; i < wordList.size(); i++) {
            sb.append("\n").append(wordList.get(i));
        }

        return sb.toString();
    }

    public ArrayList<String> showPlayerGuesses() {
        if(playGuesses == null) {
            System.out.println("No guesses yet");
            return null;
        }

        System.out.println("Current player guesses: ");

        for(int i = 0; i < playGuesses.size(); i++) {
            System.out.println(playGuesses.get(i) + "\n");
        }

        Scanner in = new Scanner(System.in);
        String playerInput = in.next();


        do {
            System.out.println("Would you like to add words to the word list? (y/n)");
            if (playerInput.equals("y")) {
                updateWordList();
                showWordList();
            }
        } while (!playerInput.equals("y") || !playerInput.equals("n"));
        // ok whtever man be yellow like that i guess

        return playGuesses;
    }

    int guess() {
        ArrayList<String> currentGuesses = new ArrayList<>();
        Scanner scan = new Scanner(System.in);
        int letterCount = 0;
        int score = WORD_SIZE + 1;
        String wordGuess;

        do {
            System.out.println("Current Score: " + score + "\n"
                    + "What is your guess? (q to quit): ");

            wordGuess = scan.next();
            wordGuess = wordGuess.replaceAll("\\s+", "");
            wordGuess = wordGuess.toLowerCase();

            if(wordGuess.equals("q")) {
                if(score > 0) {
                    score = 0;
                }
                 break;
            }

            if(wordGuess.length() == WORD_SIZE ) {
                if(currentGuesses.contains(wordGuess)) {
                    System.out.println("This word has already been entered");
                    continue;
                }
                addPlayerGuess(wordGuess);

                if (wordGuess.equals(currentWord)) {
                    System.out.println("DINGDINGDING!!! the word was " + currentWord);
                    currentGuesses.add(currentWord);
                    playerGuessScores(currentGuesses);

                    return score;
                }
            } else {
                System.out.println("Word must be " + WORD_SIZE +
                        "characters (" + wordGuess + " is " + wordGuess.length());
            }

            currentGuesses.add(wordGuess);
            letterCount = getLetterCount(wordGuess);

            if(letterCount != WORD_SIZE) {
                System.out.printf("%-14s %s", wordGuess, score + "\n" );
            }

            if(letterCount == WORD_SIZE) {
                System.out.println("The word you chose is an anagram.");
            }

            score--;
            playerGuessScores(currentGuesses);

        } while (!wordGuess.equals("q"));


        return score;

    } // CODE FINISHED, UNTESTED (done also with a headache and sleep deprived.)

    public int getLetterCount(String wordGuess) {
        int count = 0;
        ArrayList<String> checkedLetters = new ArrayList<>();

        if(wordGuess.equals(currentWord)) {
            return 5;
        }

        for(int i = 0; i < wordGuess.length(); i++) {
            for(int k = 0; k < currentWord.length(); k++) {
                if(wordGuess.charAt(i) == currentWord.charAt(k) && !checkedLetters.contains(wordGuess.charAt(i))) {
                        score++;
                        checkedLetters.add(String.valueOf(wordGuess.charAt(i)));
                    }
                }
            }


        return count;
    }

    void updateWordList() {
//        FileWriter fr = new FileWriter(filename);
//
//      try {
//
//      } catch (IOException ex) {
//      }
//      fr.close();
    }

    public boolean pickWord() {
        if(wordList == null) {
            return true;
        }

        Random r = new Random();
        int randomLocation = Math.abs(r.nextInt() % wordList.size());

        currentWord = wordList.get(randomLocation);

        if(playWords.contains(currentWord) && wordList.size() == playWords.size()) {
            System.out.println("You've guessed them all!");
            return false;
        } else if (playWords.contains(currentWord) && wordList.size() != playWords.size()) {
            pickWord();
        }

        playWords.add(currentWord);

        if(DEBUG) {
            System.out.println(currentWord);
        }

        return true;
    } // METHOD NEEDS: instantiation of playWords -- otherwise nullpointer exception.

    public boolean addPlayerGuess(String wordGuess) {
        if(!playGuesses.contains(wordGuess)) {
            playGuesses.add(wordGuess);
            return true;
        }
        return false;
    }

    void playerGuessScores(ArrayList<String> guesses){
        for(int i = 0; i < guesses.size(); i++) {
            System.out.printf("%-14s %s", guesses.get(i), score + "\n" );
        }
    }

    public ArrayList<String> getPlayedWords() {
        return null;
    }

}