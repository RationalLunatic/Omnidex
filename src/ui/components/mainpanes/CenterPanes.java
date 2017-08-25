package ui.components.mainpanes;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import ui.components.PaneKeys;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.ViewBindingsPack;
import ui.features.academyviews.LanguageLearnerView;
import ui.features.academyviews.SkillView;
import ui.features.beaconviews.*;
import ui.features.gymnasiumviews.RoutineBuilderView;
import ui.features.gymnasiumviews.WordFallChallengeView;
import ui.features.mainviews.*;
import ui.features.vaultviews.BibliographyView;
import ui.features.vaultviews.CitationView;
import ui.features.vaultviews.VocabularyView;

import java.util.HashMap;
import java.util.Map;

public class CenterPanes extends PanePack {
    private SanctuaryView sanctuaryView;
    private BeaconView beaconView;
    private AcademyView academyView;
    private VaultView vaultView;
    private GymnasiumView gymnasiumView;
    private MonthView calendarMonthDisplay;
    private DayView  calendarDayDisplay;
    private PathfinderView pathfinderView;
    private ScratchView scratchView;
    private LiteratureView literatureView;
    private GoalCreatorView goalCreatorView;
    private CitationView citationView;
    private RoutineBuilderView routineBuilderView;
    private ProjectView projectView;
    private PlanView planView;
    private GoalView goalView;
    private SkillView skillView;
    private BibliographyView bibliographyView;
    private VocabularyView vocabularyView;
    private LanguageLearnerView languageLearnerView;
    private WordFallChallengeView wordFallChallengeView;
    private Map<PaneKeys, Node> centerPanes;

    public CenterPanes(Pane corePane, ViewRequestHandler commLink, ViewBindingsPack viewBindings) {
        super(corePane, viewBindings, commLink);
        centerPanes = new HashMap<>();
        initPanes();
        mapPanes();
        corePane.getChildren().add(beaconView);
    }

    private void initPanes() {
        calendarMonthDisplay = new MonthView(getRequestHandler(), getViewBindings());
        calendarDayDisplay = new DayView(getRequestHandler(), getViewBindings());
        beaconView = new BeaconView(getRequestHandler(), getViewBindings(), PaneKeys.BEACON);
        sanctuaryView = new SanctuaryView(getRequestHandler(), getViewBindings(), PaneKeys.SANCTUARY);
        academyView = new AcademyView(getRequestHandler(), getViewBindings(), PaneKeys.ACADEMY);
        vaultView = new VaultView(getRequestHandler(), getViewBindings(), PaneKeys.VAULT);
        gymnasiumView = new GymnasiumView(getRequestHandler(), getViewBindings(), PaneKeys.GYMNASIUM);
        pathfinderView = new PathfinderView(getRequestHandler(), getViewBindings(), PaneKeys.PATHFINDER);
        literatureView = new LiteratureView(getRequestHandler(), getViewBindings(), PaneKeys.LITERATURE);
        goalCreatorView = new GoalCreatorView(getRequestHandler(), getViewBindings(), PaneKeys.GOAL_CREATOR);
        scratchView = new ScratchView(getRequestHandler(), getViewBindings(), PaneKeys.SCRATCH);
        citationView = new CitationView(getRequestHandler(), getViewBindings(), PaneKeys.CITATION);
        routineBuilderView = new RoutineBuilderView(getRequestHandler(), getViewBindings(), PaneKeys.EXERCISE_ROUTINE_BUILDER);
        projectView = new ProjectView(getRequestHandler(), getViewBindings(), PaneKeys.PROJECT);
        planView = new PlanView(getRequestHandler(), getViewBindings(), PaneKeys.PLAN);
        goalView = new GoalView(getRequestHandler(), getViewBindings(), PaneKeys.GOAL);
        skillView = new SkillView(getRequestHandler(), getViewBindings(), PaneKeys.SKILL);
        bibliographyView = new BibliographyView(getRequestHandler(), getViewBindings(), PaneKeys.BIBLIOGRAPHY);
        vocabularyView = new VocabularyView(getRequestHandler(), getViewBindings(), PaneKeys.VOCABULARY);
        languageLearnerView = new LanguageLearnerView(getRequestHandler(), getViewBindings(), PaneKeys.LANGUAGE_LEARNER);
        wordFallChallengeView = new WordFallChallengeView(getRequestHandler(), getViewBindings(), PaneKeys.WORDFALL);
    }

    private void mapPanes() {
        centerPanes.put(PaneKeys.DAY, calendarDayDisplay);
        centerPanes.put(PaneKeys.MONTH, calendarMonthDisplay);
        centerPanes.put(PaneKeys.BEACON, beaconView);
        centerPanes.put(PaneKeys.SANCTUARY, sanctuaryView);
        centerPanes.put(PaneKeys.ACADEMY, academyView);
        centerPanes.put(PaneKeys.VAULT, vaultView);
        centerPanes.put(PaneKeys.GYMNASIUM, gymnasiumView);
        centerPanes.put(PaneKeys.PATHFINDER, pathfinderView);
        centerPanes.put(PaneKeys.LITERATURE, literatureView);
        centerPanes.put(PaneKeys.GOAL_CREATOR, goalCreatorView);
        centerPanes.put(PaneKeys.SCRATCH, scratchView);
        centerPanes.put(PaneKeys.CITATION, citationView);
        centerPanes.put(PaneKeys.EXERCISE_ROUTINE_BUILDER, routineBuilderView);
        centerPanes.put(PaneKeys.PROJECT, projectView);
        centerPanes.put(PaneKeys.PLAN, planView);
        centerPanes.put(PaneKeys.GOAL, goalView);
        centerPanes.put(PaneKeys.SKILL, skillView);
        centerPanes.put(PaneKeys.BIBLIOGRAPHY, bibliographyView);
        centerPanes.put(PaneKeys.VOCABULARY, vocabularyView);
        centerPanes.put(PaneKeys.LANGUAGE_LEARNER, languageLearnerView);
        centerPanes.put(PaneKeys.WORDFALL, wordFallChallengeView);
    }

    public void switchPane(PaneKeys key) {
        getCorePane().getChildren().clear();
        getCorePane().getChildren().add(centerPanes.get(key));
    }

    public Node getPane(PaneKeys key) {
        return centerPanes.get(key);
    }
}
