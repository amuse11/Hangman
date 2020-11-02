
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * Hangman game FXML controller
 */
public class FXMLHangmanController implements Initializable {

    // to store a hangman game instance
    private Hangman game;

    // label for hangman picture, current guess, misses list, notification 
    @FXML
    private Label labelHangmanView, labelCurrentGuess, labelMisses, labelInfo;

    // character input text field
    @FXML
    private TextField textInput;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // create new game when initializing the fxml GUI
        try {
            game = new Hangman();
        } catch (IOException ex) {
            // if there is an error when creating hangman game log it
            System.out.println("Error: " + ex.getMessage());
            // exit the game
            Platform.exit();
        }
        // initialize the UI component details 
        refreshUI();
        // prompt to user to enter a new guess
        notify("Type & enter a character that you guess is in the word");
    }

    @FXML
    private void handleTextInputAction(ActionEvent event) {

        // read the input text from the text field
        String text = textInput.getText().trim();
        // validate input text
        if (text.isEmpty()) {
            // if the input text is empty return
            return;
        }
        // if the input text has more than one character
        if (text.length() > 1) {
            // notify it to the user to re-enter and return
            notify("Enter one character that you guess");
            return;
        }
        char guess = text.charAt(0);

        // check if the character has been guessed before
        if (game.isGuessedAlready(guess)) {
            notify("Try again. You have already guessed this character");
        } else if (game.playGuess(guess)) {
            // if the guessed character is a correct guess
            notify("Great guess. Gussed character is in in the word");
        } else {
            // if the guessed character is a wrong guess
            notify("Unfortunately that character is not in the word");
        }

        // refresh the UI
        refreshUI();

        // check weather the player win
        if (game.didPlayerWin()) {
            // show confirmation altert for continuing the game
            continueGame("Congrats. You won the game");
        } else if (game.didPlayerLose()) { // check weather the player lose
            // show confirmation altert for continuing the game
            continueGame("Sorry. You lost. You used all your 6 chances. \n"
                    + " The mystery word was: " + game.getMysteryWord() + ".");
        }

    }

    /**
     * This method shows an alert to continue the game or quit the game
     *
     * @param result the given result of the game end
     */
    private void continueGame(String result) {

        // create a new alert
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // set the title to the dialog
        alert.setTitle("Confirmation Message");
        // set the header
        alert.setHeaderText(result);
        // set the content text
        alert.setContentText("Do you want to play another game? ");

        // create yes and no button for alert
        ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);

        // add the created button types (yes and no)
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        // show the wait for the user response
        alert.showAndWait().ifPresent((btnType) -> {

            // if user clicked yes
            if (btnType.getButtonData() == ButtonBar.ButtonData.YES) {

                // create a new game
                try {
                    game = new Hangman();
                } catch (IOException ex) {
                    // if there is an error when creating hangman game log it
                    System.out.println("Error: " + ex.getMessage());
                    // exit the game
                    Platform.exit();
                }
                // refresh the UI
                refreshUI();
                // prompt to user to enter a new guess
                notify("Type & enter a character that you guess is in the word");

            } else if (btnType.getButtonData() == ButtonBar.ButtonData.NO) {
                // if the user clicked no
                // quit 
                Platform.exit();
            }
        });
    }

    /**
     * This method notify the information to the user
     *
     * @param info given information
     */
    private void notify(String info) {
        labelInfo.setText(info);
    }

    /**
     * This method refresh the UI. Re-draw the hangman refresh the current
     * guess.
     */
    private void refreshUI() {
        // if the game is null, return
        if (game == null) {
            return;
        }
        // set the new hangman image
        labelHangmanView.setGraphic(new ImageView(game.drawHangman()));
        // set the updated current guess
        labelCurrentGuess.setText(game.getFormalCurrentGuess());
        // set the updated misses characters
        labelMisses.setText(game.getFormalMisses());
        // clear the input text
        textInput.setText("");
    }

}
