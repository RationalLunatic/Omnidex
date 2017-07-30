package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.time.LocalDate;

public class BeaconView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingHBox dailyAffirmation;
    private ScalingHBox agenda;
    private ScalingVBox agendaImmediate;
    private ScalingVBox agendaGoals;
    private ScalingVBox agendaHighlights;
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingButton monthLink;
    private ScalingButton todayLink;
    private ScalingButton goalLink;

    public BeaconView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
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
        agenda = new ScalingHBox(getViewBindings());
        agendaImmediate = new ScalingVBox(getViewBindings());
        agendaHighlights = new ScalingVBox(getViewBindings());
        agendaGoals = new ScalingVBox(getViewBindings());
        dailyAffirmation = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        goalLink = new ScalingButton(getViewBindings());
        monthLink = new ScalingButton(getViewBindings());
        todayLink = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        goalLink.setText("View Goals");
        monthLink.setText("This Month");
        todayLink.setText("Today");
    }

    private void initButtonBehavior() {
        goalLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.GOALS)));
        monthLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.MONTH)));
        todayLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
    }

    private void addUIElementsToContainers() {
        topRow.getChildren().add(goalLink);
        bottomRow.getChildren().add(monthLink);
        bottomRow.getChildren().add(todayLink);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}
