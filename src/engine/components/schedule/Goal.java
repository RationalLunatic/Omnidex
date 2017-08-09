package engine.components.schedule;

import java.util.ArrayList;
import java.util.List;

public class Goal extends ActionPlan {
    private List<ActionPlan> pathfinder;

    public Goal(String title, String description) {
        super(title, description);
        pathfinder = new ArrayList<ActionPlan>();
    }

    public void addPlanToGoalPathfinder(ActionPlan plan) {
        pathfinder.add(plan);
    }

    public List<ActionPlan> getGoalPathfinder() { return pathfinder; }
}
