package ui.components.popupdialogs;

import javafx.scene.control.ChoiceBox;
import resources.datatypes.exercisedata.ExerciseCategories;
import resources.datatypes.exercisedata.PhysicalExercise;
import resources.datatypes.exercisedata.ExerciseSubcategories;

public class ExerciseDialog extends PathfinderDialog {
    private ChoiceBox<ExerciseCategories> exerciseCategory;
    private ChoiceBox<ExerciseSubcategories> exerciseSubcategory;

    public ExerciseDialog() {
        super("Exercise");
        init();
    }

    private void init() {
        exerciseCategory = new ChoiceBox<>();
        for(ExerciseCategories category : ExerciseCategories.values()) {
            exerciseCategory.getItems().add(category);
        }
        exerciseCategory.setValue(ExerciseCategories.AEROBIC);
        exerciseSubcategory = new ChoiceBox<>();
        for(ExerciseSubcategories category : ExerciseSubcategories.values()) {
            exerciseSubcategory.getItems().add(category);
        }
        exerciseSubcategory.setValue(ExerciseSubcategories.ENDURANCE_TRAINING);
        getGridPane().add(exerciseCategory, 0, 3);
    }

    @Override
    protected void setResultConverter() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == getSubmitButtonType()) {
                return new PhysicalExercise(getTitleField().getText(), getDescriptionField().getText(), exerciseCategory.getValue());
            }
            return null;
        });
    }
}
