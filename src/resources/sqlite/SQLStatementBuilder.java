package resources.sqlite;

import java.util.HashMap;
import java.util.Map;

public class SQLStatementBuilder {

    public SQLStatementBuilder() {
    }

    public String selectAll(String table) {
        return "SELECT * FROM " + table + ";";
    }

    public String selectAllWhere(String table, String column, String value) {
        return "SELECT * FROM " + table + " WHERE " +  column + "=" + value + ";";
    }

    public String deleteComponentFrom(String table, String column, String component) {
        return "DELETE FROM " + table + " WHERE " + column + "=" + component + ";";
    }
}
