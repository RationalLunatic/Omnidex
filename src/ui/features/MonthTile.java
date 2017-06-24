package ui.features;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.StringFormatUtility;
import ui.components.ScalingAnchorPane;
import ui.custombindings.ScaledDoubleBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class MonthTile extends ScalingAnchorPane {
    public static double DAY_TILE_FRACTION = 1.0/7;

    private ScaledDoubleBinding tileWidthBinding;
    private ScaledDoubleBinding tileHeightBinding;

    private LocalDate referenceDate;
    private VBox monthContainer;
    private List<DayTile> days;

    public MonthTile(ScaledDoubleBinding width, ScaledDoubleBinding height, LocalDate referenceDate) {
        super(width, height);
        tileWidthBinding = new ScaledDoubleBinding(width, DAY_TILE_FRACTION);
        tileHeightBinding = new ScaledDoubleBinding(height, DAY_TILE_FRACTION);
        monthContainer = new VBox();
        days = new ArrayList<>();
        this.referenceDate = referenceDate;
        this.getChildren().add(monthContainer);
        init();
    }

    private void init() {
        HBox monthTitle = new HBox();
        monthTitle.setAlignment(Pos.CENTER);
        monthTitle.getChildren().add(new Label(StringFormatUtility.capitalize(referenceDate.getMonth().toString())));
        monthContainer.getChildren().add(monthTitle);
        LocalDate currentDay = LocalDate.of(referenceDate.getYear(), referenceDate.getMonth(), 1);
        HBox currentBox = new HBox();
        currentBox = initFirstWeek(currentBox, currentDay);
        while(!(monthGenerationComplete(currentDay))) {
            if(!currentBox.getChildren().isEmpty() && currentDay.getDayOfWeek() == DayOfWeek.SUNDAY) {
                monthContainer.getChildren().add(currentBox);
                currentBox = new HBox();
            }
            currentDay = addTile(currentBox, currentDay);
        }
        finishLastWeek(currentBox, currentDay);
    }

    private LocalDate addTile(HBox currentBox, LocalDate currentDay) {
        DayTile tile = new DayTile(tileWidthBinding, tileHeightBinding, currentDay);
        days.add(tile);
        currentBox.getChildren().add(tile);
        return LocalDate.ofEpochDay(currentDay.toEpochDay() + 1);
    }

    private void finishLastWeek(HBox currentBox, LocalDate currentDay) {
        while(currentDay.getDayOfWeek() != DayOfWeek.SUNDAY) {
            currentDay = addTile(currentBox, currentDay);
        }
        monthContainer.getChildren().add(currentBox);
    }

    private HBox initFirstWeek(HBox currentBox, LocalDate currentDay) {
        int difference = Math.abs(currentDay.getDayOfWeek().getValue() % DayOfWeek.SUNDAY.getValue());
        LocalDate startDate = LocalDate.ofEpochDay(currentDay.toEpochDay() - difference);
        while(startDate.toEpochDay() != currentDay.toEpochDay()) {
            startDate = addTile(currentBox, startDate);
        }
        return currentBox;
    }

    private boolean monthGenerationComplete(LocalDate currentDay) {
        boolean isStartOfMonth = currentDay.getDayOfMonth() == 1;
        boolean isNextMonth = currentDay.getMonthValue() == referenceDate.getMonthValue() + 1;
        boolean isNextMonthAndNewYear = currentDay.getMonthValue() == 1 && referenceDate.getMonthValue() != 1;
        return isStartOfMonth && (isNextMonth || isNextMonthAndNewYear);
    }

    public boolean containsDate(LocalDate toCheck) {
        for(DayTile day : days) {
            if(day.getDate().equals(toCheck)) {
                return true;
            }
        }
        return false;
    }

}
