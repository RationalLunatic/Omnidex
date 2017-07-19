package ui.features.mainviews;

import javafx.beans.binding.DoubleBinding;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequest;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingButton;
import ui.components.scalingcomponents.ScalingHBox;
import ui.components.scalingcomponents.ScalingStackPane;
import ui.components.scalingcomponents.ViewBindingsPack;

import java.time.LocalDate;

/**
 * Created by shaev_000 on 7/9/2017.
 */
public class AcademyView extends ScalingStackPane {
    private ScalingHBox mainContainer;

    public AcademyView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        mainContainer = new ScalingHBox(viewBindings.heightProperty());
        init(viewBindings);
    }

    private void init(ViewBindingsPack viewBindings) {
        ScalingButton monthLink = new ScalingButton(viewBindings);
        monthLink.setText("Cirriculum Designer");
        monthLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.MONTH)));
        ScalingButton dayLink = new ScalingButton(viewBindings);
        dayLink.setText("Class Designer");
        dayLink.setOnMouseClicked(e -> sendViewRequest(new ViewRequest(PaneKeys.DAY, LocalDate.now())));
        mainContainer.getChildren().add(monthLink);
        mainContainer.getChildren().add(dayLink);
        this.getChildren().add(mainContainer);
    }
}
