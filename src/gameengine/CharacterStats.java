package gameengine;

import skeletonkey.Stats;

import java.util.HashMap;
import java.util.Map;

public class CharacterStats {
    private Map<Stats, CoreStat> characterStats;

    public CharacterStats() {
        init();
    }

    private void init() {
        characterStats = new HashMap<>();
        for(Stats stat : Stats.values()) {
            characterStats.put(stat, new CoreStat(stat));
        }
    }

    public void setStat(Stats stat, double value) {
        characterStats.get(stat).setLevel(value);
    }

    public CoreStat getStat(Stats stat) {
        return characterStats.get(stat);
    }
}
