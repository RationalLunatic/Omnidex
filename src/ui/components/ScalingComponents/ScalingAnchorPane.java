package ui.components.scalingcomponents;

import javafx.scene.layout.AnchorPane;
import resources.ResourceManager;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.SubViewCommLink;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;

public class ScalingAnchorPane extends AnchorPane {
    private ViewRequestHandler requestSender;
    private ViewRequestHandler requestReceiver;
    private PaneKeys personalKey;
    private ResourceManager bundleLoader;

    public ScalingAnchorPane(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        requestReceiver = commLink;
        requestSender = new SubViewCommLink(commLink);
        this.prefWidthProperty().bind(viewBindings.widthProperty());
        this.prefHeightProperty().bind(viewBindings.heightProperty());
        personalKey = key;
        bundleLoader = new ResourceManager();
    }

    public void sendViewRequest(ViewRequest request)  {
        requestSender.handleRequest(request);
    }

    public PaneKeys getPersonalKey() { return personalKey; }

    public ViewRequestHandler getRequestSender() { return requestSender; }

    public ResourceManager getResourceManager() { return bundleLoader; }
}
