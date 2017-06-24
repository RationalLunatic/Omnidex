package ui.components;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.AnchorPane;

/**
 * Created by shaev_000 on 5/9/2016.
 */
public class ScalingAnchorPane extends AnchorPane {
    public ScalingAnchorPane(DoubleBinding width, DoubleBinding height) {
        this.prefWidthProperty().bind(width);
        this.prefHeightProperty().bind(height);
    }
}
