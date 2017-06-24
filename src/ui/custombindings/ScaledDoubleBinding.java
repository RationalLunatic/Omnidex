package ui.custombindings;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;

/**
 * Created by shaev_000 on 5/10/2016.
 */
public class ScaledDoubleBinding extends DoubleBinding {
    private double scalingFactor;
    private DoubleProperty doubleProperty;

    public ScaledDoubleBinding(DoubleProperty doubleProperty, double scalingFactor) {
        super.bind(doubleProperty);
        this.doubleProperty = doubleProperty;
        this.scalingFactor = scalingFactor;
    }

    public ScaledDoubleBinding(ScaledDoubleBinding doubleBinding, double scalingFactor) {
        super.bind(doubleBinding.getDoubleProperty());
        this.doubleProperty = doubleBinding.getDoubleProperty();
        this.scalingFactor = scalingFactor * doubleBinding.scalingFactor;
    }

    public ScaledDoubleBinding(DirectDoubleBinding doubleBinding, double scalingFactor) {
        super.bind(doubleBinding.getDoubleProperty());
        this.doubleProperty = doubleBinding.getDoubleProperty();
        this.scalingFactor = scalingFactor;
    }

    public DoubleProperty getDoubleProperty() {
        return doubleProperty;
    }


    @Override
    protected double computeValue() {
        return doubleProperty.get() * scalingFactor;
    }
}
