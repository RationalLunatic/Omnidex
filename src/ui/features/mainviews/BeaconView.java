package ui.features.mainviews;

import engine.components.schedule.Daily;
import javafx.geometry.Pos;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.displaycomponents.DigitalClockDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BeaconView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingHBox dateDisplay;
    private ScalingHBox dailyAffirmation;
    private ScalingHBox agenda;
    private SimpleListTextDisplay agendaImmediate;
    private SimpleListTextDisplay agendaGoals;
    private SimpleListTextDisplay agendaHighlights;
    private ScalingLabel dateLabel;
    private DigitalClockDisplay clockDisplay;

    public BeaconView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initUIDisplays();
        addUIElementsToContainers();
        initAgenda();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        agenda = new ScalingHBox(getViewBindings());
        agendaImmediate = new SimpleListTextDisplay("Current Agenda", getViewBindings());
        agendaHighlights = new SimpleListTextDisplay("Immediate Action", getViewBindings());
        agendaGoals = new SimpleListTextDisplay("Active Goals", getViewBindings());
        dailyAffirmation = new ScalingHBox(getViewBindings());
        dateDisplay = new ScalingHBox(getViewBindings());
    }

    private void addUIElementsToContainers() {
        agenda.getChildren().addAll(agendaHighlights, agendaImmediate, agendaGoals);
        mainContainer.getChildren().addAll(dateDisplay, clockDisplay, dailyAffirmation, agenda);
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

    private List<Daily> sortDailies(List<Daily> dailies) {
        Comparator<Daily> dailyComparator = (e1, e2) ->
                (e1.getScheduledTime().compareTo(e2.getScheduledTime()));
        return dailies.stream().sorted(dailyComparator).collect(Collectors.toList());
    }

    private void initAgendaHighlights() {
        List<Daily> allDailies = sortDailies(SQLiteJDBC.getInstance().getPathfinderIO().getDailies());
        for(Daily daily : allDailies) {
            if(LocalTime.now().isAfter(daily.getScheduledTime()) && LocalTime.now().isBefore(daily.getScheduledTime().plusMinutes(daily.getDuration()))) {
                agendaHighlights.addLine(daily.getTitle());
            }
        }
    }

    private void initAgendaImmediate() {
        List<Daily> allDailies = sortDailies(SQLiteJDBC.getInstance().getPathfinderIO().getDailies());
        for(Daily daily : allDailies) {
            if(daily.getScheduledTime().isAfter(LocalTime.now())) {
                agendaImmediate.addLine(daily.getTitle());
            }
        }
    }

    private void initAgendaGoals() {

    }
}
