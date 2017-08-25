package ui.components.displaycomponents;

import engine.components.schedule.BasicTask;
import engine.components.schedule.Daily;
import engine.components.schedule.Deadline;
import javafx.scene.input.MouseEvent;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.popupdialogs.PathfinderDeadlineDialog;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingScrollPane;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.MINUTES;

public class ScalingTimeDisplay extends ScalingScrollPane {
    private List<LocalTime> minutesOfTheDay;
    private List<Integer> validMinuteScales;
    private List<Deadline> deadlinesToDisplay;
    private List<Daily> dailiesToDisplay;
    private int minutesPerTick;
    private ScalingVBox mainContainer;
    private List<ScalingTimeDisplayBlockComponent> selectedTimeBlocks;
    private LocalDate date;

    public ScalingTimeDisplay(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key, LocalDate date) {
        super(commLink, viewBindings, key);
        this.date = date;
        init();
    }

    private void init() {
        mainContainer = new ScalingVBox(getViewBindings());
        selectedTimeBlocks = new ArrayList<>();
        initMinuteData();
        loadTimeBlockData();
        loadDisplay();
        this.setContent(mainContainer);
    }

    private void initMinuteData() {
        minutesPerTick = 60;
        initMinutesOfDay();
        initValidMinuteScales();
    }

    private void initMinutesOfDay() {
        minutesOfTheDay = new ArrayList<>();
        for(int i = 0; i < 24; i++) {
            for(int j = 0; j < 60; j++) {
                if(!minutesOfTheDay.contains(LocalTime.of(i, j))) {
                    minutesOfTheDay.add(LocalTime.of(i, j));
                }
            }
        }
    }

    private void initValidMinuteScales() {
        validMinuteScales = new ArrayList<>();
        for(int i = 1; i <= 240; i++) {
            if(i <= 60) {
                if(60 % i == 0) validMinuteScales.add(i);
            } else {
                if(240 % i == 0) validMinuteScales.add(i);
            }
        }
    }

    public List<Integer> getValidMinuteScales() { return validMinuteScales; }
    public int getCurrentIndex() {
        return validMinuteScales.indexOf(minutesPerTick);
    }

    public void setIndexOfMinutesPerTick(int index) {
        minutesPerTick = validMinuteScales.get(index);
        reloadDisplay();
    }

    private void reloadDisplay() {
        mainContainer.getChildren().clear();
        loadDisplay();
    }

    private void loadDisplay() {
        for(int i = 0; i < 1440; i += minutesPerTick) {
            ScalingTimeDisplayBlockComponent timeBox = new ScalingTimeDisplayBlockComponent(getViewBindings(), i);
            loadTimeBoxDisplay(timeBox);
            setTimeBoxBehaviors(timeBox);
            mainContainer.getChildren().add(timeBox);
        }
    }

    private void loadTimeBoxDisplay(ScalingTimeDisplayBlockComponent timeBox) {
        LocalTime startTime = timeBox.getTime();
        LocalTime endTime = startTime.plusMinutes(minutesPerTick);
        if(endTime.isBefore(startTime)) endTime = startTime.plusMinutes(minutesPerTick-1);
        loadDailies(startTime, endTime, timeBox);
        loadDeadlines(startTime, endTime, timeBox);
    }

    private boolean timeBlockInRange(LocalTime blockStart, LocalTime blockEnd, LocalTime activityStart, LocalTime activityEnd) {
        if(((activityStart.isBefore(blockEnd)) && activityStart.isAfter(blockStart)) || (activityEnd.isBefore(blockEnd) && activityEnd.isAfter(blockStart))) {
            return true;
        } else if(activityStart.equals(blockStart) || activityEnd.equals(blockEnd)) {
            return true;
        } else if(blockStart.isAfter(activityStart) && blockEnd.isBefore(activityEnd)) {
            return true;
        } else if(activityEnd.isBefore(activityStart)) {
            if(blockEnd.isBefore(activityEnd)) return true;
        }
        return false;
    }

    private void loadDeadlines(LocalTime startTime, LocalTime endTime, ScalingTimeDisplayBlockComponent timeBox) {
        for(Deadline deadline : deadlinesToDisplay) {
            LocalTime deadlineStart = deadline.getSchedule().toLocalTime();
            LocalTime deadlineEnd = deadlineStart.plusMinutes(deadline.getDuration());
            if(timeBlockInRange(startTime, endTime, deadlineStart, deadlineEnd)) {
                addDeadlineToTimeBox(timeBox, deadline);
            }
        }
    }

    private void loadDailies(LocalTime startTime, LocalTime endTime, ScalingTimeDisplayBlockComponent timeBox) {
        for(Daily daily : dailiesToDisplay) {
            LocalTime dailyStart = daily.getScheduledTime();
            LocalTime dailyEnd = dailyStart.plusMinutes(daily.getDuration());
            System.out.println(daily.getDuration() + daily.getTitle() + dailyEnd.toString());
            if(timeBlockInRange(startTime, endTime, dailyStart, dailyEnd)) {
                addDailyToTimeBox(timeBox, daily);
            }
        }
    }

    private void addDailyToTimeBox(ScalingTimeDisplayBlockComponent timeBox, Daily daily) {
        timeBox.addDaily(daily);
        timeBox.setStyle("-fx-background-color: lightcyan;");
    }

    private void addDeadlineToTimeBox(ScalingTimeDisplayBlockComponent timeBox, Deadline deadline) {
        timeBox.addDeadline(deadline);
        timeBox.setStyle("-fx-background-color: wheat;");
    }

    private void setTimeBoxBehaviors(ScalingTimeDisplayBlockComponent timeBox) {
        timeBox.setOnDragDetected(e -> timeBox.startFullDrag());
        timeBox.setOnMouseDragged(e -> {
            timeBox.setMouseTransparent(true);
            addTimeBlockToSelection(e, timeBox);
        });
        timeBox.setOnMouseDragEntered(e -> addTimeBlockToSelection(e, timeBox));
        timeBox.setOnMouseDragReleased(e -> {
            timeBox.setMouseTransparent(false);
            processTimeBlockSelection();
        });
    }

    private void addTimeBlockToSelection(MouseEvent e, ScalingTimeDisplayBlockComponent timeBox) {
        if(e.isShiftDown()) {
            selectedTimeBlocks.add(timeBox);
            timeBox.setStyle("-fx-background-color: red;");
        }
    }

    private void processTimeBlockSelection() {
        if(!selectedTimeBlocks.isEmpty()) {
            if(selectedTimeBlocks.size() == 1) {
                LocalTime selectedTime = selectedTimeBlocks.get(0).getTime();
                int duration = minutesPerTick;
                LocalDateTime dateToSave = LocalDateTime.of(date, selectedTime);
                PathfinderDeadlineDialog dialog = createDialog(selectedTime, selectedTime.plusMinutes(duration));
                Optional<Deadline> result = dialog.showAndWait();
                if(result != null && result.isPresent()) {
                    unlockDialog(dialog);
                    SQLiteJDBC.getInstance().getPathfinderIO().addRowDeadline(result.get().getTitle(),
                            result.get().getDescription(), dateToSave, duration);
                }
            } else {
                selectedTimeBlocks = selectedTimeBlocks.stream().sorted(new Comparator<ScalingTimeDisplayBlockComponent> () {
                    @Override
                    public int compare(ScalingTimeDisplayBlockComponent o1, ScalingTimeDisplayBlockComponent o2) {
                        return o1.getTime().compareTo(o2.getTime());
                    }
                }).collect(Collectors.toList());
                LocalTime startTime = selectedTimeBlocks.get(0).getTime();
                LocalTime endTime = selectedTimeBlocks.get(selectedTimeBlocks.size()-1).getTime();
                LocalDateTime dateToSave = LocalDateTime.of(date, startTime);
                PathfinderDeadlineDialog dialog = createDialog(startTime, endTime);
                Optional<BasicTask> result = dialog.showAndWait();
                if(result != null && result.isPresent()) {
                    unlockDialog(dialog);
                    SQLiteJDBC.getInstance().getPathfinderIO().addRowDeadline(result.get().getTitle(),
                            result.get().getDescription(), dateToSave, result.get().getDuration());
                }
            }
            selectedTimeBlocks.clear();
            reloadDisplay();
        }
    }

    private void loadTimeBlockData() {
        deadlinesToDisplay = SQLiteJDBC.getInstance().getPathfinderIO().getDeadlinesByDate(date);
        dailiesToDisplay = SQLiteJDBC.getInstance().getPathfinderIO().getDailies();
    }

    private PathfinderDeadlineDialog createDialog(LocalTime startTime, LocalTime endTime) {
        PathfinderDeadlineDialog dialog = new PathfinderDeadlineDialog(
                StringFormatUtility.convertToHourMinutes(startTime)
                        + " through " + StringFormatUtility.convertToHourMinutes(endTime) + " Time Block");
        dialog.getDatePicker().setValue(date);
        dialog.getTimeSpinner().getValueFactory().setValue(startTime);
        dialog.getDurationSpinner().getValueFactory().setValue((int)startTime.until(endTime, MINUTES) + minutesPerTick);
        lockDialog(dialog);
        return dialog;
    }

    private void lockDialog(PathfinderDeadlineDialog dialog) {
        dialog.getDatePicker().setDisable(true);
        dialog.getTimeSpinner().setDisable(true);
        dialog.getDurationSpinner().setDisable(true);
    }

    private void unlockDialog(PathfinderDeadlineDialog dialog) {
        dialog.getDatePicker().setDisable(false);
        dialog.getTimeSpinner().setDisable(false);
        dialog.getDurationSpinner().setDisable(false);
    }
}
