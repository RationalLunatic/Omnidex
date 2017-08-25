package resources.datatypes.exercisedata;

public class ExerciseSet {
    private PhysicalExercise exercise;
    private int reps;

    public ExerciseSet(PhysicalExercise exercise, int reps) {
        this.exercise = exercise;
        this.reps = reps;
    }

    public PhysicalExercise getExercise() { return exercise; }
    public int getReps() { return reps; }
    public void setExercise(PhysicalExercise exercise) { this.exercise = exercise; }
    public void setReps(int reps) { this.reps = reps; }
}
