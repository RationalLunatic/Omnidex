package ui.components.displaycomponents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import resources.ResourceManager;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.ArrayList;
import java.util.List;

public class SimpleListTextDisplay extends ScalingVBox {
    private String listTitle;
    private List<String> linesToDisplay;
    private ResourceManager bundleLoader;
    private ListView<String> listView;
    private ObservableList<String> data;

    public SimpleListTextDisplay(String listTitle, ViewBindingsPack viewBindings) {
        super(viewBindings);
        init(listTitle);
    }

    private void init(String title) {
        initEssentials();
        initTitle(title);
        initList();
        initBorders();
    }

    private void initTitle(String title) {
        this.listTitle = title;
        Label listTitleLabel = new Label(title);
        listTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
        this.getChildren().add(listTitleLabel);
    }
    private void initList() {
        listView.setItems(data);
        this.getChildren().add(listView);
        linesToDisplay = new ArrayList<>();
    }
    private void initBorders() {
        this.getStyleClass().addAll(bundleLoader.loadBorders());
    }

    private void initEssentials() {
        this.setAlignment(Pos.CENTER);
        listView = new ListView<>();
        data = FXCollections.observableArrayList();
        bundleLoader = new ResourceManager();
    }

    public void addLine(String line) {
        linesToDisplay.add(line);
        data.add(line);
    }

    public void removeLine(String line) {
        for(int i = 0; i < linesToDisplay.size(); i++) {
            if(linesToDisplay.get(i).equals(line)) {
                linesToDisplay.remove(i);
                reload();
            }
        }
    }

    public void clear() {
        data.clear();
        linesToDisplay = new ArrayList<>();
    }

    private void reload() {
        data.clear();
        for(String line : linesToDisplay) {
            data.add(line);
        }
    }

    public String getSelectedItem() {
        return listView.getSelectionModel().getSelectedItem();
    }

    public boolean isItemSelected() {
        return !listView.getSelectionModel().isEmpty();
    }
}
