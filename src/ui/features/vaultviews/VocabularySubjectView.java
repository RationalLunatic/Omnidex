package ui.features.vaultviews;

import engine.components.schedule.BasicTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import resources.StringFormatUtility;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.interviewcommunications.ViewRequestKeys;
import ui.components.popupdialogs.PathfinderDialog;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class VocabularySubjectView extends ScalingVBox {
    private Map<String, SimpleListTextDisplay> vocabLists;
    private ChoiceBox<String> subjectSelector;
    private ScalingVBox listContainer;
    private ScalingVBox subjectOptionsContainer;
    private ScalingButton deleteSelectedSubject;
    private ScalingButton createNewSubject;
    private ScalingButton addWord;
    private ScalingButton deleteWord;
    private ViewRequestHandler requestHandler;

    public VocabularySubjectView(ViewBindingsPack viewBindings, ViewRequestHandler requestHandler) {
        super(viewBindings);
        this.requestHandler = requestHandler;
        init();
    }

    private void initContainers() {
        listContainer = new ScalingVBox(getViewBindings());
        subjectOptionsContainer = new ScalingVBox(getViewBindings());
        listContainer.setAlignment(Pos.TOP_CENTER);
        subjectOptionsContainer.setAlignment(Pos.TOP_CENTER);
    }

    private void initCategorySelector() {
        subjectSelector = new ChoiceBox<>();
        initSubjectLists();
        subjectSelector.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                selectList(newValue);
            }
        });
    }

    private void initSubjectLists() {
        vocabLists = new HashMap<>();
        subjectSelector.getItems().clear();
        for(String subject : SQLiteJDBC.getInstance().getLibraryIO().getSubjects()) {
            vocabLists.put(subject, new SimpleListTextDisplay(StringFormatUtility.capitalize(subject.toString()), getViewBindings()));
            subjectSelector.getItems().add(subject);
        }
    }

    private void init() {
        this.setAlignment(Pos.CENTER);
        initContainers();
        initCategorySelector();
        initSubjectOptions();
        addElementsToContainers();
        loadVocabWords();
    }

    private void addElementsToContainers() {
        listContainer.getChildren().add(subjectSelector);
        subjectOptionsContainer.getChildren().addAll(deleteSelectedSubject, createNewSubject, addWord, deleteWord);
        this.getChildren().addAll(listContainer, subjectOptionsContainer);
    }

    private void initSubjectOptions() {
        initButtons();
        setButtonText();
        setButtonBehavior();
    }

    private void initButtons() {
        createNewSubject = new ScalingButton(getViewBindings(), 1.0, 0.2);
        deleteSelectedSubject = new ScalingButton(getViewBindings(), 1.0, 0.2);
        addWord = new ScalingButton(getViewBindings(), 1.0, 0.2);
        deleteWord = new ScalingButton(getViewBindings(), 1.0, 0.2);
    }

    private void setButtonText() {
        createNewSubject.setText("Create New Subject");
        deleteSelectedSubject.setText("Delete Selected Subject");
        addWord.setText("Add Word");
        deleteWord.setText("Delete Word");
    }

    private void setButtonBehavior() {
        deleteSelectedSubject.setOnMouseClicked(e -> deleteActiveSubject());
        createNewSubject.setOnMouseClicked(e -> createNewSubject());
        addWord.setOnMouseClicked(e -> addWordToSubject());
        deleteWord.setOnMouseClicked(e -> removeWordFromSubject());
    }

    private void selectList(String category) {
        listContainer.getChildren().clear();
        listContainer.getChildren().add(subjectSelector);
        listContainer.getChildren().add(vocabLists.get(category));
    }

    public void loadVocabWords() {
        for(String subject : SQLiteJDBC.getInstance().getLibraryIO().getSubjects()) {
            vocabLists.get(subject).clear();
            for(BasicWord word : SQLiteJDBC.getInstance().getLibraryIO().getVocabList(subject)) {
                vocabLists.get(subject).addLine(word.getWord());
            }
        }
    }

    public String getSelectedWord() {
        if(vocabLists.get(subjectSelector.getSelectionModel().getSelectedItem()).isItemSelected()) {
            return vocabLists.get(subjectSelector.getSelectionModel().getSelectedItem()).getSelectedItem();
        } return "";
    }

    private void deleteActiveSubject() {
        String selectedItem = subjectSelector.getValue();
        if(selectedItem != null && !selectedItem.isEmpty()) {
            SQLiteJDBC.getInstance().getLibraryIO().deleteSubject(selectedItem);
            loadVocabWords();
        }
        initSubjectLists();
    }

    private void createNewSubject() {
        PathfinderDialog dialog = new PathfinderDialog("Subject");
        Optional<BasicTask> exercise = dialog.showAndWait();
        if(exercise != null && exercise.isPresent()) {
            SQLiteJDBC.getInstance().getLibraryIO().addSubjectToLanguage(exercise.get().getTitle(), "SPANISH");
        }
        initSubjectLists();
        loadVocabWords();
    }

    private void addWordToSubject() {
        String word = requestHandler.handleDataRequest(new ViewRequest(PaneKeys.VOCABULARY, ViewRequestKeys.DATA_REQUEST));
        SQLiteJDBC.getInstance().getLibraryIO().addWordToSubject(subjectSelector.getValue(), word);
        loadVocabWords();
    }

    private void removeWordFromSubject() {

    }
}
