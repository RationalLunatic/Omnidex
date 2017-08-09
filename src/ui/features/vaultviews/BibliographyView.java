package ui.features.vaultviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import resources.datatypes.bibliographicdata.PublicationTypes;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BibliographyView extends ScalingStackPane {
    private ScalingVBox mainContainer;
    private ScalingVBox titleAndChoiceContainer;
    private ScalingVBox inputContainer;
    private SimpleListTextDisplay bibliographyArchive;
    private ScalingButton submitCitation;
    private LabeledInputBox title;
    private LabeledInputBox author;
    private LabeledInputBox publisher;
    private LabeledInputBox publicationDate;
    private LabeledInputBox publicationLocation;
    private LabeledInputBox publicationFormat;
    private LabeledInputBox articleTitle;
    private LabeledInputBox journalTitle;
    private LabeledInputBox volumeNumber;
    private LabeledInputBox issueNumber;
    private LabeledInputBox internationalStandardBookNumber;
    private ChoiceBox<PublicationTypes> selectPublicationType;
    private ScalingHBox selectPublicationTypeContainer;
    private Map<PublicationTypes, List<LabeledInputBox>> citationTypeInfoRequirements;
    private ScalingButton submitCitationDetails;

    public BibliographyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initInputBoxes();
        initCitationTypeMap();
        initTitle();
        initChoiceBox();
        initBibliographyArchiveDisplay();
        initButtons();
        addUIElementsToContainers();
    }

    private void initButtons() {
        submitCitationDetails = new ScalingButton(getViewBindings());
        submitCitationDetails.setText("Submit Citation Details");
    }

    private void initTitle() {

    }

    private void addUIElementsToContainers() {
        selectPublicationTypeContainer.getChildren().addAll(new Label("Select Publication Type: "), selectPublicationType);
        titleAndChoiceContainer.getChildren().addAll(selectPublicationTypeContainer);
        mainContainer.getChildren().addAll(titleAndChoiceContainer, inputContainer,submitCitationDetails);
    }

    private void initChoiceBox() {
        selectPublicationType = new ChoiceBox<>();
        ObservableList<PublicationTypes> publicationTypes = FXCollections.observableArrayList();
        for(PublicationTypes pubType : PublicationTypes.values()) {
            publicationTypes.add(pubType);
        }
        selectPublicationType.setItems(publicationTypes);
        selectPublicationType.setOnAction(e -> {
            if(!selectPublicationType.getSelectionModel().isEmpty()) {
                inputContainer.getChildren().clear();
                for(LabeledInputBox input : citationTypeInfoRequirements.get(selectPublicationType.getSelectionModel().getSelectedItem())) {
                    inputContainer.getChildren().add(input);
                }
            }
        });
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        mainContainer.setAlignment(Pos.TOP_CENTER);
        titleAndChoiceContainer = new ScalingVBox(getViewBindings());
        inputContainer = new ScalingVBox(getViewBindings());
        selectPublicationTypeContainer = new ScalingHBox(getViewBindings());
        selectPublicationTypeContainer.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(mainContainer);
    }

    private void initInputBoxes() {
        title = new LabeledInputBox("Title: ", getViewBindings());
        author = new LabeledInputBox("Author: ", getViewBindings());
        publisher = new LabeledInputBox("Publisher: ", getViewBindings());
        publicationDate = new LabeledInputBox("Publication Date: ", getViewBindings());
        publicationLocation = new LabeledInputBox("Publication Location: ", getViewBindings());
        publicationFormat = new LabeledInputBox("Publication Format: ", getViewBindings());
        articleTitle = new LabeledInputBox("Article Title: ", getViewBindings());
        journalTitle = new LabeledInputBox("Journal Title: ", getViewBindings());
        volumeNumber = new LabeledInputBox("Volume Number: ", getViewBindings());
        issueNumber = new LabeledInputBox("Issue Number: ", getViewBindings());
        internationalStandardBookNumber = new LabeledInputBox("ISBN: ", getViewBindings());
    }

    private void initBibliographyArchiveDisplay() {
        bibliographyArchive = new SimpleListTextDisplay("Archived Bibliographic Reference", getViewBindings());
    }

    private void initCitationTypeMap() {
        citationTypeInfoRequirements = new HashMap<>();
        citationTypeInfoRequirements.put(PublicationTypes.BOOK, getBookList());
        citationTypeInfoRequirements.put(PublicationTypes.JOURNAL, getJournalList());
        citationTypeInfoRequirements.put(PublicationTypes.NEWSPAPER, getNewspaperList());
    }

    private List<LabeledInputBox> getBookList() {
        List<LabeledInputBox> bookList = new ArrayList<>();
        bookList.add(title);
        bookList.add(author);
        bookList.add(publisher);
        bookList.add(publicationDate);
        bookList.add(publicationLocation);
        bookList.add(publicationFormat);
        bookList.add(internationalStandardBookNumber);
        return bookList;
    }

    private List<LabeledInputBox> getJournalList() {
        List<LabeledInputBox> journalList = new ArrayList<>();
        journalList.add(articleTitle);
        journalList.add(journalTitle);
        journalList.add(author);
        journalList.add(volumeNumber);
        journalList.add(issueNumber);
        journalList.add(publisher);
        journalList.add(publicationDate);
        journalList.add(publicationLocation);
        journalList.add(publicationFormat);
        return journalList;
    }

    public List<LabeledInputBox> getNewspaperList() {
        List<LabeledInputBox> newspaperList = new ArrayList<>();
        newspaperList.add(articleTitle);
        newspaperList.add(journalTitle);
        newspaperList.add(author);
        newspaperList.add(volumeNumber);
        newspaperList.add(issueNumber);
        newspaperList.add(publisher);
        newspaperList.add(publicationDate);
        newspaperList.add(publicationLocation);
        newspaperList.add(publicationFormat);
        newspaperList.add(internationalStandardBookNumber);
        return newspaperList;
    }
}
