package ui.features.mainviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

public class GymnasiumView extends ScalingStackPane {
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingVBox mainContainer;
    private ScalingButton trainingDesigner;
    private ScalingButton praxisScheduler;
    private ScalingButton gym;
    private ScalingButton temple;
    private ScalingButton study;
    private ViewRequestHandler commLink;
    private ViewBindingsPack viewBindings;

    public GymnasiumView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initButtons();
        initButtonText();
        initButtonBehaviors();
        initDisplay();
    }

    private void initContainers() {
        topRow = new ScalingHBox(getViewBindings());
        bottomRow = new ScalingHBox(getViewBindings());
        mainContainer = new ScalingVBox(getViewBindings());
    }

    private void initButtons() {
        trainingDesigner = new ScalingButton(getViewBindings());
        praxisScheduler = new ScalingButton(getViewBindings());
        gym = new ScalingButton(getViewBindings());
        temple = new ScalingButton(getViewBindings());
        study = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        trainingDesigner.setText("Routine Builder");
        praxisScheduler.setText("Praxis Scheduler");
        gym.setText("The Gym");
        temple.setText("The Temple");
        study.setText("The Study");
    }

    private void initButtonBehaviors() {
        trainingDesigner.setOnMouseClicked(e -> openRoutineBuilder() );
    }

    private void openRoutineBuilder() {
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.EXERCISE_ROUTINE_BUILDER));
        getRequestSender().handleRequest(new ViewRequest(PaneKeys.EXERCISES));
    }

    private void initDisplay() {
        topRow.getChildren().addAll(trainingDesigner, praxisScheduler);
        bottomRow.getChildren().addAll(gym, temple, study);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}
