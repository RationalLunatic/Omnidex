package ui.components.displaycomponents;

import engine.components.schedule.Daily;
import engine.components.schedule.Deadline;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import resources.StringFormatUtility;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalTime;

public class ScalingTimeDisplayBlockComponent extends ScalingHBox {
    private int minuteOfDay;
    private ScalingVBox inputBox;

    public ScalingTimeDisplayBlockComponent(ViewBindingsPack viewBindings, int minuteOfDay) {
        super(viewBindings);
        this.minuteOfDay = minuteOfDay;
        init(minuteOfDay);
    }

    public void addDaily(Daily daily) {
        Label dailyLabel = new Label();
        dailyLabel.setText(daily.getTitle());
        inputBox.getChildren().add(dailyLabel);
    }

    public void addDeadline(Deadline deadline) {
        Label deadlineLabel = new Label();
        deadlineLabel.setText(deadline.getTitle());
        inputBox.getChildren().add(deadlineLabel);
    }

    private ScalingVBox generateInputBox() {
        inputBox = new ScalingVBox(getViewBindings());
        inputBox.setStyle("-fx-border-color: black; -fx-border-size: 1;");
        inputBox.setAlignment(Pos.CENTER);
        return inputBox;
    }

    private ScalingVBox generateTextBox(int minuteOfDay) {
        ScalingVBox textDisplay = new ScalingVBox(generateTextBoxBindings());
        textDisplay.getChildren().add(generateTextBoxLabel(minuteOfDay));
        return textDisplay;
    }

    private ViewBindingsPack generateTextBoxBindings() {
        ScaledDoubleBinding textWidthBinding = new ScaledDoubleBinding(getViewBindings().widthProperty(), 0.15);
        return new ViewBindingsPack(textWidthBinding, getViewBindings().heightProperty());
    }

    private Label generateTextBoxLabel(int minuteOfDay) {
        return new Label(generateTextBoxString(minuteOfDay));
    }

    public LocalTime getTime() {
        return StringFormatUtility.convertToLocalTimeFromHH_MM_AMPM(generateTextBoxString(minuteOfDay));
    }

    public int getMinuteOfDay() { return minuteOfDay; }

    private String generateTextBoxString(int minuteOfDay) {
        int currentHour = minuteOfDay / 60;
        int currentMinute = minuteOfDay % 60;
        String minuteString = (currentMinute < 10) ? "0" + currentMinute : currentMinute + "";
        String hourString = (currentHour < 10) ? "0" + currentHour : "" + currentHour;
        return StringFormatUtility.convertToHourMinutes(LocalTime.parse(hourString + ":" + minuteString));
    }

    private void init(int minuteOfDay) {
        this.getChildren().addAll(generateTextBox(minuteOfDay), generateInputBox());
    }
}
