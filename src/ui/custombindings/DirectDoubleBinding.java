package ui.custombindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

/**
 * Created by shaev_000 on 5/10/2016.
 */
public class DirectDoubleBinding extends DoubleBinding {
    private DoubleProperty doubleProperty;

    public DirectDoubleBinding(DoubleProperty doubleProperty) {
        super.bind(doubleProperty);
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
