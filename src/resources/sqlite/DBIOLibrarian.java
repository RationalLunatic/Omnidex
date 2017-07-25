package resources.sqlite;

import resources.StringFormatUtility;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBIOLibrarian extends DBCore {
    private int inventoryElementID;

    public DBIOLibrarian() {
        super();
        inventoryElementID = getGeneratedID("INVENTORY");
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
        return collectChildren;
    }

    public void addElementToCategory(String name, String description, String category) {
        establishConnection();
        if(isConnectionEstablished()) {
            Statement stmt = null;
            if(getStatementStatus() == StatementStatus.EMPTY) {
                try {
                    stmt = getConnection().createStatement();
                    name = StringFormatUtility.addSingleQuotes(name);
                    description = StringFormatUtility.addSingleQuotes(description);
                    category = StringFormatUtility.addSingleQuotes(category);
                    String element = name + ", " + description + ", " + category + ", ''";
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

    private void updateGenreID() {
        inventoryElementID++;
    }

}
