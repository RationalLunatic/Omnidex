package ui.features.gymnasiumviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import resources.datatypes.ExerciseCategories;
import resources.datatypes.bibliographicdata.ExerciseSubcategories;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.inputcomponents.ExerciseOptionsInput;
import ui.components.inputcomponents.EditableLabel;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.interviewcommunications.ViewRequestKeys;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class RoutineBuilderView extends ScalingStackPane {
    private ScalingVBox mainContainer;
    private ScalingHBox basicOptions;
    private ScalingHBox advancedOptions;
    private ScalingVBox createExerciseOptions;
    private ScalingVBox createRoutineOptions;
    private ScalingHBox routineBuilder;
    private ScalingButton createExercise;
    private ScalingButton createRoutine;
    private EditableLabel exerciseName;
    private ChoiceBox<ExerciseCategories> selectCategory;
    private ChoiceBox<ExerciseSubcategories> selectSubcategory;
    private LabeledInputBox describeExercise;
    private ScalingButton submit;
    private EditableLabel routineName;
    private ScalingButton addNewExercise;
    private ListView<ExerciseOptionsInput> routineExercises;
    private ObservableList<ExerciseOptionsInput> routineExerciseOptionBoxes;
    private ScalingButton submitRoutine;

    public RoutineBuilderView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initUIElements();
        addElementsToContainers();
    }

    private void addElementsToContainers() {
        basicOptions.getChildren().addAll(createExercise, createRoutine);
        mainContainer.getChildren().addAll(basicOptions,advancedOptions);
        this.getChildren().add(mainContainer);
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        basicOptions = new ScalingHBox(getViewBindings());
        advancedOptions = new ScalingHBox(getViewBindings());
        createExerciseOptions = new ScalingVBox(getViewBindings());
        createRoutineOptions = new ScalingVBox(getViewBindings());
        routineBuilder = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        createExercise = new ScalingButton("Create New Exercise", buttonPack);
        createRoutine = new ScalingButton("Create New Routine", buttonPack);
        submit = new ScalingButton("Submit Exercise", buttonPack);
    }

    private void initAdvancedOptions() {
        initCreateExerciseOptions();
        initCreateRoutineOptions();
    }

    private void hideAdvancedOptions() {
        advancedOptions.getChildren().clear();
    }

    private void showCreateExerciseOptions() {
        hideAdvancedOptions();
        advancedOptions.getChildren().add(createExerciseOptions);
    }

    private void showCreateRoutineOptions() {
        hideAdvancedOptions();
        advancedOptions.getChildren().add(createRoutineOptions);
    }

    private void initCreateRoutineOptions() {
        routineName = new EditableLabel(getViewBindings());
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        addNewExercise = new ScalingButton("Add New Exercise", buttonPack);
        submitRoutine = new ScalingButton("Submit Routine", buttonPack);
        routineExercises = new ListView<>();
        routineExerciseOptionBoxes = FXCollections.observableArrayList();
        routineExercises.setItems(routineExerciseOptionBoxes);
        addNewExercise.setOnMouseClicked(e -> addNewExerciseToRoutine());
        submitRoutine.setOnMouseClicked(e -> saveRoutine());
        createRoutineOptions.getChildren().addAll(routineName, addNewExercise, routineExercises, submitRoutine);
        createRoutineOptions.setAlignment(Pos.CENTER);
    }

    private void saveRoutine() {
        SQLiteJDBC.getInstance().addToRoutines(routineName.getText(), routineExerciseOptionBoxes);
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.EXERCISES, ViewRequestKeys.LIST_UPDATE, exerciseName.getText()));
    }

    private void addNewExerciseToRoutine() {
        String exerciseName = getRequestSender().handleDataRequest(new ViewRequest(PaneKeys.EXERCISES, ViewRequestKeys.DATA_REQUEST));
        if(!exerciseName.isEmpty()) {
            ExerciseOptionsInput display = new ExerciseOptionsInput(exerciseName, getViewBindings());
            routineExerciseOptionBoxes.add(display);
        }
    }

    private void initCreateExerciseOptions() {
        ScalingHBox upperRow = new ScalingHBox(getViewBindings());
        exerciseName = new EditableLabel(getViewBindings());
        selectCategory = new ChoiceBox<>();
        selectCategory.getSelectionModel().select(ExerciseCategories.ANAEROBIC);
        describeExercise = new LabeledInputBox("Describe Exercise: ", getViewBindings());
        for(ExerciseCategories category : ExerciseCategories.values()) { selectCategory.getItems().add(category); }
        selectSubcategory = new ChoiceBox<>();
        selectSubcategory.getSelectionModel().select(ExerciseSubcategories.CALISTHENICS);
        for(ExerciseSubcategories category : ExerciseSubcategories.values()) { selectSubcategory.getItems().add(category); }
        upperRow.getChildren().addAll(exerciseName, selectCategory, selectSubcategory);
        createExerciseOptions.getChildren().addAll(upperRow, describeExercise, submit);
        upperRow.setAlignment(Pos.CENTER);
    }

    private void initUIElements() {
        initButtons();
        initAdvancedOptions();
        initButtonBehavior();
    }

    private void initButtonBehavior() {
        createExercise.setOnMouseClicked(e -> showCreateExerciseOptions());
        createRoutine.setOnMouseClicked(e -> showCreateRoutineOptions());
        submit.setOnMouseClicked(e -> saveExercise());
    }

    private void saveExercise() {
        SQLiteJDBC.getInstance().addToExercises(exerciseName.getText(), describeExercise.getInput(),
                selectCategory.getSelectionModel().getSelectedItem().toString(), selectSubcategory.getSelectionModel().getSelectedItem().toString());
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.EXERCISES, ViewRequestKeys.LIST_UPDATE, exerciseName.getText()));
    }
}
