package ui.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.StackPane;

/**
 * Created by shaev_000 on 7/27/2016.
 */
public class ScalingStackPane extends StackPane {
    public ScalingStackPane(DoubleBinding width, DoubleBinding height) {
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
    }
}
