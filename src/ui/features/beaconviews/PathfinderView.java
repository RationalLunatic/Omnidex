package ui.features.beaconviews;


import engine.components.schedule.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import resources.sqlite.SQLiteJDBC;
import ui.components.*;
import ui.components.displaycomponents.DigitalClockDisplay;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.interviewcommunications.ViewRequestKeys;
import ui.components.scalingcomponents.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class PathfinderView extends ScalingStackPane {

    private ScalingVBox mainContainer;
    private ScalingHBox topRow;
    private ScalingVBox middleRow;
    private ScalingHBox bottomRow;
    private ScalingHBox tasksAndDeadlines;
    private ScalingHBox habitsAndDailies;
    private ScalingButton goals;
    private ScalingButton plans;
    private ScalingButton projects;
    private ScalingButton tasks;
    private ScalingButton deadlines;
    private ScalingButton dailies;
    private ScalingButton habits;
    private ScalingButton delete;
    private SimpleListTextDisplay goalsDisplay;
    private SimpleListTextDisplay plansDisplay;
    private SimpleListTextDisplay projectsDisplay;
    private DigitalClockDisplay clockDisplay;

    public PathfinderView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initButtons();
        initButtonText();
        initButtonBehavior();
        initTextDisplays();
        addUIElementsToContainers();
        loadTextDisplayData();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        topRow = new ScalingHBox(getViewBindings());
        middleRow = new ScalingVBox(getViewBindings());
        bottomRow = new ScalingHBox(getViewBindings());
        tasksAndDeadlines = new ScalingHBox(getViewBindings());
        habitsAndDailies = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        plans = new ScalingButton(getViewBindings());
        goals = new ScalingButton(getViewBindings());
        projects = new ScalingButton(getViewBindings());
        tasks = new ScalingButton(getViewBindings());
        deadlines = new ScalingButton(getViewBindings());
        habits = new ScalingButton(getViewBindings());
        dailies = new ScalingButton(getViewBindings());
        delete = new ScalingButton(getViewBindings());
    }

    private void initButtonText() {
        plans.setText("Create Plans");
        goals.setText("Manage Goals");
        projects.setText("Schedule Projects");
        tasks.setText("Add Task");
        deadlines.setText("Add Deadline");
        habits.setText("Add Habit");
        dailies.setText("Add Daily");
        delete.setText("Delete");
    }

    private void initButtonBehavior() {
        plans.setOnMouseClicked(e -> createPlan());
        goals.setOnMouseClicked(e -> createGoal());
        projects.setOnMouseClicked(e -> createProject());
        tasks.setOnMouseClicked(e -> addTask());
        habits.setOnMouseClicked(e -> addHabit());
        dailies.setOnMouseClicked(e -> addDaily());
        deadlines.setOnMouseClicked(e -> addDeadline());
        delete.setOnMouseClicked(e -> deleteScheduleComponent());
    }

    private void initTextDisplays() {
        projectsDisplay = new SimpleListTextDisplay("Projects", getViewBindings());
        plansDisplay = new SimpleListTextDisplay("Plans", getViewBindings());
        goalsDisplay = new SimpleListTextDisplay("Goals", getViewBindings());
        projectsDisplay.setOnClickBehavior(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.PROJECT, ViewRequestKeys.VIEW_UPDATE, projectsDisplay.getTextOfSelected())));
        plansDisplay.setOnClickBehavior(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.PLAN, ViewRequestKeys.VIEW_UPDATE, plansDisplay.getTextOfSelected())));
        goalsDisplay.setOnClickBehavior(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.GOAL, ViewRequestKeys.VIEW_UPDATE, goalsDisplay.getTextOfSelected())));
        clockDisplay = new DigitalClockDisplay(getViewBindings());
    }

    private void loadTextDisplayData() {
        List<Project> projects = SQLiteJDBC.getInstance().getPathfinderIO().getUnrelatedProjects();
        for(Project project : projects) {
            projectsDisplay.addLine(project.getTitle());
        }
        List<ActionPlan> unrelatedPlans = SQLiteJDBC.getInstance().getPathfinderIO().getUnrelatedPlans();
        for(ActionPlan plan : unrelatedPlans) {
            plansDisplay.addLine(plan.getTitle());
        }

        List<Goal> allGoals = SQLiteJDBC.getInstance().getPathfinderIO().getGoals();
        for(Goal goal : allGoals) {
            goalsDisplay.addLine(goal.getTitle());
        }
    }

    private void addTask() {
        BasicTask result = getResultsBasicDialog("Task").get();
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowTask(result.getTitle(), result.getDescription());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.TASKS_AND_DEADLINES, ViewRequestKeys.LIST_UPDATE));
        }
    }

    private void addDeadline() {
        Optional<Deadline> result = getResultsDeadlineDialog("Deadline");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowDeadline(result.get().getTitle(), result.get().getDescription(), result.get().getSchedule());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.TASKS_AND_DEADLINES, ViewRequestKeys.LIST_UPDATE));
        }
    }

    private void addHabit() {
        Optional<Habit> result = getResultsHabitDialog("Habit");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowHabit(result.get().getTitle(), result.get().getDescription(), result.get().isGoodHabit());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.HABITS_AND_DAILIES, ViewRequestKeys.LIST_UPDATE));
        }
    }

    private void addDaily() {
        Optional<Daily> result = getResultsDailyDialog("Daily");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowDaily(result.get().getTitle(), result.get().getDescription(),result.get().getScheduledTime());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.HABITS_AND_DAILIES, ViewRequestKeys.LIST_UPDATE));
        }
    }

    private void createProject() {
        Optional<BasicTask> result = getResultsBasicDialog("Project");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowProject(result.get().getTitle(), result.get().getDescription());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.PROJECT, ViewRequestKeys.VIEW_UPDATE, result.get().getTitle()));
            projectsDisplay.addLine(result.get().getTitle());
        }
    }

    private void createPlan() {
        Optional<BasicTask> result = getResultsBasicDialog("Plan");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowPlan(result.get().getTitle(), result.get().getDescription());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.PLAN, ViewRequestKeys.VIEW_UPDATE, result.get().getTitle()));
            plansDisplay.addLine(result.get().getTitle());
        }
    }

    private void createGoal() {
        Optional<BasicTask> result = getResultsBasicDialog("Goal");
        if(result != null) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowGoal(result.get().getTitle(), result.get().getDescription());
            getRequestSender().handleRequest(new ViewRequest(PaneKeys.GOAL, ViewRequestKeys.VIEW_UPDATE, result.get().getTitle()));
            goalsDisplay.addLine(result.get().getTitle());
        }
    }

    private Optional<BasicTask> getResultsBasicDialog(String title) {
        PathfinderDialog inputDialog = new PathfinderDialog(title);
        Optional<BasicTask> result = inputDialog.showAndWait();
        if(result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }

    private Optional<Habit> getResultsHabitDialog(String title) {
        PathfinderHabitDialog inputDialog = new PathfinderHabitDialog(title);
        Optional<Habit> result = inputDialog.showAndWait();
        if(result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }

    private Optional<Daily> getResultsDailyDialog(String title) {
        PathfinderDailyDialog inputDialog = new PathfinderDailyDialog(title);
        Optional<Daily> result = inputDialog.showAndWait();
        if(result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }

    private Optional<Deadline> getResultsDeadlineDialog(String title) {
        PathfinderDeadlineDialog inputDialog = new PathfinderDeadlineDialog(title);
        Optional<Deadline> result = inputDialog.showAndWait();
        if(result.isPresent()) {
            return result;
        } else {
            return null;
        }
    }

    private void addUIElementsToContainers() {
        topRow.getChildren().addAll(projects, plans, goals);
        tasksAndDeadlines.getChildren().addAll(tasks, deadlines);
        habitsAndDailies.getChildren().addAll(habits, dailies);
        bottomRow.getChildren().addAll(tasksAndDeadlines, habitsAndDailies);
        middleRow.getChildren().addAll(projectsDisplay, plansDisplay, goalsDisplay);
        mainContainer.getChildren().addAll(topRow, middleRow, bottomRow, delete);
        this.getChildren().add(mainContainer);
    }

    private void deleteScheduleComponent() {
        sendViewRequest(new ViewRequest(PaneKeys.TASKS_AND_DEADLINES, ViewRequestKeys.DELETE_REQUEST));
        sendViewRequest(new ViewRequest(PaneKeys.HABITS_AND_DAILIES, ViewRequestKeys.DELETE_REQUEST));
    }
}

