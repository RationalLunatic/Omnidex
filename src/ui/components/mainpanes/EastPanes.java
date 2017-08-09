package ui.components.mainpanes;

import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ScalingVBox;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.academyviews.QuestView;
import ui.features.beaconviews.HabitsAndDailiesView;
import ui.features.gymnasiumviews.RoutineBuilderEast;

import java.util.HashMap;
import java.util.Map;

public class EastPanes extends PanePack {
    private RoutineBuilderEast routineBuilderView;
    private HabitsAndDailiesView habitsAndDailiesView;
    private QuestView questView;
    private Map<PaneKeys, ScalingVBox> eastViewMap;

    public EastPanes(Pane corePane, ViewRequestHandler requestHandler, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, requestHandler);
        init();
        corePane.getChildren().add(habitsAndDailiesView);
    }

    private void init() {
        routineBuilderView = new RoutineBuilderEast(getViewBindings());
        habitsAndDailiesView = new HabitsAndDailiesView(getViewBindings());
        questView = new QuestView(getViewBindings());
        eastViewMap = new HashMap<>();
        eastViewMap.put(PaneKeys.ROUTINES, routineBuilderView);
        eastViewMap.put(PaneKeys.HABITS_AND_DAILIES, habitsAndDailiesView);
        eastViewMap.put(PaneKeys.QUEST, questView);
    }

    public ScalingVBox getEastPane(PaneKeys key) {
        return eastViewMap.get(key);
    }

    @Override
    public void switchPane(PaneKeys key) {
        getCorePane().getChildren().clear();
        getCorePane().getChildren().add(eastViewMap.get(key));
    }

    @Override
    public Pane getPane(PaneKeys key) {
        return eastViewMap.get(key);
    }
}
