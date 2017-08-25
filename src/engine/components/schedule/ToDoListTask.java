package engine.components.schedule;

import java.time.LocalDateTime;

public class ToDoListTask extends BasicTask {

    private LocalDateTime dateTime;

    public ToDoListTask(String title, String description, String dateTime) {
        super(title, description, 0);
        this.dateTime = LocalDateTime.parse(dateTime);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String toString() {
        return "Name: " + getTitle() + " Desc: " + getDescription() + " LocalDateTime: " + dateTime.toString();
    }

}
