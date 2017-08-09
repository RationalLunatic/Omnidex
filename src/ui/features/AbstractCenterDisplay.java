package ui.features;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCenterDisplay extends ScalingStackPane {
    private ScalingLabel viewTitle;
    private ScalingVBox mainContainer;
    private Map<String, ScalingHBox> buttonBars;

    public AbstractCenterDisplay(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        buttonBars = new HashMap<>();
        viewTitle = new ScalingLabel(getViewBindings().widthProperty(), "", 0.6);
        mainContainer = new ScalingVBox(getViewBindings());
        mainContainer.getChildren().add(viewTitle);
        mainContainer.setAlignment(Pos.TOP_CENTER);
        this.getChildren().add(mainContainer);
        this.setAlignment(Pos.TOP_CENTER);
    }

    public void setTitle(String title) {
        viewTitle.setText(title);
    }

    public ScalingVBox getMainContainer() {
        return mainContainer;
    }

    public void createButtonBar(String featureName) {
        ScalingHBox buttonBar = new ScalingHBox(getViewBindings());
        ScalingVBox buttonBarContainer = new ScalingVBox(getViewBindings());
        ScalingLabel buttonBarTitle = new ScalingLabel(getViewBindings().widthProperty(), featureName, 0.25);
        buttonBarContainer.getChildren().addAll(buttonBarTitle, buttonBar);
        buttonBarContainer.setAlignment(Pos.TOP_CENTER);
        buttonBarContainer.setStyle("-fx-border-color: black; -fx-border-width: 2;");
        buttonBar.setAlignment(Pos.TOP_CENTER);
        buttonBars.put(featureName, buttonBar);
        mainContainer.getChildren().add(buttonBarContainer);
    }

    public void addButtonToButtonBar(String buttonName, String buttonBar) {
        if(buttonBars.containsKey(buttonBar)) {
            ScalingButton button = new ScalingButton(getViewBindings());
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
}
