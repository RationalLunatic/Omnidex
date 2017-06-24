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
import resources.StringFormatUtility;
import ui.custombindings.ScaledDoubleBinding;
import ui.components.ScalingLabel;
import ui.components.ScalingStackPane;

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
    private final Color DEFAULT_BORDER = Color.BLACK;
    private final Color DEFAULT_HIGHLIGHT = Color.AQUA;
    private final Color DEFAULT_CLICK = Color.AZURE;
    private final double TOP_ROW_MID_WIDTH = 2.0/3;
    private final double TOP_ROW_CORNER_WIDTH = 1.0/6;
    private final double DAY_OF_MONTH_SCALING_FACTOR = 1.5;
    private final double DAY_OF_WEEK_SCALING_FACTOR = 6;
    private ScaledDoubleBinding cornerScale;
    private ScaledDoubleBinding centerScale;

    public DayTile(ScaledDoubleBinding width, ScaledDoubleBinding height, LocalDate date) {
        super(width, height);
        this.date = date;
        cornerScale = new ScaledDoubleBinding(width, TOP_ROW_CORNER_WIDTH);
        centerScale = new ScaledDoubleBinding(width, TOP_ROW_MID_WIDTH);
        initStackPane();
        initContainer();
        init();
    }

    private void initStackPane() {
        border = new Rectangle(0, 0);
        border.widthProperty().bind(this.prefWidthProperty());
        border.heightProperty().bind(this.prefHeightProperty());
        border.setFill(Color.TRANSPARENT);
        border.setStroke(DEFAULT_BORDER);
        border.setStrokeWidth(1.0);
        this.getChildren().add(border);
        this.setOnMouseEntered(e -> highlight());
        this.setOnMouseExited(e -> resetHighlight());
        this.setOnMouseClicked(e -> openDay());
    }

    private void highlight() {
        border.setStroke(DEFAULT_HIGHLIGHT);
    }

    private void resetHighlight() {
        border.setStroke(DEFAULT_BORDER);
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
        borderFlash.getChildren().add(generateBorderAnimation(DEFAULT_HIGHLIGHT, DEFAULT_CLICK, Duration.millis(50)));
        borderFlash.setCycleCount(1);
        borderFlash.play();
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
        String weekDay = StringFormatUtility.capitalize(date.getDayOfWeek().toString());
        int dayOfMonth = date.getDayOfMonth();
        HBox left = new HBox();
        left.prefWidthProperty().bind(cornerScale);
        HBox mid = new HBox();
        mid.prefWidthProperty().bind(centerScale);
        HBox right = new HBox();
        right.prefWidthProperty().bind(cornerScale);
        mid.getChildren().add(new ScalingLabel(centerScale, DAY_OF_WEEK_SCALING_FACTOR, weekDay));
        mid.setAlignment(Pos.CENTER);
        right.getChildren().add(new ScalingLabel(cornerScale, DAY_OF_MONTH_SCALING_FACTOR,"" + dayOfMonth));
        right.setAlignment(Pos.CENTER_LEFT);
        topRow.getChildren().addAll(left, mid, right);
        topRow.setAlignment(Pos.CENTER);
    }

    public LocalDate getDate() { return date; }
}
