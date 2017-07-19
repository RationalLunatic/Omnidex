package engine.components.schedule;

import java.time.LocalDateTime;

public class ToDoListTask {
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private boolean completed;

    public ToDoListTask(String name, String description, String dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = LocalDateTime.parse(dateTime);
        this.completed = false;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String toString() {
        return "Name: " + name + " Desc: " + description + " LocalDateTime: " + dateTime.toString()
                + " Completed: " + completed;
    }

}
