package ui.components.interviewcommunications;

/**
 * Created by shaev_000 on 7/7/2017.
 */
public class SubViewCommLink extends ViewRequestHandler {

    public SubViewCommLink(ViewRequestHandler commLink) {
        setSuccessor(commLink);
    }
}
