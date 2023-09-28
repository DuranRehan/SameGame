package g56055.atlg.samegame.model;


import g56055.atlg.samegame.view.Observer;

/**
 * Define the Observable interface
 *
 * @author g56055
 */
public interface Observable {

    /**
     * Subscribe an observer to the subject
     *
     * @param v observer to subscribe
     */
    void subscribe(Observer v);

    /**
     * Unsubscribe an observer to the subject
     *
     * @param v observer to unsubscribe
     */
    void unsubscribe(Observer v);

    /**
     * Notify all observers of the subject
     */
    void notifyObserver(String toUpdate);
}
