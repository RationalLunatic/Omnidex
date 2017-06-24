package ui.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;

/**
 * Created by shaev_000 on 7/27/2016.
 */
public class ScalingHBox extends HBox {
    public ScalingHBox(DoubleBinding height) {
        this.prefHeightProperty().bind(height);
    }
}
