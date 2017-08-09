package engine.components.schedule;

import java.util.ArrayList;
import java.util.List;

public class Project extends BasicTask {
    private List<BasicTask> basicTasks;
    private List<Deadline> timeSensitiveTasks;

    public Project(String title, String description) {
        super(title, description);
        basicTasks = new ArrayList<>();
        timeSensitiveTasks = new ArrayList<>();
    }

    public void addTask(BasicTask task) {
        basicTasks.add(task);
    }

    public void addDeadline(Deadline deadline) {
        timeSensitiveTasks.add(deadline);
    }

    public List<BasicTask> getTasks() {
        return basicTasks;
    }

    public List<Deadline> getDeadlines() {
        return timeSensitiveTasks;
    }
}
