package resources;

import com.sun.javafx.binding.StringFormatter;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;


public class ResourceManager {
    private ResourceBundle bundleLoader;
    private ResourceBundle layoutManager;
    private ResourceBundle cssManager;
    private Properties sqlTokens;

    public ResourceManager() {
        bundleLoader = ResourceBundle.getBundle("resources/property_files/paths");
        layoutManager = ResourceBundle.getBundle(loadLayoutData());
        cssManager = ResourceBundle.getBundle(loadCSSData());
        sqlTokens = new Properties();
        loadPropertyFiles();
    }

    private void loadPropertyFiles() {
        try {
            FileInputStream loadSQLTokens = new FileInputStream(new File(ResourceManager.class.getResource("property_files/sql_tokens.properties").toURI()));
            sqlTokens.load(loadSQLTokens);
            loadSQLTokens.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    public String selectAllFrom(String tableName) { return StringFormatter.format(sqlTokens.getProperty("selectAllFrom"), tableName).get();  }
    public String selectAllFromWhere(String tableName, String condition) { return StringFormatter.format(sqlTokens.getProperty("selectAllFromWhere"), tableName, condition).get();  }
    public String insertInto(String tableName, String keys, String values) { return StringFormatter.format(sqlTokens.getProperty("insertInto"), tableName, keys, values).get();  }
    public String deleteFromWhere(String tableName, String condition) { return StringFormatter.format(sqlTokens.getProperty("deleteFromWhere"), tableName, condition).get(); }
    public String openCreateTable(String tableName) { return StringFormatter.format(sqlTokens.getProperty("openCreateTable"), tableName).get();  }
    public String addTableColumn(String columnDefinition) { return StringFormatter.format(sqlTokens.getProperty("addTableColumn"), columnDefinition).get();  }
    public String closeTableStatement() { return StringFormatter.format(sqlTokens.getProperty("closeTableStatement")).get();  }
    public String titleColumn() { return sqlTokens.getProperty("titleColumn"); }
    public String descriptionColumn() { return sqlTokens.getProperty("descriptionColumn"); }
    public String scheduledTimeColumn() { return sqlTokens.getProperty("scheduledTimeColumn"); }
    public String durationInMinutesColumn() { return sqlTokens.getProperty("durationInMinutesColumn"); }
    public String repetitionsColumn() { return sqlTokens.getProperty("repetitionsColumn"); }
    public String goodHabitColumn() { return sqlTokens.getProperty("goodHabitColumn"); }
    public String scheduledDateTimeColumn() { return sqlTokens.getProperty("scheduledDateTimeColumn"); }


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
