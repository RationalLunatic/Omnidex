package ui.features.gymnasiumviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import resources.StringFormatUtility;
import resources.datatypes.exercisedata.ExerciseCategories;
import resources.datatypes.exercisedata.PhysicalExercise;
import resources.sqlite.SQLiteJDBC;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.popupdialogs.ExerciseDialog;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class RoutineBuilderWest extends ScalingVBox {

    private Map<ExerciseCategories, SimpleListTextDisplay> exerciseLists;
    private ChoiceBox<ExerciseCategories> categorySelector;
    private ScalingVBox listContainer;
    private ScalingVBox exerciseOptionsContainer;
    private ScalingButton deleteSelectedExercise;
    private ScalingButton createNewExercise;

    public RoutineBuilderWest(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void initContainers() {
        listContainer = new ScalingVBox(getViewBindings());
        exerciseOptionsContainer = new ScalingVBox(getViewBindings());
        listContainer.setAlignment(Pos.TOP_CENTER);
        exerciseOptionsContainer.setAlignment(Pos.TOP_CENTER);
    }

    private void initCategorySelector() {
        categorySelector = new ChoiceBox<>();
        initExerciseLists();
        categorySelector.getSelectionModel().select(ExerciseCategories.ANAEROBIC);
        categorySelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ExerciseCategories>() {
            @Override
            public void changed(ObservableValue<? extends ExerciseCategories> observable, ExerciseCategories oldValue, ExerciseCategories newValue) {
                selectList(newValue);
            }
        });
    }

    private void initExerciseLists() {
        exerciseLists = new HashMap<>();
        for(ExerciseCategories category : ExerciseCategories.values()) {
            exerciseLists.put(category, new SimpleListTextDisplay(StringFormatUtility.capitalize(category.toString()), getViewBindings()));
            categorySelector.getItems().add(category);
        }
    }

    private void init() {
        this.setAlignment(Pos.CENTER);
        initContainers();
        initCategorySelector();
        initExerciseOptions();
        addElementsToContainers();
        loadExercises();
        selectList(ExerciseCategories.ANAEROBIC);
    }

    private void addElementsToContainers() {
        exerciseOptionsContainer.getChildren().addAll(deleteSelectedExercise, createNewExercise);
        this.getChildren().addAll(listContainer, exerciseOptionsContainer);
    }

    private void initExerciseOptions() {
        createNewExercise = new ScalingButton(getViewBindings(), 1.0, 0.2);
        deleteSelectedExercise = new ScalingButton(getViewBindings(), 1.0, 0.2);
        createNewExercise.setText("Create New Exercise");
        deleteSelectedExercise.setText("Delete Selected Exercise");
        deleteSelectedExercise.setOnMouseClicked(e -> deleteSelectedExercise());
        createNewExercise.setOnMouseClicked(e -> createNewExercise());
    }

    private void selectList(ExerciseCategories category) {
        listContainer.getChildren().clear();
        listContainer.getChildren().add(categorySelector);
        listContainer.getChildren().add(exerciseLists.get(category));
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

    private void deleteSelectedExercise() {
        String selectedItem = exerciseLists.get(categorySelector.getValue()).getSelectedItem();
        if(selectedItem != null && !selectedItem.isEmpty()) {
            SQLiteJDBC.getInstance().deleteExercise(selectedItem);
            loadExercises();
        }
    }

    private void createNewExercise() {
        ExerciseDialog dialog = new ExerciseDialog();
        Optional<PhysicalExercise> exercise = dialog.showAndWait();
        if(exercise != null && exercise.isPresent()) {
            SQLiteJDBC.getInstance().addToExercises(exercise.get().getName(), exercise.get().getDescription(), exercise.get().getCategory().toString());
        }
        loadExercises();
    }
}
