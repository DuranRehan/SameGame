package g56055.atlg.samegame.view;

import javafx.scene.control.*;

/**
 * MenuFx class represent the menu of the game with all possible configurations
 *
 * @author g56055
 */
public class MenuFx extends MenuBar {
    private final Menu main;
    private final Menu difficulty;
    private final Menu sizeBoard;
    private MenuItem giveUp;
    private int[] selectedSize;
    private int selectedDifficulty;

    /**
     * Initialized a default menu
     */
    public MenuFx() {
        main = new Menu("Game");
        difficulty = new Menu("Difficulty");
        sizeBoard = new Menu("Size of Board");
        createMenuBar();
    }

    /**
     * Create and initialize all elements of the menu
     */
    private void createMenuBar() {
        RadioMenuItem level1 = new RadioMenuItem("Easy");
        RadioMenuItem level2 = new RadioMenuItem("Medium");
        RadioMenuItem level3 = new RadioMenuItem("Hardcore");
        ToggleGroup groupLevel = new ToggleGroup();
        groupLevel.getToggles().add(level1);
        groupLevel.getToggles().add(level2);
        groupLevel.getToggles().add(level3);
        level1.setId("level1");
        level2.setId("level2");
        level3.setId("level3");
        level1.setSelected(true);
        selectedDifficulty = getSelectedDifficulty();
        difficulty.getItems().addAll(level1, level2, level3);

        RadioMenuItem size1 = new RadioMenuItem("20x10");
        RadioMenuItem size2 = new RadioMenuItem("25x15");
        RadioMenuItem size3 = new RadioMenuItem("30x20");
        selectedSize = getSelectedSize();
        ToggleGroup groupSize = new ToggleGroup();
        groupSize.getToggles().add(size1);
        groupSize.getToggles().add(size2);
        groupSize.getToggles().add(size3);
        size1.setId("size1");
        size2.setId("size2");
        size3.setId("size3");
        size1.setSelected(true);
        sizeBoard.getItems().addAll(size1, size2, size3);

        giveUp = new MenuItem("Give up");
        main.getItems().addAll(difficulty, sizeBoard, giveUp);
        this.getMenus().add(main);
        main.getStyleClass().add("menu-main");
    }

    /**
     * Gets the give-up sub menu
     *
     * @return the give-up menu
     */
    MenuItem getGiveUp() {
        return giveUp;
    }

    /**
     * Gets the selected item in the menu
     *
     * @return an array with the size that is selected
     */
    int[] getSelectedSize() {
        for (MenuItem size : sizeBoard.getItems()) {
            if (size instanceof RadioMenuItem) {
                if (((RadioMenuItem) size).isSelected()) {
                    switch (size.getId()) {
                        case "size1" -> selectedSize = new int[]{20, 10};
                        case "size2" -> selectedSize = new int[]{25, 15};
                        case "size3" -> selectedSize = new int[]{30, 20};
                    }
                }
            }

        }
        return selectedSize;
    }

    /**
     * Gets the selected item in the menu
     *
     * @return a number that represent the number of colors that the game will be used
     */
    int getSelectedDifficulty() {
        for (MenuItem level : difficulty.getItems()) {
            if (level instanceof RadioMenuItem) {
                if (((RadioMenuItem) level).isSelected()) {
                    switch (level.getId()) {
                        case "level1" -> selectedDifficulty = 3;
                        case "level2" -> selectedDifficulty = 4;
                        case "level3" -> selectedDifficulty = 5;
                    }
                }
            }
        }
        return selectedDifficulty;
    }
}
