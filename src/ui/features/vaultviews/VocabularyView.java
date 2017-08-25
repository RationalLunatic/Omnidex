package ui.features.vaultviews;

import javafx.scene.control.ChoiceBox;
import resources.StringFormatUtility;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.datatypes.lexicographicdata.spanish.SpanishPartsOfSpeech;
import resources.sqlite.sqlenumerations.Languages;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.AbstractCenterDisplay;

import java.util.ArrayList;
import java.util.Arrays;

public class VocabularyView extends AbstractCenterDisplay {
    private ChoiceBox<Languages> languageChoices;
    private ChoiceBox<SpanishPartsOfSpeech> partsOfSpeech;

    public VocabularyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        setTitle("Vocabulary");
        createButtonBar("Submit or Delete");
        addButtonToButtonBar("Submit", "Submit or Delete");
        addButtonToButtonBar("Delete", "Submit or Delete");
        createListView("Words");
        createInputBox("Enter Word: ");
        createInputBox("Enter Definition: ");
        createInputBox("Enter Translation: ");
        languageChoices = createChoiceBox("Languages", new ArrayList<>(Arrays.asList(Languages.values())));
        partsOfSpeech = createChoiceBox("Part of Speech", new ArrayList<>(Arrays.asList(SpanishPartsOfSpeech.values())));
        setButtonOnClick("Submit", e -> submitWord());
        setButtonOnClick("Delete", e -> deleteWord());
        languageChoices.valueProperty().addListener(e -> loadDisplay());
    }

    private void submitWord() {
        SQLiteJDBC.getInstance().getLibraryIO().addVocabularyWord(getInputOfBox("Enter Word: "), getInputOfBox("Enter Definition: "), getInputOfBox("Enter Translation: "),
                languageChoices.getSelectionModel().getSelectedItem().toString(), partsOfSpeech.getSelectionModel().getSelectedItem().toString());
        loadDisplay();
    }

    private void deleteWord() {
        if(getListView("Words").getSelectedItem() != null)  SQLiteJDBC.getInstance().getLibraryIO().deleteVocabularyWord(getSelectedWord());
        loadDisplay();
    }

    private void loadDisplay() {
        getListView("Words").clear();
        for(BasicWord word : SQLiteJDBC.getInstance().getLibraryIO().getWordsOfLanguage(languageChoices.getValue().toString())) {
            getListView("Words").addLine(getListView("Words").getListView().getItems().size() + ": " + word.getWord());
        }
    }

    public String getSelectedWord() {
        return getListView("Words").getSelectedItem().substring(getListView("Words").getSelectedItem().indexOf(':') + 2);
    }
}
