package resources;

import javafx.scene.paint.Color;

import java.util.ResourceBundle;


public class ResourceManager {
    private ResourceBundle bundleLoader;
    private ResourceBundle layoutManager;
    private ResourceBundle cssManager;

    public ResourceManager() {
        bundleLoader = ResourceBundle.getBundle("resources/property_files/paths");
        layoutManager = ResourceBundle.getBundle(loadLayoutData());
        cssManager = ResourceBundle.getBundle(loadCSSData());
    }

    public String loadDebug() {
        return bundleLoader.getString("debugPath");
    }
    private String loadLayoutData() { return bundleLoader.getString("layoutPath"); }
    private String loadCSSData() { return bundleLoader.getString("cssPath");  }
    public String loadBorders() { return cssManager.getString("borders"); }

    public double dayTileTopRowMidWidth() { return Double.parseDouble(layoutManager.getString("day.tile.top.row.mid.width")); }
    public double dayTileTopRowCornerWidth() { return Double.parseDouble(layoutManager.getString("day.tile.top.row.corner.width")); }
    public double dayTileDayOfMonthScalingFactor() { return Double.parseDouble(layoutManager.getString("day.tile.day.of.month.scaling.factor")); }
    public double dayTileDayOfWeekScalingFactor() { return Double.parseDouble(layoutManager.getString("day.tile.day.of.week.scaling.factor")); }
    public Color dayTileDefaultBorder() { return Color.valueOf(layoutManager.getString("day.tile.default.border")); }
    public Color dayTileDefaultHighlight() { return Color.valueOf(layoutManager.getString("day.tile.default.highlight")); }
    public Color dayTileDefaultClick() { return Color.valueOf(layoutManager.getString("day.tile.default.click")); }

    public double monthViewMonthSizeFraction() { return Double.parseDouble(layoutManager.getString("month.view.month.size.fraction")); }
    public double monthViewCycleVboxFraction() { return Double.parseDouble(layoutManager.getString("month.view.cycle.vbox.fraction")); }

    public double monthTileDayTileFraction() { return Double.parseDouble(layoutManager.getString("month.tile.day.tile.fraction")); }

    public double eastPaneScalingFactor() {
        return Double.parseDouble(layoutManager.getString("east"));
    }
    public double westPaneScalingFactor() {
        return Double.parseDouble(layoutManager.getString("west"));
    }
    public double northPaneScalingFactor() {
        return Double.parseDouble(layoutManager.getString("north"));
    }
    public double southPaneScalingFactor() {
        return Double.parseDouble(layoutManager.getString("south"));
    }
    public double centerPaneScalingFactor() {
        return Double.parseDouble(layoutManager.getString("center"));
    }
}
