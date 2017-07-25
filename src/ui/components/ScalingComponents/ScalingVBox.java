package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;


public class ScalingVBox extends VBox {
    private ViewBindingsPack viewBindings;

    public ScalingVBox(ViewBindingsPack viewBindings) {
        this.prefWidthProperty().bind(viewBindings.widthProperty());
        this.viewBindings = viewBindings;
    }

    public ViewBindingsPack getViewBindings() { return viewBindings; }
}
