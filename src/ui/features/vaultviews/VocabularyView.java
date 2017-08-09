package ui.features.vaultviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.AbstractCenterDisplay;

public class VocabularyView extends AbstractCenterDisplay {
    public VocabularyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        setTitle("Vocabulary");
    }
}
