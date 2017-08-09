package gameengine;

import skeletonkey.Attributes;
import skeletonkey.Stats;

public class Skill extends LevelingValue {
    private Attributes governingAttribute;
    private int maxLevel;
    private StatWeights weights;

    public Skill(String valueTitle, int maxLevel, Attributes governingAttribute) {
        super(valueTitle);
        this.governingAttribute = governingAttribute;
        this.maxLevel = maxLevel;
    }

    public Skill(String valueTitle, Attributes governingAttribute) {
        super(valueTitle);
        this.governingAttribute = governingAttribute;
        this.maxLevel = 100;
    }

    public Attributes getGoverningAttribute() { return governingAttribute; }
    public StatWeights getWeights() { return weights; }
    public StatWeights setWeights() { return weights; }
}
