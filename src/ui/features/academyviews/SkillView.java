package ui.features.academyviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import skeletonkey.Attributes;
import skeletonkey.QuaternalParadigms;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.EditableLabel;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class SkillView extends ScalingStackPane {
    private SimpleListTextDisplay bodySkills;
    private SimpleListTextDisplay mindSkills;
    private SimpleListTextDisplay soulSkills;
    private SimpleListTextDisplay spiritSkills;
    private ScalingHBox listDisplayContainer;
    private ScalingVBox mainContainer;
    private ScalingLabel title;
    private ScalingButton createNewSkill;
    private ScalingVBox skillCreatorContainer;
    private Label skillCreatorTitle;
    private EditableLabel enterSkillName;
    private ScalingHBox choiceBoxContainer;
    private ChoiceBox<QuaternalParadigms> selectParadigm;
    private ChoiceBox<Attributes> governingAttribute;
    private ScalingButton submitNewSkill;


    public SkillView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initUIElements();
        addUIElementsToContainers();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        listDisplayContainer = new ScalingHBox(getViewBindings());
        skillCreatorContainer = new ScalingVBox(getViewBindings());
        choiceBoxContainer = new ScalingHBox(getViewBindings());
    }

    private void initTitle() {
        title = new ScalingLabel(getViewBindings().widthProperty(), "Skill Manager", 1.0);
        skillCreatorTitle = new Label("Skill Creator");
        enterSkillName = new EditableLabel(getViewBindings());
        enterSkillName.setText("Click This Text to Enter Skill Name");
    }

    private void initListDisplays() {
        bodySkills = new SimpleListTextDisplay("Body", getViewBindings());
        mindSkills = new SimpleListTextDisplay("Mind", getViewBindings());
        soulSkills = new SimpleListTextDisplay("Soul", getViewBindings());
        spiritSkills = new SimpleListTextDisplay("Spirit", getViewBindings());
    }

    private void initUIElements() {
        initTitle();
        initListDisplays();
        initButtons();
        initChoiceBoxes();
        closeSkillCreator();
    }

    private void initChoiceBoxes() {
        selectParadigm = new ChoiceBox<>();
        governingAttribute = new ChoiceBox<>();
        ObservableList<QuaternalParadigms> selectParadigmData = FXCollections.observableArrayList();
        for(QuaternalParadigms paradigm : QuaternalParadigms.values()) {
            selectParadigmData.add(paradigm);
        }
        selectParadigm.setItems(selectParadigmData);
        selectParadigm.setOnAction(e -> {
            if(!selectParadigm.getSelectionModel().isEmpty()) {
                governingAttribute.setDisable(false);
                ObservableList<Attributes> paradigmAttributes = FXCollections.observableArrayList();
                paradigmAttributes.add(selectParadigm.getSelectionModel().getSelectedItem().getOffensiveAttribute());
                paradigmAttributes.add(selectParadigm.getSelectionModel().getSelectedItem().getDefensiveAttribute());
                governingAttribute.setItems(paradigmAttributes);
            } else {
                governingAttribute.setDisable(true);
            }
        });
        governingAttribute.setOnAction(e -> {
            if(!governingAttribute.getSelectionModel().isEmpty()) {
                submitNewSkill.setDisable(false);
            } else {
                submitNewSkill.setDisable(true);
            }
        });
        governingAttribute.setDisable(true);
        submitNewSkill.setDisable(true);
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.2);
        ViewBindingsPack buttonBindings = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        createNewSkill = new ScalingButton(buttonBindings);
        createNewSkill.setText("Create New Skill");
        createNewSkill.setOnMouseClicked(e -> openSkillCreator());
        submitNewSkill = new ScalingButton(buttonBindings);
        submitNewSkill.setText("Submit New Skill");
        submitNewSkill.setOnMouseClicked(e -> closeSkillCreator());
    }

    private void addUIElementsToContainers() {
        choiceBoxContainer.getChildren().addAll(new Label("Select Paradigm: "), selectParadigm, new Label(" Select Governing Attribute: "), governingAttribute);
        choiceBoxContainer.setAlignment(Pos.CENTER);
        skillCreatorContainer.getChildren().addAll(skillCreatorTitle, enterSkillName, choiceBoxContainer, submitNewSkill);
        skillCreatorContainer.setAlignment(Pos.TOP_CENTER);
        listDisplayContainer.getChildren().addAll(bodySkills, mindSkills, soulSkills, spiritSkills);
        mainContainer.getChildren().addAll(title, listDisplayContainer, createNewSkill, skillCreatorContainer);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(mainContainer);
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void openSkillCreator() {
        skillCreatorContainer.setDisable(false);
        skillCreatorContainer.setVisible(true);
    }

    private void closeSkillCreator() {
        skillCreatorContainer.setVisible(false);
        skillCreatorContainer.setDisable(true);
    }
}
