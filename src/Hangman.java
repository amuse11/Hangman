
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import javafx.scene.image.Image;

/**
 * This is the model for the hangman game
 */
public class Hangman {

    // store the max tries(chances)
    public static final int maxTries = 6;

    // the mystery word
    private final String mysteryWord;
    // current word
    private final StringBuilder currentGuess;
    // all the gusses
    private final ArrayList<Character> gussedList;
    // all the misses
    private final Set<Character> misses;
    // all the dictonary words
    private final ArrayList<String> dictionary;

    // streams for read the dictionary file
    private static FileReader fileReader;
    private static BufferedReader bufferedFileReader;

    // current try turn
    public int currentTry;

    /**
     * Construct a new Hangman game
     *
     * @throws IOException
     */
    public Hangman() throws IOException {
        // define the array-lists
        this.gussedList = new ArrayList<>();
        this.misses = new HashSet<>();
        this.dictionary = new ArrayList<>();
        // load the dictionary file
        loadDictionary();
        // pick a new word
        this.mysteryWord = pickWord();
        // initialate the current guess text
        this.currentGuess = initCurrentGuess();
    }

    /**
     * This method pick a random number from the dictionary
     *
     * @return the generated word
     */
    public final String pickWord() {
        // create random instance
        Random rand = new Random();
        // get the random index
        int wordIndex = Math.abs(rand.nextInt()) % dictionary.size();
        // take the word from the list
        return dictionary.get(wordIndex);
    }

    /**
     * @return the Mystery Word
     */
    public String getMysteryWord() {
        return mysteryWord;
    }

    /**
     * @return the current guess
     */
    public String getFormalCurrentGuess() {
        return "Current Guess  : " + currentGuess.toString();
    }

    /**
     * This method checks whether the game over
     *
     * @return true or false
     */
    public boolean isGameOver() {
        return didPlayerWin() || didPlayerLose();
    }

    /**
     * This method checks whether the player won the game
     *
     * @return true or false
     */
    public boolean didPlayerWin() {
        String guess = currentGuess.toString().replace(" ", "");
        return guess.equals(mysteryWord);
    }

    /**
     * This method checks whether the player lose the game
     *
     * @return true or false
     */
    public boolean didPlayerLose() {
        return currentTry >= maxTries;
    }

    /**
     * This method checks whether the given guess is already guessed char
     *
     * @param guess given guess
     * @return true or false
     */
    public boolean isGuessedAlready(char guess) {
        return gussedList.contains(guess);
    }

    /**
     * This method checks the given guess is in the mystery word
     *
     * @param guess given guessed character
     * @return true or false
     */
    public boolean playGuess(char guess) {
        boolean isItAGoodGuess = false;
        gussedList.add(guess);
        for (int i = 0; i < mysteryWord.length(); i++) {
            if (mysteryWord.charAt(i) == guess) {
                currentGuess.setCharAt(i * 2, guess);
                isItAGoodGuess = true;
            }
        }
        if (!isItAGoodGuess) {
            currentTry++;
            misses.add(guess);
        }
        return isItAGoodGuess;
    }

    /**
     * This method opens the related hangman image and it
     *
     * @return the corresponding hangman image according to the current try
     */
    public Image drawHangman() {
        int imageIdx = currentTry;
        if (currentTry >= maxTries) {
            imageIdx = 6;
        }
        return new Image(getClass().getResourceAsStream(String.format("images/Hangman-%d.png", imageIdx)));
    }

    /**
     * This method generates the misses list
     *
     * @return the list of misses
     */
    public String getFormalMisses() {
        String missesStr = "Misses  :";
        for (char ch : misses) {
            missesStr += "  " + ch;
        }
        return missesStr;
    }

    /**
     * This method loads the dictionary file and store all the words into a
     * array-list
     *
     * @throws IOException
     */
    private void loadDictionary() throws IOException {
        // open the dictionary file
        File inFile = new File("src/dictionary.txt");
        fileReader = new FileReader(inFile);
        bufferedFileReader = new BufferedReader(fileReader);
        String currentLine = bufferedFileReader.readLine();
        // read line by line and store each word into the list
        while (currentLine != null) {
            dictionary.add(currentLine);
            currentLine = bufferedFileReader.readLine();
        }
        // close the input file streams
        bufferedFileReader.close();
        fileReader.close();
    }

    /**
     * This method initializes the current guess
     *
     * @return the current guess
     */
    private StringBuilder initCurrentGuess() {
        // string builder for current guess
        StringBuilder current = new StringBuilder();
        // generate current guess using dashes
        for (int i = 0; i < mysteryWord.length() * 2; i++) {
            if (i % 2 == 0) {
                current.append("_");
            } else {
                current.append(" ");
            }
        }
        return current;
    }

}
