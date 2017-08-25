package resources.datatypes;

import resources.sqlite.sqlenumerations.SQLDataTypes;
import resources.sqlite.sqlenumerations.SQLTableColumns;

public class SQLColumn {
    private SQLTableColumns columnName;
    private SQLDataTypes dataType;
    private boolean notNull;

    public SQLColumn(SQLTableColumns columnName, SQLDataTypes dataType, boolean notNull) {
        this.columnName = columnName;
        this.dataType = dataType;
        this.notNull = notNull;
    }

    public String getColumnDefinition() {
        String notNullString = (notNull) ? " NOT NULL" : "";
        return columnName + " " + dataType.toString() + notNullString;
    }

    public String getColumnName() { return columnName.toString(); }
}
