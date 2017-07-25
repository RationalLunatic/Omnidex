package ui.features.mainviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

public class GymnasiumView extends ScalingStackPane {
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingVBox mainContainer;
    private ScalingButton trainingDesigner;
    private ScalingButton praxisScheduler;
    private ScalingButton dojo;
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
        dojo = new ScalingButton(getViewBindings());
        temple = new ScalingButton(getViewBindings());
        study = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        trainingDesigner.setText("Training Designer");
        praxisScheduler.setText("Praxis Scheduler");
        dojo.setText("The Dojo");
        temple.setText("The Temple");
        study.setText("The Study");
    }

    private void initButtonBehaviors() {
        // TODO: e -> SendViewRequest(PaneKeys.VIEWKEY)
    }

    private void initDisplay() {
        topRow.getChildren().addAll(trainingDesigner, praxisScheduler);
        bottomRow.getChildren().addAll(dojo, temple, study);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}
