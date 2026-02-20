package momo.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import momo.Momo;

/**
 * Controller for the main GUI window.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Momo momo;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/User.png"));
    private Image momoImage = new Image(this.getClass().getResourceAsStream("/images/momo.png"));

    /**
     * Initializes the main window after its FXML elements are loaded.
     * <p>
     * Automatically scrolls the dialog pane to the bottom whenever
     * new dialog boxes are added.
     * </p>
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the {@link Momo} instance used to process user input.
     *
     * @param m The {@code Momo} application instance.
     */
    public void setMomo(Momo m) {
        momo = m;
        String welcome = momo.getWelcome();
        dialogContainer.getChildren().add(DialogBox.getMomoDialog(welcome, momoImage));
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing momo's
     * reply and then appends them to the dialog container.
     * <p>
     * Clears the user input after processing.
     * </p>
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = momo.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getMomoDialog(response, momoImage)
        );
        userInput.clear();

        if (momo.shouldExit()) {
            javafx.animation.PauseTransition pause =
                new javafx.animation.PauseTransition(javafx.util.Duration.seconds(1.5));
            pause.setOnFinished(e -> javafx.application.Platform.exit());
            pause.play();
        }
    }
}

