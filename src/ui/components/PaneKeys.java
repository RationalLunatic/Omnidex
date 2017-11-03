package ui.components;

public enum PaneKeys {
    LITERATURE (PaneLocation.CENTER),
    LANGUAGE_LEARNER (PaneLocation.CENTER),
    GENRES (PaneLocation.WEST),
    EXERCISES (PaneLocation.WEST),
    ROUTINES (PaneLocation.EAST),
    EXERCISE_ROUTINE_BUILDER (PaneLocation.CENTER),
    PATHFINDER (PaneLocation.CENTER),
    GOAL (PaneLocation.CENTER),
    GOAL_CREATOR (PaneLocation.CENTER),
    SCRATCH (PaneLocation.CENTER),
    CITATION (PaneLocation.CENTER),
    BIBLIOGRAPHY (PaneLocation.CENTER),
    CHRONOGRAPHY (PaneLocation.CENTER),
    VOCABULARY (PaneLocation.CENTER),
    WORDFALL (PaneLocation.CENTER),
    SUBJECT_LIST (PaneLocation.WEST),
    SCRIBE (PaneLocation.CENTER),
    PROJECT (PaneLocation.CENTER),
    PLAN (PaneLocation.CENTER),
    SKILL (PaneLocation.CENTER),
    TRAINING (PaneLocation.WEST),
    QUEST (PaneLocation.EAST),
    TASKS_AND_DEADLINES (PaneLocation.WEST),
    HABITS_AND_DAILIES (PaneLocation.EAST),
    MONTH (PaneLocation.CENTER),
    DAY (PaneLocation.CENTER),
    HOUR (PaneLocation.CENTER),
    SANCTUARY (PaneLocation.CENTER),
    BEACON (PaneLocation.CENTER),
    ACADEMY (PaneLocation.CENTER),
    VAULT (PaneLocation.CENTER),
    GYMNASIUM (PaneLocation.CENTER),
    BEACON_BUTTONS (PaneLocation.SOUTH);

    private final PaneLocation location;
    PaneKeys(PaneLocation location) {
        this.location = location;
    }

    public PaneLocation getLocation() {
        return location;
    }

    public enum PaneLocation {
        CENTER,
        NORTH,
        SOUTH,
        EAST,
        WEST
    }
}
