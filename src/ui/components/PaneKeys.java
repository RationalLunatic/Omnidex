package ui.components;

public enum PaneKeys {
    LITERATURE (PaneLocation.CENTER),
    GENRES (PaneLocation.WEST),
    EXERCISES (PaneLocation.WEST),
    EXERCISE_ROUTINE_BUILDER (PaneLocation.CENTER),
    GOALS (PaneLocation.CENTER),
    GOAL_CREATOR (PaneLocation.CENTER),
    SCRATCH (PaneLocation.CENTER),
    CITATION (PaneLocation.CENTER),
    MONTH (PaneLocation.CENTER),
    DAY (PaneLocation.CENTER),
    HOUR (PaneLocation.CENTER),
    SANCTUARY (PaneLocation.CENTER),
    BEACON (PaneLocation.CENTER),
    ACADEMY (PaneLocation.CENTER),
    VAULT (PaneLocation.CENTER),
    GYMNASIUM (PaneLocation.CENTER);

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
