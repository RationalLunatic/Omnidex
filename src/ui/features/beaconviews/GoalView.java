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
    private ScalingButton scratchList;

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
        scratchList = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        goalCreator.setText("Create New Goals");
        readingList.setText("Books to Read");
        scratchList.setText("Scratch List");
    }

    private void initButtonBehavior() {
        goalCreator.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.GOAL_CREATOR)));
        readingList.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.LITERATURE)));
        scratchList.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.SCRATCH)));
    }

    private void addUIElementsToContainers() {
        topRow.getChildren().add(goalCreator);
        bottomRow.getChildren().add(readingList);
        bottomRow.getChildren().add(scratchList);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}

