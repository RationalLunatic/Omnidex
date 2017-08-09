package ui.features.mainviews;

import javafx.geometry.Pos;
import resources.StringFormatUtility;
import ui.components.PaneKeys;
import ui.components.displaycomponents.DigitalClockDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;

public class BeaconView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingHBox dateDisplay;
    private ScalingHBox dailyAffirmation;
    private ScalingHBox agenda;
    private SimpleListTextDisplay agendaImmediate;
    private SimpleListTextDisplay agendaGoals;
    private SimpleListTextDisplay agendaHighlights;
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingButton monthLink;
    private ScalingButton todayLink;
    private ScalingButton pathfinderLink;
    private ScalingLabel dateLabel;
    private DigitalClockDisplay clockDisplay;

    public BeaconView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initButtons();
        initButtonText();
        initButtonBehavior();
        initUIDisplays();
        addUIElementsToContainers();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        topRow = new ScalingHBox(getViewBindings());
        bottomRow = new ScalingHBox(getViewBindings());
        agenda = new ScalingHBox(getViewBindings());
        agendaImmediate = new SimpleListTextDisplay("Current Agenda", getViewBindings());
        agendaHighlights = new SimpleListTextDisplay("Immediate Action", getViewBindings());
        agendaGoals = new SimpleListTextDisplay("Active Goals", getViewBindings());
        dailyAffirmation = new ScalingHBox(getViewBindings());
        dateDisplay = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeightBinding);
        pathfinderLink = new ScalingButton(buttonPack);
        monthLink = new ScalingButton(buttonPack);
        todayLink = new ScalingButton(buttonPack);
    }

    private void initButtonText() {
        pathfinderLink.setText("Open Pathfinder");
        monthLink.setText("Calendar");
        todayLink.setText("Daily Planner");
    }

    private void pathfinderRequests() {
        sendViewRequest(new ViewRequest(PaneKeys.PATHFINDER));
        sendViewRequest(new ViewRequest(PaneKeys.HABITS_AND_DAILIES));
        sendViewRequest(new ViewRequest(PaneKeys.TASKS_AND_DEADLINES));
    }

    private void initButtonBehavior() {
        pathfinderLink.setOnMouseClicked(e -> pathfinderRequests());
        monthLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.MONTH)));
        todayLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
    }

    private void addUIElementsToContainers() {
        topRow.getChildren().add(pathfinderLink);
        bottomRow.getChildren().add(monthLink);
        bottomRow.getChildren().add(todayLink);
        agenda.getChildren().addAll(agendaHighlights, agendaImmediate, agendaGoals);
        mainContainer.getChildren().addAll(dateDisplay, clockDisplay, dailyAffirmation, agenda, topRow, bottomRow);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(mainContainer);
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void initUIDisplays() {
        dateLabel = new ScalingLabel(getViewBindings().widthProperty(), StringFormatUtility.convertDate(LocalDate.now()), 0.8);
        clockDisplay = new DigitalClockDisplay(getViewBindings());
        dateDisplay.getChildren().addAll(dateLabel);
        dateDisplay.setAlignment(Pos.CENTER);
        ScalingLabel affirmationLabel = new ScalingLabel(getViewBindings().widthProperty(), "Only that day dawns to which we are awake. - Thoreau, Walden", 1.0);
        dailyAffirmation.getChildren().add(affirmationLabel);
    }

    private void initAgenda() {
        initAgendaHighlights();
        initAgendaImmediate();
        initAgendaGoals();
    }

    private void initAgendaHighlights() {

    }

    private void initAgendaImmediate() {

    }

    private void initAgendaGoals() {

    }
}
