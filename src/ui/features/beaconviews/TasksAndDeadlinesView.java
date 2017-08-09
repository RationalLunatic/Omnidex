package ui.features.beaconviews;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Deadline;
import javafx.util.Pair;
import resources.sqlite.SQLiteJDBC;
import ui.components.displaycomponents.PrefixedListTextDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.util.List;

public class TasksAndDeadlinesView extends ScalingVBox {
    private SimpleListTextDisplay tasks;
    private PrefixedListTextDisplay deadlines;

    public TasksAndDeadlinesView(ViewBindingsPack viewBindings) {
        super(viewBindings);
        init();
    }

    private void init() {
        deadlines = new PrefixedListTextDisplay("Deadlines", getViewBindings());
        tasks = new SimpleListTextDisplay("Tasks", getViewBindings());
        this.getChildren().addAll(tasks, deadlines);
        loadTasksAndDeadlines();
    }

    public void loadTasksAndDeadlines() {
        tasks.clear();
        deadlines.clear();
        List<BasicTask> basicTasksFromDB = SQLiteJDBC.getInstance().getPathfinderIO().getUnrelatedTasks();
        for(BasicTask task : basicTasksFromDB) {
            tasks.addLine(task.getTitle());
        }
        List<Deadline> deadlinesFromDB = SQLiteJDBC.getInstance().getPathfinderIO().getUnrelatedDeadlines();
        for(Deadline deadline : deadlinesFromDB) {
            deadlines.addLine(new Pair<>(deadline.getSchedule().toString(), deadline.getTitle()));
        }
    }

    public String getSelectedTaskItem() {
        if(tasks.getSelectedItem() != null) {
            return tasks.getSelectedItem();
        } else {
            return "";
        }
    }
    public String getSelectedDeadlineItem() {
        if(deadlines.getSelectedItem() != null) {
            return deadlines.getSelectedItem();
        } else {
            return "";
        }
    }

}
