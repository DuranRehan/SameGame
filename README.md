# SameGame

[Same Game](https://en.wikipedia.org/wiki/SameGame) is a classic solo puzzle game implemented in Java with both a JavaFX graphical interface and a console-based version. The objective is to clear the board of colored tiles and achieve the highest possible score. Players remove groups of two or more adjacent tiles of the same color. The score for each move is calculated by the formula `n * (n - 1)`, where `n` is the number of tiles removed, rewarding the removal of larger groups.


## Features

*   **Dual Interfaces:** Play using a JavaFX GUI or a classic terminal console interface.
*   
*   **Customizable Gameplay:**
    *   **Difficulty:** Choose between Easy (3 colors), Medium (4 colors), or Hardcore (5 colors).
    *   **Board Size:** Select from various board dimensions (20x10, 25x15, 30x20).
*   **Undo/Redo:** Easily undo and redo your moves to correct mistakes or explore different strategies.
*   **Strategic Scoring:** The scoring system rewards clearing larger blocks of tiles, adding a layer of strategy.
*   **Dynamic Board:** After tiles are cleared, remaining tiles fall down, and empty columns collapse to the left, creating new possibilities.

## Screenshot
![SameGame_screenshoot](/sm_screen.jpg)


## Getting Started

### Prerequisites
You must have **Java 16 or a later version** installed on your system to run the application.


### Running the Game
Download the `SameGame.jar` file from the repository. You can run the game by executing the following command in your terminal:

```bash
java -jar SameGame.jar
```
On most desktop systems, you can also double-click the `.jar` file to launch the graphical interface directly.


## How to Play

### Graphical Interface (GUI)
1.  **Launch:** Run the JAR file to open the main window.
2.  **Configure:** Before starting, use the top menu bar to select your desired **Board Size** and **Difficulty** level.
3.  **Start:** Click the central "Play" button to begin a new game with your selected settings.
4.  **Play:** Click on any group of two or more adjacent, same-colored tiles to remove them. Hovering over a valid group will provide a visual preview.
5.  **Controls:**
    *   **Undo/Redo:** Use the dedicated arrow buttons to step backward or forward through your moves.
    *   **Give Up:** Select "Give up" from the "Game" menu to end the current game and return to the main screen.
6.  **End of Game:** The game ends when no more groups of two or more tiles can be removed. Your final score is displayed.

### Console Interface
The project also includes a fully functional console version. To play it, you need to modify the main entry point in the source code to launch `SameGame.java` instead of `SameGameFx.java`.

1.  **Configuration:** When prompted, enter the desired number of rows, columns, and colors (e.g., `10 10 3`).
2.  **Play:** Enter commands to interact with the game board:
    *   `rm <row> <col>`: Removes the tile group at the specified 1-indexed coordinates.
    *   `undo`: Reverts the last move.
    *   `redo`: Re-applies the last undone move.

## Technical Overview


### Architecture
The application follows the **Model-View-Controller (MVC)** pattern to ensure a clean separation of concerns between game logic, data, and the user interface.

*   **Model** (`g56055.atlg.samegame.model`): Contains the core game logic, including the `Board`, `Game` state, `Tile`s, and business rules. `Game.java` acts as a Facade for the model.
*   **View** (`g56055.atlg.samegame.view`): Manages the user interface. It includes a console view (`View.java`) and a JavaFX-based graphical view (`ViewFx.java`, `BoardFx.java`).
*   **Controller** (`g56055.atlg.samegame.controller`): Acts as the intermediary between the Model and the View, handling user input and orchestrating updates.

### Design Patterns
*   **Observer Pattern:** The `ViewFx` observes the `Game` model. When the game state changes, the model notifies the view, which then updates itself to reflect the new state without being tightly coupled to the model's implementation.
*   **Command Pattern:** User actions, such as removing tiles, are encapsulated as `Command` objects (`RemoveCommand`). A `CommandManager` handles the execution of these commands and maintains a history, neatly enabling the undo/redo functionality.
*   **Facade Pattern:** The `Game.java` class provides a simplified, high-level interface to the more complex underlying components of the model, like the board manipulation and scoring logic.


## License

© Duran Rehan 56055
