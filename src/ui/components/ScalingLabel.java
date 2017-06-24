package ui.components;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/**
 * Created by shaev_000 on 5/9/2016.
 */
public class ScalingLabel extends Label {
    public ScalingLabel(DoubleBinding width, double divisor, String text) {
        super(text);
        ObjectProperty<Font> fontProperty = new SimpleObjectProperty<>();
        this.fontProperty().bind(fontProperty);
        Label reference = this;
        width.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                double textSize = newWidth.doubleValue() / divisor;
                fontProperty.set(Font.font(textSize));
            }
        });
    }
}
