package ui.components;

import ui.custombindings.ScaledDoubleBinding;
import ui.custombindings.ScaledDoubleBinding;
import ui.features.Calendar;

/**
 * Created by shaev_000 on 7/27/2016.
 */
public class CenterPanes {
    private Calendar calendar;

    public CenterPanes(ScaledDoubleBinding width, ScaledDoubleBinding height) {
        calendar = new Calendar(width, height);
    }

    public Calendar getCalendar() {
        return calendar;
    }
}
