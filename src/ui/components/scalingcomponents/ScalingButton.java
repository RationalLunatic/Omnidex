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
    public ScalingButton(ViewBindingsPack buttonBindings, double desiredWidthPercentageScale, double desiredHeightPercentageScale) {
        this.prefHeightProperty().bind(new ScaledDoubleBinding(buttonBindings.heightProperty(), desiredHeightPercentageScale));
        this.prefWidthProperty().bind(new ScaledDoubleBinding(buttonBindings.widthProperty(), desiredWidthPercentageScale));
    }

    public ScalingButton(String text, ViewBindingsPack buttonBindings, double desiredWidthPercentageScale, double desiredHeightPercentageScale) {
        super(text);
        this.prefHeightProperty().bind(new ScaledDoubleBinding(buttonBindings.heightProperty(), desiredHeightPercentageScale));
        this.prefWidthProperty().bind(new ScaledDoubleBinding(buttonBindings.widthProperty(), desiredWidthPercentageScale));
    }
}
