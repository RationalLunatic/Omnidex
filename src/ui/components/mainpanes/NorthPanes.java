package ui.components.mainpanes;

import javafx.beans.binding.DoubleBinding;
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

public class NorthPanes {
    private ScalingVBox mainContainer;
    private ScalingHBox buttonBarContainer;
    private ScalingButton sanctuaryLink;
    private ScalingButton beaconLink;
    private ScalingButton academyLink;
    private ScalingButton vaultLink;
    private ScalingButton gymnasiumLink;
    private ViewRequestHandler commLink;
    private ViewBindingsPack viewBindings;

    public NorthPanes(ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        this.viewBindings = viewBindings;
        this.commLink = commLink;
        mainContainer = new ScalingVBox(viewBindings.widthProperty());
        buttonBarContainer = new ScalingHBox(viewBindings.heightProperty());
        initButtons();
        mainContainer.getChildren().add(buttonBarContainer);
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
        sanctuaryLink = new ScalingButton(viewBindings);
        beaconLink = new ScalingButton(viewBindings);
        academyLink = new ScalingButton(viewBindings);
        vaultLink = new ScalingButton(viewBindings);
        gymnasiumLink = new ScalingButton(viewBindings);
    }

    private void sendViewRequest(PaneKeys key) {
        commLink.handleRequest(new ViewRequest(key));
    }

    public ScalingVBox getNorth() {
        return mainContainer;
    }
}
