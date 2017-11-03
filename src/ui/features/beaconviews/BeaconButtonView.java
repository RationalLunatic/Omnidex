package ui.features.beaconviews;

import resources.StringFormatUtility;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.AbstractButtonDisplay;

import java.time.LocalDate;

public class BeaconButtonView extends AbstractButtonDisplay {
    private enum BeaconButtons {
        OPEN_CALENDAR, OPEN_DAILY_PLANNER, OPEN_PATHFINDER
    }

    private ViewRequestHandler requestHandler;

    public BeaconButtonView(ViewBindingsPack viewBindings, ViewRequestHandler requestHandler) {
        super(viewBindings);
        this.requestHandler = requestHandler;
        init();
    }

    private void init() {
        createButtonRow("Schedule");
        for(BeaconButtons button : BeaconButtons.values()) {
            addButtonToRow(convertToString(button), "Schedule");
        }
        setButtonBehavior(convertToString(BeaconButtons.OPEN_CALENDAR), e -> requestHandler.handleRequest(new ViewRequest(PaneKeys.MONTH)));
        setButtonBehavior(convertToString(BeaconButtons.OPEN_DAILY_PLANNER), e -> requestHandler.handleRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
        setButtonBehavior(convertToString(BeaconButtons.OPEN_PATHFINDER), e -> pathfinderRequests());
    }

    private void pathfinderRequests() {
        requestHandler.handleRequest(new ViewRequest(PaneKeys.PATHFINDER));
        requestHandler.handleRequest(new ViewRequest(PaneKeys.HABITS_AND_DAILIES));
        requestHandler.handleRequest(new ViewRequest(PaneKeys.TASKS_AND_DEADLINES));
    }

    private String convertToString(BeaconButtons val) {
        return StringFormatUtility.capitalize(String.valueOf(val).replace("_", " "));
    }
}
