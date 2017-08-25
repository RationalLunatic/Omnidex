package ui.features.beaconviews;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Deadline;
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

public class ProjectView extends ScalingStackPane {
    private EditableLabel projectTitle;
    private SimpleListTextDisplay tasks;
    private SimpleListTextDisplay deadlines;
    private ScalingVBox mainContainer;
    private ScalingHBox buttonContainer;
    private ScalingHBox listDisplayContainer;
    private ScalingButton addTask;
    private ScalingButton addDeadline;
    private ScalingButton returnToPathfinder;

    public ProjectView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    public void setTitle(String title) {
        projectTitle.setText(title);
    }

    private void initTitle() {
        projectTitle = new EditableLabel(getViewBindings());
        projectTitle.setFont(Font.font(20));
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
    }

    private void initButtons() {
        ScaledDoubleBinding heightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.2);
        ViewBindingsPack buttonBindings = new ViewBindingsPack(getViewBindings().widthProperty(), heightBinding);
        addTask = new ScalingButton(buttonBindings);
        addDeadline = new ScalingButton(buttonBindings);
        returnToPathfinder = new ScalingButton(buttonBindings);
        addTask.setText("Add Task");
        addDeadline.setText("Add Deadline");
        returnToPathfinder.setText("Return to Pathfinder");
    }

    private void initButtonBehavior() {
        addTask.setOnMouseClicked(e -> createTask());
        addDeadline.setOnMouseClicked(e -> createDeadline());
        returnToPathfinder.setOnMouseClicked(e -> getRequestSender().handleRequest(new ViewRequest(PaneKeys.PATHFINDER)));
    }

    private void addUIElementsToContainers() {
        listDisplayContainer.getChildren().addAll(tasks, deadlines);
        buttonContainer.getChildren().addAll(addTask, addDeadline);
        mainContainer.getChildren().addAll(projectTitle, listDisplayContainer, buttonContainer, returnToPathfinder);
        this.getChildren().add(mainContainer);
    }

    public void loadDisplayData() {
        tasks.clear();
        deadlines.clear();
        List<BasicTask> relatedTasks = SQLiteJDBC.getInstance().getPathfinderIO().getTasksOfProject(projectTitle.getText());
        for(BasicTask task : relatedTasks) {
            tasks.addLine(task.getTitle());
        }
        List<Deadline> relatedDeadlines = SQLiteJDBC.getInstance().getPathfinderIO().getDeadlinesOfProject(projectTitle.getText());
        for(Deadline deadline : relatedDeadlines) {
            deadlines.addLine(deadline.getTitle());
        }
    }

    private void createTask() {
        PathfinderDialog taskDialog = new PathfinderDialog("Task");
        Optional<BasicTask> result = taskDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowTask(result.get().getTitle(), result.get().getDescription(), 0);
            SQLiteJDBC.getInstance().getPathfinderIO().addProjectTaskRelation(projectTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }

    private void createDeadline() {
        PathfinderDeadlineDialog deadlineDialog = new PathfinderDeadlineDialog("Deadline");
        Optional<Deadline> result = deadlineDialog.showAndWait();
        if(result != null && result.isPresent()) {
            SQLiteJDBC.getInstance().getPathfinderIO().addRowDeadline(result.get().getTitle(), result.get().getDescription(), result.get().getSchedule(), 0);
            SQLiteJDBC.getInstance().getPathfinderIO().addProjectDeadlineRelation(projectTitle.getText(), result.get().getTitle());
            loadDisplayData();
        }
    }
}
