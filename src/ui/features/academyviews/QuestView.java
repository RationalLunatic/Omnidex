package ui.features.academyviews;

import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

public class QuestView extends ScalingVBox {
    private SimpleListTextDisplay questDisplay;
    public QuestView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        questDisplay = new SimpleListTextDisplay("Quests", getViewBindings());
        this.getChildren().add(questDisplay);
    }
}
