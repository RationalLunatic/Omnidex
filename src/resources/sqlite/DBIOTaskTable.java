package resources.sqlite;

import engine.components.schedule.ToDoListTask;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

public class DBIOTaskTable extends DBCore {
    private int generatedID;

    public DBIOTaskTable() {
        super();
        generatedID = getGeneratedID("TASK");
    }

    public Map<Integer, ToDoListTask> getTasksForDay(LocalDate localDate) {
        Map<Integer, ToDoListTask> mapOfTasks = new HashMap<>();
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM TASK WHERE SCHEDULE BETWEEN '" + localDate.toString() + "T00:00:00:00' AND '" + localDate.toString() + "T23:59:59:999';";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                ToDoListTask loadTask = new ToDoListTask(rs.getString("NAME"), rs.getString("DESCRIPTION"), rs.getString("SCHEDULE"));
                int taskHour = LocalDateTime.parse(rs.getString("SCHEDULE")).getHour();
                mapOfTasks.put(taskHour, loadTask);
            }
            System.out.println("Task Count: " + mapOfTasks.keySet().size());
            return mapOfTasks;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        return mapOfTasks;
    }

    public void insertTask(String task) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            try {
                stmt = getConnection().createStatement();
                if(isValidTask(task)) {
                    updateGeneratedID();
                    String sql = "INSERT INTO TASK (ID,NAME,DESCRIPTION,SCHEDULE,COMPLETED) " +
                            "VALUES (" + generatedID + ", " + task + ", 0);";
                    stmt.executeUpdate(sql);
                    System.out.println("Successfully inserted task");
                } else {
                    System.out.println("Task Invalid");
                    System.out.println(task);
                }
            } catch (SQLException e) {
                System.out.println("Failed to insert task");
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt);
            }
        } else {
            notConnectedMessage("insertTask");
        }
        closeConnection();
    }

    public void deleteTask(LocalDateTime localDateTime) {
        establishConnection();
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "DELETE FROM TASK WHERE SCHEDULE='" + localDateTime.toString() + "'";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
    }

    public ToDoListTask getTask(LocalDateTime localDateTime) {
        establishConnection();
        ResultSet rs = null;
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM TASK WHERE SCHEDULE='" + localDateTime.toString() + "'";
            rs = stmt.executeQuery(sql);
            rs.next();
            ToDoListTask result = new ToDoListTask(rs.getString("NAME"), rs.getString("DESCRIPTION"), localDateTime.toString());
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            closeDownDBAction(stmt, rs);
        }

    }

    public boolean hasTask(LocalDateTime localDateTime) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM TASK WHERE SCHEDULE='" + localDateTime.toString() + "'";
            rs = stmt.executeQuery(sql);
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        return false;
    }

    private boolean isValidTask(String task) {
        String[] breakdown = task.split("[,]\\s+");
        if(breakdown.length != 3) return false;
        else if(breakdown[0].charAt(0) != '\'') return false;
        else if(breakdown[1].charAt(0) != '\'') return false;
        else if(breakdown[2].charAt(0) != '\'') return false;
        System.out.println("Task Valid");
        return true;
    }

    private void updateGeneratedID() {
        generatedID += 1;
    }
}
