package ui.components.inputcomponents;

import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import ui.components.editablelabel.ScalingInputBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

public class EditableLabel extends Group {
    private Label textDisplay;
    private ScalingInputBox inputBox;
    private ScaledDoubleBinding labelWidth;
    private ViewBindingsPack viewBindings;
    private final double defaultWidth = 0.25;
    private double customWidth;
    private boolean inputActive;

    public EditableLabel(ViewBindingsPack viewBindings) {
        this.viewBindings = viewBindings;
        customWidth = 0;
        inputActive = false;
        init();
    }

    public EditableLabel(ViewBindingsPack viewBindings, double customWidth) {
        this.viewBindings = viewBindings;
        this.customWidth = customWidth;
        init();
    }

    private void init() {
        initWidth();
        initElements();
        initBehavior();
    }

    private void initElements() {
        textDisplay = new Label();
        inputBox = new ScalingInputBox(labelWidth, "Click to Enter Text");
        textDisplay.prefWidthProperty().bind(labelWidth);
        textDisplay.textProperty().bind(inputBox.textProperty());
        this.getChildren().addAll(textDisplay, inputBox);
        showText();
    }

    private void initWidth() {
        if(customWidth == 0) {
            customWidth = defaultWidth;
        }
        labelWidth = new ScaledDoubleBinding(viewBindings.widthProperty(), customWidth);
    }

    private void initBehavior() {
        textDisplay.setOnMouseClicked(e -> toggleDisplay());
        inputBox.setOnKeyReleased(e -> { if(e.getCode() == KeyCode.ENTER) toggleDisplay(); });
    }

    private void hideText() {
        inputBox.setVisible(true);
        inputBox.setDisable(false);
        textDisplay.setVisible(false);
        textDisplay.setDisable(true);
        inputBox.requestFocus();
    }

    private void showText() {
        inputBox.setVisible(false);
        inputBox.setDisable(true);
        textDisplay.setVisible(true);
        textDisplay.setDisable(false);
    }

    private void toggleDisplay() {
        if(inputActive) {
            showText();
            inputActive = false;
        } else {
            hideText();
            inputActive = true;
        }
    }

    public String getText() { return textDisplay.getText(); }
    public StringProperty textProperty() { return inputBox.textProperty(); }
}
