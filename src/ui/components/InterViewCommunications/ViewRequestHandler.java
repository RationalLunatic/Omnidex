package ui.components.interviewcommunications;

/**
 * Created by shaev_000 on 7/7/2017.
 */
public abstract class ViewRequestHandler {
    private ViewRequestHandler successor;

    public void setSuccessor(ViewRequestHandler successor) {
        this.successor = successor;
    }

    // Passes the request along until it finds a handler with an overridden method.
    public void handleRequest(ViewRequest request) {
        successor.handleRequest(request);
    }
}
