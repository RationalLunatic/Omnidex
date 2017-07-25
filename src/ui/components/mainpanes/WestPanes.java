package ui.components.mainpanes;

import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.beaconviews.GenreView;

import java.util.HashMap;
import java.util.Map;

public class WestPanes extends PanePack {
    private GenreView genreView;
    private Map<PaneKeys, ScalingVBox> westViewMap;

    public WestPanes(Pane corePane, ViewRequestHandler requestHandler, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, requestHandler);
        init();
        corePane.getChildren().add(genreView);
    }

    private void init() {
        genreView = new GenreView(getViewBindings());
        westViewMap = new HashMap<>();
        westViewMap.put(PaneKeys.GENRES, genreView);
    }

    public ScalingVBox getWestPane(PaneKeys key) {
        return westViewMap.get(key);
    }

    @Override
    public void switchPane(PaneKeys key) {
        getCorePane().getChildren().clear();
        getCorePane().getChildren().add(westViewMap.get(key));
    }

    @Override
    public Pane getPane(PaneKeys key) {
        return null;
    }
}
