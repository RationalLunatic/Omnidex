package gameengine;

import skeletonkey.Attributes;

public class Skill extends LevelingValue {
    private Attributes governingAttribute;

    public Skill(String valueTitle, Attributes governingAttribute) {
        super(valueTitle);
    }


}
