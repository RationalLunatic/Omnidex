package gameengine;

import resources.StringFormatUtility;
import skeletonkey.Stats;

public class CoreStat extends LevelingValue {
    private Stats stat;

    public CoreStat(Stats stat) {
        super(StringFormatUtility.capitalize(stat.toString()));
        this.stat = stat;
    }

    public Stats statKey() {
        return stat;
    }
}
