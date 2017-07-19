package ui.components.scalingcomponents;

// This is where the Chain of Responsibility ends; final link in the communication chain.
// Therefore, this pane does not require a commLink in its constructor.
// If this had a ViewRequestHandler in its constructor, it would be impossible to initialize in MainView,
// because the MainViewCommLink requires a pane for its constructor.   Thus, they would each require the other object
// to be initialized before itself, and initialization would be impossible for both.  Thus, there must be at least one
// pane in the chain of responsibility that does not take a commLink/ViewRequestHandler in its constructor.

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.StackPane;
import resources.ResourceManager;

public class CenterParentScalingStackPane extends StackPane {

    private ResourceManager bundleLoader;

    public CenterParentScalingStackPane(ViewBindingsPack viewBindings) {
        this.prefWidthProperty().bind(viewBindings.widthProperty());
        this.prefHeightProperty().bind(viewBindings.heightProperty());
        bundleLoader = new ResourceManager();
    }

    public ResourceManager getResourceManager() { return bundleLoader; }
}
