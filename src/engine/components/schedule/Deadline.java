package engine.components.schedule;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by shaev_000 on 5/8/2016.
 */
public class Deadline {
    private String title;
    private String info;
    private List<LocalDateTime> schedule;

    public Deadline(String title, String info, List<LocalDateTime> schedule) {
        this.title = title;
        this.info = info;
        this.schedule = schedule;
    }

    public List<LocalDateTime> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<LocalDateTime> schedule) {
        this.schedule = schedule;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
