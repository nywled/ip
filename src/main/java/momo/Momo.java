/**
 * Main entry point for chatbot.
 * @author e1384477
 */
package momo;

import momo.controller.Controller;
import momo.ui.Ui;

/**
 * Application launcher for Momo.
 */
public class Momo {
    /**
     * Starts the Momo chatbot application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        Controller controller = new Controller(ui);
        controller.run();
    }
}
