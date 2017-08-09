package resources.sqlite;

public enum PathfinderRelationTables {

    PROJECT_TASK_RELATIONS (RelationColumnNames.PROJECT_ID, RelationColumnNames.TASK_ID),
    PROJECT_DEADLINE_RELATIONS (RelationColumnNames.PROJECT_ID, RelationColumnNames.DEADLINE_ID),
    PLAN_TASK_RELATIONS (RelationColumnNames.PLAN_ID, RelationColumnNames.TASK_ID),
    PLAN_DEADLINE_RELATIONS (RelationColumnNames.PLAN_ID, RelationColumnNames.DEADLINE_ID),
    PLAN_PROJECT_RELATIONS (RelationColumnNames.PLAN_ID, RelationColumnNames.PROJECT_ID),
    GOAL_TASK_RELATIONS (RelationColumnNames.GOAL_ID, RelationColumnNames.TASK_ID),
    GOAL_DEADLINE_RELATIONS (RelationColumnNames.GOAL_ID, RelationColumnNames.DEADLINE_ID),
    GOAL_PROJECT_RELATIONS (RelationColumnNames.GOAL_ID, RelationColumnNames.PROJECT_ID),
    GOAL_PLAN_RELATIONS (RelationColumnNames.GOAL_ID, RelationColumnNames.PLAN_ID);

    private RelationColumnNames first;
    private RelationColumnNames second;

    PathfinderRelationTables(RelationColumnNames firstColumn, RelationColumnNames secondColumn) {
        this.first = firstColumn;
        this.second = secondColumn;
    }

    public RelationColumnNames getFirstRelation() { return first; }
    public RelationColumnNames getSecondRelation() { return second; }

    public enum RelationColumnNames {
        PROJECT_ID,
        PLAN_ID,
        GOAL_ID,
        TASK_ID,
        DEADLINE_ID
    }
}
