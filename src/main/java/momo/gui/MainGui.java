package momo.gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import momo.Momo;

/**
 * JavaFX application entry point for the Momo chatbot GUI.
 */
public class MainGui extends Application {
    private Momo momo = new Momo();

    /**
     * Starts the JavaFX application.
     * <p>
     * Loads the main window layout from FXML, initializes the scene,
     * sets minimum window dimensions, and displays the stage.
     * </p>
     *
     * @param stage The primary stage for this application.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainGui.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            fxmlLoader.<MainWindow>getController().setMomo(momo); // inject the Momo instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

