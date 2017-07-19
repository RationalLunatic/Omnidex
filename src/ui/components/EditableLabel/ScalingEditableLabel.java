package ui.components.editablelabel;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;

import java.net.URL;

public class ScalingEditableLabel extends TextField {
    private IntegerProperty clicksThreshold;
    private StringProperty baseText;

    public ScalingEditableLabel(DoubleBinding width) {
        this(width, "");
        this.prefWidthProperty().bind(width);
    }

    public ScalingEditableLabel(DoubleBinding width, String text) {
        super(text);
        this.prefWidthProperty().bind(width);
        init();
    }

    private void init() {
        clicksThreshold = new SimpleIntegerProperty(1);
        baseText = new SimpleStringProperty(getText());
        setFocusTraversable(false);
        setEditable(false);
    }

    @Override
    protected Skin<?> createDefaultSkin() { return new ScalingEditableLabelSkin(this); }


    @Override
    public String getUserAgentStylesheet() {
        URL pathToCSS = ScalingEditableLabel.class.getResource("editablelabel.css");
        if ( pathToCSS != null ) {
            return pathToCSS.toExternalForm();
        } else {
            System.err.println("CSS file for editablelabel could not be found.");
            return null;
        }
    }

    public int getClicksThreshold() {
        return clicksThreshold.get();
    }

    public IntegerProperty clicksThresholdProperty() {
        return clicksThreshold;
    }

    public void setClicksThreshold(int threshold) {
        clicksThreshold.set(threshold);
    }

    public String getBaseText() {
        return baseText.get();
    }

    public StringProperty baseTextProperty() {
        return baseText;
    }

    public void setBaseText(String text) {
        baseText.set(text);
    }
}
