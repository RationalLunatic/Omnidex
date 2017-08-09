package engine.components.schedule;

import java.util.ArrayList;
import java.util.List;

public class ActionPlan extends Project {
    private List<Project> milestones;

    public ActionPlan(String title, String description) {
        super(title, description);
        milestones = new ArrayList<>();
    }

    public void addMilestoneProject(Project project) {
        milestones.add(project);
    }

    public List<Project> getMilestoneProjects() { return milestones; }
}
