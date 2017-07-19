package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.control.Button;
import ui.custombindings.ScaledDoubleBinding;

/**
 * Created by shaev_000 on 7/8/2017.
 */
public class ScalingButton extends Button {
    public ScalingButton(ViewBindingsPack buttonBindings) {
        this.prefHeightProperty().bind(buttonBindings.heightProperty());
        this.prefWidthProperty().bind(buttonBindings.widthProperty());
    }

    public ScalingButton(String text, ViewBindingsPack buttonBindings) {
        super(text);
        this.prefHeightProperty().bind(buttonBindings.heightProperty());
        this.prefWidthProperty().bind(buttonBindings.widthProperty());
    }
    public ScalingButton(ViewBindingsPack buttonBindings, double desiredPercentageScale) {
        this.prefHeightProperty().bind(buttonBindings.heightProperty());
        this.prefWidthProperty().bind(new ScaledDoubleBinding(buttonBindings.widthProperty(), desiredPercentageScale));
    }

    public ScalingButton(String text, ViewBindingsPack buttonBindings, double desiredPercentageScale) {
        super(text);
        this.prefHeightProperty().bind(buttonBindings.heightProperty());
        this.prefWidthProperty().bind(new ScaledDoubleBinding(buttonBindings.widthProperty(), desiredPercentageScale));
    }
}
