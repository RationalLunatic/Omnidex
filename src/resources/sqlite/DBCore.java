package resources.sqlite;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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

    public DBCore() {
        connectionEstablished = false;
        cumulativeStatement = "";
        currentStatementStatus = StatementStatus.EMPTY;
        establishConnection();
        initializeDatabase();
        closeConnection();
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
            }
        } else {
            System.out.println("Connection already established.");
        }
    }

    public void closeConnection() {
        if(connectionEstablished) {
            try {
                dbConnection.close();
                connectionEstablished = false;
                System.out.println("Closed database successfully");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            notConnectedMessage("closeConnection");
        }
    }

    private boolean alreadyExists() {
        if(connectionEstablished) {
            String[] requiredTables = { "TASK" };
            try {
                boolean alreadyExists = true;
                for(String table : requiredTables) {
                    if(!tableExists(table)) {
                        alreadyExists = false;
                    }
                }
                return alreadyExists;
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            }
        } else {
            notConnectedMessage("alreadyExists");
        }
        return false;
    }

    private boolean tableExists(String tableName) {
        if(connectionEstablished) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                boolean alreadyExists = false;
                stmt = dbConnection.createStatement();
                String sql = "SELECT COUNT(*) AS tablecount FROM sqlite_master WHERE type='table' AND name=" +
                        "'" + tableName.toUpperCase() +"'";
                rs = stmt.executeQuery(sql);
                if(rs.getInt("tablecount") == 1) {
                    alreadyExists = true;
                    System.out.println(tableName + " table exists");
                }
                return alreadyExists;
            } catch ( Exception e ) {
                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            } finally {
                try {
                    if(stmt != null) stmt.close();
                    if(rs != null) rs.close();
                    closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } else {
            notConnectedMessage("tableExists");
        }
        return false;
    }

    private void initializeDatabase() {
        if(!alreadyExists()) {
            System.out.println("TASK table does not exist.  Creating table...");
            openTableStatement();
            setTableName("TASK");
            addTableColumn("NAME           TEXT    NOT NULL");
            addTableColumn("DESCRIPTION    TEXT    NOT NULL");
            addTableColumn("SCHEDULE   DATETIME");
            addTableColumn("COMPLETED   BOOLEAN");
            createTable();
        }
    }

    private void openTableStatement() {
        if(currentStatementStatus == StatementStatus.EMPTY) {
            cumulativeStatement = "CREATE TABLE ";
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
}
