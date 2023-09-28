package g56055.atlg.samegame.controller;

import g56055.atlg.samegame.model.CommandManager;
import g56055.atlg.samegame.model.Game;
import g56055.atlg.samegame.model.Position;
import g56055.atlg.samegame.model.RemoveCommand;
import g56055.atlg.samegame.view.View;

/**
 * Gathers and controls the different elements of the model and the console view.
 *
 * @author g56055
 */
public class Controller {

    private final View view;
    private final Game game;
    private final CommandManager cmdMgr;

    /**
     * Define a console Controller
     */
    public Controller() {
        this.game = new Game();
        this.view = new View(game);
        cmdMgr = new CommandManager();
    }

    /**
     * Run the principal loop of the game
     */
    public void run() {
        view.displayWelcome();
        launchGame();
        while (!game.checkIsOver()) {
            view.printBoard();
            view.displayScore();
            String[] answer = view.askPosition();
            switch (answer[0]) {
                case "rm" -> remove(answer);
                case "redo" -> redo();
                case "undo" -> undo();
                default -> view.showError();
            }
        }
        view.printBoard();
        view.displayScore();
        view.displayEndMessage();
    }

    /**
     * Ask user to enter the desired configuration and start the game
     */
    private void launchGame() {
        String[] userConfig = view.askConfiguration();
        int row = Integer.parseInt(userConfig[0]);
        int col = Integer.parseInt(userConfig[1]);
        int level = Integer.parseInt(userConfig[2]);
        game.startGame(row, col, level);
    }

    /**
     * Manage the action of play a round of the game
     *
     * @param userAnswer the position of the tile that must be removed
     */
    private void remove(String[] userAnswer) {
        int x = Integer.parseInt(userAnswer[1]) - 1;
        int y = Integer.parseInt(userAnswer[2]) - 1;
        if (game.canBeRemoved(new Position(x, y))) {
            cmdMgr.addCommand(new RemoveCommand(new Position(x, y), game));
        } else {
            view.showError();
        }
    }

    /**
     * Ask to Command manager to un-execute the previous command
     */
    private void undo() {
        cmdMgr.undoCommand(game);
    }

    /**
     * Ask to Command manager to re-execute the previous canceled command
     */
    private void redo() {
        cmdMgr.redoCommand(game);
    }
}
