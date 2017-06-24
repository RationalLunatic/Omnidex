package main;

import javafx.scene.layout.BorderPane;
import ui.custombindings.DirectDoubleBinding;
import ui.custombindings.ScaledDoubleBinding;
import ui.components.CenterPanes;
import ui.components.ScalingHBox;
import ui.components.ScalingStackPane;
import ui.components.ScalingVBox;
import resources.ResourceManager;

/**
 * Created by shaev_000 on 7/27/2016.
 */
public class MainView extends BorderPane {
    private ScalingVBox west;
    private ScalingVBox east;
    private ScalingHBox north;
    private ScalingHBox south;
    private ScalingStackPane center;
    private ResourceManager bundleLoader;
    private CenterPanes centerPanes;
    private boolean debugMode;

    public MainView(DirectDoubleBinding width, DirectDoubleBinding height) {
       init(width, height);
    }

    private void init(DirectDoubleBinding width, DirectDoubleBinding height) {
        loadResources();
        initScalingPanes(width, height);
        setScalingPanes();
        centerPanes = new CenterPanes(new ScaledDoubleBinding(width, bundleLoader.centerPaneScalingFactor()),
                new ScaledDoubleBinding(height, bundleLoader.centerPaneScalingFactor()));
        center.getChildren().add(centerPanes.getCalendar());
        debugMode = false;
        toggleBorders();
    }

    private void loadResources() {
        bundleLoader = new ResourceManager();
        this.getStylesheets().add(bundleLoader.loadDebug());
    }

    private void setScalingPanes() {
        this.setCenter(center);
        this.setLeft(west);
        this.setRight(east);
        this.setTop(north);
        this.setBottom(south);
    }

    private void initScalingPanes(DirectDoubleBinding width, DirectDoubleBinding height) {
        west = new ScalingVBox(new ScaledDoubleBinding(width, bundleLoader.westPaneScalingFactor()));
        east = new ScalingVBox(new ScaledDoubleBinding(width, bundleLoader.eastPaneScalingFactor()));
        north = new ScalingHBox(new ScaledDoubleBinding(height, bundleLoader.northPaneScalingFactor()));
        south = new ScalingHBox(new ScaledDoubleBinding(height, bundleLoader.southPaneScalingFactor()));
        center = new ScalingStackPane(new ScaledDoubleBinding(width, bundleLoader.centerPaneScalingFactor()),
                new ScaledDoubleBinding(height, bundleLoader.centerPaneScalingFactor()));
    }

    private void showBorders() {
        center.getStyleClass().add(bundleLoader.loadBorders());
        east.getStyleClass().add(bundleLoader.loadBorders());
        west.getStyleClass().add(bundleLoader.loadBorders());
        north.getStyleClass().add(bundleLoader.loadBorders());
        south.getStyleClass().add(bundleLoader.loadBorders());
        debugMode = true;
    }

    private void hideBorders() {
        center.getStyleClass().removeAll(bundleLoader.loadBorders());
        east.getStyleClass().removeAll(bundleLoader.loadBorders());
        west.getStyleClass().removeAll(bundleLoader.loadBorders());
        north.getStyleClass().removeAll(bundleLoader.loadBorders());
        south.getStyleClass().removeAll(bundleLoader.loadBorders());
        debugMode = false;
    }

    public void toggleBorders() {
        if(!debugMode) {
            showBorders();
        } else {
            hideBorders();
        }
    }

}
