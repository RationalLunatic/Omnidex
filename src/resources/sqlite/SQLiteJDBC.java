package resources.sqlite;

import engine.components.schedule.ToDoListTask;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class SQLiteJDBC {
    private DBIOTaskTable taskIO;
    private static SQLiteJDBC sInstance;

    private SQLiteJDBC() {
        taskIO = new DBIOTaskTable();
    }

    public static SQLiteJDBC getInstance() {
        if (sInstance == null) {
            sInstance = new SQLiteJDBC();
        }
        return sInstance;
    }

    public void addTask(String taskName, String taskDesc, String taskDate) {
        taskIO.insertTask("'" + taskName + "', '" + taskDesc + "', '" + taskDate + "'");
    }

    public Map<Integer, ToDoListTask> getTasksForDay(LocalDate day) { return taskIO.getTasksForDay(day); }

    public ToDoListTask getTask(LocalDateTime localDateTime) {
        return taskIO.getTask(localDateTime);
    }

    public void deleteTask(LocalDateTime localDateTime) {
        taskIO.deleteTask(localDateTime);
    }

    public boolean hasTask(LocalDateTime localDateTime) {
        return taskIO.hasTask(localDateTime);
    }
}
