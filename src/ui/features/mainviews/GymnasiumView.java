package ui.features.mainviews;

import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.features.AbstractCenterDisplay;

public class GymnasiumView extends AbstractCenterDisplay {
    public GymnasiumView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initUIElements();
        initBehaviors();
    }

    private void initUIElements() {
        setTitle("Gymnasium");
        createButtonBar("The Quartermaster");
        createButtonBar("The Mentor");
        addButtonToButtonBar("Exercise Routine Designer", "The Quartermaster");
        addButtonToButtonBar("Word Fall Challenge", "The Mentor");
    }

    private void initBehaviors() {
        setButtonOnClick("Word Fall Challenge", e -> sendViewRequest(new ViewRequest(PaneKeys.WORDFALL)));
        setButtonOnClick("Exercise Routine Designer", e -> openRoutineDesigner());
    }

    private void openRoutineDesigner() {
        sendViewRequest(new ViewRequest(PaneKeys.EXERCISES));
        sendViewRequest(new ViewRequest(PaneKeys.EXERCISE_ROUTINE_BUILDER));
        sendViewRequest(new ViewRequest(PaneKeys.ROUTINES));
    }
}
