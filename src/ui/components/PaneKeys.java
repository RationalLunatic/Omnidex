package ui.components;

/**
 * Created by shaev_000 on 7/7/2017.
 */
public enum PaneKeys {
    LITERATURE (PaneLocation.CENTER),
    GENRES (PaneLocation.WEST),
    GOALS (PaneLocation.CENTER),
    GOAL_CREATOR (PaneLocation.CENTER),
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
