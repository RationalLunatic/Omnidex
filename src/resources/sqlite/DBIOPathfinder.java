package resources.sqlite;

import engine.components.schedule.*;
import org.sqlite.SQLiteException;
import resources.StringFormatUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBIOPathfinder extends DBCore {

    private Map<PathfinderTables, Integer> tableIDs;
    private Map<PathfinderRelationTables, Integer> relationTableIDs;
    private Map<PathfinderRelationTables.RelationColumnNames, PathfinderTables> directRelationsToTables;

    public DBIOPathfinder() {
        init();
    }

    private void init() {
        initTableIDs();
        initRelationTableIDs();
        initRelationToTableDirector();
    }

    private void initRelationToTableDirector() {
        directRelationsToTables = new HashMap<>();
        directRelationsToTables.put(PathfinderRelationTables.RelationColumnNames.GOAL_ID, PathfinderTables.GOAL);
        directRelationsToTables.put(PathfinderRelationTables.RelationColumnNames.TASK_ID, PathfinderTables.BASIC_TASK);
        directRelationsToTables.put(PathfinderRelationTables.RelationColumnNames.PROJECT_ID, PathfinderTables.PROJECT);
        directRelationsToTables.put(PathfinderRelationTables.RelationColumnNames.DEADLINE_ID, PathfinderTables.DEADLINE);
        directRelationsToTables.put(PathfinderRelationTables.RelationColumnNames.PLAN_ID, PathfinderTables.ACTION_PLAN);
    }

    private void initRelationTableIDs() {
        relationTableIDs = new HashMap<>();
        for(PathfinderRelationTables table : PathfinderRelationTables.values()) {
            relationTableIDs.put(table, getGeneratedID(table.toString().toUpperCase()));
        }
    }

    private void initTableIDs() {
        tableIDs = new HashMap<>();
        for(PathfinderTables table : PathfinderTables.values()) {
            tableIDs.put(table, getGeneratedID(table.toString().toUpperCase()));
        }
    }

    private void executeCreateExpressionBasicTables(String title, String description, Statement stmt, LocalDateTime deadlineDate, LocalTime dailyTime, boolean goodHabit, PathfinderTables table) throws SQLException {
        tableIDs.put(table, tableIDs.get(table) + 1);
        stmt = getConnection().createStatement();
        description = StringFormatUtility.addSingleQuotes(description);
        title = StringFormatUtility.addSingleQuotes(title);
        String sql = "INSERT INTO " + table.toString().toUpperCase();
        if(table == PathfinderTables.GOAL
                || table == PathfinderTables.BASIC_TASK
                || table == PathfinderTables.ACTION_PLAN
                || table == PathfinderTables.PROJECT) {
            sql += " (ID, TITLE, DESCRIPTION) VALUES (" + tableIDs.get(table) + ", " + title + ", " + description + ");";
        } else if(table == PathfinderTables.DEADLINE) {
            sql += " (ID, TITLE, DESCRIPTION, SCHEDULED_DATETIME) VALUES (" + tableIDs.get(table) + ", " + title + ", " + description + ", '" + deadlineDate.toString() + "');";
        } else if(table == PathfinderTables.DAILY) {
            sql += " (ID, TITLE, DESCRIPTION, SCHEDULED_TIME) VALUES (" + tableIDs.get(table) + ", " + title + ", " + description + ", '" + dailyTime.toString() + ":00');";
        } else if(table == PathfinderTables.HABIT) {
            String isGoodHabit = (goodHabit) ? "TRUE" : "FALSE";
            sql += "(ID, TITLE, DESCRIPTION, REPETITIONS, GOOD_HABIT) VALUES (" + tableIDs.get(table) + ", " + title + ", " + description + ", " + "0, '" + isGoodHabit + "');";
        }
        stmt.executeUpdate(sql);
        System.out.println("Success:  " + sql);
    }

    private void addRowBasic(String title, String description, LocalDateTime date, LocalTime time, boolean goodHabit, PathfinderTables table) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            try {
                executeCreateExpressionBasicTables(title, description, stmt, date, time, goodHabit, table);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt);
            }
        }
    }

    private int findID(String title, PathfinderTables table) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindID(title, table, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return -1;
    }

    private int executeFindID(String title, PathfinderTables table, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        title = StringFormatUtility.addSingleQuotes(title);
        String sql = "SELECT * FROM " + table.toString().toUpperCase() + " WHERE TITLE=" + title + ";";
        System.out.println(sql);
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getInt("ID");
        } else return -1;
    }

    private void addRelation(String relationOneTitle, String relationTwoTitle, PathfinderRelationTables table) {
        int relationOneID = findID(relationOneTitle, directRelationsToTables.get(table.getFirstRelation()));
        int relationTwoID = findID(relationTwoTitle, directRelationsToTables.get(table.getSecondRelation()));
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            try {
                if(relationOneID == -1) throw new SQLException();
                if(relationTwoID == -1) throw new SQLException();
                executeCreateExpressionRelationTables(relationOneID, relationTwoID, stmt, table);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt);
            }
        }
    }

    private void executeCreateExpressionRelationTables(int relationOneID, int relationTwoID, Statement stmt, PathfinderRelationTables table) throws SQLException {
        relationTableIDs.put(table, relationTableIDs.get(table) + 1);
        stmt = getConnection().createStatement();
        String relationColumnNameOne = table.getFirstRelation().toString().toUpperCase();
        String relationColumnNameTwo = table.getSecondRelation().toString().toUpperCase();
        String sql = "INSERT INTO " + table.toString().toUpperCase() + " (ID, " +
                relationColumnNameOne + ", " + relationColumnNameTwo + ") VALUES( "
                + relationTableIDs.get(table) + ", " + relationOneID + ", " + relationTwoID + ");";
        stmt.executeUpdate(sql);
        System.out.println("Success:  " + sql);
    }

    private int findProjectIDOfParent(String componentTitle, PathfinderTables componentType) {
        int ID = findID(componentTitle, componentType);
        int badID = -1;
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindProjectIDOfParent(ID, componentType, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return badID;
    }

    private int executeFindProjectIDOfParent(int ID, PathfinderTables componentType, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql;
        switch(componentType) {
            case BASIC_TASK:  sql = "SELECT * FROM PROJECT_TASK_RELATIONS WHERE TASK_ID=" + ID + ";"; break;
            case DEADLINE: sql = "SELECT * FROM PROJECT_DEADLINE_RELATIONS WHERE DEADLINE_ID=" + ID + ";"; break;
            default: return -1;
        }
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getInt("PROJECT_ID");
        }
        return -1;
    }

    private int findPlanIDOfParent(String componentTitle, PathfinderTables componentType) {
        int ID = findID(componentTitle, componentType);
        int badID = -1;
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindPlanIDOfParent(ID, componentType, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return badID;
    }

    private int executeFindPlanIDOfParent(int ID, PathfinderTables componentType, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql;
        switch(componentType) {
            case BASIC_TASK: sql = "SELECT * FROM PLAN_TASK_RELATIONS WHERE TASK_ID=" + ID + ";"; break;
            case DEADLINE: sql = "SELECT * FROM PLAN_DEADLINE_RELATIONS WHERE DEADLINE_ID=" + ID + ";"; break;
            case PROJECT: sql = "SELECT * FROM PLAN_PROJECT_RELATIONS WHERE PROJECT_ID=" + ID + ";"; break;
            default: return -1;
        }

        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getInt("PLAN_ID");
        }
        return -1;
    }

    private int findGoalIDOfParent(String componentTitle, PathfinderTables componentType) {
        int ID = findID(componentTitle, componentType);
        int badID = -1;
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindGoalIDOfParent(ID, componentType, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return badID;
    }

    private int executeFindGoalIDOfParent(int ID, PathfinderTables componentType, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql;
        switch (componentType) {
            case BASIC_TASK: sql = "SELECT * FROM GOAL_TASK_RELATIONS WHERE TASK_ID=" + ID + ";"; break;
            case DEADLINE: sql = "SELECT * FROM GOAL_DEADLINE_RELATIONS WHERE DEADLINE_ID=" + ID + ";"; break;
            case PROJECT: sql = "SELECT * FROM GOAL_PROJECT_RELATIONS WHERE PROJECT_ID=" + ID + ";"; break;
            case ACTION_PLAN: sql = "SELECT * FROM GOAL_PLAN_RELATIONS WHERE PLAN_ID=" + ID + ";"; break;
            default: return -1;
        }
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return rs.getInt("GOAL_ID");
        }
        return -1;
    }

    public void deleteDeadline(String deadlineTitle) {
        int deadlineID = findID(deadlineTitle, PathfinderTables.DEADLINE);
        int projectID = findProjectIDOfParent(deadlineTitle, PathfinderTables.DEADLINE);
        int planID = findPlanIDOfParent(deadlineTitle, PathfinderTables.DEADLINE);
        int goalID = findGoalIDOfParent(deadlineTitle, PathfinderTables.DEADLINE);
        if(projectID == -1 && planID == -1 && goalID == -1) {
            deleteUnrelatedComponent(deadlineID, PathfinderTables.DEADLINE);
        } else {
            if(projectID != -1) deleteRelatedComponent(projectID, deadlineID, PathfinderRelationTables.PROJECT_DEADLINE_RELATIONS);
            else if(planID != -1) deleteRelatedComponent(planID, deadlineID, PathfinderRelationTables.PLAN_DEADLINE_RELATIONS);
            else if(goalID != -1) deleteRelatedComponent(goalID, deadlineID, PathfinderRelationTables.GOAL_DEADLINE_RELATIONS);
        }
    }

    public void deleteTask(String taskTitle) {
        int taskID = findID(taskTitle, PathfinderTables.BASIC_TASK);
        int projectID = findProjectIDOfParent(taskTitle, PathfinderTables.BASIC_TASK);
        int planID = findPlanIDOfParent(taskTitle, PathfinderTables.BASIC_TASK);
        int goalID = findGoalIDOfParent(taskTitle, PathfinderTables.BASIC_TASK);
        if(projectID == -1 && planID == -1 && goalID == -1) {
            deleteUnrelatedComponent(taskID, PathfinderTables.BASIC_TASK);
        } else {
            if(projectID != -1) deleteRelatedComponent(projectID, taskID, PathfinderRelationTables.PROJECT_TASK_RELATIONS);
            else if(planID != -1) deleteRelatedComponent(planID, taskID, PathfinderRelationTables.PLAN_TASK_RELATIONS);
            else if(goalID != -1) deleteRelatedComponent(goalID, taskID, PathfinderRelationTables.GOAL_TASK_RELATIONS);
        }
    }

    public void deleteProject(String projectTitle) {
        int projectID = findID(projectTitle, PathfinderTables.PROJECT);
        int planID = findPlanIDOfParent(projectTitle, PathfinderTables.PROJECT);
        int goalID = findGoalIDOfParent(projectTitle, PathfinderTables.GOAL);
        if(planID == -1 && goalID == -1) {
            deleteUnrelatedComponent(projectID, PathfinderTables.PROJECT);
        } else {
            if(planID != -1) deleteRelatedComponent(planID, projectID, PathfinderRelationTables.PLAN_PROJECT_RELATIONS);
            else if(goalID != -1) deleteRelatedComponent(planID, projectID, PathfinderRelationTables.GOAL_PROJECT_RELATIONS);
        }
    }

    public void deleteHabit(String habitTitle) {
        int habitID = findID(habitTitle, PathfinderTables.HABIT);
        deleteUnrelatedComponent(habitID, PathfinderTables.HABIT);
    }

    public void deleteDaily(String dailyTitle) {
        int dailyID = findID(dailyTitle, PathfinderTables.DAILY);
        deleteUnrelatedComponent(dailyID, PathfinderTables.DAILY);
    }

    private void executeDeleteUnrelatedComponent(int ID, PathfinderTables componentType, Statement stmt) throws SQLException {
        stmt = getConnection().createStatement();
        String sql;
        switch(componentType) {
            case BASIC_TASK: sql = "DELETE FROM BASIC_TASK WHERE ID=" + ID + ";"; break;
            case DEADLINE: sql = "DELETE FROM DEADLINE WHERE ID=" + ID + ";"; break;
            case PROJECT: sql = "DELETE FROM PROJECT WHERE ID=" + ID + ";"; break;
            case ACTION_PLAN: sql = "DELETE FROM ACTION_PLAN WHERE ID=" + ID + ";"; break;
            case GOAL: sql = "DELETE FROM GOAL WHERE ID=" + ID + ";"; break;
            case HABIT: sql = "DELETE FROM HABIT WHERE ID=" + ID + ";"; break;
            case DAILY: sql = "DELETE FROM DAILY WHERE ID=" + ID + ";"; break;
            default: return;
        }
        stmt.executeUpdate(sql);
        System.out.println("Success: " + sql);
    }

    private void deleteUnrelatedComponent(int ID, PathfinderTables componentType) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            try {
                executeDeleteUnrelatedComponent(ID, componentType, stmt);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt);
            }
        }
    }

    private void deleteRelatedComponent(int parentID, int componentID, PathfinderRelationTables table) {
        deleteUnrelatedComponent(componentID, directRelationsToTables.get(table.getSecondRelation()));
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            try {
                executeDeleteRelatedComponent(parentID, componentID, table, stmt);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt);
            }
        }
    }

    private void executeDeleteRelatedComponent(int groupID, int ID, PathfinderRelationTables table, Statement stmt) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "DELETE FROM " + table.toString().toUpperCase() + " WHERE " + table.getSecondRelation().toString().toUpperCase() + "=" + ID + ";";
        stmt.executeUpdate(sql);
        System.out.println("Success: " + sql);
    }

    public List<BasicTask> getTasksOfProject(String projectTitle) {
        return getTasksByRelationalID(findID(projectTitle, PathfinderTables.PROJECT), PathfinderTables.PROJECT);
    }

    public List<Deadline> getDeadlinesOfProject(String projectTitle) {
        return getDeadlinesByRelationalID(findID(projectTitle, PathfinderTables.PROJECT));
    }

    public List<BasicTask> getTasksOfPlan(String planTitle) {
        return getTasksByRelationalID(findID(planTitle, PathfinderTables.ACTION_PLAN), PathfinderTables.ACTION_PLAN);
    }

    public List<Deadline> getDeadlinesOfPlan(String planTitle) {
        return getDeadlinesByRelationalID(findID(planTitle, PathfinderTables.ACTION_PLAN));
    }

    private List<BasicTask> getTasksByRelationalID(int associatedID, PathfinderTables table) {
        List<BasicTask> gatherTasks = new ArrayList<>();
        List<Integer> taskIDs = findTaskIDs(associatedID, table);
        for(int id : taskIDs) {
            gatherTasks.add(getTaskByID(id));
        }
        return gatherTasks;

    }

    private BasicTask getTaskByID(int id) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindTaskByID(id, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return null;
    }

    private BasicTask executeFindTaskByID(int id, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM BASIC_TASK WHERE ID=" + id + ";";
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return new BasicTask(rs.getString("TITLE"), rs.getString("DESCRIPTION"));
        } else {
            return null;
        }
    }

    private List<Integer> findTaskIDs(int associatedID, PathfinderTables table) {
        if(associatedID == -1) {
            return findUnrelatedTaskIDs();
        } else {
            List<Integer> emptyTaskIDs = new ArrayList<>();
            establishConnection();
            if(isConnectionEstablished()) {
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    return executeFindTaskIDs(associatedID, table, stmt, rs);
                } catch(SQLException e) {
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
            return emptyTaskIDs;
        }
    }

    private List<Integer> executeFindTaskIDs(int associatedID, PathfinderTables table, Statement stmt, ResultSet rs) throws SQLException {
        List<Integer> taskIDs = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql;
        switch(table) {
            case PROJECT: sql = "SELECT * FROM PROJECT_TASK_RELATIONS WHERE PROJECT_ID=" + associatedID + ";";
                break;
            case ACTION_PLAN: sql = "SELECT * FROM PLAN_TASK_RELATIONS WHERE PLAN_ID=" + associatedID + ";";
                break;
            case GOAL: sql = "SELECT * FROM GOAL_TASK_RELATIONS WHERE GOAL_ID=" + associatedID + ";";
                break;
            default: return taskIDs;
        }
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            taskIDs.add(rs.getInt("TASK_ID"));
        }
        return taskIDs;
    }

    private List<Integer> findUnrelatedTaskIDs() {
        List<Integer> emptyTaskIDs = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindUnrelatedTaskIDs(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyTaskIDs;
    }

    private List<Integer> executeFindUnrelatedTaskIDs(Statement stmt, ResultSet rs) throws SQLException {
        List<Integer> taskIDs = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM BASIC_TASK WHERE NOT EXISTS" +
                " ( SELECT * FROM PROJECT_TASK_RELATIONS WHERE BASIC_TASK.ID = PROJECT_TASK_RELATIONS.TASK_ID " +
                "UNION SELECT * FROM PLAN_TASK_RELATIONS WHERE BASIC_TASK.ID = PLAN_TASK_RELATIONS.TASK_ID " +
                "UNION SELECT * FROM GOAL_TASK_RELATIONS WHERE BASIC_TASK.ID = GOAL_TASK_RELATIONS.TASK_ID)";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            taskIDs.add(rs.getInt("ID"));
        }
        return taskIDs;
    }

    public List<BasicTask> getUnrelatedTasks() {
        return getTasksByRelationalID(-1, PathfinderTables.BASIC_TASK);
    }


    public void addProjectTaskRelation(String projectTitle, String taskTitle) {
        addRelation(projectTitle, taskTitle, PathfinderRelationTables.PROJECT_TASK_RELATIONS);
    }

    public void addProjectDeadlineRelation(String projectTitle, String deadlineTitle) {
        addRelation(projectTitle, deadlineTitle, PathfinderRelationTables.PROJECT_DEADLINE_RELATIONS);
    }

    public void addPlanTaskRelation(String planTitle, String taskTitle) {
        addRelation(planTitle, taskTitle, PathfinderRelationTables.PLAN_TASK_RELATIONS);
    }

    public void addPlanDeadlineRelation(String planTitle, String deadlineTitle) {
        addRelation(planTitle, deadlineTitle, PathfinderRelationTables.PLAN_DEADLINE_RELATIONS);
    }

    public void addPlanProjectRelation(String planTitle, String projectTitle) {
        addRelation(planTitle, projectTitle, PathfinderRelationTables.PLAN_PROJECT_RELATIONS);
    }

    public void addGoalTaskRelation(String goalTitle, String taskTitle) {
        addRelation(goalTitle, taskTitle, PathfinderRelationTables.GOAL_TASK_RELATIONS);
    }

    public void addGoalDeadlineRelation(String goalTitle, String deadlineTitle) {
        addRelation(goalTitle, deadlineTitle, PathfinderRelationTables.GOAL_DEADLINE_RELATIONS);
    }

    public void addGoalProjectRelation(String goalTitle, String projectTitle) {
        addRelation(goalTitle, projectTitle, PathfinderRelationTables.GOAL_PROJECT_RELATIONS);
    }

    public void addGoalPlanRelation(String goalTitle, String planTitle) {
        addRelation(goalTitle, planTitle, PathfinderRelationTables.GOAL_PLAN_RELATIONS);
    }

    public void addRowDeadline(String title, String description, LocalDateTime date) {
        addRowBasic(title, description, date, LocalTime.now(), false, PathfinderTables.DEADLINE);
    }

    public void addRowDaily(String title, String description, LocalTime time) {
        addRowBasic(title, description, LocalDateTime.now(), time, false, PathfinderTables.DAILY);
    }

    public void addRowHabit(String title, String description, boolean goodHabit) {
        addRowBasic(title, description, LocalDateTime.now(), LocalTime.now(), goodHabit, PathfinderTables.HABIT);
    }

    public void addRowTask(String title, String description) {
        addRowBasic(title, description, LocalDateTime.now(), LocalTime.now(), false, PathfinderTables.BASIC_TASK);
    }

    public void addRowProject(String title, String description) {
        addRowBasic(title, description, LocalDateTime.now(), LocalTime.now(), false, PathfinderTables.PROJECT);
    }

    public void addRowGoal(String title, String description) {
        addRowBasic(title, description, LocalDateTime.now(), LocalTime.now(), false, PathfinderTables.GOAL);
    }

    public void addRowPlan(String title, String description) {
        addRowBasic(title, description, LocalDateTime.now(), LocalTime.now(), false, PathfinderTables.ACTION_PLAN);
    }

    private List<Deadline> getDeadlinesByRelationalID(int associatedID) {
        List<Deadline> gatherDeadlines = new ArrayList<>();
        List<Integer> deadlineIDs = findDeadlineIDs(associatedID);
        for(int id : deadlineIDs) {
            gatherDeadlines.add(getDeadlineByID(id));
        }
        return gatherDeadlines;

    }

    private Deadline getDeadlineByID(int id) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindDeadlineByID(id, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return null;
    }

    private Deadline executeFindDeadlineByID(int id, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM DEADLINE WHERE ID=" + id + ";";
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return new Deadline(rs.getString("TITLE"), rs.getString("DESCRIPTION"), LocalDateTime.parse(rs.getString("SCHEDULED_DATETIME")));
        } else {
            return null;
        }
    }

    private List<Integer> findDeadlineIDs(int associatedID) {
        if(associatedID == -1) {
            return findUnrelatedDeadlineIDs();
        } else {
            List<Integer> emptyDeadlineIDs = new ArrayList<>();
            establishConnection();
            if(isConnectionEstablished()) {
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    return executeFindDeadlineIDs(associatedID, stmt, rs);
                } catch(SQLException e) {
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
            return emptyDeadlineIDs;
        }
    }

    private List<Integer> executeFindDeadlineIDs(int associatedID, Statement stmt, ResultSet rs) throws SQLException {
        List<Integer> deadlineIDs = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM PROJECT_DEADLINE_RELATIONS WHERE PROJECT_ID=" + associatedID
                + " UNION "
                + "SELECT * FROM PLAN_DEADLINE_RELATIONS WHERE PLAN_ID=" + associatedID
                + " UNION "
                + "SELECT * FROM GOAL_DEADLINE_RELATIONS WHERE GOAL_ID=" + associatedID + ";";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            deadlineIDs.add(rs.getInt("DEADLINE_ID"));
        }
        return deadlineIDs;
    }

    private List<Integer> findUnrelatedDeadlineIDs() {
        List<Integer> emptyTaskIDs = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindUnrelatedDeadlineIDs(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyTaskIDs;
    }

    private List<Integer> executeFindUnrelatedDeadlineIDs(Statement stmt, ResultSet rs) throws SQLException {
        List<Integer> deadlineIDs = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM DEADLINE WHERE NOT EXISTS" +
                " ( SELECT * FROM PROJECT_DEADLINE_RELATIONS WHERE DEADLINE.ID = PROJECT_DEADLINE_RELATIONS.DEADLINE_ID " +
                "UNION SELECT * FROM PLAN_DEADLINE_RELATIONS WHERE DEADLINE.ID = PLAN_DEADLINE_RELATIONS.DEADLINE_ID " +
                "UNION SELECT * FROM GOAL_DEADLINE_RELATIONS WHERE DEADLINE.ID = GOAL_DEADLINE_RELATIONS.DEADLINE_ID)";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            deadlineIDs.add(rs.getInt("ID"));
        }
        return deadlineIDs;
    }

    public List<Deadline> getUnrelatedDeadlines() {
        return getDeadlinesByRelationalID(-1);
    }

    public List<Habit> getHabits() {
        List<Habit> emptyHabitList = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetHabits(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyHabitList;
    }

    private List<Habit> executeGetHabits(Statement stmt, ResultSet rs) throws SQLException {
        List<Habit> results = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM HABIT;";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            results.add(new Habit(rs.getString("TITLE"), rs.getString("DESCRIPTION"), rs.getBoolean("GOOD_HABIT"), rs.getInt("REPETITIONS")));
        }
        return results;
    }

    public List<Daily> getDailies() {
        List<Daily> emptyDailyList = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetDailies(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyDailyList;
    }

    private List<Daily> executeGetDailies(Statement stmt, ResultSet rs) throws SQLException {
        List<Daily> results = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM DAILY;";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            results.add(new Daily(rs.getString("TITLE"), rs.getString("DESCRIPTION"), LocalTime.parse(rs.getString("SCHEDULED_TIME"))));
        }
        return results;
    }

    private List<Integer> executeFindRelatedProjectIDs(int associatedID, Statement stmt, ResultSet rs) throws SQLException {
        List<Integer> results = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM PLAN_PROJECT_RELATIONS WHERE PLAN_ID=" + associatedID
                + " UNION "
                + "SELECT * FROM GOAL_PROJECT_RELATIONS WHERE GOAL_ID=" + associatedID + ";";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            results.add(rs.getInt("PROJECT_ID"));
        }
        return results;
    }

    private List<Integer> findRelatedProjectIDs(int associatedID) {
        List<Integer> emptyIDList = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindRelatedProjectIDs(associatedID, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyIDList;
    }

    private Project findProjectByID(int projectID) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeFindProjectByID(projectID, stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return null;
    }

    private Project executeFindProjectByID(int projectID, Statement stmt, ResultSet rs) throws SQLException {
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM PROJECT WHERE ID=" + projectID + ";";
        rs = stmt.executeQuery(sql);
        if(rs.next()) {
            return new Project(rs.getString("TITLE"), rs.getString("DESCRIPTION"));
        }
        return null;
    }

    public List<Project> getPlanProjects(String planTitle) {
        int parentID = findID(planTitle, PathfinderTables.ACTION_PLAN);
        List<Project> relatedProjects = new ArrayList<>();
        List<Integer> relatedProjectIDs = findRelatedProjectIDs(parentID);
        for(int id : relatedProjectIDs) {
            relatedProjects.add(findProjectByID(id));
        }
        return relatedProjects;
    }

    public List<Project> getUnrelatedProjects() {
        List<Project> emptyProjectList = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetUnrelatedProjects(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyProjectList;
    }

    private List<Project> executeGetUnrelatedProjects(Statement stmt, ResultSet rs) throws SQLException {
        List<Project> projects = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM PROJECT WHERE NOT EXISTS (" +
                "SELECT * FROM PLAN_PROJECT_RELATIONS WHERE PROJECT.ID = PLAN_PROJECT_RELATIONS.PROJECT_ID " +
                "UNION SELECT * FROM GOAL_PROJECT_RELATIONS WHERE PROJECT.ID = GOAL_PROJECT_RELATIONS.PROJECT_ID)";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            projects.add(new Project(rs.getString("TITLE"), rs.getString("DESCRIPTION")));
        }
        return projects;
    }

    public List<ActionPlan> getUnrelatedPlans() {
        List<ActionPlan> emptyPlans = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetUnrelatedPlans(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyPlans;
    }

    private List<ActionPlan> executeGetUnrelatedPlans(Statement stmt, ResultSet rs) throws SQLException {
        List<ActionPlan> unrelatedPlans = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM ACTION_PLAN WHERE NOT EXISTS ( SELECT * FROM GOAL_PLAN_RELATIONS WHERE ACTION_PLAN.ID = GOAL_PLAN_RELATIONS.PLAN_ID );";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            unrelatedPlans.add(new ActionPlan(rs.getString("TITLE"), rs.getString("DESCRIPTION")));
        }
        return unrelatedPlans;
    }

    public List<Goal> getGoals() {
        List<Goal> emptyGoals = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            try {
                return executeGetGoals(stmt, rs);
            } catch(SQLException e) {
                e.printStackTrace();
            } finally {
                closeDownDBAction(stmt, rs);
            }
        }
        return emptyGoals;
    }

    private List<Goal> executeGetGoals(Statement stmt, ResultSet rs) throws SQLException {
        List<Goal> goals = new ArrayList<>();
        stmt = getConnection().createStatement();
        String sql = "SELECT * FROM GOAL;";
        rs = stmt.executeQuery(sql);
        while(rs.next()) {
            goals.add(new Goal(rs.getString("TITLE"), rs.getString("DESCRIPTION")));
        }
        return goals;
    }
}
