package resources.sqlite.testpackage;

import resources.sqlite.DBCore;

public class DBIOSage extends DBCore {
    private int skillID;

    public DBIOSage() {
        skillID = getGeneratedID("SKILL");
    }

}
