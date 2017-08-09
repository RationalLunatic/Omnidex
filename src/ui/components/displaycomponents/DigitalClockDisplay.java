package ui.components.displaycomponents;

import javafx.application.Platform;
import javafx.geometry.Pos;
import resources.StringFormatUtility;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

public class DigitalClockDisplay extends ScalingHBox {
    private ScalingLabel digitalClockDisplay;

    public DigitalClockDisplay(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }
    private void init() {
        digitalClockDisplay = new ScalingLabel(getViewBindings().widthProperty(), "", 0.35);
        digitalClockDisplay.setText(StringFormatUtility.convertToHourMinutesSeconds(LocalTime.now()));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        digitalClockDisplay.setText(StringFormatUtility.convertToHourMinutesSeconds(LocalTime.now()));
                    }
                });
            }
        }, 0, 1000);

        this.getChildren().add(digitalClockDisplay);
        this.setAlignment(Pos.TOP_CENTER);
    }
}
