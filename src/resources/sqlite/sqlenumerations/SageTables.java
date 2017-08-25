package resources.sqlite.sqlenumerations;

import resources.datatypes.SQLColumn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum SageTables {
    SKILL(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true),
            new SQLColumn(SQLTableColumns.GOVERNING_ATTRIBUTE, SQLTableColumns.GOVERNING_ATTRIBUTE.getDataType(), true),
            new SQLColumn(SQLTableColumns.GOVERNING_STAT, SQLTableColumns.GOVERNING_STAT.getDataType(), true)
            )));

    private List<SQLColumn> columns;

    SageTables(List<SQLColumn> columns) {
        this.columns = columns;
    }

    public List<SQLColumn> getColumns() { return columns; }
}
