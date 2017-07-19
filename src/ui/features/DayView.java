package ui.features;

import engine.components.schedule.ToDoListTask;
import javafx.beans.binding.DoubleBinding;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingLabel;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DayView extends ScalingStackPane {
    private VBox mainContainer;
    private VBox hourDisplay;
    private HBox dayTitleContainer;
    private HBox returnButtonContainer;
    private Button returnButton;
    private ScalingLabel dayTitle;
    private ScalingButton previous;
    private ScalingButton next;
    private LocalDate today;
    private ViewBindingsPack viewBindings;
    private Map<Integer, HourTile> hourMap;
    private Map<Integer, ToDoListTask> taskList;

    public DayView(ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(commLink, viewBindings, PaneKeys.DAY);
        this.hourMap = new HashMap<>();
        this.viewBindings = viewBindings;
        this.today = LocalDate.now();
        this.setAlignment(Pos.CENTER);
        init(commLink);
        updateDayData();
    }

    private void init(ViewRequestHandler commLink) {
        initElements();
        loadDayData();
        for(int i = 0; i < 24; i++) {
            LocalTime currentHour = LocalTime.of(i, 0);
            LocalDateTime currentDateTime = LocalDateTime.of(today, currentHour);
            HourTile currentHourTile = new HourTile(commLink, viewBindings, PaneKeys.HOUR, currentDateTime);
            if(taskList.keySet().contains(currentHour.getHour())) {
                currentHourTile.setTaskDescription(taskList.get(currentHour.getHour()).getDescription());
            }
            hourMap.put(i, currentHourTile);
            hourDisplay.getChildren().add(currentHourTile);
        }

        mainContainer.getChildren().add(dayTitleContainer);
        mainContainer.getChildren().add(hourDisplay);
        mainContainer.getChildren().add(returnButtonContainer);
        this.getChildren().add(mainContainer);
    }

    private void initElements() {
        initContainers();
        initButtons();
        initTitle();
        addChildrenToContainers();
        alignContainers();
    }

    private void alignContainers() {
        dayTitleContainer.setAlignment(Pos.CENTER);
        returnButtonContainer.setAlignment(Pos.CENTER);
        hourDisplay.prefHeightProperty().bind(this.prefHeightProperty());
        mainContainer.prefHeightProperty().bind(this.prefHeightProperty());
    }

    private void addChildrenToContainers() {
        dayTitleContainer.getChildren().add(previous);
        dayTitleContainer.getChildren().add(dayTitle);
        dayTitleContainer.getChildren().add(next);
        returnButtonContainer.getChildren().add(returnButton);
    }

    private void initContainers() {
        mainContainer = new VBox();
        hourDisplay = new VBox();
        dayTitleContainer = new HBox();
        returnButtonContainer = new HBox();
    }

    private void initButtons() {
        returnButton = new Button("Return");
        returnButton.setOnMouseClicked(e -> returnToMonthView());
        previous = new ScalingButton("Previous", viewBindings);
        previous.setOnMouseClicked(e -> loadPreviousDay());
        next = new ScalingButton("Next", viewBindings);
        next.setOnMouseClicked(e -> loadNextDay());
    }

    private void loadPreviousDay() {
        LocalDate yesterday = today.minusDays(1);
        setDate(yesterday);
        loadDayData();
        updateDayData();
    }

    private void loadNextDay() {
        LocalDate tomorrow = today.plusDays(1);
        setDate(tomorrow);
        loadDayData();
        updateDayData();
    }

    private void initTitle() {
        dayTitle = new ScalingLabel(viewBindings.widthProperty(), "Init Title");
        dayTitle.setAlignment(Pos.CENTER);
        dayTitle.prefWidthProperty().bind(this.prefWidthProperty());
    }

    private void loadDayData() {
        taskList = SQLiteJDBC.getInstance().getTasksForDay(today);
    }

    private void clearDayData() {
        for(int i = 0; i < 24; i++) {
            hourMap.get(i).setTaskDescription("");
        }
    }

    private void updateDayData() {
        clearDayData();
        for(int hour : taskList.keySet()) {
            hourMap.get(hour).setTaskDescription(taskList.get(hour).getDescription());
        }
    }

    public void setDate(LocalDate date) {
        today = date;
        dayTitle.setText(today.toString());

    }

    public void returnToMonthView() {
        sendViewRequest(new ViewRequest(PaneKeys.MONTH));
    }
}
