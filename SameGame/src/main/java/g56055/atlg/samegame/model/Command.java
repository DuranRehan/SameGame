package g56055.atlg.samegame.model;

/**
 * Interface for the Command DP
 *
 * @author g56055
 */
public interface Command {
    /**
     * Executes the command
     */
    void execute();

    /**
     * Cancels the executed command
     */
    void unexecute();
}
