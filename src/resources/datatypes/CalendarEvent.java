package resources.datatypes;

import java.time.LocalDateTime;

public class CalendarEvent {
    private String name;
    private String description;
    private LocalDateTime dateTime;

    public CalendarEvent(String name, String description, LocalDateTime dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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
}
