package g56055.atlg.samegame.model;

import java.util.Stack;

/**
 * CommandManager class manages the executed commands
 *
 * @author g56055
 */
public class CommandManager {

    private final Stack<Command> undoStack;
    private final Stack<Command> redoStack;

    /**
     * Initialize a default CommandManager
     */
    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    /**
     * Add a command to the manager and execute de command
     *
     * @param command command that will be added
     */
    public void addCommand(Command command) {
        undoStack.push(command);
        command.execute();
        redoStack.clear();
    }

    /**
     * Un-execute the previous command
     *
     * @param game the game where the command will be un-executed
     */
    public void undoCommand(Game game) {
        if (!undoStack.empty()) {
            undoStack.lastElement().unexecute();
            redoStack.push(undoStack.lastElement());
            undoStack.pop();
        } else {
            game.notifyObserver("undoError");
        }
    }

    /**
     * Execute the previous canceled command
     *
     * @param game the game where the command will be re-executed
     */
    public void redoCommand(Game game) {
        if (!redoStack.empty()) {
            redoStack.lastElement().execute();
            undoStack.push(redoStack.lastElement());
            redoStack.pop();
        } else {
            game.notifyObserver("redoError");
        }
    }
}
