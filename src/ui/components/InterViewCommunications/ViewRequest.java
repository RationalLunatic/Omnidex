package ui.components.interviewcommunications;

import ui.components.PaneKeys;

import java.time.LocalDate;

public class ViewRequest {
    private PaneKeys targetView;
    private LocalDate localDate;
    private String listUpdate;
    private ViewRequestKeys requestType;

    public ViewRequest(PaneKeys key) {
        localDate = LocalDate.now();
        targetView = key;
        requestType = ViewRequestKeys.VIEW_UPDATE;
    }

    public ViewRequest(PaneKeys key, LocalDate localDate) {
        this.localDate = localDate;
        targetView = key;
        requestType = ViewRequestKeys.VIEW_UPDATE;
    }

    public ViewRequest(PaneKeys key, ViewRequestKeys requestType, String listUpdate) {
        this.targetView = key;
        this.listUpdate = listUpdate;
        this.requestType = requestType;
    }

    public ViewRequest(PaneKeys key, ViewRequestKeys requestType) {
        this.targetView = key;
        this.requestType = requestType;
    }

    public PaneKeys getTargetView() {
        return targetView;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public ViewRequestKeys getRequestType() { return requestType; }
}
