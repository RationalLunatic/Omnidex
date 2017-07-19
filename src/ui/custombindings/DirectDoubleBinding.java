package ui.custombindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;


public class DirectDoubleBinding extends ScaledDoubleBinding {
    private DoubleProperty doubleProperty;

    public DirectDoubleBinding(DoubleProperty doubleProperty) {
        super(doubleProperty, 1);
        this.doubleProperty = doubleProperty;
    }

    public DoubleProperty getDoubleProperty() {
        return doubleProperty;
    }


    @Override
    protected double computeValue() {
        return doubleProperty.get();
    }
}
