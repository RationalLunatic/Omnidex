package main;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import resources.sqlite.SQLiteJDBC;
import ui.components.interviewcommunications.MainViewCommLink;
import ui.components.mainpanes.*;
import ui.components.scalingcomponents.CenterParentScalingStackPane;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.DirectDoubleBinding;
import ui.custombindings.ScaledDoubleBinding;
import resources.ResourceManager;

import java.time.LocalDateTime;

public class MainView extends BorderPane {
    private ScalingVBox west;
    private ScalingVBox east;
    private ScalingHBox north;
    private ScalingHBox south;
    private CenterParentScalingStackPane center;
    private ResourceManager bundleLoader;
    private CenterPanes centerPanes;
    private NorthPanes northPanes;
    private WestPanes westPanes;
    private EastPanes eastPanes;
    private SouthPanes southPanes;
    private boolean debugMode;
    private MainViewCommLink commLink;
    private DirectDoubleBinding width;
    private DirectDoubleBinding height;

    public MainView(DirectDoubleBinding width, DirectDoubleBinding height) {
        this.width = width;
        this.height = height;
        init();
    }

    private void init() {
        loadResources();
        initScalingPanes();
        commLink = new MainViewCommLink();
        initCenterPanes();
        initNorthPanes();
        initWestPanes();
        initEastPanes();
        initSouthPanes();
        setScalingPanes();
        commLink.addCenterPanes(centerPanes, center);
        commLink.addNorthPanes(northPanes, north);
        commLink.addWestPanes(westPanes, west);
        commLink.addEastPanes(eastPanes, east);
        commLink.addSouthPanes(southPanes, south);
        debugMode = false;
        toggleBorders();
        initBehaviors();
    }

    private void initCenterPanes() {
        ScaledDoubleBinding widthBinding = new ScaledDoubleBinding(width, bundleLoader.centerPaneScalingFactor());
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(height, 1);
        ViewBindingsPack centerPanesBindingPack = new ViewBindingsPack(widthBinding, heightBinding);
        center = new CenterParentScalingStackPane(centerPanesBindingPack);
        centerPanes = new CenterPanes(center, commLink, centerPanesBindingPack);
    }

    private void initNorthPanes() {
        ScaledDoubleBinding widthBinding = new ScaledDoubleBinding(width, 1);
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(height, 0.125);
        ViewBindingsPack northPanesBindingPack = new ViewBindingsPack(widthBinding, heightBinding);
        northPanes = new NorthPanes(north, commLink, northPanesBindingPack);
    }

    private void initWestPanes() {
        ScaledDoubleBinding widthBinding = new ScaledDoubleBinding(width, 0.125);
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(height, 1);
        ViewBindingsPack westPanesBindingPack = new ViewBindingsPack(widthBinding, heightBinding);
        westPanes = new WestPanes(west, commLink, westPanesBindingPack);
    }

    private void initEastPanes() {
        ScaledDoubleBinding widthBinding = new ScaledDoubleBinding(width, 0.125);
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(height, 1);
        ViewBindingsPack eastPanesBindingPack = new ViewBindingsPack(widthBinding, heightBinding);
        eastPanes = new EastPanes(east, commLink, eastPanesBindingPack);
    }

    private void initSouthPanes() {
        ScaledDoubleBinding widthBinding = new ScaledDoubleBinding(width, 1);
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(height, 0.125);
        ViewBindingsPack southPanesBindingPack = new ViewBindingsPack(widthBinding, heightBinding);
        southPanes = new SouthPanes(south, commLink, southPanesBindingPack);
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

    private void initScalingPanes() {
        ViewBindingsPack westViewBindings = new ViewBindingsPack(new ScaledDoubleBinding(width, bundleLoader.westPaneScalingFactor()), height);
        ViewBindingsPack eastViewBindings = new ViewBindingsPack(new ScaledDoubleBinding(width, bundleLoader.eastPaneScalingFactor()), height);
        ViewBindingsPack northViewBindings = new ViewBindingsPack(width, new ScaledDoubleBinding(height, bundleLoader.northPaneScalingFactor()));
        ViewBindingsPack southViewBindings = new ViewBindingsPack(width, new ScaledDoubleBinding(height, bundleLoader.southPaneScalingFactor()));
        west = new ScalingVBox(westViewBindings);
        east = new ScalingVBox(eastViewBindings);
        north = new ScalingHBox(northViewBindings);
        south = new ScalingHBox(southViewBindings);
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

    private void initBehaviors() {
        //this.setOnMouseMoved(e -> toggleEdgeDisplays(e));
    }

    private void toggleEdgeDisplays(MouseEvent e) {
        if (e.getScreenY() < this.getHeight()/8) {
            this.setTop(north);
        } else if (e.getScreenY() > this.getHeight() * 7 / 8) {
            this.setBottom(south);
        } else if (e.getScreenX() > this.getWidth() * 7 / 8) {
            this.setRight(east);
        } else if (e.getScreenX() < this.getWidth() / 8) {
            this.setLeft(west);
        } else {
            this.setTop(null);
            this.setBottom(null);
            this.setRight(null);
            this.setLeft(null);
        }
    }

}
