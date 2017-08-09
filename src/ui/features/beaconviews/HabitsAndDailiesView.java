package ui.features.beaconviews;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Daily;
import engine.components.schedule.Deadline;
import engine.components.schedule.Habit;
import javafx.util.Pair;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.displaycomponents.PrefixedListTextDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HabitsAndDailiesView extends ScalingVBox {
    private SimpleListTextDisplay habits;
    private PrefixedListTextDisplay dailies;

    public HabitsAndDailiesView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
        loadHabitsAndDailies();
    }

    private void init() {
        dailies = new PrefixedListTextDisplay("Dailies", getViewBindings());
        habits = new SimpleListTextDisplay("Habits", getViewBindings());
        this.getChildren().addAll(dailies, habits);
    }

    public void loadHabitsAndDailies() {
        dailies.clear();
        habits.clear();
        List<Daily> basicTasksFromDB = SQLiteJDBC.getInstance().getPathfinderIO().getDailies();
        Comparator<Daily> dailyComparator = (e1, e2) ->
             (e1.getScheduledTime().compareTo(e2.getScheduledTime()));
        basicTasksFromDB = basicTasksFromDB.stream().sorted(dailyComparator).collect(Collectors.toList());
        for(Daily daily : basicTasksFromDB) {
            dailies.addLine(new Pair<>(StringFormatUtility.convertToHourMinutes(daily.getScheduledTime()), daily.getTitle()));
        }
        List<Habit> habitsFromDB = SQLiteJDBC.getInstance().getPathfinderIO().getHabits();
        for(Habit habit : habitsFromDB) {
            habits.addLine(habit.getTitle());
        }
    }

    public String getSelectedHabitItem() {
        if(habits.getSelectedItem() != null) {
            return habits.getSelectedItem();
        } else {
            return "";
        }
    }
    public String getSelectedDailyItem() {
        if(dailies.getSelectedItem() != null) {
            return dailies.getSelectedItem();
        } else {
            return "";
        }
    }
}
