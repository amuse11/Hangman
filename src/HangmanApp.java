
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * This class is the driver class of the hangman game
 */
public class HangmanApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // load the fxml file
        Parent root = FXMLLoader.load(getClass().getResource("FXMLHangman.fxml"));

        // create a scene
        Scene scene = new Scene(root);

        // add the created scene to the stage
        stage.setScene(scene);

        // set the stage title
        stage.setTitle("Hangman");

        // show the stage
        stage.show();

        // off the resizable feature
        stage.setResizable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
