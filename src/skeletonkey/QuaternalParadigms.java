package skeletonkey;

import java.util.ArrayList;
import java.util.List;

public enum QuaternalParadigms {
    BODY(Stats.STRENGTH, Stats.DEXTERITY, Stats.PERCEPTION, Stats.VITALITY, Attributes.INTENSITY, Attributes.CONSTANCY),
    MIND(Stats.INTELLIGENCE, Stats.CUNNING, Stats.CHARISMA, Stats.WISDOM, Attributes.THEORY, Attributes.PRAXIS),
    SOUL(Stats.DISCIPLINE, Stats.NERVE, Stats.COURAGE, Stats.WILLPOWER, Attributes.ALACRITY, Attributes.RESOLUTION),
    SPIRIT(Stats.CREATIVITY, Stats.EMPATHY, Stats.FAITH, Stats.AWARENESS, Attributes.INSIGHT, Attributes.VISION);

    private Stats physicalStat;
    private Stats mentalStat;
    private Stats emotionalStat;
    private Stats spiritualStat;

    private Attributes offensiveAttribute;
    private Attributes defensiveAttribute;

    QuaternalParadigms(Stats physical, Stats mental, Stats emotional, Stats spiritual, Attributes offensive, Attributes defensive) {
        this.physicalStat = physical;
        this.mentalStat = mental;
        this.emotionalStat = emotional;
        this.spiritualStat = spiritual;
        this.offensiveAttribute = offensive;
        this.defensiveAttribute = defensive;
    }

    public Stats getPhysicalStat() {
        return physicalStat;
    }

    public Stats getMentalStat() {
        return mentalStat;
    }

    public Stats getEmotionalStat() {
        return emotionalStat;
    }

    public Stats getSpiritualStat() {
        return spiritualStat;
    }

    public Attributes getOffensiveAttribute() {
        return offensiveAttribute;
    }

    public Attributes getDefensiveAttribute() {
        return defensiveAttribute;
    }

    public List<Stats> getStats() {
        List<Stats> toReturn = new ArrayList<>();
        toReturn.add(physicalStat);
        toReturn.add(mentalStat);
        toReturn.add(emotionalStat);
        toReturn.add(spiritualStat);
        return toReturn;
    }
}
