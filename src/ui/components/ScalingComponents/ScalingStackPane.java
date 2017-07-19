package ui.components.scalingcomponents;

import javafx.beans.binding.DoubleBinding;
import ui.components.*;
import ui.components.interviewcommunications.SubViewCommLink;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;


public class ScalingStackPane extends CenterParentScalingStackPane {
    private PaneKeys personalKey;
    private ViewRequestHandler requestSender;
    private ViewRequestHandler requestReceiver;
    private ViewBindingsPack viewBindings;
    public ScalingStackPane(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(viewBindings);
        this.viewBindings = viewBindings;
        requestReceiver = commLink;
        requestSender = new SubViewCommLink(requestReceiver);
        personalKey = key;
    }

    public void sendViewRequest(ViewRequest request)  {
        requestSender.handleRequest(request);
    }

    public PaneKeys getPersonalKey() { return personalKey; }

    public ViewRequestHandler getRequestSender() { return requestSender; }

    public ViewBindingsPack getViewBindings() {
        return viewBindings;
    }
}
