package ui.features;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.HashMap;
import java.util.Map;

public class AbstractButtonDisplay extends ScalingVBox {
    private Map<String, ScalingHBox> rowsOfButtons;
    private Map<String, Map<String, ScalingButton>> buttonsInRow;

    public AbstractButtonDisplay(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        initDataStructures();
        initMainPane();
    }

    private void initMainPane() {
        this.setAlignment(Pos.CENTER);
    }

    private void initDataStructures() {
        rowsOfButtons = new HashMap<>();
        buttonsInRow = new HashMap<>();
    }

    public void createButtonRow(String rowName) {
        ScalingHBox newRow = new ScalingHBox(getViewBindings());
        rowsOfButtons.put(rowName, newRow);
        buttonsInRow.put(rowName, new HashMap<>());
        this.getChildren().add(newRow);
    }

    public void addButtonToRow(String buttonName, String rowName) {
        ScalingButton newButton = new ScalingButton(getViewBindings());
        newButton.setText(buttonName);
        rowsOfButtons.get(rowName).getChildren().add(newButton);
        buttonsInRow.get(rowName).put(buttonName, newButton);
    }

    public void setButtonBehavior(String buttonName, EventHandler eventHandler) {
        for(String row : rowsOfButtons.keySet()) {
            for(String button : buttonsInRow.get(row).keySet()) {
                if(buttonName.equals(button)) {
                    buttonsInRow.get(row).get(button).setOnMouseClicked(eventHandler);
                }
            }
        }
    }
}
