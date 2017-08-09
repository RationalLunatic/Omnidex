package engine.components.schedule;

import java.time.LocalDateTime;

public class Deadline extends BasicTask {
    private LocalDateTime deadline;

    public Deadline(String title, String description, LocalDateTime deadline) {
        super(title, description);
        this.deadline = deadline;
    }

    public LocalDateTime getSchedule() {
        return deadline;
    }
    public void setSchedule(LocalDateTime deadline) {
        this.deadline = deadline;
    }
}
