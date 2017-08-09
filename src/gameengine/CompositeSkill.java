package gameengine;

import skeletonkey.Attributes;

import java.util.List;

public class CompositeSkill extends Skill {
    private List<Skill> componentSkills;

    public CompositeSkill(String valueTitle, Attributes governingAttribute) {
        super(valueTitle, governingAttribute);
    }

    public List<Skill> getComponentSkills() { return componentSkills; }
    public void addSkill(Skill skill) { componentSkills.add(skill); }
}
