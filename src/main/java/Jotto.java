import java.util.ArrayList;

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
//        readWords();
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
}