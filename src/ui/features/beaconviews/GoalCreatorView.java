package ui.features.beaconviews;

import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import resources.sqlite.SQLiteJDBC;
import skeletonkey.QuaternalParadigms;
import ui.components.PaneKeys;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.util.HashMap;
import java.util.Map;

public class GoalCreatorView extends ScalingScrollPane {
    private LabeledInputBox goalName;
    private LabeledInputBox goalDescription;
    private ChoiceBox<QuaternalParadigms> goalCategory;
    private ScalingButton submitGoal;
    private ScalingVBox bodyContainer;
    private ScalingVBox mindContainer;
    private ScalingVBox soulContainer;
    private ScalingVBox spiritContainer;
    private ScalingVBox mainContainer;
    private ScalingHBox upper;
    private ScalingVBox lower;
    private Map<QuaternalParadigms, ScalingVBox> listSelector;

    public GoalCreatorView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initElements();
        addGoalCategories();
        addElementsToContainers();
        loadGoals();
    }

    private void addElementsToContainers() {
        mainContainer.getChildren().addAll(upper, lower);
        lower.getChildren().addAll(goalName, goalDescription, goalCategory, submitGoal);
        upper.getChildren().addAll(bodyContainer, mindContainer, soulContainer, spiritContainer);
        this.setContent(mainContainer);
    }

    private void initGoalContainers() {
        bodyContainer = new ScalingVBox(getViewBindings());
        mindContainer = new ScalingVBox(getViewBindings());
        soulContainer = new ScalingVBox(getViewBindings());
        spiritContainer = new ScalingVBox(getViewBindings());
        bodyContainer.setAlignment(Pos.CENTER);
        mindContainer.setAlignment(Pos.CENTER);
        soulContainer.setAlignment(Pos.CENTER);
        spiritContainer.setAlignment(Pos.CENTER);
        bodyContainer.getChildren().add(new Label("Body"));
        mindContainer.getChildren().add(new Label("Mind"));
        soulContainer.getChildren().add(new Label("Soul"));
        spiritContainer.getChildren().add(new Label("Spirit"));
    }

    private void initOuterContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        upper = new ScalingHBox(getViewBindings());
        lower = new ScalingVBox(getViewBindings());
        mainContainer.setAlignment(Pos.CENTER);
        lower.setAlignment(Pos.BOTTOM_CENTER);
        upper.setAlignment(Pos.CENTER);
    }

    private void initContainers() {
        initOuterContainers();
        initGoalContainers();
    }

    private void initElements() {
        goalName = new LabeledInputBox("Enter Goal Name: ", getViewBindings());
        goalDescription = new LabeledInputBox("Enter Goal Description: ", getViewBindings());
        goalCategory = new ChoiceBox();
        ScaledDoubleBinding buttonHeightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.2);
        ViewBindingsPack scalingButtonBindingsPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeightBinding);
        submitGoal = new ScalingButton("Submit Goal", scalingButtonBindingsPack);
        submitGoal.setOnMouseClicked(e -> submitGoal());
    }

    private void addGoalCategories() {
        listSelector = new HashMap<>();
        listSelector.put(QuaternalParadigms.BODY, bodyContainer);
        listSelector.put(QuaternalParadigms.MIND, mindContainer);
        listSelector.put(QuaternalParadigms.SOUL, soulContainer);
        listSelector.put(QuaternalParadigms.SPIRIT, spiritContainer);
        goalCategory.getItems().add(QuaternalParadigms.BODY);
        goalCategory.getItems().add(QuaternalParadigms.MIND);
        goalCategory.getItems().add(QuaternalParadigms.SOUL);
        goalCategory.getItems().add(QuaternalParadigms.SPIRIT);
    }

    private void loadGoals() {
        for(QuaternalParadigms category : QuaternalParadigms.values()) {
            System.out.println(category.toString());
            for(String goal : SQLiteJDBC.getInstance().getItemsOfLibraryCategory(category.toString())) {
                System.out.println(goal);
                listSelector.get(category).getChildren().add(new Label(goal));
            }
        }
    }

    private void submitGoal() {
        if(!goalName.isEmpty()) {
            if(goalCategory.getSelectionModel().getSelectedItem() != null) {
                Label newGoal = new Label(goalName.getInput());
                listSelector.get(goalCategory.getSelectionModel().getSelectedItem()).getChildren().add(newGoal);
                SQLiteJDBC.getInstance().addToLibrary(goalName.getInput(), goalDescription.getInput(), goalCategory.getSelectionModel().getSelectedItem().toString());
            }
        }
    }
}
