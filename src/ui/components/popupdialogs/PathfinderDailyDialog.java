package ui.components.popupdialogs;

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
    private Spinner<Integer> durationSpinner;

    public PathfinderDailyDialog(String title) {
        super(title);
        init();
    }

    public PathfinderDailyDialog(Daily daily) {
        super("Daily");
        init();
        timeSpinner.getValueFactory().setValue(daily.getScheduledTime());
        durationSpinner.getValueFactory().setValue(daily.getDuration());
        getTitleField().setText(daily.getTitle());
        getDescriptionField().setText(daily.getDescription());
    }

    private void init() {
        getGridPane().add(new Label("Scheduled Time: "), 0, 2);
        initTimeSpinner();
        getGridPane().add(timeSpinner, 1, 2);
        getGridPane().add(new Label("Duration in Minutes: "), 0, 3);
        initDurationSpinner();
        getGridPane().add(durationSpinner, 1, 3);
    }

    private void initTimeSpinner() {
        timeSpinner = new Spinner<>();
        List<LocalTime> timeValues = generateTimeValues();
        SpinnerValueFactory<LocalTime> timeValueFactory = createValueFactory(timeValues, new MyTimeConverter());
        timeValueFactory.setValue(LocalTime.of(0, 0));
        timeSpinner.setValueFactory(timeValueFactory);
        timeSpinner.setEditable(true);
    }

    private <E> SpinnerValueFactory<E> createValueFactory(List<E> values, StringConverter<E> converter) {
        SpinnerValueFactory<E> valueFactory = new SpinnerValueFactory<E>() {
            @Override
            public void decrement(int steps) {
                E current = this.getValue();
                int idx = values.indexOf(current);
                int newIdx = (values.size() + idx - steps) % values.size();
                E newValue = values.get(newIdx);
                this.setValue(newValue);
            }

            @Override
            public void increment(int steps) {
                E current = this.getValue();
                int idx = values.indexOf(current);
                int newIdx = (idx + steps) % values.size();
                E newValue = values.get(newIdx);
                this.setValue(newValue);
            }
        };
        valueFactory.setConverter(converter);
        return valueFactory;
    }

    private void initDurationSpinner() {
        durationSpinner = new Spinner<>();
        List<Integer> durationValues = generateDurationValues();
        SpinnerValueFactory<Integer> durationValueFactory = createValueFactory(durationValues, new MyDurationConverter());
        durationValueFactory.setValue(30);
        durationSpinner.setValueFactory(durationValueFactory);
        durationSpinner.setEditable(true);
    }

    private List<Integer> generateDurationValues() {
        List<Integer> minutesInTheDay = new ArrayList<>();
        for(int i = 1; i <= 1440; i++) {
            minutesInTheDay.add(i);
        }
        return minutesInTheDay;
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
                String durationText = durationSpinner.getEditor().getText();
                durationSpinner.getValueFactory().setValue(durationSpinner.getValueFactory().getConverter().fromString(durationText));
                return new Daily(getTitleField().getText(), getDescriptionField().getText(), durationSpinner.getValue(), timeSpinner.getValue());
            }
            return null;
        });
    }

    public Spinner<LocalTime> getTimeSpinner() {
        return timeSpinner;
    }
    public Spinner<Integer> getDurationSpinner() { return durationSpinner; }

    private class MyDurationConverter extends StringConverter<Integer> {
        @Override
        public String toString(Integer object) {
            return "" + object;
        }

        @Override
        public Integer fromString(String string) {
            try {
                return Integer.parseInt(string);
            } catch(NumberFormatException e) {
                return 30;
            }
        }
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
