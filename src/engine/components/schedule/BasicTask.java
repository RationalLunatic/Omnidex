package engine.components.schedule;

public class BasicTask {
    private String title;
    private String description;
    private int durationInMinutes;

    public BasicTask(String title, String description, int durationInMinutes) {
        this.title = title;
        this.description = description;
        this.durationInMinutes = durationInMinutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() { return durationInMinutes; }

    public void setDuration(int minutes) { durationInMinutes = minutes; }
}
