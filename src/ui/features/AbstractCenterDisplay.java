package ui.features;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Labeled;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.editablelabel.ScalingInputBox;
import ui.components.inputcomponents.LabeledInputBox;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCenterDisplay extends ScalingStackPane {
    private ScalingLabel viewTitle;
    private ScalingVBox mainContainer;
    private ScalingVBox buttonBarContainer;
    private ScalingVBox inputFieldContainer;
    private ScalingHBox choiceBoxContainer;
    private ScalingHBox listViewContainer;
    private Map<String, ScalingHBox> buttonBars;
    private Map<String, ChoiceBox> choiceBoxes;
    private Map<String, LabeledInputBox> inputBoxes;
    private Map<String, SimpleListTextDisplay> listViews;

    public AbstractCenterDisplay(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        viewTitle = new ScalingLabel(getViewBindings().widthProperty(), "", 0.6);
        initUIMaps();
        initContainers();
        addChildrenToContainers();
        alignContainers();
    }


    private void alignContainers() {
        choiceBoxContainer.setAlignment(Pos.TOP_CENTER);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        this.setAlignment(Pos.TOP_CENTER);
    }

    private void addChildrenToContainers() {
        mainContainer.getChildren().addAll(viewTitle, choiceBoxContainer, listViewContainer, inputFieldContainer, buttonBarContainer);
        this.getChildren().add(mainContainer);
    }

    private void initContainers() {
        listViewContainer = new ScalingHBox(getViewBindings());
        choiceBoxContainer = new ScalingHBox(getViewBindings());
        buttonBarContainer = new ScalingVBox(getViewBindings());
        inputFieldContainer = new ScalingVBox(getViewBindings());
        mainContainer = new ScalingVBox(getViewBindings());

    }

    private void initUIMaps() {
        listViews = new HashMap<>();
        buttonBars = new HashMap<>();
        choiceBoxes = new HashMap<>();
        inputBoxes = new HashMap<>();
    }

    public void setTitle(String title) {
        viewTitle.setText(title);
    }

    public ScalingVBox getMainContainer() {
        return mainContainer;
    }

    public void createButtonBar(String featureName) {
        ScalingHBox buttonBar = new ScalingHBox(getViewBindings());
        ScalingVBox barContainer = new ScalingVBox(getViewBindings());
        ScalingLabel buttonBarTitle = new ScalingLabel(getViewBindings().widthProperty(), featureName, 0.25);
        barContainer.getChildren().addAll(buttonBarTitle, buttonBar);
        barContainer.setAlignment(Pos.TOP_CENTER);
        buttonBarContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        buttonBar.setAlignment(Pos.TOP_CENTER);
        buttonBars.put(featureName, buttonBar);
        buttonBarContainer.getChildren().add(barContainer);
    }

    public void addButtonToButtonBar(String buttonName, String buttonBar) {
        if(buttonBars.containsKey(buttonBar)) {
            ScalingButton button = new ScalingButton(getViewBindings(), 1.0, 0.2);
            button.setText(buttonName);
            buttonBars.get(buttonBar).getChildren().add(button);
        }
    }

    public void setButtonOnClick(String buttonName, EventHandler<Event> handler) {
        for(String buttonBar : buttonBars.keySet()) {
            for(Node button : buttonBars.get(buttonBar).getChildren()) {
                ScalingButton castButton = (ScalingButton) button;
                if(castButton.getText().equals(buttonName)) {
                    castButton.setOnMouseClicked(handler);
                }
            }
        }
    }

    public <T> ChoiceBox createChoiceBox(String choiceBoxName, List<T> choiceBoxValues) {
        ChoiceBox<T> choiceBox = new ChoiceBox<>();
        for(T val : choiceBoxValues) {
            choiceBox.getItems().add(val);
        }
        choiceBoxes.put(choiceBoxName, choiceBox);
        choiceBoxContainer.getChildren().add(choiceBox);
        return choiceBox;
    }

    public void createInputBox(String header) {
        LabeledInputBox inputBox = new LabeledInputBox(header, getViewBindings(),  0.6);
        inputBoxes.put(header, inputBox);
        inputFieldContainer.getChildren().add(inputBox);
    }

    public String getInputOfBox(String header) {
        return (inputBoxes.keySet().contains(header)) ? inputBoxes.get(header).getInput() : "";
    }

    public void createListView(String title) {
        SimpleListTextDisplay listView = new SimpleListTextDisplay(title, getViewBindings());
        listViews.put(title, listView);
        listViewContainer.getChildren().add(listView);
    }

    public SimpleListTextDisplay getListView(String title) {
        return listViews.get(title);
    }

}
