package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import ui.components.PaneKeys;
import ui.components.inputcomponents.EditableLabel;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class SanctuaryView extends ScalingScrollPane {

    private ScalingVBox mainContainer;
    private ScalingHBox profileDisplay;
    private ScalingVBox profileInfo;
    private ScalingVBox profileLog;
    private ScalingHBox activeProjects;
    private ScalingHBox sanctuaryLinks;
    private ScalingButton morningJournal;
    private ScalingButton eveningReflections;
    private EditableLabel name;
    private EditableLabel age;
    private EditableLabel gender;

    public SanctuaryView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initLabels();
        initButtons();
        addChildrenToContainers();
    }

    private void addChildrenToContainers() {
        sanctuaryLinks.getChildren().addAll(morningJournal, eveningReflections);
        profileDisplay.getChildren().addAll(profileInfo, profileLog);
        mainContainer.getChildren().addAll(profileDisplay, activeProjects, sanctuaryLinks);
        this.setContent(mainContainer);
    }

    private void initContainers() {
        profileDisplay = new ScalingHBox(getViewBindings());
        profileInfo = new ScalingVBox(getViewBindings());
        profileLog = new ScalingVBox(getViewBindings());
        activeProjects = new ScalingHBox(getViewBindings());
        sanctuaryLinks = new ScalingHBox(getViewBindings());
        mainContainer = new ScalingVBox(getViewBindings());
        profileInfo.setAlignment(Pos.CENTER);
    }

    private void initLabels() {
        name = new EditableLabel(getViewBindings());
        age = new EditableLabel(getViewBindings());
        gender = new EditableLabel(getViewBindings());
        profileInfo.getChildren().addAll(name, age, gender);
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        morningJournal = new ScalingButton("Mourning Journal", buttonPack);
        eveningReflections = new ScalingButton("Evening Reflections", buttonPack);
    }
}
