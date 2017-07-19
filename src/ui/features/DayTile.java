package ui.features;

import engine.components.schedule.Deadline;
import javafx.animation.SequentialTransition;
import javafx.animation.StrokeTransition;
import javafx.geometry.Pos;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import resources.ResourceManager;
import resources.StringFormatUtility;
import ui.components.*;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class DayTile extends ScalingStackPane {
    private LocalDate date;
    private List<Deadline> deadlines;
    private Rectangle border;
    private VBox container;
    private HBox topRow;
    private HBox midRow;
    private HBox lowRow;
    private ScaledDoubleBinding cornerScale;
    private ScaledDoubleBinding centerScale;

    public DayTile(ViewRequestHandler commLink, ViewBindingsPack viewBindings, LocalDate date, PaneKeys key) {
        super(commLink, viewBindings, key);
        this.date = date;
        cornerScale = new ScaledDoubleBinding(viewBindings.widthProperty(), getResourceManager().dayTileTopRowCornerWidth());
        centerScale = new ScaledDoubleBinding(viewBindings.widthProperty(), getResourceManager().dayTileTopRowMidWidth());
        initStackPane();
        initContainer();
        init();
    }

    private void initStackPane() {
        border = new Rectangle(0, 0);
        border.widthProperty().bind(this.prefWidthProperty());
        border.heightProperty().bind(this.prefHeightProperty());
        border.setFill(Color.TRANSPARENT);
        border.setStroke(getResourceManager().dayTileDefaultBorder());
        border.setStrokeWidth(1.0);
        this.getChildren().add(border);
        this.setOnMouseEntered(e -> highlight());
        this.setOnMouseExited(e -> resetHighlight());
        this.setOnMouseClicked(e -> openDay());
    }

    private void highlight() {
        border.setStroke(getResourceManager().dayTileDefaultHighlight());
    }

    private void resetHighlight() {
        border.setStroke(getResourceManager().dayTileDefaultBorder());
    }

    private StrokeTransition generateBorderAnimation(Color fromColor, Color toColor, Duration duration) {
        StrokeTransition highlightAnimation = new StrokeTransition();
        highlightAnimation.setFromValue(fromColor);
        highlightAnimation.setToValue(toColor);
        highlightAnimation.setDuration(duration);
        highlightAnimation.setShape(border);
        return highlightAnimation;
    }


    private void openDay() {
        SequentialTransition borderFlash = new SequentialTransition();
        borderFlash.getChildren().add(generateBorderAnimation(getResourceManager().dayTileDefaultHighlight(), getResourceManager().dayTileDefaultClick(), Duration.millis(50)));
        borderFlash.setCycleCount(1);
        borderFlash.play();
        sendViewRequest(new ViewRequest(getPersonalKey(), date));
    }


    private void initContainer() {
        container = new VBox();
        topRow = new HBox();
        midRow = new HBox();
        lowRow = new HBox();
        container.getChildren().addAll(topRow, midRow, lowRow);
        this.getChildren().add(container);
        AnchorPane.setLeftAnchor(container, 0.0);
        AnchorPane.setRightAnchor(container, 0.0);
        AnchorPane.setTopAnchor(container, 0.0);
        AnchorPane.setBottomAnchor(container, 0.0);
    }


    private void init() {
        HBox left = new HBox();
        HBox mid = new HBox();
        HBox right = new HBox();
        left.prefWidthProperty().bind(cornerScale);
        mid.prefWidthProperty().bind(centerScale);
        right.prefWidthProperty().bind(cornerScale);
        mid.getChildren().add(new ScalingLabel(centerScale, StringFormatUtility.capitalize(date.getDayOfWeek().toString())));
        right.getChildren().add(new ScalingLabel(cornerScale,"" + date.getDayOfMonth()));
        topRow.getChildren().addAll(left, mid, right);
        mid.setAlignment(Pos.CENTER);
        right.setAlignment(Pos.CENTER_LEFT);
        topRow.setAlignment(Pos.CENTER);
    }

    public LocalDate getDate() { return date; }
}
