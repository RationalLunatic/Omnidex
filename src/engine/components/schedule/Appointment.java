package engine.components.schedule;

import java.time.LocalDateTime;

public class Appointment extends Deadline {
    public Appointment(String title, String description, LocalDateTime deadline) {
        super(title, description, deadline);
    }
}
