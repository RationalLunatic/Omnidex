package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.VBox;

/**
 * Created by shaev_000 on 7/27/2016.
 */
public class ScalingVBox extends VBox {
    public ScalingVBox(DoubleBinding width) {
        this.prefWidthProperty().bind(width);
    }
}
