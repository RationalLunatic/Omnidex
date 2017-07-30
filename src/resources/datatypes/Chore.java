package resources.datatypes;

import java.time.LocalDateTime;

public class Chore extends CalendarEvent {
    public Chore(String name, String description, LocalDateTime dateTime) {
        super(name, description, dateTime);
    }
}
