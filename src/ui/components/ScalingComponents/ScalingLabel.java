package ui.components.scalingcomponents;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import ui.custombindings.ScaledDoubleBinding;
import ui.custombindings.StrLenBinding;

/**
 * Created by shaev_000 on 5/9/2016.
 */
public class ScalingLabel extends Label {
    public ScalingLabel(ScaledDoubleBinding width, String text) {
        super(text);
        this.prefWidthProperty().bind(new StrLenBinding(width.getDoubleProperty(), this.textProperty()));
        ObjectProperty<Font> fontProperty = new SimpleObjectProperty<>();
        this.fontProperty().bind(fontProperty);
        fontProperty.set(Font.font(this.prefWidthProperty().get() / (this.textProperty().get().length()) / 4 - 6));
        Label reference = this;
        width.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                double textSize = newWidth.doubleValue() / (reference.textProperty().get().length()) / 4 - 6; // Trial and error number, sorry.....  Still need to find a better solution for this.
                fontProperty.set(Font.font(textSize));
            }
        });
    }
}
