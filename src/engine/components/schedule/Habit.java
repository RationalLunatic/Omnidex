package engine.components.schedule;

public class Habit extends BasicTask {
    private int count;
    private boolean goodHabit;

    public Habit(String title, String description, boolean goodHabit, int count) {
        super(title, description);
        this.goodHabit = goodHabit;
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() { this.count++; }

    public boolean isGoodHabit() {
        return goodHabit;
    }

    public void setGoodHabit(boolean goodHabit) {
        this.goodHabit = goodHabit;
    }
}
