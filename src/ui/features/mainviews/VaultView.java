package ui.features.mainviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.features.AbstractCenterDisplay;


public class VaultView extends AbstractCenterDisplay {

    public VaultView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initUIElements();
        initUIBehaviors();
    }

    private void initUIElements() {
        setTitle("The Vault");
        createButtonBar("Historical Archives");
        createButtonBar("Literary Archives");
        addButtonToButtonBar("The Chronograph", "Historical Archives");
        addButtonToButtonBar("The Archaeologist", "Historical Archives");
        addButtonToButtonBar("The Bibliographic Archive", "Literary Archives");
        addButtonToButtonBar("The Citation Archive", "Literary Archives");
        addButtonToButtonBar("The Lexicographic Archive", "Literary Archives");
    }

    private void initUIBehaviors() {
        setButtonOnClick("The Bibliographic Archive", e -> sendViewRequest(new ViewRequest(PaneKeys.BIBLIOGRAPHY)));
        setButtonOnClick("The Citation Archive", e -> sendViewRequest(new ViewRequest(PaneKeys.CITATION)));
        setButtonOnClick("The Lexicographic Archive", e -> sendViewRequest(new ViewRequest(PaneKeys.VOCABULARY)));
    }
}
