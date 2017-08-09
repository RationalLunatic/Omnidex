package ui.components.inputcomponents;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import ui.components.editablelabel.ScalingInputBox;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

public class LabeledInputBox extends ScalingHBox {
    private String inputPrompt;
    private Label inputLabel;
    private ScalingInputBox inputField;

    public LabeledInputBox(String inputPrompt, ViewBindingsPack viewBindings) {
        super(viewBindings);
        this.inputPrompt = inputPrompt;
        ScaledDoubleBinding labelBinding = new ScaledDoubleBinding(viewBindings.widthProperty(), 0.2);
        inputLabel = new Label(inputPrompt);
        inputField = new ScalingInputBox(getViewBindings().widthProperty(), 0.8);
        inputLabel.prefWidthProperty().bind(labelBinding);
        this.getChildren().addAll(inputLabel, inputField);
        this.setAlignment(Pos.CENTER);
    }

    public LabeledInputBox(String inputPrompt, ViewBindingsPack viewBindings, double percentageScale) {
        super(viewBindings);
        this.inputPrompt = inputPrompt;
        ScaledDoubleBinding labelBinding = new ScaledDoubleBinding(viewBindings.widthProperty(), 0.2 * percentageScale);
        inputLabel = new Label(inputPrompt);
        inputField = new ScalingInputBox(getViewBindings().widthProperty(), 0.8 * percentageScale);
        inputLabel.prefWidthProperty().bind(labelBinding);
        this.getChildren().addAll(inputLabel, inputField);
        this.setAlignment(Pos.CENTER);
    }

    public void setText(String value) {
        inputField.setText(value);
        inputField.setBaseText(value);
    }
    public String getInput() { return inputField.getText(); }
    public boolean isEmpty() { return inputField.getText().isEmpty(); }
}
