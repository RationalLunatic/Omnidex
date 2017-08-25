package ui.features.gymnasiumviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ListView;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.inputcomponents.ExerciseOptionsInput;
import ui.components.inputcomponents.EditableLabel;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.interviewcommunications.ViewRequestKeys;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class RoutineBuilderView extends ScalingStackPane {
    private ScalingVBox mainContainer;
    private ScalingHBox advancedOptions;
    private ScalingVBox createRoutineOptions;
    private EditableLabel routineName;
    private ScalingButton addNewExercise;
    private ListView<ExerciseOptionsInput> routineExercises;
    private ObservableList<ExerciseOptionsInput> routineExerciseOptionBoxes;
    private ScalingButton submitRoutine;
    private ScalingButton deleteRoutine;

    public RoutineBuilderView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initRoutineCreationOptions();
        addElementsToContainers();
    }

    private void addElementsToContainers() {
        advancedOptions.getChildren().add(createRoutineOptions);
        mainContainer.getChildren().addAll(advancedOptions);
        this.getChildren().add(mainContainer);
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        advancedOptions = new ScalingHBox(getViewBindings());
        createRoutineOptions = new ScalingVBox(getViewBindings());
    }

    private void initRoutineCreationOptions() {
        routineName = new EditableLabel(getViewBindings());
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        addNewExercise = new ScalingButton("Add New Exercise", buttonPack);
        submitRoutine = new ScalingButton("Submit Routine", buttonPack);
        deleteRoutine = new ScalingButton("Delete Routine", buttonPack);
        routineExercises = new ListView<>();
        routineExerciseOptionBoxes = FXCollections.observableArrayList();
        routineExercises.setItems(routineExerciseOptionBoxes);
        addNewExercise.setOnMouseClicked(e -> addNewExerciseToRoutine());
        submitRoutine.setOnMouseClicked(e -> saveRoutine());
        deleteRoutine.setOnMouseClicked(e -> deleteRoutine());
        createRoutineOptions.getChildren().addAll(routineName, addNewExercise, routineExercises, submitRoutine, deleteRoutine);
        createRoutineOptions.setAlignment(Pos.CENTER);
    }

    private void saveRoutine() {
        SQLiteJDBC.getInstance().addToRoutines(routineName.getText(), routineExerciseOptionBoxes);
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.ROUTINES, ViewRequestKeys.LIST_UPDATE, routineName.getText()));
    }

    private void deleteRoutine() {
        String routine = getRequestSender().handleDataRequest(new ViewRequest(PaneKeys.ROUTINES, ViewRequestKeys.DATA_REQUEST));
        SQLiteJDBC.getInstance().deleteRoutine(routine);
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.ROUTINES, ViewRequestKeys.LIST_UPDATE));
    }

    private void addNewExerciseToRoutine() {
        String exerciseName = getRequestSender().handleDataRequest(new ViewRequest(PaneKeys.EXERCISES, ViewRequestKeys.DATA_REQUEST));
        if(!exerciseName.isEmpty()) {
            ExerciseOptionsInput display = new ExerciseOptionsInput(exerciseName, getViewBindings());
            routineExerciseOptionBoxes.add(display);
        }
    }
}
