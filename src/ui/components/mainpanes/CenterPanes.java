package ui.components.mainpanes;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.mainviews.*;
import ui.features.DayView;
import ui.features.MonthView;

import java.util.HashMap;
import java.util.Map;

public class CenterPanes {
    private SanctuaryView sanctuaryView;
    private BeaconView beaconView;
    private AcademyView academyView;
    private VaultView vaultView;
    private GymnasiumView gymnasiumView;
    private MonthView calendarMonthDisplay;
    private DayView  calendarDayDisplay;
    private Map<PaneKeys, ScalingStackPane> centerPanes;
    private ViewRequestHandler commLink;
    private ViewBindingsPack viewBindings;

    public CenterPanes(ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        centerPanes = new HashMap<>();
        this.commLink = commLink;
        this.viewBindings = viewBindings;
        initPanes();
        mapPanes();
    }

    private void initPanes() {
        calendarMonthDisplay = new MonthView(commLink, viewBindings);
        calendarDayDisplay = new DayView(commLink, viewBindings);
        beaconView = new BeaconView(commLink, viewBindings, PaneKeys.BEACON);
        sanctuaryView = new SanctuaryView(commLink, viewBindings, PaneKeys.SANCTUARY);
        academyView = new AcademyView(commLink, viewBindings, PaneKeys.ACADEMY);
        vaultView = new VaultView(commLink, viewBindings, PaneKeys.VAULT);
        gymnasiumView = new GymnasiumView(commLink, viewBindings, PaneKeys.GYMNASIUM);
    }

    private void mapPanes() {
        centerPanes.put(PaneKeys.DAY, calendarDayDisplay);
        centerPanes.put(PaneKeys.MONTH, calendarMonthDisplay);
        centerPanes.put(PaneKeys.BEACON, beaconView);
        centerPanes.put(PaneKeys.SANCTUARY, sanctuaryView);
        centerPanes.put(PaneKeys.ACADEMY, academyView);
        centerPanes.put(PaneKeys.VAULT, vaultView);
        centerPanes.put(PaneKeys.GYMNASIUM, gymnasiumView);
    }

    public ScalingStackPane getCenterPane(PaneKeys key) {
        return centerPanes.get(key);
    }
}
