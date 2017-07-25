package ui.components.mainpanes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.beaconviews.*;
import ui.features.mainviews.*;

import java.util.HashMap;
import java.util.Map;

public class CenterPanes extends PanePack {
    private SanctuaryView sanctuaryView;
    private BeaconView beaconView;
    private AcademyView academyView;
    private VaultView vaultView;
    private GymnasiumView gymnasiumView;
    private MonthView calendarMonthDisplay;
    private DayView  calendarDayDisplay;
    private GoalView goalView;
    private LiteratureView literatureView;
    private GoalCreatorView goalCreatorView;
    private Map<PaneKeys, Node> centerPanes;

    public CenterPanes(Pane corePane, ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, commLink);
        centerPanes = new HashMap<>();
        initPanes();
        mapPanes();
        corePane.getChildren().add(beaconView);
    }

    private void initPanes() {
        calendarMonthDisplay = new MonthView(getRequestHandler(), getViewBindings());
        calendarDayDisplay = new DayView(getRequestHandler(), getViewBindings());
        beaconView = new BeaconView(getRequestHandler(), getViewBindings(), PaneKeys.BEACON);
        sanctuaryView = new SanctuaryView(getRequestHandler(), getViewBindings(), PaneKeys.SANCTUARY);
        academyView = new AcademyView(getRequestHandler(), getViewBindings(), PaneKeys.ACADEMY);
        vaultView = new VaultView(getRequestHandler(), getViewBindings(), PaneKeys.VAULT);
        gymnasiumView = new GymnasiumView(getRequestHandler(), getViewBindings(), PaneKeys.GYMNASIUM);
        goalView = new GoalView(getRequestHandler(), getViewBindings(), PaneKeys.GOALS);
        literatureView = new LiteratureView(getRequestHandler(), getViewBindings(), PaneKeys.LITERATURE);
        goalCreatorView = new GoalCreatorView(getRequestHandler(), getViewBindings(), PaneKeys.GOAL_CREATOR);
    }

    private void mapPanes() {
        centerPanes.put(PaneKeys.DAY, calendarDayDisplay);
        centerPanes.put(PaneKeys.MONTH, calendarMonthDisplay);
        centerPanes.put(PaneKeys.BEACON, beaconView);
        centerPanes.put(PaneKeys.SANCTUARY, sanctuaryView);
        centerPanes.put(PaneKeys.ACADEMY, academyView);
        centerPanes.put(PaneKeys.VAULT, vaultView);
        centerPanes.put(PaneKeys.GYMNASIUM, gymnasiumView);
        centerPanes.put(PaneKeys.GOALS, goalView);
        centerPanes.put(PaneKeys.LITERATURE, literatureView);
        centerPanes.put(PaneKeys.GOAL_CREATOR, goalCreatorView);
    }

    public void switchPane(PaneKeys key) {
        getCorePane().getChildren().clear();
        getCorePane().getChildren().add(centerPanes.get(key));
    }

    public Node getPane(PaneKeys key) {
        return centerPanes.get(key);
    }
}
