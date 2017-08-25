package resources.sqlite.sqlenumerations;

import resources.datatypes.SQLColumn;
import resources.sqlite.sqlenumerations.SQLTableColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum LibrarianTables {
    EXERCISES(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true),
            new SQLColumn(SQLTableColumns.CATEGORY, SQLTableColumns.CATEGORY.getDataType(), true),
            new SQLColumn(SQLTableColumns.SUBCATEGORY, SQLTableColumns.SUBCATEGORY.getDataType(), true),
            new SQLColumn(SQLTableColumns.COMPLETED_REPS, SQLTableColumns.COMPLETED_REPS.getDataType(), true)
            ))),
    ROUTINES(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.REPETITIONS, SQLTableColumns.REPETITIONS.getDataType(), true),
    new SQLColumn(SQLTableColumns.COMPLETED_REPS, SQLTableColumns.COMPLETED_REPS.getDataType(), true)
    ))),
    ROUTINE_EXERCISE_RELATIONS(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.ROUTINE_ID, SQLTableColumns.ROUTINE_ID.getDataType(), true),
            new SQLColumn(SQLTableColumns.EXERCISE_ID, SQLTableColumns.EXERCISE_ID.getDataType(), true),
            new SQLColumn(SQLTableColumns.EXERCISE_REPS, SQLTableColumns.EXERCISE_REPS.getDataType(), true),
            new SQLColumn(SQLTableColumns.EXERCISE_SETS, SQLTableColumns.EXERCISE_SETS.getDataType(), true),
            new SQLColumn(SQLTableColumns.EXERCISE_NAME, SQLTableColumns.EXERCISE_NAME.getDataType(), true),
            new SQLColumn(SQLTableColumns.ROUTINE_NAME, SQLTableColumns.ROUTINE_NAME.getDataType(), true)
    ))),
    TAGS(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true)
    ))),
    QUOTE_TAGS(new ArrayList<>(Arrays.asList(
                    new SQLColumn(SQLTableColumns.QUOTE_ID, SQLTableColumns.QUOTE_ID.getDataType(), true),
                    new SQLColumn(SQLTableColumns.TAG_ID, SQLTableColumns.TAG_ID.getDataType(), true)
                    ))),
    QUOTES(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.AUTHOR, SQLTableColumns.AUTHOR.getDataType(), true),
            new SQLColumn(SQLTableColumns.SOURCE, SQLTableColumns.SOURCE.getDataType(), true),
            new SQLColumn(SQLTableColumns.QUOTE, SQLTableColumns.QUOTE.getDataType(), true)
    ))),
    INVENTORY(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true),
            new SQLColumn(SQLTableColumns.CATEGORY, SQLTableColumns.CATEGORY.getDataType(), true),
            new SQLColumn(SQLTableColumns.PARENT, SQLTableColumns.PARENT.getDataType(), false)
            ))),
    BIBLIOGRAPHIC_ARCHIVE(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.AUTHOR, SQLTableColumns.AUTHOR.getDataType(), false),
            new SQLColumn(SQLTableColumns.SOURCE, SQLTableColumns.SOURCE.getDataType(), false),
            new SQLColumn(SQLTableColumns.ISBN, SQLTableColumns.ISBN.getDataType(), false),
            new SQLColumn(SQLTableColumns.PUBLISHER, SQLTableColumns.PUBLISHER.getDataType(), false),
            new SQLColumn(SQLTableColumns.PUBLISHER_LOCATION, SQLTableColumns.PUBLISHER_LOCATION.getDataType(), false),
            new SQLColumn(SQLTableColumns.PUBLICATION_DATE, SQLTableColumns.PUBLICATION_DATE.getDataType(), false),
            new SQLColumn(SQLTableColumns.ARTICLE_TITLE, SQLTableColumns.ARTICLE_TITLE.getDataType(), false),
            new SQLColumn(SQLTableColumns.JOURNAL_TITLE, SQLTableColumns.JOURNAL_TITLE.getDataType(), false),
            new SQLColumn(SQLTableColumns.VOLUME, SQLTableColumns.VOLUME.getDataType(), false),
            new SQLColumn(SQLTableColumns.ISSUE, SQLTableColumns.ISSUE.getDataType(), false)
            ))),
    HISTORICAL_EVENT(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true),
            new SQLColumn(SQLTableColumns.EVENT_DATE, SQLTableColumns.EVENT_DATE.getDataType(), true)
    ))),
    HISTORICAL_TIMELINE(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true)
    ))),
    EVENT_TIMELINE_RELATIONS(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.EVENT_ID, SQLTableColumns.EVENT_ID.getDataType(), true),
            new SQLColumn(SQLTableColumns.TIMELINE_ID, SQLTableColumns.TIMELINE_ID.getDataType(), true)
    ))),
    VOCABULARY_WORD(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.DESCRIPTION, SQLTableColumns.DESCRIPTION.getDataType(), true),
            new SQLColumn(SQLTableColumns.TRANSLATION, SQLTableColumns.TRANSLATION.getDataType(), true),
            new SQLColumn(SQLTableColumns.LANGUAGE, SQLTableColumns.LANGUAGE.getDataType(), true),
            new SQLColumn(SQLTableColumns.PART_OF_SPEECH, SQLTableColumns.PART_OF_SPEECH.getDataType(), true)
    ))),
    SUBJECT_GROUP(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.TITLE, SQLTableColumns.TITLE.getDataType(), true),
            new SQLColumn(SQLTableColumns.LANGUAGE, SQLTableColumns.LANGUAGE.getDataType(), true)

            ))),
    VOCABULARY_SUBJECT_LIST(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.VOCAB_ID, SQLTableColumns.VOCAB_ID.getDataType(), true),
            new SQLColumn(SQLTableColumns.SUBJECT_ID, SQLTableColumns.SUBJECT_ID.getDataType(), true)
    ))),
    PERSONAL_SETTINGS(new ArrayList<>(Arrays.asList(
            new SQLColumn(SQLTableColumns.NAME, SQLTableColumns.NAME.getDataType(), true),
            new SQLColumn(SQLTableColumns.GENDER, SQLTableColumns.GENDER.getDataType(), true),
            new SQLColumn(SQLTableColumns.BIRTHDAY, SQLTableColumns.BIRTHDAY.getDataType(), true)
    )));

    private List<SQLColumn> columns;

    LibrarianTables(List<SQLColumn> columns) {
        this.columns = columns;
    }

    public List<SQLColumn> getColumns() { return columns; }
}

