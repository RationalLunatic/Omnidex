package ui.features.gymnasiumviews;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import resources.datatypes.ExerciseRoutineRelation;
import resources.sqlite.SQLiteJDBC;
import sun.java2d.pipe.SpanShapeRenderer;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.List;

public class RoutineBuilderEast extends ScalingVBox {
    private SimpleListTextDisplay routineList;
    private ScalingVBox routineDisplayContainer;
    private SimpleListTextDisplay routineDisplay;

    public RoutineBuilderEast(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        routineList = new SimpleListTextDisplay("Routines", getViewBindings());
        routineDisplayContainer = new ScalingVBox(getViewBindings());
        routineList.getListView().getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                String selectedItem = (String) newValue;
                displaySelectedList(selectedItem);
            }
        });
        this.getChildren().add(routineList);
        this.getChildren().add(routineDisplayContainer);
        loadRoutines();
    }

    private void displaySelectedList(String selectedItem) {
        routineDisplayContainer.getChildren().clear();
        routineDisplay = new SimpleListTextDisplay(selectedItem, getViewBindings());
        List<ExerciseRoutineRelation> toDisplay = SQLiteJDBC.getInstance().getRoutineExercises(selectedItem);
        for(ExerciseRoutineRelation display : toDisplay) {
            routineDisplay.addLine(display.getExerciseName() + " Reps: " + display.getNumReps() + " Sets: " + display.getNumSets());
        }
        routineDisplayContainer.getChildren().add(routineDisplay);
    }

    private void loadRoutines() {
        for(String routine : SQLiteJDBC.getInstance().getRoutines()) {
            routineList.addLine(routine);
        }
    }
}
