package ui.features;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.custombindings.ScaledDoubleBinding;
import ui.components.ScalingStackPane;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class Calendar extends ScalingStackPane {
    private final double MONTH_SIZE_FRACTION = 2.0/3;
    private final double CYCLE_VBOX_FRACTION = 1.0/6;
    private ScaledDoubleBinding monthWidth;
    private ScaledDoubleBinding monthHeight;
    private ScaledDoubleBinding cycleBoxWidth;
    private LocalDate today;
    private VBox calendarContainer;
    private VBox cycleLeftContainer;
    private VBox cycleRightContainer;
    private HBox mainContainer;
    private List<MonthTile> months;
    private MonthTile currentMonth;
    private Button cycleLeft;
    private Button cycleRight;

    public Calendar(ScaledDoubleBinding width, ScaledDoubleBinding height) {
        super(width, height);
        months = new ArrayList<>();
        today = LocalDate.now();
        this.setAlignment(Pos.CENTER);
        initBoxes(width, height);
        initButtons();
        init();
    }

    private void initBoxes(ScaledDoubleBinding width, ScaledDoubleBinding height) {
        monthWidth = new ScaledDoubleBinding(width.getDoubleProperty(), MONTH_SIZE_FRACTION);
        monthHeight = new ScaledDoubleBinding(height.getDoubleProperty(), MONTH_SIZE_FRACTION);
        cycleBoxWidth = new ScaledDoubleBinding(width.getDoubleProperty(), CYCLE_VBOX_FRACTION);
        calendarContainer = new VBox();
        cycleLeftContainer = new VBox();
        cycleRightContainer = new VBox();
        mainContainer = new HBox();
        mainContainer.getChildren().addAll(cycleLeftContainer, calendarContainer, cycleRightContainer);
        this.getChildren().add(mainContainer);
    }

    private void initButtons() {
        cycleLeftContainer.prefWidthProperty().bind(cycleBoxWidth);
        cycleLeftContainer.setAlignment(Pos.CENTER);
        cycleRightContainer.prefWidthProperty().bind(cycleBoxWidth);
        cycleRightContainer.setAlignment(Pos.CENTER);
        cycleLeft = new Button("Cycle Left");
        cycleLeft.setOnAction(e -> prevMonth());
        cycleRight = new Button("Cycle Right");
        cycleRight.setOnAction(e -> nextMonth());
        cycleLeftContainer.getChildren().add(cycleLeft);
        cycleRightContainer.getChildren().add(cycleRight);
    }


    private void init() {
        calendarContainer.setAlignment(Pos.CENTER);
        LocalDate currentDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
        while(!(currentDay.getMonth() == today.getMonth() && currentDay.getYear() == today.getYear() + 1)) {
            MonthTile tile = new MonthTile(monthWidth, monthHeight, currentDay);
            months.add(tile);
            currentDay = LocalDate.ofEpochDay(currentDay.toEpochDay() + currentDay.getMonth().length(currentDay.isLeapYear()));
        }
        displayCurrentMonth();
    }

    public void displayCurrentMonth() {
        for(MonthTile month : months) {
            if(month.containsDate(LocalDate.now())) {
                displayMonth(month);
                break;
            }
        }
    }

    public void displayMonth(MonthTile month) {
        calendarContainer.getChildren().clear();
        calendarContainer.getChildren().add(month);
        currentMonth = month;
    }

    public void nextMonth() {
        stepMonth(true);
    }

    public void prevMonth() {
        stepMonth(false);
    }

    private void stepMonth(boolean forward) {
        int step = (forward) ? 1 : -1;
        int currentMonthIndex = months.indexOf(currentMonth);
        currentMonthIndex += step;
        currentMonthIndex = (currentMonthIndex == months.size()) ? 0 : currentMonthIndex;
        currentMonthIndex = (currentMonthIndex < 0) ? months.size() -1 : currentMonthIndex;
        displayMonth(months.get(currentMonthIndex));
    }
}
