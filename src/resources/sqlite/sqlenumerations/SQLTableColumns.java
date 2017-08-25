package resources.sqlite.sqlenumerations;

import resources.sqlite.sqlenumerations.SQLDataTypes;

public enum SQLTableColumns {
    TITLE(SQLDataTypes.TEXT),
    DESCRIPTION(SQLDataTypes.TEXT),
    SCHEDULED_DATE(SQLDataTypes.DATE),
    SCHEDULED_TIME(SQLDataTypes.DATE),
    SCHEDULED_DATETIME(SQLDataTypes.DATETIME),
    DURATION_IN_MINUTES(SQLDataTypes.INT),
    CATEGORY(SQLDataTypes.TEXT),
    SUBCATEGORY(SQLDataTypes.TEXT),
    COMPLETED_REPS(SQLDataTypes.INT),
    PARENT(SQLDataTypes.TEXT),
    NAME(SQLDataTypes.TEXT),
    BIRTHDAY(SQLDataTypes.DATE),
    GENDER(SQLDataTypes.TEXT),
    REPETITIONS(SQLDataTypes.INT),
    GOOD_HABIT(SQLDataTypes.BOOLEAN),
    EVENT_DATE(SQLDataTypes.INT),
    EVENT_ID(SQLDataTypes.INT),
    TIMELINE_ID(SQLDataTypes.INT),
    ACTIVE_PROJECT(SQLDataTypes.TEXT),
    ACTIVE_PLAN(SQLDataTypes.TEXT),
    ACTIVE_GOAL(SQLDataTypes.TEXT),
    AUTHOR(SQLDataTypes.TEXT),
    SOURCE(SQLDataTypes.TEXT),
    ISBN(SQLDataTypes.TEXT),
    PUBLISHER(SQLDataTypes.TEXT),
    PUBLICATION_DATE(SQLDataTypes.INT),
    PUBLISHER_LOCATION(SQLDataTypes.TEXT),
    ARTICLE_TITLE(SQLDataTypes.TEXT),
    JOURNAL_TITLE(SQLDataTypes.TEXT),
    VOLUME(SQLDataTypes.TEXT),
    ISSUE(SQLDataTypes.TEXT),
    ROUTINE_ID(SQLDataTypes.INT),
    EXERCISE_ID(SQLDataTypes.INT),
    EXERCISE_REPS(SQLDataTypes.INT),
    EXERCISE_SETS(SQLDataTypes.INT),
    EXERCISE_NAME(SQLDataTypes.TEXT),
    ROUTINE_NAME(SQLDataTypes.TEXT),
    QUOTE_ID(SQLDataTypes.INT),
    TAG_ID(SQLDataTypes.INT),
    QUOTE(SQLDataTypes.TEXT),
    SCHEDULE(SQLDataTypes.INT),
    COMPLETED(SQLDataTypes.BOOLEAN),
    LANGUAGE(SQLDataTypes.TEXT),
    PART_OF_SPEECH(SQLDataTypes.TEXT),
    TRANSLATION(SQLDataTypes.TEXT),
    VOCAB_ID(SQLDataTypes.INT),
    SUBJECT_ID(SQLDataTypes.INT),
    GOVERNING_ATTRIBUTE (SQLDataTypes.TEXT),
    GOVERNING_STAT(SQLDataTypes.TEXT),
    ID(SQLDataTypes.INT);

    private SQLDataTypes dataType;

    SQLTableColumns(SQLDataTypes dataType) {
        this.dataType = dataType;
    }

    public SQLDataTypes getDataType() { return dataType; }
}
