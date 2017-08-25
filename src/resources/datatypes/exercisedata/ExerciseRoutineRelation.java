package resources.datatypes.exercisedata;

public class ExerciseRoutineRelation {
    private String routineName;
    private String exerciseName;
    private int relationID;
    private int routineID;
    private int exerciseID;
    private int numReps;
    private int numSets;

    public ExerciseRoutineRelation(String routineName, String exerciseName, int relationID, int routineID, int exerciseID, int numReps, int numSets) {
        this.routineName = routineName;
        this.exerciseName = exerciseName;
        this.relationID = relationID;
        this.routineID = routineID;
        this.exerciseID = exerciseID;
        this.numReps = numReps;
        this.numSets = numSets;
    }

    public String getRoutineName() {
        return routineName;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public int getRelationID() {
        return relationID;
    }

    public int getRoutineID() {
        return routineID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public int getNumReps() {
        return numReps;
    }

    public int getNumSets() {
        return numSets;
    }
}
