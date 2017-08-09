package resources.sqlite;

import resources.StringFormatUtility;
import resources.datatypes.ExerciseRoutineRelation;
import resources.datatypes.Quote;
import ui.components.inputcomponents.ExerciseOptionsInput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBIOLibrarian extends DBCore {
    private int inventoryElementID;
    private int quoteElementID;
    private int quoteTagID;
    private int routineID;
    private int exerciseRoutineRelationID;

    public DBIOLibrarian() {
        super();
        inventoryElementID = getGeneratedID("INVENTORY");
        quoteElementID = getGeneratedID("QUOTES");
        quoteTagID = getGeneratedID("QUOTE_TAGS");
        routineID = getGeneratedID("ROUTINES");
        exerciseRoutineRelationID = getGeneratedID("ROUTINE_EXERCISE_RELATIONS");
    }

    public List<String> getChildrenOfLibraryItem(String parentName) {
        List<String> collectChildren = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM INVENTORY WHERE PARENT='" + parentName + "';";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        collectChildren.add(rs.getString("NAME"));
                    }
                    return collectChildren;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        closeConnection();
        return collectChildren;
    }

    public List<String> getItemsOfCategory(String category) {
        List<String> collectChildren = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM INVENTORY WHERE CATEGORY='" + category + "';";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        collectChildren.add(rs.getString("NAME"));
                    }
                    return collectChildren;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        closeConnection();
        return collectChildren;
    }

    public boolean isQuoteUnique(String quote) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM QUOTES WHERE QUOTE='" + quote + "';";
            rs = stmt.executeQuery(sql);
            return !rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return true;
    }


    public void addQuoteToLibrary(String author, String source, String quote, List<String> tags) {
        boolean success = false;
        boolean quoteUnique = isQuoteUnique(quote);
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    author = StringFormatUtility.addSingleQuotes(author);
                    source = StringFormatUtility.addSingleQuotes(source);
                    String enquotedQuote = StringFormatUtility.addSingleQuotes(quote);
                    String element = author + ", " + source + ", " + enquotedQuote;
                    if(isValidQuote(element) && quoteUnique) {
                        updateQuoteID();
                        String sql = "INSERT INTO QUOTES (ID,AUTHOR,SOURCE,QUOTE) " +
                                "VALUES (" + quoteElementID + ", " + element + ");";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully inserted quote item DBIO Librarian");
                        System.out.println(element);
                        success = true;
                    } else {
                        System.out.println("Library Item Invalid");
                        System.out.println(element);
                    }
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
        if(success) {
            for(String tag : tags) addTagToQuote(quote, tag);
        }
        closeConnection();
    }

    public List<Quote> getQuotesByTag(String tag) {
        int tagID = getTagID(tag);
        List<Quote> collectChildren = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM QUOTE_TAGS WHERE TAG_ID=" + tag + ";";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        Quote quote = getQuoteByID(rs.getInt("QUOTE_ID"));
                        collectChildren.add(quote);
                    }
                    return collectChildren;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        return collectChildren;
    }

    private Quote getQuoteByID(int id) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM QUOTES WHERE ID=" + id + "';";
                    rs = stmt.executeQuery(sql);
                    Quote toReturn = new Quote("", "", "");
                    if(rs.next()) {
                        toReturn = new Quote(rs.getString("AUTHOR"), rs.getString("SOURCE"), rs.getString("QUOTE"));
                    }
                    return toReturn;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        closeConnection();
        return new Quote("", "", "");
    }

    public List<Quote> getAllQuotes() {
        List<Quote> collectChildren = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM QUOTES;";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        Quote quote = new Quote(rs.getString("AUTHOR"), rs.getString("SOURCE"), rs.getString("QUOTE"));
                        collectChildren.add(quote);
                    }
                    return collectChildren;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        return collectChildren;
    }

    public void addTagToQuote(String quote, String tag) {
        if(!hasTag(tag)) createTag(tag);
        int quoteID = getQuoteID(quote);
        int tagID = getTagID(tag);
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    updateQuoteTagID();
                    stmt = getConnection().createStatement();
                    String sql = "INSERT INTO QUOTE_TAGS (ID, QUOTE_ID, TAG_ID) VALUES ("
                            + quoteTagID + ", " + quoteID+ ", " + tagID + ");";
                    stmt.executeUpdate(sql);
                    System.out.println("Successfully inserted quote tag relation DBIO Librarian");

                } catch (SQLException e) {
                    System.out.println("Failed to insert tag");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt);
                }
            } else {
                invalidStatementMessage();
            }
        } else {
            notConnectedMessage("addElementToCategory DBIOLibrarian");
        }
        closeConnection();
    }

    public void deleteQuote(String quote) {
        establishConnection();
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "DELETE FROM QUOTES WHERE QUOTE='" + quote + "';";
            stmt.executeUpdate(sql);
            System.out.println("Successfully deleted '" + quote + "' from quotes");
        } catch (SQLException e) {
            System.out.println("Failed to delete library item from inventory");
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
    }

    public void deleteElementFromCategory(String name, String category) {
        establishConnection();
        Statement stmt = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "DELETE FROM INVENTORY WHERE NAME='" + name + "' AND CATEGORY='" + category + "';";
            stmt.executeUpdate(sql);
            System.out.println("Successfully deleted " + name + " from inventory");
        } catch (SQLException e) {
            System.out.println("Failed to delete library item from inventory");
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt);
        }
    }

    public void addElementToCategory(String name, String description, String category) {
        addElementToCategory(name, description, category, "");
    }

    public void addElementToCategory(String name, String description, String category, String parent) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    name = StringFormatUtility.addSingleQuotes(name);
                    description = StringFormatUtility.addSingleQuotes(description);
                    category = StringFormatUtility.addSingleQuotes(category);
                    String element = name + ", " + description + ", " + category + ", '" + parent + "'";
                    if(isValidGenre(element)) {
                        updateGenreID();
                        String sql = "INSERT INTO INVENTORY (ID,NAME,DESCRIPTION,CATEGORY, PARENT) " +
                                "VALUES (" + inventoryElementID + ", " + element + ");";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully inserted library item DBIO Librarian");
                        System.out.println(element);
                    } else {
                        System.out.println("Library Item Invalid");
                        System.out.println(element);
                    }
                } catch (SQLException e) {
                    System.out.println("Failed to insert task");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt);
                }
            } else {
                invalidStatementMessage();
            }
        } else {
            notConnectedMessage("addElementToCategory DBIOLibrarian");
        }
        closeConnection();
    }

    public void addNewRoutine(String routineName, List<ExerciseOptionsInput> exerciseData) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    updateRoutineID();
                    stmt = getConnection().createStatement();
                    routineName = StringFormatUtility.addSingleQuotes(routineName);
                    String sql = "INSERT INTO ROUTINES (ID,TITLE) " +
                            "VALUES (" + routineID + ", " + routineName + ");";
                    stmt.executeUpdate(sql);
                    System.out.println("Successfully inserted tag item CoreDB Librarian");
                    System.out.println(routineName);
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
        for(ExerciseOptionsInput exercise : exerciseData) {
            addExerciseToRoutine(exercise, routineName, routineID);
        }
    }

    private void addExerciseToRoutine(ExerciseOptionsInput exercise, String routineName, int targetRoutineID) {
        int targetExerciseID = getExerciseID(exercise.getName());
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    updateExerciseRoutineRelationsID();
                    stmt = getConnection().createStatement();

                    String sql = "INSERT INTO ROUTINE_EXERCISE_RELATIONS (ID, ROUTINE_ID, EXERCISE_ID, EXERCISE_REPS, EXERCISE_SETS, ROUTINE_NAME, EXERCISE_NAME) " +
                            "VALUES (" + exerciseRoutineRelationID + ", " + targetRoutineID + ", " + targetExerciseID + ", "
                            + exercise.getReps() + ", " + exercise.getSets() + ", " + routineName + ", '" + exercise.getName() +  "');";
                    System.out.println(sql);
                    stmt.executeUpdate(sql);
                    System.out.println("Successfully inserted exercise to routine Librarian");
                    System.out.println(sql);
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
            notConnectedMessage("addExerciseToRoutine DBIOLibrarian");
        }
        closeConnection();
    }

    public int getRoutineID(String exercise) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM ROUTINES WHERE TITLE='" + exercise + "';";
            rs = stmt.executeQuery(sql);
            if(rs.next()) return rs.getInt("ID");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return -1;
    }

    public int getExerciseID(String exercise) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM EXERCISES WHERE TITLE='" + exercise + "';";
            rs = stmt.executeQuery(sql);
            if(rs.next()) return rs.getInt("ID");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return -1;
    }

    public List<String> getExercisesByCategory(String category) {
        List<String> collectChildren = new ArrayList<>();
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM EXERCISES WHERE CATEGORY='" + category + "';";
                    rs = stmt.executeQuery(sql);
                    while(rs.next()) {
                        collectChildren.add(rs.getString("TITLE"));
                    }
                    return collectChildren;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        closeConnection();
        return collectChildren;
    }

    public void addExercise(String name, String description, String category, String subcategory) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    name = StringFormatUtility.addSingleQuotes(name);
                    description = StringFormatUtility.addSingleQuotes(description);
                    category = StringFormatUtility.addSingleQuotes(category);
                    subcategory = StringFormatUtility.addSingleQuotes(subcategory);
                    String element = name + ", " + description + ", " + category + ", " + subcategory + "";
                    if(isValidGenre(element)) {
                        updateGenreID();
                        String sql = "INSERT INTO EXERCISES (ID,TITLE,DESCRIPTION,CATEGORY, SUBCATEGORY, COMPLETED_REPS) " +
                                "VALUES (" + inventoryElementID + ", " + element + ", 0);";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully inserted exercise DBIO Librarian");
                        System.out.println(element);
                    } else {
                        System.out.println("Exercise Invalid");
                        System.out.println(element);
                    }
                } catch (SQLException e) {
                    System.out.println("Failed to insert exercise");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt);
                }
            } else {
                invalidStatementMessage();
            }
        } else {
            notConnectedMessage("addElementToCategory DBIOLibrarian");
        }
        closeConnection();
    }

    public List<ExerciseRoutineRelation> getRoutineExercises(String routine) {
        int targetRoutineID = getRoutineID(routine);
        List<ExerciseRoutineRelation> results = new ArrayList<>();
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM ROUTINE_EXERCISE_RELATIONS WHERE ROUTINE_ID=" + targetRoutineID;
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                ExerciseRoutineRelation relation = new ExerciseRoutineRelation(
                        routine,
                        rs.getString("EXERCISE_NAME"),
                        rs.getInt("ID"),
                        rs.getInt("ROUTINE_ID"),
                        rs.getInt("EXERCISE_ID"),
                        rs.getInt("EXERCISE_REPS"),
                        rs.getInt("EXERCISE_SETS")
                );
                results.add(relation);
            }
            return results;
        } catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        return results;
    }

    public List<String> getRoutines() {
        List<String> tags = new ArrayList<>();
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM ROUTINES;";
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
                tags.add(rs.getString("TITLE"));
            }
            return tags;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return tags;
    }

    public void addElementToCategory(String element, InventoryCategories category, String parentName) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    element = "'" + element +"', ''";
                    element += ", " + "'" + category.toString().toUpperCase() + "', '" + parentName + "'";
                    if(isValidGenre(element)) {
                        updateGenreID();
                        String sql = "INSERT INTO INVENTORY (ID,NAME,DESCRIPTION,CATEGORY, PARENT) " +
                                "VALUES (" + inventoryElementID + ", " + element + ");";
                        stmt.executeUpdate(sql);
                        System.out.println("Successfully inserted library item DBIO Librarian");
                        System.out.println(element);
                    } else {
                        System.out.println("Library Item Invalid");
                        System.out.println(element);
                    }
                } catch (SQLException e) {
                    System.out.println("Failed to insert task");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt);
                }
            } else {
                invalidStatementMessage();
            }
        } else {
            notConnectedMessage("addElementToCategory DBIOLibrarian");
        }
        closeConnection();
    }

    public InventoryCategories checkCategory(String name) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    String sql = "SELECT * FROM INVENTORY WHERE NAME='" + name + "';";
                    rs = stmt.executeQuery(sql);
                    if(rs.next()) {
                        return InventoryCategories.valueOf(rs.getString("CATEGORY"));
                    }
                    return InventoryCategories.GENRE;
                } catch (SQLException e) {
                    System.out.println("Failed to get libraryItem children");
                    e.printStackTrace();
                } finally {
                    closeDownDBAction(stmt, rs);
                }
            }
        }
        return InventoryCategories.GENRE;
    }

    private boolean isValidQuote(String quote) {
        String[] breakdown = quote.split("[,]\\s+");
        if(breakdown.length < 3) return false;
        else if(breakdown[0].charAt(0) != '\'') return false;
        else if(breakdown[1].charAt(0) != '\'') return false;
        else if(breakdown[2].charAt(0) != '\'') return false;
        System.out.println("Library Item Valid");
        return true;
    }

    private boolean isValidGenre(String task) {
        String[] breakdown = task.split("[,]\\s+");
        if(breakdown.length != 4) return false;
        else if(breakdown[0].charAt(0) != '\'') return false;
        else if(breakdown[1].charAt(0) != '\'') return false;
        else if(breakdown[2].charAt(0) != '\'') return false;
        else if(breakdown[3].charAt(0) != '\'') return false;
        System.out.println("Library Item Valid");
        return true;
    }

    private int getQuoteID(String quote) {
        establishConnection();
        Statement stmt = null;
        ResultSet rs = null;
        try {
            stmt = getConnection().createStatement();
            String sql = "SELECT * FROM QUOTES WHERE QUOTE='" + quote + "';";
            rs = stmt.executeQuery(sql);
            if(rs.next()) return rs.getInt("ID");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeDownDBAction(stmt, rs);
        }
        closeConnection();
        return -1;
    }

    private void updateGenreID() {
        inventoryElementID++;
    }
    private void updateQuoteID() { quoteElementID++; }
    private void updateQuoteTagID() { quoteTagID++; }
    private void updateRoutineID() { routineID++; }
    private void updateExerciseRoutineRelationsID() { exerciseRoutineRelationID++; }

}
