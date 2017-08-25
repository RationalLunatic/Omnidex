package resources.sqlite;

import resources.ResourceManager;
import resources.sqlite.sqlenumerations.LibrarianTables;
import resources.datatypes.SQLColumn;
import resources.sqlite.sqlenumerations.PathfinderRelationTables;
import resources.sqlite.sqlenumerations.PathfinderTables;
import resources.sqlite.sqlenumerations.SageTables;

import java.sql.*;
import java.util.*;

public class DBCore {
    private boolean connectionEstablished;
    private Connection dbConnection;
    private String cumulativeStatement;
    private int tagID;
    private ResourceManager bundleLoader;

    public DBCore() {
        init();
        establishConnection();
        initializeDatabase();
        closeConnection();
    }

    private void init() {
        bundleLoader = new ResourceManager();
        connectionEstablished = false;
        cumulativeStatement = "";
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
        createTaskTable();
        createSageTables();
        createLibrarianTables();
        createPathfinderTables();
        createPathfinderRelationTables();
    }

    private void createSageTables() {
        for(SageTables table : SageTables.values()) {
            openTableStatement();
            setTableName(table.toString());
            for(SQLColumn column : table.getColumns()) {
                addTableColumn(column.getColumnDefinition());
            }
            createTable();
        }
    }

    private void createLibrarianTables() {
        for(LibrarianTables table : LibrarianTables.values()) {
            openTableStatement();
            setTableName(table.toString());
            for(SQLColumn column : table.getColumns()) {
                addTableColumn(column.getColumnDefinition());
            }
            createTable();
        }
    }

    private void createPathfinderTables() {
        createBasicTaskTable();
        createDailyTable();
        createHabitTable();
        createDeadlineTable();
        createProjectTable();
        createActionPlanTable();
        createGoalTable();
    }

    private void createBasicTaskTable() {
        generateCommonTableEssentials(PathfinderTables.BASIC_TASK.toString());
        createTable();
    }

    private void createDailyTable() {
        generateCommonTableEssentials(PathfinderTables.DAILY.toString());
        addTableColumn(bundleLoader.scheduledTimeColumn());
        addTableColumn(bundleLoader.durationInMinutesColumn());
        createTable();
    }

    private void createHabitTable() {
        generateCommonTableEssentials(PathfinderTables.HABIT.toString());
        addTableColumn(bundleLoader.repetitionsColumn());
        addTableColumn(bundleLoader.goodHabitColumn());
        createTable();
    }

    private void createDeadlineTable() {
        generateCommonTableEssentials(PathfinderTables.DEADLINE.toString());
        addTableColumn(bundleLoader.scheduledDateTimeColumn());
        createTable();
    }

    private void createProjectTable() {
        generateCommonTableEssentials(PathfinderTables.PROJECT.toString());
        createTable();
    }

    private void createActionPlanTable() {
        generateCommonTableEssentials(PathfinderTables.ACTION_PLAN.toString());
        createTable();
    }

    private void createGoalTable() {
        generateCommonTableEssentials(PathfinderTables.GOAL.toString());
        createTable();
    }

    private void createPathfinderRelationTables() {
        for(PathfinderRelationTables relation : PathfinderRelationTables.values()) {
            openTableStatement();
            setTableName(relation.toString());
            addTableColumn(relation.getFirstRelation().toString() + " INT NOT NULL");
            addTableColumn(relation.getSecondRelation().toString() + " INT NOT NULL");
            createTable();
        }
    }

    private void generateCommonTableEssentials(String tableName) {
        openTableStatement();
        setTableName(tableName.toUpperCase());
        addTableColumn(bundleLoader.titleColumn());
        addTableColumn(bundleLoader.descriptionColumn());
    }

    private void createTaskTable() {
        generateCommonTableEssentials("TASK");
        addTableColumn("SCHEDULE   DATETIME");
        addTableColumn("COMPLETED   BOOLEAN");
        createTable();
    }

    private void openTableStatement() {
        cumulativeStatement = "CREATE TABLE IF NOT EXISTS ";
    }

    private void setTableName(String tableName) {
        cumulativeStatement += tableName.toUpperCase() + " ";
        cumulativeStatement += "(ID INT PRIMARY KEY NOT NULL";
    }

    private void addTableColumn(String column) {
        column = column.toUpperCase();
        cumulativeStatement += ", " + column;
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
                System.out.println("Table created successfully");
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            } finally {
                closeDownDBAction(stmt);
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
        updateTagID();
        insertInto(LibrarianTables.TAGS.toString(), "ID, TITLE", tagID + ", " + tag);
    }

    public void deleteTag(String tag) {
        deleteFromWhere(LibrarianTables.TAGS.toString(), "TITLE='" + tag + "'");
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
        return getRowID(LibrarianTables.TAGS.toString(), "TITLE='" + tag + "'");
    }

    private int executeGetGeneratedID(String tableName, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM " + tableName.toUpperCase() + " ORDER BY ID DESC LIMIT 1;";
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            int numRecords = rs.getInt("ID");
            return numRecords + 1;
        }
        return -1;
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

    protected int getRowID(String table, String condition) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetRowID(table, condition, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return -1;
    }

    private int executeGetRowID(String table, String condition, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFromWhere(table, condition);
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            System.out.println("Success: Found ID: " + sql);
            return rs.getInt("ID");
        }
        return -1;
    }

    protected void insertInto(String tableName, String valueTypes, String actualValues) {
        establishConnection();
        Statement stmt = null;
        try {
            executeInsertInto(stmt, tableName, valueTypes, actualValues);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
        closeConnection();
    }

    protected void executeInsertInto(Statement stmt, String tableName, String valueTypes, String actualValues) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = bundleLoader.insertInto(tableName, valueTypes, actualValues);
        System.out.println(sql);
        stmt.executeUpdate(sql);
        System.out.println("Success: " + sql);
    }

    protected void deleteFromWhere(String tableName, String condition) {
        establishConnection();
        Statement stmt = null;
        try {
            executeDeleteFromWhere(stmt, tableName, condition);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
        closeConnection();
    }

    private void executeDeleteFromWhere(Statement stmt, String tableName, String condition) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = bundleLoader.deleteFromWhere(tableName, condition);
        stmt.executeUpdate(sql);
        System.out.println("Success: " + sql);
    }

    protected List<String> getFromWhere(String tableName, String condition, String columnToCollect) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetFromWhere(tableName, condition, columnToCollect, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return new ArrayList<>();
    }

    private List<String> executeGetFromWhere(String tableName, String condition, String columnToCollect, Statement stmt, ResultSet rs) throws SQLException {
        List<String> toReturn = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFromWhere(tableName, condition);
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            toReturn.add(rs.getString(columnToCollect));
        }
        return toReturn;
    }

    protected boolean rowExists(String tableName, String condition) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeRowExists(tableName, condition, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return false;
    }

    private boolean executeRowExists(String tableName, String condition, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFromWhere(tableName, condition);
        rs = stmt.executeQuery(sql);
        return rs.next();
    }

    protected List<String> getAllFrom(String tableName, String columnToCollect) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            return executeGetAllFrom(tableName, columnToCollect, stmt, rs);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return new ArrayList<>();
    }

    private List<String> executeGetAllFrom(String tableName, String columnToCollect, Statement stmt, ResultSet rs) throws SQLException {
        List<String> toReturn = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFrom(tableName);
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            toReturn.add(rs.getString(columnToCollect));
        }
        return toReturn;
    }

    protected <T, E> List<T> getAllDBList(E table) {
        List<T> toReturn = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetAllDBList(table,stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return toReturn;
    }

    private <T, E> List<T> executeGetAllDBList(E table, Statement stmt, ResultSet rs) throws SQLException {
        List<T> toReturn = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = getBundleLoader().selectAllFrom(table.toString());
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            if(table instanceof PathfinderTables) {
                PathfinderTables pathfinderTable = (PathfinderTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, pathfinderTable);
                toReturn.add(resultBuilder.getResult());
            } else if(table instanceof LibrarianTables) {
                LibrarianTables librarianTable = (LibrarianTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, librarianTable);
                toReturn.add(resultBuilder.getResult());
            }
        }
        return toReturn;
    }

    protected <T, E> List<T> getAllFromWhereDBList(E table, String condition) {
        List<T> toReturn = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetAllFromWhereDBList(table, condition, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return toReturn;
    }

    private <T, E> List<T> executeGetAllFromWhereDBList(E table, String condition, Statement stmt, ResultSet rs) throws SQLException {
        List<T> toReturn = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFromWhere(table.toString(), condition);
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            if(table instanceof PathfinderTables) {
                PathfinderTables pathfinderTable = (PathfinderTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, pathfinderTable);
                if(resultBuilder.getResult() != null) toReturn.add(resultBuilder.getResult());
            } else if(table instanceof LibrarianTables) {
                LibrarianTables librarianTable = (LibrarianTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, librarianTable);
                if(resultBuilder.getResult() != null) toReturn.add(resultBuilder.getResult());
            }
        }
        return toReturn;
    }

    protected <T, E> T getRowWhere(E table, String condition) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetRowWhere(table, condition, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return null;
    }

    private <T, E> T executeGetRowWhere(E table, String condition, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = bundleLoader.selectAllFromWhere(table.toString(), condition);
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            if(table instanceof PathfinderTables) {
                PathfinderTables pathfinderTable = (PathfinderTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, pathfinderTable);
                return resultBuilder.getResult();
            } else if(table instanceof LibrarianTables) {
                LibrarianTables librarianTable = (LibrarianTables) table;
                SQLResultBuilder resultBuilder = new SQLResultBuilder(rs, librarianTable);
                return resultBuilder.getResult();
            }
        }
        return null;
    }

    protected ResourceManager getBundleLoader() { return bundleLoader; }
    private void updateTagID() { tagID++; }
    public Connection getConnection() {
        return dbConnection;
    }
    public boolean isConnectionEstablished() { return connectionEstablished; }
    public void notConnectedMessage(String command) {
        System.out.println("Not connected to a database: " + command);
    }
}
