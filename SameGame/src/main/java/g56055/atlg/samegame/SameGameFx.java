package g56055.atlg.samegame;

import g56055.atlg.samegame.controller.ControllerFx;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Main Class for SameGame with a Fx view
 *
 * @author g56055
 */
public class SameGameFx extends Application {

    @Override
    public void start(Stage stage) {
        new ControllerFx(stage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
