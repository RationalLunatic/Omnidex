package resources.sqlite;

import java.sql.Connection;

public class DBCoreProcessor {
    private boolean connectionEstablished;
    private Connection dbConnection;

    public DBCoreProcessor() {
        connectionEstablished = false;
        dbConnection = null;
    }

    public Connection getConnection() { return dbConnection; }
    public boolean isConnectionEstablished() { return connectionEstablished; }
}
