package ui.features.beaconviews;

import engine.components.schedule.ToDoListTask;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import resources.StringFormatUtility;
import resources.sqlite.SQLiteJDBC;
import ui.components.PaneKeys;
import ui.components.displaycomponents.ScalingTimeDisplay;
import ui.components.scalingcomponents.*;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.custombindings.ScaledDoubleBinding;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;


public class DayView extends ScalingStackPane {
    private ScalingVBox mainContainer;
    private ScalingHBox dayTitleContainer;
    private ScalingHBox returnButtonContainer;
    private Button returnButton;
    private ScalingLabel dayTitle;
    private ScalingButton previous;
    private ScalingButton next;
    private Slider timeScale;
    private ScalingTimeDisplay timeDisplay;
    private LocalDate today;
    private ViewBindingsPack viewBindings;


    public DayView(ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(commLink, viewBindings, PaneKeys.DAY);
        this.viewBindings = viewBindings;
        this.today = LocalDate.now();
        this.setAlignment(Pos.CENTER);
        init(commLink);
    }

    private void init(ViewRequestHandler commLink) {
        initElements();
        addElementsToMainContainer();
        this.getChildren().add(mainContainer);
    }

    private void addElementsToMainContainer() {
        mainContainer.getChildren().add(dayTitleContainer);
        mainContainer.getChildren().add(timeScale);
        mainContainer.getChildren().add(timeDisplay);
        mainContainer.getChildren().add(returnButtonContainer);
    }

    private void initElements() {
        initContainers();
        initButtons();
        initTitle();
        initScalingTimeDisplay();
        initTimeScaleSlider();
        addChildrenToContainers();
        alignContainers();
    }

    private void initScalingTimeDisplay() {
        timeDisplay = new ScalingTimeDisplay(getRequestSender(), getViewBindings(), getPersonalKey(), today);
    }

    private void initTimeScaleSlider() {
        timeScale = new Slider();
        timeScale.setMajorTickUnit(1.0);
        timeScale.setMin(0);
        timeScale.setMax(timeDisplay.getValidMinuteScales().size()-1);
        timeScale.setValue(timeDisplay.getCurrentIndex());
        timeScale.showTickMarksProperty().set(true);
        timeScale.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                timeDisplay.setIndexOfMinutesPerTick(newValue.intValue());
            }
        });
    }

    private void alignContainers() {
        dayTitleContainer.setAlignment(Pos.CENTER);
        returnButtonContainer.setAlignment(Pos.CENTER);
        mainContainer.prefHeightProperty().bind(this.prefHeightProperty());
    }

    private void addChildrenToContainers() {
        dayTitleContainer.getChildren().add(previous);
        dayTitleContainer.getChildren().add(dayTitle);
        dayTitleContainer.getChildren().add(next);
        returnButtonContainer.getChildren().add(returnButton);
    }

    private void initContainers() {
        mainContainer = new ScalingVBox(getViewBindings());
        dayTitleContainer = new ScalingHBox(getViewBindings());
        returnButtonContainer = new ScalingHBox(getViewBindings());
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeightBinding = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.25);
        ViewBindingsPack buttonBindingsPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeightBinding);
        returnButton = new Button("Return");
        returnButton.setOnMouseClicked(e -> returnToMonthView());
        previous = new ScalingButton("Previous", buttonBindingsPack);
        previous.setOnMouseClicked(e -> loadPreviousDay());
        next = new ScalingButton("Next", buttonBindingsPack);
        next.setOnMouseClicked(e -> loadNextDay());
    }

    private void loadPreviousDay() {
        LocalDate yesterday = today.minusDays(1);
        setDate(yesterday);
        initScalingTimeDisplay();
        mainContainer.getChildren().clear();
        addElementsToMainContainer();
    }

    private void loadNextDay() {
        LocalDate tomorrow = today.plusDays(1);
        setDate(tomorrow);
        initScalingTimeDisplay();
        mainContainer.getChildren().clear();
        addElementsToMainContainer();
    }

    private void initTitle() {
        dayTitle = new ScalingLabel(viewBindings.widthProperty(), "Init Title", 0.175);
        dayTitle.setAlignment(Pos.CENTER);
        dayTitle.prefWidthProperty().bind(this.prefWidthProperty());
    }

    public void setDate(LocalDate date) {
        today = date;
        dayTitle.setText(
                StringFormatUtility.capitalize(today.getDayOfWeek().toString()) + " " +
                StringFormatUtility.capitalize(today.getMonth().toString()) + " " +
                today.getDayOfMonth() + ", " + today.getYear());

    }

    public void returnToMonthView() {
        sendViewRequest(new ViewRequest(PaneKeys.MONTH));
    }
}
