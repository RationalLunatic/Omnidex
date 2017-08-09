package gameengine;

public class LevelingValue {
    private double totalExperience;
    private String valueTitle;
    private double minutes;
    private double hours;

    public LevelingValue(String valueTitle) {
        totalExperience = 0;
    }

    public void gainXP(int xp) {
        totalExperience += xp;
        minutes = totalExperience;
        hours = totalExperience / 60;
    }
    public void setLevel(double level) { hours = level * level;  totalExperience = hours * 60; }
    public double getLevel() {
        return Math.sqrt(hours);
    }
}
