package ui.components.scalingcomponents;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import resources.ResourceManager;
import ui.custombindings.ScaledDoubleBinding;
import ui.custombindings.StrLenBinding;

public class ScalingLabel extends Label {
    public ScalingLabel(ScaledDoubleBinding width, String text, double desiredScalingPercentage) {
        super(text);
        this.setAlignment(Pos.TOP_CENTER);
        ScaledDoubleBinding boundWidth = new ScaledDoubleBinding(width.getDoubleProperty(), desiredScalingPercentage);
        this.prefWidthProperty().bind(boundWidth);
        Label reference = this;
        boundWidth.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                double textSize = newWidth.doubleValue() / (reference.textProperty().get().length()) ;
                int size = (int)Math.floor(textSize);
                reference.setStyle("-fx-font-size: " + size + "px;");
            }
        });
    }
}
