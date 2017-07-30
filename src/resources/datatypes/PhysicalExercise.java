package resources.datatypes;

public class PhysicalExercise {
    private String name;
    private String description;
    private ExerciseCategories category;

    public PhysicalExercise(String name, String description, ExerciseCategories category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public ExerciseCategories getCategory() { return category; }
    public void setName(String name) { this.name = name; }
    public void setDescription(String description) { this.description = description; }
    public void setCategory(ExerciseCategories category) { this.category = category; }
}
