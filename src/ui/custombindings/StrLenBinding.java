package ui.custombindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.StringProperty;

public class StrLenBinding extends DoubleBinding {
    private DoubleProperty doubleProperty;
    private StringProperty stringProperty;

    public StrLenBinding(DoubleProperty doubleProperty, StringProperty stringProperty) {
        super.bind(doubleProperty);
        this.doubleProperty = doubleProperty;
        this.stringProperty = stringProperty;
    }

    public DoubleProperty getDoubleProperty() {
        return doubleProperty;
    }
    public StringProperty getStringProperty() { return stringProperty; }


    @Override
    protected double computeValue() {
        return doubleProperty.get() / stringProperty.get().length() * 1.5;
    }
}
