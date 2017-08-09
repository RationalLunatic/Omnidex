package gameengine;

import skeletonkey.QuaternalParadigms;

public class StatWeights {
    private QuaternalParadigms paradigm;
    private double physicalWeight;
    private double mentalWeight;
    private double emotionalWeight;
    private double spiritualWeight;

    public StatWeights(QuaternalParadigms paradigm, double physicalWeight, double mentalWeight, double emotionalWeight, double spiritualWeight) {
        this.paradigm = paradigm;
        this.physicalWeight = physicalWeight;
        this.mentalWeight = mentalWeight;
        this.emotionalWeight = emotionalWeight;
        this.spiritualWeight = spiritualWeight;
    }

    public double getPhysicalWeight() {
        return physicalWeight;
    }

    public double getMentalWeight() {
        return mentalWeight;
    }

    public double getEmotionalWeight() {
        return emotionalWeight;
    }

    public double getSpiritualWeight() {
        return spiritualWeight;
    }

    public QuaternalParadigms getParadigm() {
        return paradigm;
    }
}
