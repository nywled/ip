/**
 * Main entry point for chatbot
 * @author e1384477
 */
package momo;

import momo.controller.Controller;
import momo.ui.Ui;

public class Momo {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Controller controller = new Controller(ui);
        controller.run();
    }
}
