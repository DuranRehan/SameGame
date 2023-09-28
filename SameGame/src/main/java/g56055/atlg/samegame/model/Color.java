package g56055.atlg.samegame.model;

/**
 * Define the different possible color in SameGame
 *
 * @author g56055
 */
public enum Color {
    RED('R'),
    GREEN('G'),
    BLUE('B'),
    YELLOW('Y'),
    PINK('P');

    public final char letter;

    Color(char letter) {
        this.letter = letter;
    }

    /**
     * Gets the color in letter format
     *
     * @return the color in letter format
     */
    public char getLetter() {
        return letter;
    }

}
