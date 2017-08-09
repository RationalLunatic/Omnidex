package ui.components.displaycomponents;

import javafx.scene.control.Label;
import ui.components.inputcomponents.ExerciseOptionsInput;

public class ExerciseElementDisplay extends Label {

    public ExerciseElementDisplay(ExerciseOptionsInput exerciseInfo) {
        init(exerciseInfo);
    }

    private void init(ExerciseOptionsInput exerciseInfo) {
        String exerciseDisplay = exerciseInfo.getName() + " " + exerciseInfo.getReps() + " " + exerciseInfo.getSets();
        this.setText(exerciseDisplay);
    }
}
