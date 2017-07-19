package ui.components.interviewcommunications;

// This is how actions generated in the subviews are communicated to the parent pane.
// It allows the outer display to change views/panes based upon an action initiated in an interior pane.
// Essentially, when the user clicks on a link or a button in an inner view, it will send a request up the
// chain of ViewRequestHandlers, until it reaches this one.  At that point, the MainViewCommLink will change
// the display to the appropriate view.

import ui.components.mainpanes.CenterPanes;
import ui.components.PaneKeys;
import ui.components.mainpanes.NorthPanes;
import ui.components.scalingcomponents.CenterParentScalingStackPane;
import ui.components.scalingcomponents.ScalingHBox;
import ui.features.DayView;

public class MainViewCommLink extends ViewRequestHandler {

    private CenterPanes centerPanes;
    private CenterParentScalingStackPane center;
    private NorthPanes northPanes;
    private ScalingHBox north;

    public MainViewCommLink() {

    }

    public void addCenterPanes(CenterPanes centerPanes, CenterParentScalingStackPane center) {
        this.centerPanes = centerPanes;
        this.center = center;
    }

    public void addNorthPanes(NorthPanes northPanes, ScalingHBox north) {
        this.northPanes = northPanes;
        this.north = north;
    }

    public void init() {
        center.getChildren().clear();
        center.getChildren().add(centerPanes.getCenterPane(PaneKeys.BEACON));
        north.getChildren().clear();
        north.getChildren().add(northPanes.getNorth());
    }

    @Override
    public void handleRequest(ViewRequest request) {
        center.getChildren().clear();
        center.getChildren().add(centerPanes.getCenterPane(request.getTargetView()));
        if(request.getTargetView() == PaneKeys.DAY) {
            setDate(request);
        }
    }

    private void setDate(ViewRequest request) {
        DayView dayView = (DayView)centerPanes.getCenterPane(PaneKeys.DAY);
        dayView.setDate(request.getLocalDate());
    }
}
