package ui.components.mainpanes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;


public abstract class PanePack {
    private ViewBindingsPack getViewBindings;
    private ViewRequestHandler requestHandler;
    private Pane corePane;

    public PanePack(Pane corePane, ViewBindingsPack viewBindings, ViewRequestHandler requestHandler) {
        this.corePane = corePane;
        this.getViewBindings = viewBindings;
        this.requestHandler = requestHandler;
    }

    public ViewBindingsPack getViewBindings() {
        return getViewBindings;
    }

    public ViewRequestHandler getRequestHandler() {
        return requestHandler;
    }

    public abstract void switchPane(PaneKeys key);

    public Pane getCorePane() {
        return corePane;
    }

    public abstract Node getPane(PaneKeys key);
}
