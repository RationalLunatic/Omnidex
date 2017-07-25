package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;

public class ScalingHBox extends HBox {
    private ViewBindingsPack viewBindings;

    public ScalingHBox(ViewBindingsPack viewBindings) {
        this.prefWidthProperty().bind(viewBindings.heightProperty());
        this.viewBindings = viewBindings;
    }

    public ViewBindingsPack getViewBindings() { return viewBindings; }
}
