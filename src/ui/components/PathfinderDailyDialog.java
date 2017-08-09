package ui.components;

import engine.components.schedule.Daily;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.util.StringConverter;
import resources.StringFormatUtility;

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PathfinderDailyDialog extends PathfinderDialog {
    private Spinner<LocalTime> timeSpinner;

    public PathfinderDailyDialog(String title) {
        super(title);
        init();
    }

    private void init() {
        getGridPane().add(new Label("Scheduled Time: "), 0, 2);
        timeSpinner = new Spinner<>();
        List<LocalTime> timeValues = generateTimeValues();
        SpinnerValueFactory<LocalTime> timeValueFactory = new SpinnerValueFactory<LocalTime>() {
            @Override
            public void decrement(int steps) {
                LocalTime current = this.getValue();
                int idx = timeValues.indexOf(current);
                int newIdx = (timeValues.size() + idx - steps) % timeValues.size();
                LocalTime newTime = timeValues.get(newIdx);
                this.setValue(newTime);
            }

            @Override
            public void increment(int steps) {
                LocalTime current = this.getValue();
                int idx = timeValues.indexOf(current);
                int newIdx = (idx + steps) % timeValues.size();
                LocalTime newTime = timeValues.get(newIdx);
                this.setValue(newTime);
            }
        };
        timeValueFactory.setValue(LocalTime.of(0, 0));
        timeValueFactory.setConverter(new MyTimeConverter());
        timeSpinner.setValueFactory(timeValueFactory);
        timeSpinner.setEditable(true);

        getGridPane().add(timeSpinner, 1, 2);
    }

    private List<LocalTime> generateTimeValues() {
        List<LocalTime> timeValues = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j++) {
                LocalTime currentMinute = LocalTime.of(i, j);
                timeValues.add(currentMinute);
            }
        }
        return timeValues;
    }

    @Override
    protected void setResultConverter() {
        this.setResultConverter(dialogButton -> {
            if (dialogButton == getSubmitButtonType()) {
                String text = timeSpinner.getEditor().getText();
                SpinnerValueFactory<LocalTime> valueFactory = (SpinnerValueFactory<LocalTime>) timeSpinner.getValueFactory();
                StringConverter<LocalTime> converter = valueFactory.getConverter();
                LocalTime enterValue = converter.fromString(text);
                valueFactory.setValue(enterValue);
                return new Daily(getTitleField().getText(), getDescriptionField().getText(), timeSpinner.getValue());
            }
            return null;
        });
    }

    protected Spinner<LocalTime> getTimeSpinner() {
        return timeSpinner;
    }

    private class MyTimeConverter extends StringConverter<LocalTime> {

        @Override
        public String toString(LocalTime object) {
            return StringFormatUtility.convertToHourMinutes(object);
        }

        @Override
        public LocalTime fromString(String string) {
            try {
                return StringFormatUtility.convertToLocalTimeFromHH_MM_AMPM(string);
            } catch(DateTimeParseException e) {
                return LocalTime.now();
            }
        }
    }
}
