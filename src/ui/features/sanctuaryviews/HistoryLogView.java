package ui.features.sanctuaryviews;

import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.AbstractCenterDisplay;

public class HistoryLogView extends AbstractCenterDisplay {
    private SimpleListTextDisplay historyLog;

    public HistoryLogView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        setTitle("Player History");
    }
}
