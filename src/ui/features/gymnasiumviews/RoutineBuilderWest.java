package ui.features.gymnasiumviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import resources.StringFormatUtility;
import resources.datatypes.ExerciseCategories;
import resources.sqlite.SQLiteJDBC;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.HashMap;
import java.util.Map;

public class RoutineBuilderWest extends ScalingVBox {

    private Map<ExerciseCategories, SimpleListTextDisplay> exerciseLists;
    private ChoiceBox<ExerciseCategories> categorySelector;

    public RoutineBuilderWest(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        categorySelector = new ChoiceBox<>();
        exerciseLists = new HashMap<>();
        for(ExerciseCategories category : ExerciseCategories.values()) {
            exerciseLists.put(category, new SimpleListTextDisplay(StringFormatUtility.capitalize(category.toString()), getViewBindings()));

            categorySelector.getItems().add(category);
        }
        loadExercises();
        selectList(ExerciseCategories.ANAEROBIC);
        categorySelector.getSelectionModel().select(ExerciseCategories.ANAEROBIC);
        categorySelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExerciseCategories>() {
            @Override
            public void changed(ObservableValue<? extends ExerciseCategories> observable, ExerciseCategories oldValue, ExerciseCategories newValue) {
                selectList(newValue);
            }
        });
    }

    private void selectList(ExerciseCategories category) {
        this.getChildren().clear();
        this.getChildren().add(categorySelector);
        this.getChildren().add(exerciseLists.get(category));
    }

    public void loadExercises() {
        for(ExerciseCategories category : ExerciseCategories.values()) {
            exerciseLists.get(category).clear();
            for(String exercise : SQLiteJDBC.getInstance().getExercisesByCategory(category.toString())) {
                exerciseLists.get(category).addLine(exercise);
            }
        }
    }

    public String getSelectedExercise() {
        if(exerciseLists.get(categorySelector.getSelectionModel().getSelectedItem()).isItemSelected()) {
            return exerciseLists.get(categorySelector.getSelectionModel().getSelectedItem()).getSelectedItem();
        } return "";
    }
}
