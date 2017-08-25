package resources.sqlite;

import resources.StringFormatUtility;
import resources.datatypes.lexicographicdata.BasicWord;
import resources.datatypes.exercisedata.ExerciseRoutineRelation;
import resources.sqlite.sqlenumerations.LibrarianTables;
import resources.datatypes.Quote;
import resources.datatypes.lexicographicdata.spanish.SpanishWord;
import resources.sqlite.sqlenumerations.InventoryCategories;
import resources.sqlite.sqlenumerations.SQLTableColumns;
import ui.components.inputcomponents.ExerciseOptionsInput;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBIOLibrarian extends DBCore {
    private int inventoryElementID;
    private int exerciseID;
    private int quoteElementID;
    private int quoteTagID;
    private int routineID;
    private int exerciseRoutineRelationID;
    private int vocabularyID;
    private int subjectID;
    private int vocabularySubjectRelationID;

    public DBIOLibrarian() {
        super();
        subjectID = getGeneratedID("SUBJECT_GROUP");
        vocabularySubjectRelationID = getGeneratedID("VOCABULARY_SUBJECT_LIST");
        vocabularyID = getGeneratedID("VOCABULARY_WORD");
        exerciseID = getGeneratedID("EXERCISES");
        inventoryElementID = getGeneratedID("INVENTORY");
        quoteElementID = getGeneratedID("QUOTES");
        quoteTagID = getGeneratedID("QUOTE_TAGS");
        routineID = getGeneratedID("ROUTINES");
        exerciseRoutineRelationID = getGeneratedID("ROUTINE_EXERCISE_RELATIONS");
    }

    public void addQuoteToLibrary(String author, String source, String quote, List<String> tags) {
        boolean quoteUnique = isQuoteUnique(quote);
        author = StringFormatUtility.addDoubleQuotes(author);
        source = StringFormatUtility.addDoubleQuotes(source);
        quote = StringFormatUtility.addDoubleQuotes(quote);
        String element = author + ", " + source + ", " + quote;
        if(isValidQuote(element) && quoteUnique) {
            updateQuoteID();
            insertInto(LibrarianTables.QUOTES.toString(), "ID,AUTHOR,SOURCE,QUOTE", quoteElementID + ", " + element);
        }
    }

    public void addTagToQuote(String quote, String tag) {
        if(!hasTag(tag)) createTag(tag);
        int quoteID = getQuoteID(quote);
        int tagID = getTagID(tag);
        updateQuoteTagID();
        insertInto(LibrarianTables.QUOTE_TAGS.toString(), "ID, QUOTE_ID, TAG_ID", quoteTagID + ", " + quoteID+ ", " + tagID);
    }

    public void addElementToCategory(String name, String description, String category, String parent) {
        updateGenreID();
        name = StringFormatUtility.addDoubleQuotes(name);
        description = StringFormatUtility.addDoubleQuotes(description);
        category = StringFormatUtility.addDoubleQuotes(category);
        String element = name + ", " + description + ", " + category + ", '" + parent + "'";
        insertInto(LibrarianTables.INVENTORY.toString(), "ID, NAME, DESCRIPTION, CATEGORY, PARENT", inventoryElementID + ", " + element);
    }

    public void addNewRoutine(String routineName, List<ExerciseOptionsInput> exerciseData) {
        updateRoutineID();
        routineName = StringFormatUtility.addDoubleQuotes(routineName);
        insertInto(LibrarianTables.ROUTINES.toString(), "ID,TITLE", routineID + ", " + routineName);
        for(ExerciseOptionsInput exercise : exerciseData) {
            addExerciseToRoutine(exercise, routineName, routineID);
        }
    }

    private void addExerciseToRoutine(ExerciseOptionsInput exercise, String routineName, int targetRoutineID) {
        int targetExerciseID = getExerciseID(exercise.getName());
        updateExerciseRoutineRelationsID();
        insertInto(LibrarianTables.ROUTINE_EXERCISE_RELATIONS.toString(),
                "ID, ROUTINE_ID, EXERCISE_ID, EXERCISE_REPS, EXERCISE_SETS, ROUTINE_NAME, EXERCISE_NAME",
                exerciseRoutineRelationID + ", " + targetRoutineID + ", " + targetExerciseID + ", "
                        + exercise.getReps() + ", " + exercise.getSets() + ", " + routineName + ", '" + exercise.getName() +  "'");
    }

    public void addExercise(String name, String description, String category) {
        updateExerciseID();
        String element = StringFormatUtility.addDoubleQuotes(name) + ", " + StringFormatUtility.addDoubleQuotes(description) + ", " + StringFormatUtility.addDoubleQuotes(category);
        insertInto(LibrarianTables.EXERCISES.toString(), "ID,TITLE,DESCRIPTION,CATEGORY,SUBCATEGORY,COMPLETED_REPS", exerciseID + ", " + element + ", \"\", 0");
    }

    public void addElementToCategory(String element, InventoryCategories category, String parentName) {
        updateGenreID();
        element = "'" + element +"', ''";
        element += ", " + "'" + category.toString().toUpperCase() + "', '" + parentName + "'";
        insertInto(LibrarianTables.INVENTORY.toString(), "ID,NAME,DESCRIPTION,CATEGORY, PARENT", inventoryElementID + ", " + element);
    }

    public InventoryCategories checkCategory(String name) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            ResultSet rs = null;
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

    public void addVocabularyWord(String word, String definition, String translation, String language, String partOfSpeech) {
        updateVocabularyID();
        String toInsert = vocabularyID + ", " + StringFormatUtility.addDoubleQuotes(word) + ", " + StringFormatUtility.addDoubleQuotes(definition)
                + ", " + StringFormatUtility.addDoubleQuotes(translation) + ", " + StringFormatUtility.addDoubleQuotes(language) + ", " + StringFormatUtility.addDoubleQuotes(partOfSpeech);
        insertInto(LibrarianTables.VOCABULARY_WORD.toString(), "ID, TITLE, DESCRIPTION, TRANSLATION, LANGUAGE, PART_OF_SPEECH", toInsert);
    }

    public void deleteVocabularyWord(String word) {
        int wordID = getRowID("VOCABULARY_WORD", "TITLE='" + word + "'");
        deleteFromWhere("VOCABULARY_WORD", "TITLE='" + word + "'");
        deleteFromWhere("VOCABULARY_SUBJECT_LIST", "VOCAB_ID=" + wordID);
    }

    public List<BasicWord> getWordsOfLanguage(String language) {
        return getAllFromWhereDBList(LibrarianTables.VOCABULARY_WORD, "LANGUAGE='" + language + "'");
    }

    public void addSubjectToLanguage(String subject, String language) {
        subjectID++;
        insertInto("SUBJECT_GROUP", "ID, TITLE, LANGUAGE", subjectID + ", " + StringFormatUtility.addDoubleQuotes(subject) + ", " + StringFormatUtility.addDoubleQuotes(language));
    }

    public void deleteSubject(String subject) {
        deleteFromWhere(LibrarianTables.SUBJECT_GROUP.toString(), "TITLE='" + subject + "'");
    }

    public void addWordToSubject(String subject, String word) {
        vocabularySubjectRelationID++;
        int subjectID = getRowID("SUBJECT_GROUP", "TITLE='" + subject + "'");
        int wordID = getRowID("VOCABULARY_WORD", "TITLE='" + word + "'");
        insertInto("VOCABULARY_SUBJECT_LIST", "ID, VOCAB_ID, SUBJECT_ID", vocabularySubjectRelationID + ", " + wordID + ", " + subjectID);
    }

    public List<BasicWord> getVocabList(String subject) {
        int subjectID = getRowID(LibrarianTables.SUBJECT_GROUP.toString(), "TITLE='" + subject + "'");
        List<Integer> wordIDs = getAllFromWhereDBList(LibrarianTables.VOCABULARY_SUBJECT_LIST, "SUBJECT_ID=" + subjectID);
        List<BasicWord> words = new ArrayList<>();
        for(int id : wordIDs) {
            BasicWord toAdd = getRowWhere(LibrarianTables.VOCABULARY_WORD, "ID=" + id);
            if(toAdd != null) words.add(toAdd);
        }
        return words;
    }

    public List<String> getSubjects() {
        return getAllFrom("SUBJECT_GROUP", "TITLE");
    }

    public void addElementToCategory(String name, String description, String category) { addElementToCategory(name, description, category, ""); }

    public List<Quote> getQuotesByTag(String tag) { return getAllFromWhereDBList(LibrarianTables.QUOTES, "TAG_ID=" + getTagID(tag));}
    public List<Quote> getAllQuotes() { return getAllDBList(LibrarianTables.QUOTES); }
    public List<String> getRoutines() { return getAllFrom(LibrarianTables.ROUTINES.toString(), SQLTableColumns.TITLE.toString()); }
    public List<ExerciseRoutineRelation> getRoutineExercises(String routine) { return getAllFromWhereDBList(LibrarianTables.ROUTINE_EXERCISE_RELATIONS, "ROUTINE_ID=" + getRoutineID(routine));}
    public List<String> getExercisesByCategory(String category) { return getFromWhere(LibrarianTables.EXERCISES.toString(), "CATEGORY='" + category + "'", "TITLE"); }
    public List<String> getChildrenOfLibraryItem(String parentName) { return getFromWhere(LibrarianTables.INVENTORY.toString(), "PARENT='" + parentName + "'", "NAME"); }
    public List<String> getItemsOfCategory(String category) { return getFromWhere(LibrarianTables.INVENTORY.toString(), "CATEGORY='" + category + "'", "NAME"); }

    public void deleteExercise(String exerciseName) {
        deleteFromWhere("EXERCISES", "TITLE='"+exerciseName+"'");
    }
    public void deleteRoutine(String routineName) { deleteFromWhere(LibrarianTables.ROUTINES.toString(), "TITLE='" + routineName + "'"); }
    public void deleteQuote(String quote) {
        deleteFromWhere("QUOTES", "QUOTE='" + quote + "'");
    }
    public void deleteElementFromCategory(String name, String category) { deleteFromWhere(LibrarianTables.INVENTORY.toString(), "NAME='" + name + "' AND CATEGORY='" + category + "'"); }

    private void updateGenreID() { inventoryElementID++; }
    private void updateExerciseID() { exerciseID++; }
    private void updateQuoteID() { quoteElementID++; }
    private void updateQuoteTagID() { quoteTagID++; }
    private void updateRoutineID() { routineID++; }
    private void updateExerciseRoutineRelationsID() { exerciseRoutineRelationID++; }
    private void updateVocabularyID() { vocabularyID++; }

    private int getQuoteID(String quote) { return getRowID(LibrarianTables.QUOTES.toString(), "QUOTE='" + quote + "'");}
    public int getRoutineID(String routine) { return getRowID(LibrarianTables.ROUTINES.toString(), "TITLE='" + routine + "'"); }
    public int getExerciseID(String exercise) { return getRowID(LibrarianTables.EXERCISES.toString(), "TITLE='" + exercise + "'"); }

    public boolean isQuoteUnique(String quote) { return !rowExists(LibrarianTables.QUOTES.toString(), "QUOTE='" + quote + "'"); }

    private Quote getQuoteByID(int id) { return getRowWhere(LibrarianTables.QUOTES, "ID=" + id);}
}
