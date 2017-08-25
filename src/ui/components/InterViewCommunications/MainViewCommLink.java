package ui.components.interviewcommunications;

// This is how actions generated in the subviews are communicated to the parent pane.
// It allows the outer display to change views/panes based upon an action initiated in an interior pane.
// Essentially, when the user clicks on a link or a button in an inner view, it will send a request up the
// chain of ViewRequestHandlers, until it reaches this one.  At that point, the MainViewCommLink will change
// the display to the appropriate view.

import resources.sqlite.SQLiteJDBC;
import ui.components.mainpanes.*;
import ui.components.PaneKeys;
import ui.components.scalingcomponents.CenterParentScalingStackPane;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingVBox;
import ui.features.beaconviews.*;
import ui.features.gymnasiumviews.RoutineBuilderEast;
import ui.features.gymnasiumviews.RoutineBuilderWest;
import ui.features.vaultviews.VocabularyView;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MainViewCommLink extends ViewRequestHandler {
    private CenterPanes centerPanes;
    private CenterParentScalingStackPane center;
    private NorthPanes northPanes;
    private ScalingHBox north;
    private WestPanes westPanes;
    private ScalingVBox west;
    private EastPanes eastPanes;
    private ScalingVBox east;
    private Map<PaneKeys.PaneLocation, PanePack> actionToLocationRouter;

    public MainViewCommLink() {
        init();
    }

    public void addCenterPanes(CenterPanes centerPanes, CenterParentScalingStackPane center) {
        this.centerPanes = centerPanes;
        this.center = center;
        actionToLocationRouter.put(PaneKeys.PaneLocation.CENTER, centerPanes);
    }

    public void addNorthPanes(NorthPanes northPanes, ScalingHBox north) {
        this.northPanes = northPanes;
        this.north = north;
        actionToLocationRouter.put(PaneKeys.PaneLocation.NORTH, northPanes);
    }

    public void addWestPanes(WestPanes westPanes, ScalingVBox west) {
        this.westPanes = westPanes;
        this.west = west;
        actionToLocationRouter.put(PaneKeys.PaneLocation.WEST, westPanes);
    }

    public void addEastPanes(EastPanes eastPanes, ScalingVBox east) {
        this.eastPanes = eastPanes;
        this.east = east;
        actionToLocationRouter.put(PaneKeys.PaneLocation.EAST, eastPanes);
    }


    private void init() {
        actionToLocationRouter = new HashMap<>();
    }

    @Override
    public void handleRequest(ViewRequest request) {
        if(request.getRequestType() == ViewRequestKeys.DELETE_REQUEST && request.getTargetView() == PaneKeys.TASKS_AND_DEADLINES) {
            TasksAndDeadlinesView tasksAndDeadlines = (TasksAndDeadlinesView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.TASKS_AND_DEADLINES);
            if(!tasksAndDeadlines.getSelectedTaskItem().isEmpty()) {
                SQLiteJDBC.getInstance().getPathfinderIO().deleteTask(tasksAndDeadlines.getSelectedTaskItem());
                tasksAndDeadlines.loadTasksAndDeadlines();
            } else if(!tasksAndDeadlines.getSelectedDeadlineItem().isEmpty()) {
                SQLiteJDBC.getInstance().getPathfinderIO().deleteDeadline(tasksAndDeadlines.getSelectedDeadlineItem());
                tasksAndDeadlines.loadTasksAndDeadlines();
            }
            return;
        }

        if(request.getRequestType() == ViewRequestKeys.DELETE_REQUEST && request.getTargetView() == PaneKeys.HABITS_AND_DAILIES) {
            HabitsAndDailiesView habitsAndDailiesView = (HabitsAndDailiesView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.HABITS_AND_DAILIES);
            if(!habitsAndDailiesView.getSelectedHabitItem().isEmpty()) {
                SQLiteJDBC.getInstance().getPathfinderIO().deleteHabit(habitsAndDailiesView.getSelectedHabitItem());
                habitsAndDailiesView.loadHabitsAndDailies();
            } else if(!habitsAndDailiesView.getSelectedDailyItem().isEmpty()) {
                SQLiteJDBC.getInstance().getPathfinderIO().deleteDaily(habitsAndDailiesView.getSelectedDailyItem());
                habitsAndDailiesView.loadHabitsAndDailies();
            }
            return;
        }

        if(request.getRequestType() == ViewRequestKeys.VIEW_UPDATE && request.getTargetView() == PaneKeys.PROJECT) {
            ProjectView view = (ProjectView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.PROJECT);
            view.setTitle(request.getListUpdate());
            view.loadDisplayData();
        }

        if(request.getRequestType() == ViewRequestKeys.VIEW_UPDATE && request.getTargetView() == PaneKeys.PLAN) {
            PlanView view = (PlanView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.PLAN);
            view.setTitle(request.getListUpdate());
            view.loadDisplayData();
        }

        if(request.getRequestType() == ViewRequestKeys.VIEW_UPDATE && request.getTargetView() == PaneKeys.GOAL) {
            GoalView view = (GoalView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.GOAL);
            view.setTitle(request.getListUpdate());
            view.loadDisplayData();
        }

        if(request.getRequestType() == ViewRequestKeys.LIST_UPDATE && request.getTargetView() == PaneKeys.EXERCISES) {
            RoutineBuilderWest view = (RoutineBuilderWest)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.EXERCISES);
            view.loadExercises();
            return;
        }

        if(request.getRequestType() == ViewRequestKeys.LIST_UPDATE && request.getTargetView() == PaneKeys.TASKS_AND_DEADLINES) {
            TasksAndDeadlinesView tasksAndDeadlines = (TasksAndDeadlinesView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.TASKS_AND_DEADLINES);
            tasksAndDeadlines.loadTasksAndDeadlines();
            return;
        }

        if(request.getRequestType() == ViewRequestKeys.LIST_UPDATE && request.getTargetView() == PaneKeys.HABITS_AND_DAILIES) {
            HabitsAndDailiesView habitsAndDailies = (HabitsAndDailiesView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.HABITS_AND_DAILIES);
            habitsAndDailies.loadHabitsAndDailies();
            return;
        }

        if(request.getRequestType() == ViewRequestKeys.LIST_UPDATE && request.getTargetView() == PaneKeys.ROUTINES) {
            RoutineBuilderEast routineBuilderEast = (RoutineBuilderEast) actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.ROUTINES);
            routineBuilderEast.reload();
            return;
        }

        actionToLocationRouter.get(request.getTargetView().getLocation()).switchPane(request.getTargetView());
        if(request.getTargetView() == PaneKeys.DAY) {
            setDate(request);
        }



    }

    @Override
    public String handleDataRequest(ViewRequest request) {
        if(request.getRequestType() == ViewRequestKeys.DATA_REQUEST && request.getTargetView() == PaneKeys.EXERCISES) {
            RoutineBuilderWest view = (RoutineBuilderWest)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.EXERCISES);
            return view.getSelectedExercise();
        } else  if(request.getRequestType() == ViewRequestKeys.DATA_REQUEST && request.getTargetView() == PaneKeys.ROUTINES) {
            RoutineBuilderEast view = (RoutineBuilderEast)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.ROUTINES);
            return view.getSelectedRoutine();
        } else  if(request.getRequestType() == ViewRequestKeys.DATA_REQUEST && request.getTargetView() == PaneKeys.VOCABULARY) {
            VocabularyView view = (VocabularyView)actionToLocationRouter.get(request.getTargetView().getLocation()).getPane(PaneKeys.VOCABULARY);
            return view.getSelectedWord();
        }
        return "";
    }

    private void setDate(ViewRequest request) {
        DayView dayView = (DayView)centerPanes.getPane(PaneKeys.DAY);
        dayView.setDate(request.getLocalDate());
    }
}
