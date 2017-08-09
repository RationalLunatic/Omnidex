package ui.features.mainviews;

import javafx.geometry.Pos;
import resources.StringFormatUtility;
import skeletonkey.QuaternalParadigms;
import skeletonkey.Stats;
import sun.java2d.pipe.SpanShapeRenderer;
import ui.components.PaneKeys;
import ui.components.displaycomponents.SimpleListTextDisplay;
import ui.components.inputcomponents.EditableLabel;
import ui.components.interviewcommunications.ViewRequestHandler;
import ui.components.scalingcomponents.*;
import ui.custombindings.ScaledDoubleBinding;

import java.util.HashMap;
import java.util.Map;

public class SanctuaryView extends ScalingScrollPane {

    private ScalingVBox mainContainer;
    private ScalingHBox profileDisplay;
    private ScalingVBox profileInfo;
    private ScalingVBox profileLog;
    private ScalingHBox activeProjects;
    private ScalingHBox sanctuaryLinks;
    private ScalingButton morningJournal;
    private ScalingButton eveningReflections;
    private EditableLabel name;
    private EditableLabel age;
    private EditableLabel gender;
    private ScalingVBox bodyStats;
    private ScalingVBox mindStats;
    private ScalingVBox soulStats;
    private ScalingVBox spiritStats;
    private ScalingHBox bodyMind;
    private ScalingHBox soulSpirit;
    private ScalingVBox fullStatDisplay;
    private Map<QuaternalParadigms, ScalingVBox> statDisplays;

    public SanctuaryView(ViewRequestHandler commLink, ViewBindingsPack viewBindings, PaneKeys key) {
        super(commLink, viewBindings, key);
        init();
    }

    private void init() {
        initContainers();
        initStatDisplays();
        initLabels();
        initButtons();
        addChildrenToContainers();
    }

    private void addChildrenToContainers() {
        bodyMind.getChildren().addAll(bodyStats, mindStats);
        soulSpirit.getChildren().addAll(soulStats, spiritStats);
        fullStatDisplay.getChildren().addAll(bodyMind, soulSpirit);
        sanctuaryLinks.getChildren().addAll(morningJournal, eveningReflections);
        profileDisplay.getChildren().addAll(profileInfo, profileLog);
        mainContainer.getChildren().addAll(profileDisplay, fullStatDisplay, activeProjects, sanctuaryLinks);
        this.setContent(mainContainer);
    }

    private void initContainers() {
        profileDisplay = new ScalingHBox(getViewBindings());
        profileInfo = new ScalingVBox(getViewBindings());
        profileLog = new ScalingVBox(getViewBindings());
        activeProjects = new ScalingHBox(getViewBindings());
        sanctuaryLinks = new ScalingHBox(getViewBindings());
        bodyStats = new ScalingVBox(getViewBindings());
        mindStats = new ScalingVBox(getViewBindings());
        soulStats = new ScalingVBox(getViewBindings());
        spiritStats = new ScalingVBox(getViewBindings());
        bodyMind = new ScalingHBox(getViewBindings());
        soulSpirit = new ScalingHBox(getViewBindings());
        fullStatDisplay = new ScalingVBox(getViewBindings());
        mainContainer = new ScalingVBox(getViewBindings());
        profileInfo.setAlignment(Pos.CENTER);
    }

    private void initStatDisplays() {
        statDisplays = new HashMap<>();
        statDisplays.put(QuaternalParadigms.BODY, bodyStats);
        statDisplays.put(QuaternalParadigms.MIND, mindStats);
        statDisplays.put(QuaternalParadigms.SOUL, soulStats);
        statDisplays.put(QuaternalParadigms.SPIRIT, spiritStats);
        for(QuaternalParadigms paradigm : QuaternalParadigms.values()) {
            SimpleListTextDisplay paradigmDisplay = new SimpleListTextDisplay(StringFormatUtility.capitalize(paradigm.toString()), getViewBindings());
            for(Stats stat : paradigm.getStats()) {
                paradigmDisplay.addLine(StringFormatUtility.capitalize(stat.toString()) + ": 8");
            }
            statDisplays.get(paradigm).getChildren().add(paradigmDisplay);
        }
    }

    private void initLabels() {
        name = new EditableLabel(getViewBindings());
        age = new EditableLabel(getViewBindings());
        gender = new EditableLabel(getViewBindings());
        profileInfo.getChildren().addAll(name, age, gender);
    }

    private void initButtons() {
        ScaledDoubleBinding buttonHeight = new ScaledDoubleBinding(getViewBindings().heightProperty(), 0.15);
        ViewBindingsPack buttonPack = new ViewBindingsPack(getViewBindings().widthProperty(), buttonHeight);
        morningJournal = new ScalingButton("Mourning Journal", buttonPack);
        eveningReflections = new ScalingButton("Evening Reflections", buttonPack);
    }
}
