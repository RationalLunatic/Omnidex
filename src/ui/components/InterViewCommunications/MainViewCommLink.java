package ui.components.interviewcommunications;

// This is how actions generated in the subviews are communicated to the parent pane.
// It allows the outer display to change views/panes based upon an action initiated in an interior pane.
// Essentially, when the user clicks on a link or a button in an inner view, it will send a request up the
// chain of ViewRequestHandlers, until it reaches this one.  At that point, the MainViewCommLink will change
// the display to the appropriate view.

import ui.components.mainpanes.CenterPanes;
import ui.components.PaneKeys;
import ui.components.mainpanes.NorthPanes;
import ui.components.mainpanes.PanePack;
import ui.components.mainpanes.WestPanes;
import ui.components.scalingcomponents.CenterParentScalingStackPane;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.features.beaconviews.DayView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MainViewCommLink extends ViewRequestHandler {
    private CenterPanes centerPanes;
    private CenterParentScalingStackPane center;
    private NorthPanes northPanes;
    private ScalingHBox north;
    private WestPanes westPanes;
    private ScalingVBox west;
    private Map<PaneKeys.PaneLocation, PanePack> actionToLocationRouter;

    public MainViewCommLink() {
        init();
    }

    public void addCenterPanes(CenterPanes centerPanes, CenterParentScalingStackPane center) {
        this.centerPanes = centerPanes;
        this.center = center;
        actionToLocationRouter.put(PaneKeys.PaneLocation.CENTER, centerPanes);
    }

    public void addNorthPanes(NorthPanes northPanes, ScalingHBox north) {
        this.northPanes = northPanes;
        this.north = north;
        actionToLocationRouter.put(PaneKeys.PaneLocation.NORTH, northPanes);
    }

    public void addWestPanes(WestPanes westPanes, ScalingVBox west) {
        this.westPanes = westPanes;
        this.west = west;
        actionToLocationRouter.put(PaneKeys.PaneLocation.WEST, westPanes);
    }


    private void init() {
        actionToLocationRouter = new HashMap<>();
    }

    @Override
    public void handleRequest(ViewRequest request) {
        actionToLocationRouter.get(request.getTargetView().getLocation()).switchPane(request.getTargetView());
        if(request.getTargetView() == PaneKeys.DAY) {
            setDate(request);
        }

    }

    private void setDate(ViewRequest request) {
        DayView dayView = (DayView)centerPanes.getPane(PaneKeys.DAY);
        dayView.setDate(request.getLocalDate());
    }
}
