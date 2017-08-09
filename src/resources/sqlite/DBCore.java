package resources.sqlite;

import resources.StringFormatUtility;

import java.sql.*;
import java.util.*;

public class DBCore {
    protected enum StatementStatus {
        SETTING_TABLE_NAME,
        ADDING_TABLE_COLUMNS,
        EMPTY
    }

    private boolean connectionEstablished;
    private Connection dbConnection;
    private String cumulativeStatement;
    private StatementStatus currentStatementStatus;
    private int tagID;

    public DBCore() {
        connectionEstablished = false;
        cumulativeStatement = "";
        currentStatementStatus = StatementStatus.EMPTY;
        establishConnection();
        initializeDatabase();
        closeConnection();
        tagID = getGeneratedID("TAGS");
    }

    public void establishConnection() {
        if(!connectionEstablished) {
            dbConnection = null;
            try {
                Class.forName("org.sqlite.JDBC");
                dbConnection = DriverManager.getConnection("jdbc:sqlite:omnidex.db");
                connectionEstablished = true;
                System.out.println("Opened database successfully");
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
                connectionEstablished = false;
                dbConnection = null;
            }
        } else {
            System.out.println("Connection already established.");
        }
    }

    public void closeConnection() {
        if(dbConnection != null) {
            try {
                dbConnection.close();
                dbConnection = null;
                connectionEstablished = false;
                System.out.println("Closed database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            notConnectedMessage("closeConnection");
        }
    }

    private void initializeDatabase() {
        // TODO: This really needs to be refactored.....
        createTaskTable();
        createInventoryTable();
        createQuoteTable();
        createTagTable();
        createQuoteTagRelationTable();
        createExerciseTable();
        createRoutineTable();
        createRoutineExerciseRelationTable();
        createBasicTaskTable();
        createDailyTable();
        createHabitTable();
        createDeadlineTable();
        createProjectTable();
        createActionPlanTable();
        createGoalTable();
        createProjectTaskRelationsTable();
        createProjectDeadlineRelationsTable();
        createActionPlanTaskRelationsTable();
        createActionPlanDeadlineRelationsTable();
        createActionPlanProjectRelationsTable();
        createGoalTaskRelationsTable();
        createGoalDeadlineRelationsTable();
        createGoalProjectRelationsTable();
        createGoalPlanRelationsTable();
    }

    private void createBasicTaskTable() {
        generateCommonTableEssentials("BASIC_TASK");
        createTable();
    }

    private void createDailyTable() {
        generateCommonTableEssentials("DAILY");
        addTableColumn("SCHEDULED_TIME  TIME");
        createTable();
    }

    private void createHabitTable() {
        generateCommonTableEssentials("HABIT");
        addTableColumn("REPETITIONS INT NOT NULL");
        addTableColumn("GOOD_HABIT BOOLEAN NOT NULL");
        createTable();
    }

    private void createDeadlineTable() {
        generateCommonTableEssentials("DEADLINE");
        addTableColumn("SCHEDULED_DATETIME  DATETIME NOT_NULL");
        createTable();
    }

    private void createProjectTable() {
        generateCommonTableEssentials("PROJECT");
        createTable();
    }

    private void createActionPlanTable() {
        generateCommonTableEssentials("ACTION_PLAN");
        createTable();
    }

    private void createGoalTable() {
        generateCommonTableEssentials("GOAL");
        createTable();
    }

    private void createProjectTaskRelationsTable() {
        openTableStatement();
        setTableName("PROJECT_TASK_RELATIONS");
        addTableColumn("PROJECT_ID INT NOT NULL");
        addTableColumn("TASK_ID INT NOT NULL");
        createTable();
    }

    private void createProjectDeadlineRelationsTable() {
        openTableStatement();
        setTableName("PROJECT_DEADLINE_RELATIONS");
        addTableColumn("PROJECT_ID INT NOT NULL");
        addTableColumn("DEADLINE_ID INT NOT NULL");
        createTable();
    }

    private void createActionPlanTaskRelationsTable() {
        openTableStatement();
        setTableName("PLAN_TASK_RELATIONS");
        addTableColumn("PLAN_ID INT NOT NULL");
        addTableColumn("TASK_ID INT NOT NULL");
        createTable();
    }

    private void createActionPlanDeadlineRelationsTable() {
        openTableStatement();
        setTableName("PLAN_DEADLINE_RELATIONS");
        addTableColumn("PLAN_ID INT NOT NULL");
        addTableColumn("DEADLINE_ID INT NOT NULL");
        createTable();
    }

    private void createActionPlanProjectRelationsTable() {
        openTableStatement();
        setTableName("PLAN_PROJECT_RELATIONS");
        addTableColumn("PLAN_ID INT NOT NULL");
        addTableColumn("PROJECT_ID INT NOT NULL");
        createTable();
    }

    private void createGoalTaskRelationsTable() {
        openTableStatement();
        setTableName("GOAL_TASK_RELATIONS");
        addTableColumn("GOAL_ID INT NOT NULL");
        addTableColumn("TASK_ID INT NOT NULL");
        createTable();
    }

    private void createGoalDeadlineRelationsTable() {
        openTableStatement();
        setTableName("GOAL_DEADLINE_RELATIONS");
        addTableColumn("GOAL_ID INT NOT NULL");
        addTableColumn("DEADLINE_ID INT NOT NULL");
        createTable();
    }

    private void createGoalProjectRelationsTable() {
        openTableStatement();
        setTableName("GOAL_PROJECT_RELATIONS");
        addTableColumn("GOAL_ID INT NOT NULL");
        addTableColumn("PROJECT_ID INT NOT NULL");
        createTable();
    }

    private void createGoalPlanRelationsTable() {
        openTableStatement();
        setTableName("GOAL_PLAN_RELATIONS");
        addTableColumn("GOAL_ID INT NOT NULL");
        addTableColumn("PLAN_ID INT NOT NULL");
        createTable();
    }

    private void createExerciseTable() {
        openTableStatement();
        setTableName("EXERCISES");
        addTableColumn("TITLE TEXT NOT NULL");
        addTableColumn("DESCRIPTION TEXT NOT NULL");
        addTableColumn("CATEGORY TEXT NOT NULL");
        addTableColumn("SUBCATEGORY TEXT NOT NULL");
        addTableColumn("COMPLETED_REPS INT NOT NULL");
        createTable();
    }

    private void createRoutineTable() {
        openTableStatement();
        setTableName("ROUTINES");
        addTableColumn("TITLE TEXT NOT NULL");
        createTable();
    }

    private void createRoutineExerciseRelationTable() {
        openTableStatement();
        setTableName("ROUTINE_EXERCISE_RELATIONS");
        addTableColumn("ROUTINE_ID INT NOT NULL");
        addTableColumn("EXERCISE_ID INT NOT NULL");
        addTableColumn("EXERCISE_REPS INT NOT_NULL");
        addTableColumn("EXERCISE_SETS INT NOT_NULL");
        addTableColumn("EXERCISE_NAME TEXT NOT_NULL");
        addTableColumn("ROUTINE_NAME TEXT NOT_NULL");
        createTable();
    }

    private void createTagTable() {
        openTableStatement();
        setTableName("TAGS");
        addTableColumn("TITLE TEXT NOT NULL");
        createTable();
    }

    private void createQuoteTagRelationTable() {
        openTableStatement();
        setTableName("QUOTE_TAGS");
        addTableColumn("QUOTE_ID    INT     NOT NULL");
        addTableColumn("TAG_ID      INT     NOT NULL");
        createTable();
    }

    private void createQuoteTable() {
        openTableStatement();
        setTableName("QUOTES");
        addTableColumn("AUTHOR      TEXT    NOT NULL");
        addTableColumn("SOURCE      TEXT    NOT NULL");
        addTableColumn("QUOTE       TEXT    NOT NULL");
        addTableColumn("CONSTRAINT UNIQUE_QUOTE UNIQUE(QUOTE)");
        createTable();
    }

    private void generateCommonTableEssentials(String tableName) {
        openTableStatement();
        setTableName(tableName.toUpperCase());
        addTableColumn("TITLE           TEXT    NOT NULL");
        addTableColumn("DESCRIPTION    TEXT    NOT NULL");
    }

    private void createInventoryTable() {
        generateCommonTableEssentials("INVENTORY");
        addTableColumn("CATEGORY   TEXT   NOT NULL");
        addTableColumn("PARENT   TEXT");
        createTable();
    }

    private void createTaskTable() {
        generateCommonTableEssentials("TASK");
        addTableColumn("SCHEDULE   DATETIME");
        addTableColumn("COMPLETED   BOOLEAN");
        createTable();
    }

    private void openTableStatement() {
        if(currentStatementStatus == StatementStatus.EMPTY) {
            cumulativeStatement = "CREATE TABLE IF NOT EXISTS ";
            currentStatementStatus = StatementStatus.SETTING_TABLE_NAME;
        } else {
            invalidStatementMessage();
        }
    }

    private void setTableName(String tableName) {
        if(currentStatementStatus == StatementStatus.SETTING_TABLE_NAME) {
            cumulativeStatement += tableName.toUpperCase() + " ";
            cumulativeStatement += "(ID INT PRIMARY KEY NOT NULL";
            currentStatementStatus = StatementStatus.ADDING_TABLE_COLUMNS;
        } else {
            invalidStatementMessage();
        }
    }

    private void addTableColumn(String column) {
        column = column.toUpperCase();
        if(currentStatementStatus == StatementStatus.ADDING_TABLE_COLUMNS) {
            if(columnValid(column)) cumulativeStatement += ", " + column;
            else System.out.println(column + " is invalid");
        } else {
            invalidStatementMessage();
        }
    }

    private boolean columnValid(String column) {
        String[] breakdownByWord = column.split("\\s+");
        if(breakdownByWord.length != 2 && breakdownByWord.length != 4) {
            return false;
        } else if(breakdownByWord.length == 2) {
            return isValidDataType(breakdownByWord[1]);
        } else if (!breakdownByWord[2].equals("NOT") || !breakdownByWord[3].equals("NULL")) {
            return false;
        } else {
            return isValidDataType(breakdownByWord[1]);
        }
    }

    private boolean isValidDataType(String dataType) {
        Set<String> validTypes = new HashSet<>();
        String[] validDataTypes = {"TEXT", "REAL", "INTEGER", "NUMERIC", "NONE", "INT", "DATE", "DATETIME",
                "DOUBLE", "FLOAT", "BOOLEAN"};
        validTypes.addAll(Arrays.asList(validDataTypes));
        return validTypes.contains(dataType);
    }

    private void createTable() {
        establishConnection();
        if(connectionEstablished) {
            Statement stmt = null;
            try {
                stmt = dbConnection.createStatement();
                String sql = cumulativeStatement.toUpperCase() + ")";
                stmt.executeUpdate(sql);
                cumulativeStatement = "";
                currentStatementStatus = StatementStatus.EMPTY;
                System.out.println("Table created successfully");
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            } finally {
                try {
                    if(stmt != null) stmt.close();
                    closeConnection();
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            notConnectedMessage("createTable");
        }
    }

    protected int getGeneratedID(String tableName) {

        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetGeneratedID(tableName, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        } finally {
            closeDownDBAction(stmt, rs);
        }
    }


    protected void closeDownDBAction(Statement stmt, ResultSet rs) {
        try {
            if(stmt != null) stmt.close();
            if(rs != null) rs.close();
            closeConnection();
        } catch(SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
    }

    protected void closeDownDBAction(Statement stmt) {
        try{
            if(stmt != null) stmt.close();
            closeConnection();
        } catch(SQLException e) {
            closeConnection();
            e.printStackTrace();
        }
    }

    public void createTag(String tag) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    executeCreateTag(tag, stmt);
                } catch (SQLException e) {
                    System.out.println("Failed to insert quote");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt);
                }
            } else {
                invalidStatementMessage();
            }
        } else {
            notConnectedMessage("addQuoteToLibrary DBIOLibrarian");
        }
        closeConnection();
    }

    public void deleteTag(String tag) {
        establishConnection();
        Statement stmt = null;
        try {
            executeDeleteTag(tag, stmt);
        } catch (SQLException e) {
            System.out.println("Failed to delete library item from inventory");
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
    }

    public boolean hasTag(String tag) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeHasTag(tag, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return false;
    }

    public List<String> getTags() {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetTags(stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return new ArrayList<>();
    }

    public int getTagID(String tag) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetTagID(tag, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return -1;
    }

    private int executeGetGeneratedID(String tableName, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT MAX(id) AS max_id FROM " + tableName.toUpperCase();
        rs = stmt.executeQuery(sql);
        int numRecords = rs.getInt("max_id");
        return numRecords + 1;
    }

    private void executeCreateTag(String tag, Statement stmt) throws SQLException {
        updateTagID();
        stmt = getConnection().createStatement();
        tag = StringFormatUtility.addSingleQuotes(tag);
        String sql = "INSERT INTO TAGS (ID,TITLE) " +
                "VALUES (" + tagID + ", " + tag + ");";
        stmt.executeUpdate(sql);
        System.out.println("Successfully inserted tag item CoreDB Librarian");
        System.out.println(tag);
    }

    private void executeDeleteTag(String tag, Statement stmt) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "DELETE FROM TAGS WHERE TITLE='" + tag + "';";
        stmt.executeUpdate(sql);
        System.out.println("Successfully deleted '" + tag + "' from tags");
    }

    private boolean executeHasTag(String tag, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM TAGS WHERE TITLE='" + tag + "';";
        rs = stmt.executeQuery(sql);
        boolean result = rs.next();
        System.out.println(tag + " " + result);
        return result;
    }

    private List<String> executeGetTags(Statement stmt, ResultSet rs) throws SQLException {
        List<String> tags = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM TAGS;";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            tags.add(rs.getString("TITLE"));
        }
        return tags;
    }

    private int executeGetTagID(String tag, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM TAGS WHERE TITLE='" + tag + "';";
        rs = stmt.executeQuery(sql);
        if(rs.next()) return rs.getInt("ID");
        return -1;
    }

    private void updateTagID() { tagID++; }
    public void appendStatement(String statement) { cumulativeStatement += statement;}
    public void resetCumulativeStatement() { cumulativeStatement = ""; }
    public String getCumulativeStatement() { return cumulativeStatement; }
    public Connection getConnection() {
        return dbConnection;
    }
    public StatementStatus getStatementStatus() { return currentStatementStatus; }
    public void setStatementStatus(StatementStatus status) { currentStatementStatus = status; }
    public boolean isConnectionEstablished() { return connectionEstablished; }

    public void notConnectedMessage(String command) {
        System.out.println("Not connected to a database: " + command);
    }
    public void invalidStatementMessage() {
        System.out.println("Invalid Statement Operation");
    }
    public boolean checkForSQLInjection(String statement) {
        String[] toCheck = statement.split("\\s+");
        for(String s : toCheck) {
            if(s.contains("SELECT") || s.contains("INSERT") || s.contains("DELETE") || s.contains("ALTER") || s.contains("UPDATE")) {
                return true;
            }
        }
        return false;
    }
}
