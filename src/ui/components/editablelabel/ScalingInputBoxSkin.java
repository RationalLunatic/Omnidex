package ui.components.editablelabel;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.text.Text;

/**
 * The Skin Class for ScalingInputBox
 *
 * @sa ScalingInputBox, ScalingInputBoxBehavior
 */
public class ScalingInputBoxSkin extends TextFieldSkin {

    private ScalingInputBox editableLabel;
    private Boolean editableState;

    public ScalingInputBoxSkin(final ScalingInputBox editableLabel) {
        this(editableLabel, new ScalingInputBoxBehavior(editableLabel));
    }
    public ScalingInputBoxSkin(final ScalingInputBox editableLabel, final ScalingInputBoxBehavior editableLabelBehavior) {
        super(editableLabel, editableLabelBehavior);
        this.editableLabel = editableLabel;
        init();
    }

    private void init() {
        editableState = false;

        Platform.runLater(this::updateVisibleText);

        // Register listeners and binds
        editableLabel.getPseudoClassStates().addListener( (SetChangeListener<PseudoClass>) e -> {
            if (e.getSet().contains(PseudoClass.getPseudoClass("editable"))) {
                if ( !editableState ) {
                    editableState = true;
                    updateVisibleText();
                }
            } else {
                if ( editableState ) {
                    editableState = false;
                    updateVisibleText();
                }
            }
        });
        editableLabel.widthProperty().addListener( observable -> updateVisibleText() );
        editableLabel.baseTextProperty().addListener( observable -> updateVisibleText() );
    }

    private void updateVisibleText() {
        String baseText = editableLabel.getBaseText();
        if ( !editableState ) {
            editableLabel.setText(baseText);
        } else {
            editableLabel.setText(baseText);
            editableLabel.positionCaret(baseText.length());
        }
    }
}
