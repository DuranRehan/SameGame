package g56055.atlg.samegame.view;

import g56055.atlg.samegame.controller.ControllerFx;
import g56055.atlg.samegame.model.Game;
import g56055.atlg.samegame.model.Position;
import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

import static javafx.geometry.Pos.TOP_CENTER;

/**
 * ViewFx class define the Fx view of SameGame
 *
 * @author g56055
 */
public class ViewFx implements Observer {

    private final ControllerFx controller;
    private final Game game;
    private final VBox root;
    private final BoardFx board;
    private final MenuFx menu;
    private final Label errorLabel;
    private final Label score;
    private final Label remainingTiles;
    private final Button bStart;
    private final Button bUndo;
    private final Button bRedo;
    //private final Button buttonBoard;
    private final HBox alertBar;
    private final HBox footer;
    private final HBox buttonContainer;
    private final BoardFx boardDisplay;
    private Scene boardScene;
    private Stage boardStage;
    private List<BoardFx> boardsOpen;

    /**
     * Define the default fx view
     *
     * @param controller controller of the game
     * @param game the game that will be displayed
     */
    public ViewFx(ControllerFx controller, Game game) {
        this.controller = controller;
        this.game = game;
        root = new VBox(10);
        menu = new MenuFx();
        board = new BoardFx();
        boardDisplay = new BoardFx();
        bStart = new Button();
        bUndo = new Button();
        bRedo = new Button();
        //buttonBoard = new Button();
        errorLabel = new Label("This tile cannot be removed !");
        score = new Label("Score : ?");
        remainingTiles = new Label("Remaining : ?");
        footer = new HBox();
        alertBar = new HBox();
        buttonContainer = new HBox();
        boardStage = new Stage();
        boardsOpen = new ArrayList<>();
        game.subscribe(this);
    }

    /**
     * Initialize and configure all nodes
     *
     * @param stage stage where the elements will be initialized
     */
    public void start(Stage stage) {
        /* Configuration attribute */
        root.getChildren().addAll(menu, board, alertBar, footer);
        root.setAlignment(TOP_CENTER);
        menu.getStyleClass().add("menu-bar");
        board.getStyleClass().add("board");
        alertBar.getStyleClass().add("alertBar");
        alertBar.getChildren().add(errorLabel);
        errorLabel.setId("error-tile-label");
        footer.getStyleClass().add("footer");
        footer.getChildren().addAll(score, buttonContainer, remainingTiles);
        buttonContainer.setId("button-container");
        buttonContainer.getChildren().addAll(bUndo, bStart, bRedo/*, buttonBoard*/);

        /* Configure label/Button */
        initializedButton();
        remainingTiles.setId("remaining-tiles");
        score.setId("score");
        errorLabel.setVisible(false);
        /* Configure Event */
        bStart.setOnMouseClicked(mouseEvent -> {
            clickStartEvent();
            clickOnTileEvent();
            onMouseEnterTile();
            onMouseLeaveTile();
        });
        bUndo.setOnMouseClicked(mouseEvent -> clickUndoEvent());
        bRedo.setOnMouseClicked(mouseEvent -> clickRedoEvent());
        menu.getGiveUp().setOnAction(mouseEvent -> {
            if (board.getBoardFx() != null) {
                controller.resetScore();
                controller.giveUpGame();
            }
        });
//        buttonBoard.setOnMouseClicked(mouseEvent -> {
//            if (board.getBoardFx() != null) {
//                openBoard();
//            }
//        });

        /* Configuration scene/stage */
        Scene scene = new Scene(root, 1000, 750);
        scene.getStylesheets().add("/style.css");
        stage.setResizable(false);
        stage.setTitle("SameGame - Duran Rehan 56055");
        stage.getIcons().add(
                new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("icon.png"))));
        stage.setScene(scene);
        stage.show();
    }

    private void openBoard() {
        if (boardScene == null) {
            boardScene = new Scene(boardDisplay, 800, 600);
            boardDisplay.getStylesheets().add("/style.css");
            boardDisplay.getGame(controller);
            boardDisplay.displayBoard();
            boardsOpen.add(boardDisplay);
            boardStage.setScene(boardScene);
            boardStage.setTitle("SameGame - Second display");
            boardStage.setResizable(false);
            boardStage.showAndWait();

        } else {
            Stage stage = new Stage();
            BoardFx b = new BoardFx();
            b.getGame(controller);
            b.displayBoard();
            boardsOpen.add(b);
            b.getStylesheets().add("/style.css");
            Scene scene = new Scene(b, 800, 600);
            stage.setScene(scene);
            stage.showAndWait();
        }
    }

    /**
     * Initialized all buttons styles
     */
    private void initializedButton() {
        bStart.setId("button-start");
        bUndo.setId("button-undo");
        bRedo.setId("button-redo");
        Image startImg = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("play.png")));
        bStart.setBackground(new Background(new BackgroundImage(startImg,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, false, false, false, false))));

        Image undoImg = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("undo.png")));
        bUndo.setBackground(new Background(new BackgroundImage(undoImg,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, false, false, false, false))));

        Image redoImg = new Image(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("redo.png")));
        bRedo.setBackground(new Background(new BackgroundImage(redoImg,
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, false, false, false, false))));
        bUndo.setVisible(false);
        bRedo.setVisible(false);
    }

    /**
     * Manages the event when the start button is pressed
     */
    private void clickStartEvent() {
        int[] difficulty = menu.getSelectedSize();
        controller.startGame(difficulty[1], difficulty[0], menu.getSelectedDifficulty());
        board.getGame(controller);
        board.displayBoard();
        bStart.setVisible(false);
        bUndo.setVisible(true);
        bRedo.setVisible(true);
        remainingTiles.setText("Remaining : " + controller.getRemainingTile());
        score.setText("Score : " + controller.getScore());
        alertBar.getChildren().clear();
        alertBar.getChildren().add(errorLabel);
    }

    /**
     * Manages the event when the undo button is pressed
     */
    private void clickUndoEvent() {
        controller.undo();
    }

    /**
     * Manages the event when the redo button is pressed
     */
    private void clickRedoEvent() {
        controller.redo();
    }

    /**
     * Manages the event when the tiles are pressed
     */
    private void clickOnTileEvent() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                TileFx tile = board.getBoardFx()[i][j];
                if (tile != null) {
                    tile.setOnMouseClicked(
                            mouseEvent -> controller.playRound(tile.getPosition().getX(), tile.getPosition().getY()));
                }
            }
        }
    }

    /**
     * Manages the event when the mouse enter an area of tiles
     */
    private void onMouseEnterTile() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                TileFx tile = board.getBoardFx()[i][j];
                if (tile != null) {
                    List<Position> posList = controller.getNeighbors(new Position(i, j));
                    tile.setOnMouseEntered(mouseEvent -> {
                        for (Position pos : posList) {
                            board.getBoardFx()[pos.getX()][pos.getY()].setOpacity(0.5);
                        }
                    });
                }

            }
        }
    }

    /**
     * Manages the event when the mouse leave an area of tiles
     */
    private void onMouseLeaveTile() {
        for (int i = 0; i < game.getBoard().length; i++) {
            for (int j = 0; j < game.getBoard()[i].length; j++) {
                TileFx tile = board.getBoardFx()[i][j];
                if (tile != null) {
                    List<Position> posList = controller.getNeighbors(new Position(i, j));
                    tile.setOnMouseExited(mouseEvent -> {
                        for (Position pos : posList) {
                            board.getBoardFx()[pos.getX()][pos.getY()].setOpacity(1);
                        }
                    });
                }

            }
        }
    }

    /**
     * Update the Score label and Remaining tile label
     */
    private void updateLabel() {
        score.setText("Score : " + controller.getScore());
        remainingTiles.setText("Remaining : " + controller.getRemainingTile());
    }

    /**
     * Display the maximum score of the game
     */
    private void showMaxScore() {
        Label maxScore = new Label("The Maximum score is : " + controller.getScore());
        maxScore.setId("scoreMax-label");
        alertBar.getChildren().clear();
        alertBar.getChildren().addAll(maxScore);
    }

    @Override
    public void update(String toUpdate) {
        switch (toUpdate) {
            case "undoError" -> {
                Alert undoAlert = new Alert(Alert.AlertType.ERROR, "Nothing to undo");
                undoAlert.show();
            }
            case "redoError" -> {
                Alert redoAlert = new Alert(Alert.AlertType.ERROR, "Nothing to redo");
                redoAlert.show();
            }
            case "removeError" ->
                errorLabel.setVisible(true);
            case "update" -> {
                board.displayBoard();
                if (boardScene != null) {
                    for (BoardFx b : boardsOpen) {
                        b.displayBoard();
                    }
                }
                updateLabel();
                clickOnTileEvent();
                onMouseEnterTile();
                onMouseLeaveTile();
                errorLabel.setVisible(false);
                controller.gameIsOver();
            }
            case "win" -> {
                showMaxScore();
                bStart.setVisible(true);
                bUndo.setVisible(false);
                bRedo.setVisible(false);
            }
            case "giveUp" -> {
                score.setText("Score : ?");
                remainingTiles.setText("Remaining : ?");
                board.resetBoard();
                errorLabel.setVisible(false);
                bStart.setVisible(true);
                bUndo.setVisible(false);
                bRedo.setVisible(false);

            }
        }
    }

}
