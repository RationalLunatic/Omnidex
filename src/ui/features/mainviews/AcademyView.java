package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.features.AbstractCenterDisplay;

import java.time.LocalDate;


public class AcademyView extends AbstractCenterDisplay {


    public AcademyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initUIElements();
        initUIBehavior();
    }

    private void initUIElements() {
        setTitle("The Academy");
        createButtonBar("Character Class Paradigm Manager");
        createButtonBar("Library of Alexandria");
        addButtonToButtonBar("Open Skill Manager", "Character Class Paradigm Manager");
        addButtonToButtonBar("Open Language Learner", "Library of Alexandria");
    }

    private void initUIBehavior() {
        setButtonOnClick("Open Skill Manager", e -> openSkillManager());
        setButtonOnClick("Open Language Learner", e -> openLanguageLearner());
    }

    private void openSkillManager() {
        sendViewRequest(new ViewRequest(PaneKeys.SKILL));
        sendViewRequest(new ViewRequest(PaneKeys.QUEST));
        sendViewRequest(new ViewRequest(PaneKeys.TRAINING));
    }

    private void openLanguageLearner() {
        sendViewRequest(new ViewRequest(PaneKeys.LANGUAGE_LEARNER));
    }
}
