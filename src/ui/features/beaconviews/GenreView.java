package ui.features.beaconviews;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import resources.StringFormatUtility;
import resources.sqlite.InventoryCategories;
import resources.sqlite.SQLiteJDBC;
import ui.components.editablelabel.ScalingEditableLabel;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.util.HashMap;
import java.util.Map;


public class GenreView extends ScalingVBox {
    private final double textScale = 0.6;
    private ScalingVBox genreInputContainer;
    private TreeView<ScalingLabel> genreDisplay;
    private TreeItem<ScalingLabel> fictionItem;
    private TreeItem<ScalingLabel> nonfictionItem;
    private ScalingLabel fiction;
    private ScalingLabel nonfiction;
    private ScalingEditableLabel genreInputBox;
    private ScalingButton addNewGenre;
    private Map<TreeItem<ScalingLabel>, InventoryCategories> itemCategory;

    public GenreView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        initCategoryMap();
        initUIElements();
        initUIBehavior();
        initTreeView();
        loadGenreView();
        this.getChildren().add(genreDisplay);
        this.getChildren().add(genreInputContainer);
    }

    private void loadGenreView() {
        loadTreeElements("Fiction", fictionItem);
        loadTreeElements("Nonfiction", nonfictionItem);
    }

    private void loadTreeElements(String parentName, TreeItem<ScalingLabel> parentItem) {
        for(String childName : SQLiteJDBC.getInstance().getChildrenOfLibraryItem(parentName)) {
            InventoryCategories category;
            if(parentItem == fictionItem || parentItem == nonfictionItem) category = InventoryCategories.GENRE;
            else category = discernCategoryOfChild(SQLiteJDBC.getInstance().checkLibraryCategory(parentItem.getValue().getText()));
            TreeItem<ScalingLabel> child = addTreeItem(childName, parentItem, category);
            System.out.println(parentName);
            System.out.println(childName);
            if(!SQLiteJDBC.getInstance().getChildrenOfLibraryItem(childName).isEmpty()) {
                loadTreeElements(childName, child);
            }
        }
    }

    private void initCategoryMap() {
        itemCategory = new HashMap<>();
    }

    private void initUIBehavior() {
        addNewGenre.setOnMouseClicked(e -> addTreeItem());
    }

    private void addTreeItem() {
        if(!genreInputBox.getText().isEmpty()) {
            if(genreDisplay.getSelectionModel().getSelectedItem() != null) {
                TreeItem<ScalingLabel> parentElement = genreDisplay.getSelectionModel().getSelectedItem();
                if(SQLiteJDBC.getInstance().checkLibraryCategory(parentElement.getValue().getText()) == InventoryCategories.BOOK) {
                    parentElement = parentElement.getParent();
                }
                addTreeItem(genreInputBox.getText(), parentElement, discernCategoryOfChild(parentElement));
                SQLiteJDBC.getInstance().addToLibrary(genreInputBox.getText(), discernCategoryOfChild(parentElement), parentElement.getValue().getText());
            }
        }
    }

    private TreeItem<ScalingLabel> addTreeItem(String itemName, TreeItem<ScalingLabel> parent, InventoryCategories category) {
        ScalingLabel newGenre = new ScalingLabel(getViewBindings().widthProperty(),
                StringFormatUtility.capitalize(itemName), textScale);
        TreeItem<ScalingLabel> newGenreItem = new TreeItem<>(newGenre);
        itemCategory.put(newGenreItem, category);
        parent.getChildren().add(newGenreItem);
        return newGenreItem;
    }

    private void initUIElements() {
        genreDisplay = new TreeView<>();
        fiction = new ScalingLabel(getViewBindings().widthProperty(), "Fiction", textScale);
        nonfiction = new ScalingLabel(getViewBindings().widthProperty(), "Nonfiction", textScale);
        initGenreInput();
    }

    private void initTreeView() {
        fictionItem = new TreeItem<>(fiction);
        nonfictionItem = new TreeItem<>(nonfiction);
        itemCategory.put(fictionItem, InventoryCategories.GENRE);
        itemCategory.put(nonfictionItem, InventoryCategories.GENRE);
        TreeItem<ScalingLabel> root = new TreeItem<>();
        root.getChildren().addAll(fictionItem, nonfictionItem);
        genreDisplay.setRoot(root);
        genreDisplay.setShowRoot(false);
        genreDisplay.prefHeightProperty().bind(getViewBindings().heightProperty());
    }

    private void initGenreInput() {
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonBindings = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        genreInputContainer = new ScalingVBox(getViewBindings());
        addNewGenre = new ScalingButton("Submit New Genre", buttonBindings);
        genreInputBox = new ScalingEditableLabel(getViewBindings().widthProperty());
        genreInputContainer.getChildren().addAll(genreInputBox, addNewGenre);
    }

    private InventoryCategories discernCategoryOfChild(InventoryCategories category) {
        if(category == InventoryCategories.GENRE) return InventoryCategories.TOPIC;
        else return InventoryCategories.BOOK;
    }

    private InventoryCategories discernCategoryOfChild(TreeItem<ScalingLabel> libraryTreeItem) {
        if(libraryTreeItem == fictionItem || libraryTreeItem == nonfictionItem) {
            return InventoryCategories.GENRE;
        } else if(itemCategory.get(libraryTreeItem) == InventoryCategories.GENRE) return InventoryCategories.TOPIC;
        else if(itemCategory.get(libraryTreeItem) == InventoryCategories.TOPIC) return InventoryCategories.BOOK;
        else if(itemCategory.get(libraryTreeItem) == InventoryCategories.BOOK) return InventoryCategories.BOOK;
        else return InventoryCategories.GENRE;
    }
}
