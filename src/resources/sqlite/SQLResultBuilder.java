package resources.sqlite;

import engine.components.schedule.*;
import resources.datatypes.exercisedata.ExerciseRoutineRelation;
import resources.sqlite.sqlenumerations.LibrarianTables;
import resources.datatypes.Quote;
import resources.datatypes.lexicographicdata.spanish.SpanishPartsOfSpeech;
import resources.datatypes.lexicographicdata.spanish.SpanishWord;
import resources.sqlite.sqlenumerations.PathfinderTables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class SQLResultBuilder {
    private enum ResultTypes { PATHFINDER, LIBRARIAN }
    private ResultTypes resultType;
    private ResultSet resultSet;
    private PathfinderTables pathfinderTable;
    private LibrarianTables librarianTable;
    private Map<PathfinderTables, Supplier> pathfinderResultBuilders;
    private Map<LibrarianTables, Supplier> librarianResultBuilders;

    public SQLResultBuilder(ResultSet resultSet, PathfinderTables table) {
        this.resultSet = resultSet;
        this.pathfinderTable = table;
        this.resultType = ResultTypes.PATHFINDER;
        init();
    }

    public SQLResultBuilder(ResultSet resultSet, LibrarianTables table) {
        this.resultSet = resultSet;
        this.librarianTable = table;
        this.resultType = ResultTypes.LIBRARIAN;
        init();
    }

    private void init() {
        pathfinderResultBuilders = new HashMap<>();
        librarianResultBuilders = new HashMap<>();
        initPathfinderResultBuilders();
        initLibrarianResultBuilders();
    }

    private void initPathfinderResultBuilders() {
        pathfinderResultBuilders.put(PathfinderTables.BASIC_TASK, this::generateTask);
        pathfinderResultBuilders.put(PathfinderTables.DEADLINE, this::generateDeadline);
        pathfinderResultBuilders.put(PathfinderTables.DAILY, this::generateDaily);
        pathfinderResultBuilders.put(PathfinderTables.HABIT, this::generateHabit);
        pathfinderResultBuilders.put(PathfinderTables.PROJECT, this::generateProject);
        pathfinderResultBuilders.put(PathfinderTables.ACTION_PLAN, this::generatePlan);
        pathfinderResultBuilders.put(PathfinderTables.GOAL, this::generateGoal);
    }

    public <T> T getResult() {
        if(resultType == ResultTypes.PATHFINDER) return (T) pathfinderResultBuilders.get(pathfinderTable).get();
        else if(resultType == ResultTypes.LIBRARIAN) return (T) librarianResultBuilders.get(librarianTable).get();
        return null;
    }

    private void initLibrarianResultBuilders() {
        librarianResultBuilders.put(LibrarianTables.ROUTINE_EXERCISE_RELATIONS, this::generateRoutineRelation);
        librarianResultBuilders.put(LibrarianTables.QUOTES, this::generateQuote);
        librarianResultBuilders.put(LibrarianTables.VOCABULARY_WORD, this::generateSpanishWord);
        librarianResultBuilders.put(LibrarianTables.VOCABULARY_SUBJECT_LIST, this::generateVocabID);
    }

    private BasicTask generateTask() {
        try {
            return new BasicTask(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("DURATION_IN_MINUTES"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Deadline generateDeadline() {
        try {
            return new Deadline(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("DURATION_IN_MINUTES"), LocalDateTime.parse(resultSet.getString("SCHEDULED_DATETIME")));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Daily generateDaily() {
        try {
            return new Daily(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("DURATION_IN_MINUTES"), LocalTime.parse(resultSet.getString("SCHEDULED_TIME")));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Habit generateHabit() {
        try {
            return new Habit(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"), resultSet.getInt("DURATION_IN_MINUTES"), resultSet.getBoolean("GOOD_HABIT"), resultSet.getInt("REPETITIONS"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Project generateProject() {
        try {
            return new Project(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ActionPlan generatePlan() {
        try {
            return new ActionPlan(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Goal generateGoal() {
        try {
            return new Goal(resultSet.getString("TITLE"), resultSet.getString("DESCRIPTION"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Quote generateQuote() {
        try {
            return new Quote(resultSet.getString("AUTHOR"), resultSet.getString("SOURCE"), resultSet.getString("QUOTE"));
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ExerciseRoutineRelation generateRoutineRelation() {
        try {
            return new ExerciseRoutineRelation(
                    resultSet.getString("ROUTINE_NAME"),
                    resultSet.getString("EXERCISE_NAME"),
                    resultSet.getInt("ID"),
                    resultSet.getInt("ROUTINE_ID"),
                    resultSet.getInt("EXERCISE_ID"),
                    resultSet.getInt("EXERCISE_REPS"),
                    resultSet.getInt("EXERCISE_SETS")
            );
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SpanishWord generateSpanishWord() {
        try {
            return new SpanishWord(
                    resultSet.getString("TITLE"),
                    resultSet.getString("TRANSLATION"),
                    SpanishPartsOfSpeech.valueOf(resultSet.getString("PART_OF_SPEECH"))
            );
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private int generateVocabID() {
        try {
            return resultSet.getInt("VOCAB_ID");
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
