package resources.datatypes.exercisedata;

import java.util.List;

public class CircuitTraining {
    private List<ExerciseSet> circuit;
    private int setRepetitions;
    private int circuitRepetitions;

    public CircuitTraining(List<ExerciseSet> circuit, int setRepititions, int circuitRepetitions) {
        this.circuit = circuit;
        this.setRepetitions = setRepetitions;
        this.circuitRepetitions = circuitRepetitions;
    }

    public List<ExerciseSet> getCircuit() {
        return circuit;
    }

    public void setCircuit(List<ExerciseSet> circuit) {
        this.circuit = circuit;
    }

    public int getSetRepetitions() {
        return setRepetitions;
    }

    public void setSetRepetitions(int setRepetitions) {
        this.setRepetitions = setRepetitions;
    }

    public int getCircuitRepetitions() {
        return circuitRepetitions;
    }

    public void setCircuitRepetitions(int circuitRepetitions) {
        this.circuitRepetitions = circuitRepetitions;
    }
}
