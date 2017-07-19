package ui.features;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.StringFormatUtility;
import ui.components.PaneKeys;
import ui.components.scalingcomponents.ScalingAnchorPane;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MonthTile extends ScalingAnchorPane {
    private ScaledDoubleBinding tileWidthBinding;
    private ScaledDoubleBinding tileHeightBinding;
    private LocalDate referenceDate;
    private VBox monthContainer;
    private List<DayTile> days;

    public MonthTile(ViewRequestHandler commLink, ViewBindingsPack viewBindings, LocalDate referenceDate, PaneKeys key) {
        super(commLink, viewBindings, key);
        tileWidthBinding = new ScaledDoubleBinding(viewBindings.widthProperty(), getResourceManager().monthTileDayTileFraction());
        tileHeightBinding = new ScaledDoubleBinding(viewBindings.heightProperty(), getResourceManager().monthTileDayTileFraction());
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
        ViewBindingsPack tileBindings = new ViewBindingsPack(tileWidthBinding, tileHeightBinding);
        DayTile tile = new DayTile(getRequestSender(), tileBindings, currentDay, PaneKeys.DAY);
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
