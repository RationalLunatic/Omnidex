package ui.components.interviewcommunications;

import ui.components.PaneKeys;

import java.time.LocalDate;

public class ViewRequest {
    private PaneKeys targetView;
    private LocalDate localDate;

    public ViewRequest(PaneKeys key) {
        localDate = LocalDate.now();
        targetView = key;
    }

    public ViewRequest(PaneKeys key, LocalDate localDate) {
        this.localDate = localDate;
        targetView = key;
    }

    public PaneKeys getTargetView() {
        return targetView;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }
}
