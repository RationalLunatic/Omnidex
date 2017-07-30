package ui.features.beaconviews;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeItem;
import resources.sqlite.InventoryCategories;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

public class LiteratureView extends ScalingTabPane {
    private ScalingTabPane fiction;
    private ScalingTabPane nonfiction;

    public LiteratureView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
        loadLiteratureElements("Fiction", fiction);
        loadLiteratureElements("Nonfiction", nonfiction);
    }

    private void init() {
        fiction = new ScalingTabPane(getRequestSender(), getViewBindings(), getPersonalKey());
        nonfiction = new ScalingTabPane(getRequestSender(), getViewBindings(), getPersonalKey());
        Tab fictionTab = new Tab("Fiction");
        Tab nonfictionTab = new Tab("Nonfiction");
        fictionTab.setContent(fiction);
        nonfictionTab.setContent(nonfiction);
        this.getTabs().addAll(fictionTab, nonfictionTab);
    }

    private void loadTopicElements(String parentName, ScalingStackPane parentPane) {
        ScalingVBox bookListContainer = new ScalingVBox(getViewBindings());
        for(String childName : SQLiteJDBC.getInstance().getChildrenOfLibraryItem(parentName)) {
            bookListContainer.getChildren().add(
                    new Label(childName)
            );
        }
        parentPane.getChildren().add(bookListContainer);
    }

    private void loadLiteratureElements(String parentName, ScalingTabPane parentPane) {
        for(String childName : SQLiteJDBC.getInstance().getChildrenOfLibraryItem(parentName)) {
            Tab childTab = new Tab(childName);
            if(SQLiteJDBC.getInstance().checkLibraryCategory(childName) == InventoryCategories.TOPIC) {
                ScalingStackPane topicPane = new ScalingStackPane(getRequestSender(), getViewBindings(), getPersonalKey());
                childTab.setContent(topicPane);
                parentPane.getTabs().add(childTab);
                if(!SQLiteJDBC.getInstance().getChildrenOfLibraryItem(parentName).isEmpty()) {
                    loadTopicElements(childName, topicPane);
                }
            } else {
                ScalingTabPane childPane = new ScalingTabPane(getRequestSender(), getViewBindings(), getPersonalKey());
                childTab.setContent(childPane);
                parentPane.getTabs().add(childTab);
                if(!SQLiteJDBC.getInstance().getChildrenOfLibraryItem(parentName).isEmpty()) {
                    loadLiteratureElements(childName, childPane);
                }
            }

        }
    }
}
