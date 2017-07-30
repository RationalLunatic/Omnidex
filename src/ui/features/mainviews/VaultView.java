package ui.features.mainviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;



public class VaultView extends ScalingStackPane {
    private ScalingHBox topRow;
    private ScalingHBox bottomRow;
    private ScalingVBox mainContainer;
    private ScalingButton bibliographicArchive;
    private ScalingButton chronographicArchive;
    private ScalingButton lexicographicArchive;
    private ScalingButton mnemonicArchive;
    private ScalingButton citationArchive;
    private ViewRequestHandler commLink;
    private ViewBindingsPack viewBindings;

    public VaultView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initButtons();
        initButtonText();
        initButtonBehaviors();
        initDisplay();
    }

    private void initContainers() {
        topRow = new ScalingHBox(getViewBindings());
        bottomRow = new ScalingHBox(getViewBindings());
        mainContainer = new ScalingVBox(getViewBindings());
    }

    private void initButtons() {
        bibliographicArchive = new ScalingButton(getViewBindings());
        chronographicArchive = new ScalingButton(getViewBindings());
        lexicographicArchive = new ScalingButton(getViewBindings());
        mnemonicArchive = new ScalingButton(getViewBindings());
        citationArchive = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        bibliographicArchive.setText("Bibliographic Archive");
        chronographicArchive.setText("Chronographic Archive");
        lexicographicArchive.setText("Lexicographic Archive");
        mnemonicArchive.setText("Mnemonic Archive");
        citationArchive.setText("Citation Archive");
    }

    private void initButtonBehaviors() {
        // TODO: e -> SendViewRequest(PaneKeys.VIEWKEY)
        citationArchive.setOnMouseClicked(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.CITATION)));
    }

    private void initDisplay() {
        topRow.getChildren().addAll(bibliographicArchive, chronographicArchive);
        bottomRow.getChildren().addAll(mnemonicArchive, citationArchive, lexicographicArchive);
        mainContainer.getChildren().addAll(topRow, bottomRow);
        this.getChildren().add(mainContainer);
    }
}
