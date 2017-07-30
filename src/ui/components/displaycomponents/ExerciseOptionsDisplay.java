package ui.components.displaycomponents;

import javafx.scene.control.Label;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

public class ExerciseOptionsDisplay extends ScalingHBox {
    private Label exerciseName;
    private LabeledInputBox repsPerSet;
    private LabeledInputBox setsPerRoutine;

    public ExerciseOptionsDisplay(String exerciseName, ViewBindingsPack viewBindings) {
        super(viewBindings);
        init(exerciseName);
    }

    private void init(String exerciseName) {
        this.exerciseName = new Label(exerciseName);
        this.exerciseName.prefWidthProperty().bind(new ScaledDoubleBinding(getViewBindings().widthProperty(), 0.66));
        repsPerSet = new LabeledInputBox("Reps: ", getViewBindings(), 0.33);
        setsPerRoutine = new LabeledInputBox("Sets: ", getViewBindings(), 0.33);
        this.getChildren().addAll(this.exerciseName, repsPerSet, setsPerRoutine);
    }
}
