package resources;

import java.util.ResourceBundle;

/**
 * Created by shaev_000 on 7/27/2016.
 */
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
