package ui.features.beaconviews;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Deadline;
import engine.components.schedule.Project;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.popupdialogs.PathfinderDeadlineDialog;
import ui.components.popupdialogs.PathfinderDialog;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.EditableLabel;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.util.List;
import java.util.Optional;

public class GoalView extends ScalingStackPane {
    private EditableLabel goalTitle;
    private SimpleListTextDisplay tasks;
    private SimpleListTextDisplay deadlines;
    private SimpleListTextDisplay projects;
    private SimpleListTextDisplay plans;
    private ScalingVBox mainContainer;
    private ScalingHBox buttonContainer;
    private ScalingHBox listDisplayContainer;
    private ScalingButton addTask;
    private ScalingButton addDeadline;
    private ScalingButton addProject;
    private ScalingButton addPlan;
    private ScalingButton delete;
    private ScalingButton returnToPathfinder;

    public GoalView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    public void setTitle(String title) {
        goalTitle.setText(title);
    }

    private void initTitle() {
        goalTitle = new EditableLabel(getViewBindings());
        goalTitle.setFont(Font.font(20));
    }

    private void init() {
        initTitle();
        initContainers();
        initListDisplays();
        initButtons();
        initButtonBehavior();
        addUIElementsToContainers();
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        buttonContainer = new ScalingHBox(getViewBindings());
        listDisplayContainer = new ScalingHBox(getViewBindings());
        mainContainer.setAlignment(Pos.TOP_CENTER);
    }

    private void initListDisplays() {
        tasks = new SimpleListTextDisplay("Tasks", getViewBindings());
        deadlines = new SimpleListTextDisplay("Deadlines", getViewBindings());
        projects = new SimpleListTextDisplay("Projects", getViewBindings());
        plans = new SimpleListTextDisplay("Plans", getViewBindings());
    }

    private void initButtons() {
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.2);
        ViewBindingsPack buttonBindings = new ViewBindingsPack(getViewBindings().widthProperty(), heightBinding);
        addTask = new ScalingButton(buttonBindings);
        addDeadline = new ScalingButton(buttonBindings);
        addProject = new ScalingButton(buttonBindings);
        addPlan = new ScalingButton(buttonBindings);
        delete = new ScalingButton(buttonBindings);
        returnToPathfinder = new ScalingButton(buttonBindings);
        addTask.setText("Add Task");
        addDeadline.setText("Add Deadline");
        addProject.setText("Add Project");
        addPlan.setText("Add Plan");
        delete.setText("Delete");
        returnToPathfinder.setText("Return to Pathfinder");
    }

    private void initButtonBehavior() {
        addTask.setOnMouseClicked(e -> createTask());
        addDeadline.setOnMouseClicked(e -> createDeadline());
        addProject.setOnMouseClicked(e -> createProject());
        addPlan.setOnMouseClicked(e -> createPlan());
        delete.setOnMouseClicked(e -> deleteScheduleComponent());
        returnToPathfinder.setOnMouseClicked(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.PATHFINDER)));
    }

    private void addUIElementsToContainers() {
        listDisplayContainer.getChildren().addAll(tasks, deadlines);
        buttonContainer.getChildren().addAll(addTask, addDeadline);
        mainContainer.getChildren().addAll(goalTitle, addProject, listDisplayContainer, projects, plans, buttonContainer, delete, returnToPathfinder);
        this.getChildren().add(mainContainer);
    }

    public void loadDisplayData() {
        tasks.clear();
        deadlines.clear();
        projects.clear();
        List<BasicTask> relatedTasks = SQLiteJDBC.getInstance().getPathfinderIO().getTasksOfPlan(goalTitle.getText());
        for(BasicTask task : relatedTasks) {
            tasks.addLine(task.getTitle());
        }
        List<Deadline> relatedDeadlines = SQLiteJDBC.getInstance().getPathfinderIO().getDeadlinesOfPlan(goalTitle.getText());
        for(Deadline deadline : relatedDeadlines) {
            deadlines.addLine(deadline.getTitle());
        }
        List<Project> relatedProjects = SQLiteJDBC.getInstance().getPathfinderIO().getPlanProjects(goalTitle.getText());
        for(Project project : relatedProjects) {
            projects.addLine(project.getTitle());
        }
    }

    private void createTask() {
        PathfinderDialog taskDialog = new PathfinderDialog("Task");
        Optional<BasicTask> result = taskDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowTask(result.get().getTitle(), result.get().getDescription(), 0);
            SQLiteJDBC.getInstance().getPathfinderIO().addGoalTaskRelation(goalTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }

    private void createDeadline() {
        PathfinderDeadlineDialog deadlineDialog = new PathfinderDeadlineDialog("Deadline");
        Optional<Deadline> result = deadlineDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowDeadline(result.get().getTitle(), result.get().getDescription(), result.get().getSchedule(), 0);
            SQLiteJDBC.getInstance().getPathfinderIO().addGoalDeadlineRelation(goalTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }

    private void createProject() {
        PathfinderDialog projectDialog = new PathfinderDialog("Project");
        Optional<BasicTask> result = projectDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowProject(result.get().getTitle(), result.get().getDescription());
            SQLiteJDBC.getInstance().getPathfinderIO().addGoalProjectRelation(goalTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }

    private void createPlan() {
        PathfinderDialog projectDialog = new PathfinderDialog("Plan");
        Optional<BasicTask> result = projectDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowPlan(result.get().getTitle(), result.get().getDescription());
            SQLiteJDBC.getInstance().getPathfinderIO().addGoalPlanRelation(goalTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }

    private void deleteScheduleComponent() {
        if(!tasks.getSelectedItem().isEmpty()) {
            SQLiteJDBC.getInstance().getPathfinderIO().deleteTask(tasks.getSelectedItem());
        } else if(!deadlines.getSelectedItem().isEmpty()) {
            SQLiteJDBC.getInstance().getPathfinderIO().deleteDeadline(deadlines.getSelectedItem());
        } else if(!projects.getSelectedItem().isEmpty()) {
            SQLiteJDBC.getInstance().getPathfinderIO().deleteProject(projects.getSelectedItem());
        }
        loadDisplayData();
    }
}
