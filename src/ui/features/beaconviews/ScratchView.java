package ui.features.beaconviews;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import resources.sqlite.ScratchCategories;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.util.HashMap;
import java.util.Map;

public class ScratchView extends ScalingScrollPane {

    private ScalingVBox mainContainer;
    private ScalingHBox upper;
    private ScalingVBox lower;
    private LabeledInputBox nameInput;
    private LabeledInputBox descriptionInput;
    private ScalingButton submit;
    private ScalingButton delete;
    private ChoiceBox<ScratchCategories> scratchCategory;
    private Map<ScratchCategories, SimpleListTextDisplay> scratchActionListViews;

    public ScratchView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initUIElements();
        addElementsToContainers();
        loadScratchLists();
    }

    private void addElementsToContainers() {
        lower.getChildren().addAll(nameInput, descriptionInput, scratchCategory, submit, delete);
        upper.getChildren().addAll(scratchActionListViews.values());
        mainContainer.getChildren().addAll(upper, lower);
        this.setContent(mainContainer);
    }

    private void initUIElements() {
        ScaledDoubleBinding buttonHeightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.2);
        ViewBindingsPack scalingButtonBindingsPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeightBinding);
        submit = new ScalingButton("Submit", scalingButtonBindingsPack);
        delete = new ScalingButton("Delete", scalingButtonBindingsPack);
        submit.setOnMouseClicked(e -> addAction());
        delete.setOnMouseClicked(e -> deleteAction());
    }

    private void initScratchLists() {
        scratchActionListViews = new HashMap<>();
        scratchCategory = new ChoiceBox<>();
        for(ScratchCategories category : ScratchCategories.values()) {
            SimpleListTextDisplay scratchList = new SimpleListTextDisplay(
                    StringFormatUtility.capitalize(category.toString()), getViewBindings());
            scratchActionListViews.put(category, scratchList);
            scratchCategory.getItems().add(category);
        }
        scratchCategory.getSelectionModel().select(ScratchCategories.TASK);
    }

    private void initContainers() {
        initOuterContainers();
        initScratchLists();
        initInputFields();
    }

    private void initOuterContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        upper = new ScalingHBox(getViewBindings());
        lower = new ScalingVBox(getViewBindings());
        mainContainer.setAlignment(Pos.CENTER);
        upper.setAlignment(Pos.CENTER);
        lower.setAlignment(Pos.CENTER);
    }

    private void initInputFields() {
        nameInput = new LabeledInputBox("Enter Name: ", getViewBindings());
        descriptionInput = new LabeledInputBox("Enter Description: ", getViewBindings());
    }

    private void addAction() {
        if(!nameInput.isEmpty()) {
            if(scratchCategory.getSelectionModel().getSelectedItem() != null) {
                scratchActionListViews.get(scratchCategory.getSelectionModel().getSelectedItem()).addLine(nameInput.getInput());
                SQLiteJDBC.getInstance().addToLibrary(nameInput.getInput(), descriptionInput.getInput(), scratchCategory.getSelectionModel().getSelectedItem().toString());
            }
        }
    }

    private void deleteAction() {
        if(scratchCategory.getSelectionModel().getSelectedItem() != null) {
            SQLiteJDBC.getInstance().deleteFromLibrary(nameInput.getInput(), scratchCategory.getSelectionModel().getSelectedItem().toString());
            loadScratchLists();
        }
    }

    private void loadScratchLists() {
        for(ScratchCategories category : ScratchCategories.values()) {
            scratchActionListViews.get(category).clear();
            for(String element : SQLiteJDBC.getInstance().getItemsOfLibraryCategory(category.toString())) {
                scratchActionListViews.get(category).addLine(element);
            }
        }
    }
}
