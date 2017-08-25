package ui.features.academyviews;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import resources.datatypes.lexicographicdata.spanish.*;
import ui.components.PaneKeys;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.AbstractCenterDisplay;

public class LanguageLearnerView extends AbstractCenterDisplay {
    private LabeledInputBox enterVerb;
    private ChoiceBox<SpanishVerbTenses> selectTense;
    private ChoiceBox<SpanishVerbPersons> selectPerson;
    private ChoiceBox<SpanishVerbNumber> selectNumber;
    private ScalingLabel verbDisplay;
    private ScalingButton conjugate;

    public LanguageLearnerView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initUIElements();
        addElementsToContainers();
    }

    private void addElementsToContainers() {
        getMainContainer().getChildren().addAll(conjugate, selectTense, selectPerson, selectNumber, verbDisplay, enterVerb);
    }

    private void initUIElements() {
        getMainContainer().setAlignment(Pos.TOP_CENTER);
        setTitle("Spanish Language Learner");
        enterVerb = new LabeledInputBox("Enter Verb: ", getViewBindings());
        verbDisplay = new ScalingLabel(getViewBindings().widthProperty(), "Enter Verb", 1.0);
        conjugate = new ScalingButton(getViewBindings(), 0.25, 0.1);
        conjugate.setText("Conjugate!");
        initChoiceBoxes();
        conjugate.setOnMouseClicked(e -> conjugateVerb());
    }

    private void conjugateVerb() {
        if(enterVerb.getInput() != null && !enterVerb.getInput().isEmpty()) {
            if(selectTense.getValue() != null) {
                if(selectNumber.getValue() != null) {
                    if(selectPerson.getValue() != null) {
                        SpanishVerbEndings ending;
                        String word = enterVerb.getInput();
                        switch(word.substring(word.length()-2)) {
                            case "ir": ending = SpanishVerbEndings.IR; break;
                            case "er": ending = SpanishVerbEndings.ER; break;
                            case "ar": ending = SpanishVerbEndings.AR; break;
                            default : ending = SpanishVerbEndings.AR;
                        }
                        SpanishVerb verb = new SpanishVerb(enterVerb.getInput(), "");
                        verbDisplay.setText(verb.getStem() + selectTense.getValue().conjugate(selectPerson.getValue(), selectNumber.getValue(), ending));
                    }
                }
            }
        }
    }

    private void initChoiceBoxes() {
        selectTense = new ChoiceBox<>();
        selectPerson = new ChoiceBox<>();
        selectNumber = new ChoiceBox<>();
        for(SpanishVerbTenses tense : SpanishVerbTenses.values()) {
            selectTense.getItems().add(tense);
        }
        for(SpanishVerbNumber number : SpanishVerbNumber.values()) {
            selectNumber.getItems().add(number);
        }
        for(SpanishVerbPersons person : SpanishVerbPersons.values()) {
            selectPerson.getItems().add(person);
        }
    }
}
