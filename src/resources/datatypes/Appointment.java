package resources.datatypes;

import java.time.LocalDateTime;

public class Appointment extends CalendarEvent {
    public Appointment(String name, String description, LocalDateTime dateTime) {
        super(name, description, dateTime);
    }
}
