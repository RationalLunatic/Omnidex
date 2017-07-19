package ui.components.editablelabel;

import com.sun.javafx.scene.control.skin.TextFieldSkin;
import javafx.application.Platform;
import javafx.collections.SetChangeListener;
import javafx.css.PseudoClass;
import javafx.scene.text.Text;

/**
 * The Skin Class for ScalingEditableLabel
 *
 * @sa ScalingEditableLabel, ScalingEditableLabelBehavior
 */
public class ScalingEditableLabelSkin extends TextFieldSkin {

    private ScalingEditableLabel editableLabel;
    private Boolean editableState;

    public ScalingEditableLabelSkin(final ScalingEditableLabel editableLabel) {
        this(editableLabel, new ScalingEditableLabelBehavior(editableLabel));
    }
    public ScalingEditableLabelSkin(final ScalingEditableLabel editableLabel, final ScalingEditableLabelBehavior editableLabelBehavior) {
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
            editableLabel.setText(calculateClipString(baseText));
        } else {
            editableLabel.setText(baseText);
            editableLabel.positionCaret(baseText.length());
        }
    }

    /**
     * Truncates text to fit into the editablelabel
     *
     * @param text The text that needs to be truncated
     * @return The truncated text with an appended "..."
     */
    private String calculateClipString(String text) {
        double labelWidth = editableLabel.getWidth();

        Text layoutText = new Text(text);
        layoutText.setFont(editableLabel.getFont());

        if ( layoutText.getLayoutBounds().getWidth() < labelWidth ) {
            return text;
        } else {
            layoutText.setText(text+"...");
            while ( layoutText.getLayoutBounds().getWidth() > labelWidth ) {
                text = text.substring(0, text.length()-1);
                layoutText.setText(text+"...");
            }
            return text+"...";
        }
    }
}
