package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import ui.custombindings.ScaledDoubleBinding;

public class ViewBindingsPack {
    private ScaledDoubleBinding width;
    private ScaledDoubleBinding height;

    public ViewBindingsPack(ScaledDoubleBinding width, ScaledDoubleBinding height) {
        this.width = width;
        this.height = height;
    }

    public Number getWidth() {
        return width.get();
    }

    public ScaledDoubleBinding widthProperty() {
        return width;
    }

    public void setWidth(ScaledDoubleBinding width) {
        this.width = width;
    }

    public Number getHeight() {
        return height.get();
    }

    public ScaledDoubleBinding heightProperty() {
        return height;
    }

    public void setHeight(ScaledDoubleBinding height) {
        this.height = height;
    }
}
