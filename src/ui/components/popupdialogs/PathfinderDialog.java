package ui.components.popupdialogs;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Deadline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PathfinderDialog extends Dialog {
    private GridPane gridPane;
    private TextField titleField;
    private TextField descriptionField;
    private ButtonType submitButtonType;

    public PathfinderDialog(String title) {
        init(title);
    }

    private void init(String title) {
        initEssentials(title);
        initTextFields(title);
        initGridPane();
        initButtons(title);
        initContentAndFocus();
        setResultConverter();
    }

    private void initContentAndFocus() {
        this.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> titleField.requestFocus());
    }

    private void initButtons(String title) {
        submitButtonType = new ButtonType("Submit " + title, ButtonBar.ButtonData.OK_DONE);
        this.getDialogPane().getButtonTypes().addAll(submitButtonType, ButtonType.CANCEL);
        Node submitButton = this.getDialogPane().lookupButton(submitButtonType);
        submitButton.setDisable(true);
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            submitButton.setDisable(newValue.trim().isEmpty());
        });
    }

    private void initEssentials(String title) {
        this.setTitle("Create " + title);
        this.setHeaderText("Enter " + title + " Details");
    }

    private void initGridPane() {
        gridPane = new GridPane();
        gridPane.add(new Label("Title: "), 0, 0);
        gridPane.add(titleField, 1, 0);
        gridPane.add(new Label("Description: "), 0, 1);
        gridPane.add(descriptionField, 1, 1);
    }

    private void initTextFields(String title) {
        titleField = new TextField();
        descriptionField = new TextField();
        titleField.setPromptText("Enter " + title + " Title");
        descriptionField.setPromptText("Enter Description");
    }

    protected void setResultConverter() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == submitButtonType) {
                return new BasicTask(titleField.getText(), descriptionField.getText(), 0);
            }
            return null;
        });
    }

    protected TextField getTitleField() { return titleField; }
    protected TextField getDescriptionField() { return descriptionField; }
    protected ButtonType getSubmitButtonType() { return submitButtonType; }
    protected GridPane getGridPane() { return gridPane; }
}
