package resources.sqlite;

import engine.components.schedule.ToDoListTask;
import resources.datatypes.Quote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class SQLiteJDBC {
    private DBIOTaskTable taskIO;
    private DBIOLibrarian libraryIO;
    private static SQLiteJDBC sInstance;

    private SQLiteJDBC() {
        taskIO = new DBIOTaskTable();
        libraryIO = new DBIOLibrarian();
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

    public void addToLibrary(String text, InventoryCategories category, String parentName) {
        libraryIO.addElementToCategory(text, category, parentName);
    }

    public List<String> getChildrenOfLibraryItem(String parentName) {
        return libraryIO.getChildrenOfLibraryItem(parentName);
    }

    public List<String> getItemsOfLibraryCategory(String category) {
        return libraryIO.getItemsOfCategory(category);
    }

    public InventoryCategories checkLibraryCategory(String name) {
        return libraryIO.checkCategory(name);
    }

    public void addToLibrary(String name, String desc, String category) {
        libraryIO.addElementToCategory(name, desc, category);
    }

    public void addToLibrary(String name, String desc, String category, String parent) {
        libraryIO.addElementToCategory(name, desc, category, parent);
    }

    public void addQuoteToLibrary(String author, String source, String quote, List<String> tags) {
        libraryIO.addQuoteToLibrary(author, source, quote, tags);
    }

    public void deleteQuote(String quote) {
        libraryIO.deleteQuote(quote);
    }

    public List<Quote> getQuotesByTag(String tag) {
        return libraryIO.getQuotesByTag(tag);
    }

    public List<Quote> getAllQuotes() {
        return libraryIO.getAllQuotes();
    }

    public List<String> getTags() { return libraryIO.getTags(); }

    public boolean isQuoteUnique(String quote) { return libraryIO.isQuoteUnique(quote); }

    public void deleteFromLibrary(String name, String category) {
        libraryIO.deleteElementFromCategory(name, category);
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
