package resources.datatypes.lexicographicdata.spanish;

import java.util.Map;

public class SpanishConjugation {
    private SpanishVerbTenses tense;
    private SpanishVerbEndings ending;
    private SpanishVerbMoods mood;
    private Map<SpanishVerbPersons, Map<SpanishVerbNumber, String>> conjugations;

    public SpanishConjugation(SpanishVerbTenses tense, SpanishVerbMoods mood, SpanishVerbEndings ending) {
        this.tense = tense;
        this.mood = mood;
        this.ending = ending;
        init();
    }

    private void init() {

    }
}
