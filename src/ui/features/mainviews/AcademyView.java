package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;

import java.time.LocalDate;


public class AcademyView extends ScalingStackPane {
    private ScalingVBox mainContainer;
    private ScalingLabel academyTitle;
    private ScalingButton curriculumLink;
    private ScalingButton classDesignerLink;
    private ScalingButton skillCreator;

    public AcademyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        mainContainer = new ScalingVBox(viewBindings);
        init();
    }

    private void init() {
        academyTitle = new ScalingLabel(getViewBindings().widthProperty(), "Welcome to the Academy", 1.0);
        curriculumLink = new ScalingButton(getViewBindings());
        curriculumLink.setText("Curriculum Manager");
        curriculumLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.MONTH)));
        classDesignerLink = new ScalingButton(getViewBindings());
        classDesignerLink.setText("Character Class Designer");
        classDesignerLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
        skillCreator = new ScalingButton(getViewBindings());
        skillCreator.setText("Skill Manager");
        skillCreator.setOnMouseClicked(e -> openSkillManager());
        mainContainer.getChildren().add(academyTitle);
        mainContainer.getChildren().add(curriculumLink);
        mainContainer.getChildren().add(classDesignerLink);
        mainContainer.getChildren().add(skillCreator);
        this.getChildren().add(mainContainer);
    }

    private void openSkillManager() {
        sendViewRequest(new ViewRequest(PaneKeys.SKILL));
        sendViewRequest(new ViewRequest(PaneKeys.QUEST));
        sendViewRequest(new ViewRequest(PaneKeys.TRAINING));
    }
}
