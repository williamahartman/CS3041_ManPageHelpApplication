package manPageHelp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class, runs the program.
 *
 * @author William Hartman
 */
public class Main extends Application {

    /**
     * Build the JavaFX stage, general setup
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        primaryStage.setTitle("CS 3041 Project 4 - William Hartman");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    /**
     * Run the program.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        //Fix the awful text rendering JavaFX uses on Linux
        if(System.getProperty("os.name").toLowerCase().contains("linux")) {
            System.setProperty("prism.text", "t2k");
        }

        launch(args);
    }
}
