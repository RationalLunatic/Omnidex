package ui.features.academyviews;

import javafx.scene.control.Label;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

public class TrainingView extends ScalingVBox {
    private Label title;
    private SimpleListTextDisplay trainingExercises;

    public TrainingView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        trainingExercises = new SimpleListTextDisplay("Training", getViewBindings());
        this.getChildren().add(trainingExercises);
    }
}
