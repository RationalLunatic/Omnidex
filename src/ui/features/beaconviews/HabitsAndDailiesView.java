package ui.features.beaconviews;

import engine.components.schedule.Daily;
import engine.components.schedule.Habit;
import javafx.util.Pair;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.popupdialogs.PathfinderDailyDialog;
import ui.components.displaycomponents.PrefixedListTextDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.DoubleClickReader;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HabitsAndDailiesView extends ScalingVBox {
    private SimpleListTextDisplay habits;
    private PrefixedListTextDisplay dailies;
    private DoubleClickReader doubleClickReader;

    public HabitsAndDailiesView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
        loadHabitsAndDailies();
        setMouseBehaviors();
    }

    private void init() {
        doubleClickReader = new DoubleClickReader();
        dailies = new PrefixedListTextDisplay("Dailies", getViewBindings());
        habits = new SimpleListTextDisplay("Habits", getViewBindings());
        this.getChildren().addAll(dailies, habits);
    }

    public void loadHabitsAndDailies() {
        dailies.clear();
        habits.clear();
        List<Daily> dailiesFromDB = sortDailies(SQLiteJDBC.getInstance().getPathfinderIO().getDailies());
        for(Daily daily : dailiesFromDB) {
            dailies.addLine(new Pair<>(StringFormatUtility.convertToHourMinutes(daily.getScheduledTime()), daily.getTitle()));
        }
        List<Habit> habitsFromDB = SQLiteJDBC.getInstance().getPathfinderIO().getHabits();
        for(Habit habit : habitsFromDB) {
            habits.addLine(habit.getTitle());
        }
    }

    private void setMouseBehaviors() {
        setDailyBehavior();
        setHabitBehavior();
    }

    private void setHabitBehavior() {

    }

    private void setDailyBehavior() {
        dailies.setOnClickBehavior(e -> {
            doubleClickReader.updateClicks();
            if(doubleClickReader.isDoubleClick()) {
                List<Daily> allDailies = SQLiteJDBC.getInstance().getPathfinderIO().getDailies();
                if(!dailies.getSelectedItem().isEmpty()) {
                    Daily selectedDaily;
                    for(Daily daily : allDailies) {
                        if(daily.getTitle().equals(dailies.getSelectedItem())) {
                            selectedDaily = daily;
                            PathfinderDailyDialog dialog = new PathfinderDailyDialog(selectedDaily);
                            Optional<Daily> result = dialog.showAndWait();
                            if(result != null && result.isPresent()) {
                                SQLiteJDBC.getInstance().getPathfinderIO().deleteDaily(selectedDaily.getTitle());
                                SQLiteJDBC.getInstance().getPathfinderIO().addRowDaily(result.get().getTitle(), result.get().getDescription(), result.get().getScheduledTime(), result.get().getDuration());
                                loadHabitsAndDailies();
                            }

                            break;
                        }
                    }
                }
            }
        });
    }

    private List<Daily> sortDailies(List<Daily> dailies) {
        Comparator<Daily> dailyComparator = (e1, e2) ->
                (e1.getScheduledTime().compareTo(e2.getScheduledTime()));
        return dailies.stream().sorted(dailyComparator).collect(Collectors.toList());
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
