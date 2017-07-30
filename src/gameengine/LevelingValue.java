package gameengine;

public class LevelingValue {
    private double totalExperience;
    private String valueTitle;
    private final double maxXP = 10000;
    private final double maxLevel = 100;

    public LevelingValue(String valueTitle) {
        totalExperience = 0;
    }

    public void gainXP(int xp) { totalExperience += xp; }
    public void setLevel(double level) { totalExperience = level * level; }
    public double getLevel() {
        return Math.sqrt(totalExperience);
    }
}
