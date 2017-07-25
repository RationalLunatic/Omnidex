package ui.components.mainpanes;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Pane;
import resources.StringFormatUtility;
import skeletonkey.Palace;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

public class NorthPanes extends PanePack {
    private ScalingVBox mainContainer;
    private ScalingHBox buttonBarContainer;
    private ScalingButton sanctuaryLink;
    private ScalingButton beaconLink;
    private ScalingButton academyLink;
    private ScalingButton vaultLink;
    private ScalingButton gymnasiumLink;

    public NorthPanes(Pane corePane, ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, commLink);
        mainContainer = new ScalingVBox(viewBindings);
        buttonBarContainer = new ScalingHBox(viewBindings);
        initButtons();
        mainContainer.getChildren().add(buttonBarContainer);
        corePane.getChildren().add(mainContainer);
    }

    private void initButtons() {
        instantiateButtons();
        setButtonBehavior();
        setButtonText();
        buttonBarContainer.getChildren().addAll(sanctuaryLink, beaconLink, academyLink, vaultLink, gymnasiumLink);
    }

    private void setButtonText() {
        sanctuaryLink.setText(StringFormatUtility.capitalize(Palace.SANCTUARY.toString()));
        beaconLink.setText(StringFormatUtility.capitalize(Palace.BEACON.toString()));
        academyLink.setText(StringFormatUtility.capitalize(Palace.ACADEMY.toString()));
        vaultLink.setText(StringFormatUtility.capitalize(Palace.VAULT.toString()));
        gymnasiumLink.setText(StringFormatUtility.capitalize(Palace.GYMNASIUM.toString()));
    }

    private void setButtonBehavior() {
        sanctuaryLink.setOnMouseClicked(e -> sendViewRequest(PaneKeys.SANCTUARY));
        beaconLink.setOnMouseClicked(e -> sendViewRequest(PaneKeys.BEACON));
        academyLink.setOnMouseClicked(e -> sendViewRequest(PaneKeys.ACADEMY));
        vaultLink.setOnMouseClicked(e -> sendViewRequest(PaneKeys.VAULT));
        gymnasiumLink.setOnMouseClicked(e -> sendViewRequest(PaneKeys.GYMNASIUM));
    }

    private void instantiateButtons() {
        sanctuaryLink = new ScalingButton(getViewBindings());
        beaconLink = new ScalingButton(getViewBindings());
        academyLink = new ScalingButton(getViewBindings());
        vaultLink = new ScalingButton(getViewBindings());
        gymnasiumLink = new ScalingButton(getViewBindings());
    }

    private void sendViewRequest(PaneKeys key) {
        getRequestHandler().handleRequest(new ViewRequest(key));
    }

    public ScalingVBox getNorth() {
        return mainContainer;
    }

    @Override
    public void switchPane(PaneKeys key) {
        // TODO: Add more panes to NorthPanes
    }

    @Override
    public Pane getPane(PaneKeys key) {
        return getCorePane();
    }
}
