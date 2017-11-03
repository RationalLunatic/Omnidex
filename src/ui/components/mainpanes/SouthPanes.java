package ui.components.mainpanes;

import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.beaconviews.BeaconButtonView;

import java.util.HashMap;
import java.util.Map;

public class SouthPanes extends PanePack {
    private BeaconButtonView beaconButtonView;
    private Map<PaneKeys, ScalingVBox> southViewMap;

    public SouthPanes(Pane corePane, ViewRequestHandler requestHandler, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, requestHandler);
        init();
        corePane.getChildren().add(beaconButtonView);
    }

    private void init() {
        beaconButtonView = new BeaconButtonView(getViewBindings(), getRequestHandler());
        southViewMap = new HashMap<>();
        southViewMap.put(PaneKeys.BEACON_BUTTONS, beaconButtonView);
    }

    public ScalingVBox getSouthPane(PaneKeys key) {
        return southViewMap.get(key);
    }

    @Override
    public void switchPane(PaneKeys key) {
        getCorePane().getChildren().clear();
        getCorePane().getChildren().add(southViewMap.get(key));
    }

    @Override
    public Pane getPane(PaneKeys key) {
        return southViewMap.get(key);
    }
}
