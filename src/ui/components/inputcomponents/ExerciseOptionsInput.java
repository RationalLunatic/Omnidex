package ui.components.inputcomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

public class ExerciseOptionsInput extends ScalingHBox {
    private Label exerciseName;
    private LabeledInputBox repsPerSet;
    private LabeledInputBox setsPerRoutine;

    public ExerciseOptionsInput(String exerciseName, ViewBindingsPack viewBindings) {
        super(viewBindings);
        init(exerciseName);
    }

    private void init(String exerciseName) {
        this.setAlignment(Pos.CENTER);
        this.exerciseName = new Label(exerciseName);
        this.exerciseName.prefWidthProperty().bind(new ScaledDoubleBinding(getViewBindings().widthProperty(), 0.66));
        this.exerciseName.setAlignment(Pos.CENTER);
        repsPerSet = new LabeledInputBox("Reps: ", getViewBindings(), 0.25);
        setsPerRoutine = new LabeledInputBox("Sets: ", getViewBindings(), 0.25);
        repsPerSet.setText("1");
        setsPerRoutine.setText("1");
        this.getChildren().addAll(this.exerciseName, repsPerSet, setsPerRoutine);
    }

    public String getName() {
        return exerciseName.getText();
    }

    public String getReps() {
        return repsPerSet.getInput();
    }

    public String getSets() {
        return setsPerRoutine.getInput();
    }
}
