package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

public class SanctuaryView extends ScalingStackPane {

    private ScalingHBox mainContainer;
    private ScalingVBox profileDisplay;
    private ScalingLabel name;
    private ScalingLabel age;
    private ScalingLabel gender;
    private ViewBindingsPack viewBindings;

    public SanctuaryView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        this.viewBindings = viewBindings;
        init();
    }

    private void init() {
        profileDisplay = new ScalingVBox(viewBindings.widthProperty());
        mainContainer = new ScalingHBox(viewBindings.heightProperty());
        initLabels();
        mainContainer.getChildren().add(profileDisplay);
        this.getChildren().add(mainContainer);
    }

    private void initLabels() {
        name = new ScalingLabel(viewBindings.widthProperty(), "Jack", 0.3);
        age = new ScalingLabel(viewBindings.widthProperty(), "21", 0.2);
        gender = new ScalingLabel(viewBindings.widthProperty(), "Male", 0.3);
        profileDisplay.getChildren().addAll(name, age, gender);
    }
}
