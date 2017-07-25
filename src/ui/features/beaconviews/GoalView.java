package ui.features.beaconviews;


import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.time.LocalDate;


public class GoalView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingButton readingList;
    private ScalingButton goalCreator;
    private ScalingButton skillList;

    public GoalView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initButtons();
        initButtonText();
        initButtonBehavior();
        addUIElementsToContainers();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        topRow = new ScalingHBox(getViewBindings());
        bottomRow = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        goalCreator = new ScalingButton(getViewBindings());
        readingList = new ScalingButton(getViewBindings());
        skillList = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        goalCreator.setText("Create New Goals");
        readingList.setText("Books to Read");
        skillList.setText("Skills to Practice");
    }

    private void initButtonBehavior() {
        goalCreator.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.GOAL_CREATOR)));
        readingList.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.LITERATURE)));
        skillList.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
    }

    private void addUIElementsToContainers() {
        topRow.getChildren().add(goalCreator);
        bottomRow.getChildren().add(readingList);
        bottomRow.getChildren().add(skillList);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}

