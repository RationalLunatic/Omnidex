package ui.features;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.ResourceManager;
import ui.components.*;
import ui.components.interviewcommunications.MainViewCommLink;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class MonthView extends ScalingStackPane {
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

    public MonthView(ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(commLink, viewBindings, PaneKeys.MONTH);
        months = new ArrayList<>();
        today = LocalDate.now();
        this.setAlignment(Pos.CENTER);
        initBoxes(viewBindings.widthProperty(), viewBindings.heightProperty());
        initButtons();
        init();
    }

    private void initBoxes(ScaledDoubleBinding width, ScaledDoubleBinding height) {
        monthWidth = new ScaledDoubleBinding(width.getDoubleProperty(), getResourceManager().monthViewMonthSizeFraction());
        monthHeight = new ScaledDoubleBinding(height.getDoubleProperty(), getResourceManager().monthViewMonthSizeFraction());
        cycleBoxWidth = new ScaledDoubleBinding(width.getDoubleProperty(), getResourceManager().monthViewCycleVboxFraction());
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
        cycleLeft = new Button("<");
        cycleLeft.setOnAction(e -> prevMonth());
        cycleRight = new Button(">");
        cycleRight.setOnAction(e -> nextMonth());
        cycleLeftContainer.getChildren().add(cycleLeft);
        cycleRightContainer.getChildren().add(cycleRight);
    }


    private void init() {
        calendarContainer.setAlignment(Pos.CENTER);
        LocalDate currentDay = LocalDate.of(today.getYear(), today.getMonth(), 1);
        while(!(currentDay.getMonth() == today.getMonth() && currentDay.getYear() == today.getYear() + 1)) {
            ViewBindingsPack tileBindings = new ViewBindingsPack(monthWidth, monthHeight);
            MonthTile tile = new MonthTile(getRequestSender(), tileBindings, currentDay, getPersonalKey());
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
        stepMonth(1);
    }

    public void prevMonth() {
        stepMonth(-1);
    }

    private void stepMonth(int step) {
        int currentMonthIndex = months.indexOf(currentMonth);
        currentMonthIndex += step;
        currentMonthIndex = (currentMonthIndex == months.size()) ? 0 : currentMonthIndex;
        currentMonthIndex = (currentMonthIndex < 0) ? months.size() -1 : currentMonthIndex;
        displayMonth(months.get(currentMonthIndex));
    }
}
