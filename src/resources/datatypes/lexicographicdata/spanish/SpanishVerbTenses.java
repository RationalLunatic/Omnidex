package resources.datatypes.lexicographicdata.spanish;

import java.util.*;

public enum SpanishVerbTenses {
    PRESENT (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    IMPERFECT (new ArrayList<>(Arrays.asList("aba", "abas", "abas", "aba", "abaamos", "abais", "aban")),
            new ArrayList<>(Arrays.asList("ia", "ias", "ias", "ia", "iamos", "iais", "ian")),
            new ArrayList<>(Arrays.asList("ia", "ias", "ias", "ia", "iamos", "iais", "ian"))),
    PRETERITE (new ArrayList<>(Arrays.asList("e", "aste", "aste", "o", "amos", "asteis", "aron")),
            new ArrayList<>(Arrays.asList("i", "iste", "iste", "io", "imos", "isteis", "ieron")),
            new ArrayList<>(Arrays.asList("i", "iste", "iste", "io", "imos", "isteis", "ieron"))),
    FUTURE (new ArrayList<>(Arrays.asList("are", "aras", "aras", "ara", "aremos", "areis", "aran")),
            new ArrayList<>(Arrays.asList("ere", "eras", "eras", "era", "eremos", "ereis", "eran")),
            new ArrayList<>(Arrays.asList("ire", "iras", "iras", "ira", "iremos", "ireis", "iran"))),

    PRESENT_PERFECT (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    PAST_PERFECT (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    PAST_ANTERIOR (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    FUTURE_PERFECT (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),

    SIMPLE_CONDITIONAL (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    COMPOUND_CONDITIONAL (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),

    AFFIRMATIVE_IMPERATIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    NEGATIVE_IMPERATIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),

    PRESENT_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    IMPERFECT_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    FUTURE_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),

    PRESENT_PERFECT_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    PAST_PERFECT_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en"))),
    FUTURE_PERFECT_SUBJUNCTIVE (new ArrayList<>(Arrays.asList("o", "as", "as", "a", "amos", "ais", "an")),
            new ArrayList<>(Arrays.asList("o", "es", "es", "e", "emos", "eis", "en")),
            new ArrayList<>(Arrays.asList("o", "es", "is", "e", "imos", "is", "en")));

    private Map<SpanishVerbEndings, Map<SpanishVerbPersons, Map<SpanishVerbNumber, String>>> conjugations;
    private Map<SpanishVerbEndings, String> secondPersonFormalEndings;

    SpanishVerbTenses(List<String> ar, List<String> er, List<String> ir) {
        conjugations = new HashMap<>();
        secondPersonFormalEndings = new HashMap<>();
        secondPersonFormalEndings.put(SpanishVerbEndings.AR, ar.get(2));
        secondPersonFormalEndings.put(SpanishVerbEndings.ER, er.get(2));
        secondPersonFormalEndings.put(SpanishVerbEndings.IR, ir.get(2));
        conjugations.put(SpanishVerbEndings.AR, loadVerbEndingTypeMaps(ar));
        conjugations.put(SpanishVerbEndings.ER, loadVerbEndingTypeMaps(er));
        conjugations.put(SpanishVerbEndings.IR, loadVerbEndingTypeMaps(ir));
    }

    public String conjugate(SpanishVerbPersons person, SpanishVerbNumber number, SpanishVerbEndings ending) {
        return conjugations.get(ending).get(person).get(number);
    }

    public String getSecondPersonFormalEnding(SpanishVerbEndings ending) {
        return secondPersonFormalEndings.get(ending);
    }

    private Map<SpanishVerbPersons, Map<SpanishVerbNumber, String>> loadVerbEndingTypeMaps(List<String> endingsList) {
        Map<SpanishVerbPersons, Map<SpanishVerbNumber, String>> persons = new HashMap<>();
        Map<SpanishVerbNumber, String> firstPerson = new HashMap<>();
        Map<SpanishVerbNumber, String> secondPerson = new HashMap<>();
        Map<SpanishVerbNumber, String> thirdPerson = new HashMap<>();
        firstPerson.put(SpanishVerbNumber.SINGULAR, endingsList.get(0));
        firstPerson.put(SpanishVerbNumber.PLURAL, endingsList.get(4));
        secondPerson.put(SpanishVerbNumber.SINGULAR, endingsList.get(1));
        secondPerson.put(SpanishVerbNumber.PLURAL, endingsList.get(5));
        thirdPerson.put(SpanishVerbNumber.SINGULAR, endingsList.get(3));
        thirdPerson.put(SpanishVerbNumber.PLURAL, endingsList.get(6));
        persons.put(SpanishVerbPersons.FIRST, firstPerson);
        persons.put(SpanishVerbPersons.SECOND, secondPerson);
        persons.put(SpanishVerbPersons.THIRD, thirdPerson);
        return persons;
    }


}
