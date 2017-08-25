package resources.datatypes.lexicographicdata.spanish;

public class SpanishVerb extends SpanishWord {
    private SpanishVerbEndings ending;
    private String stem;

    public SpanishVerb(String word, String translation) {
        super(word, translation, SpanishPartsOfSpeech.VERB);
        if(getWord().substring(getWord().length()-2, getWord().length()).equals("ir")) {
            ending = SpanishVerbEndings.IR;
        } else if(getWord().substring(getWord().length()-2, getWord().length()).equals("er")) {
            ending = SpanishVerbEndings.ER;
        } else if(getWord().substring(getWord().length()-2, getWord().length()).equals("ar")) {
            ending = SpanishVerbEndings.AR;
        }
        stem = getWord().substring(0, getWord().length()-2);
    }

    public String getStem() { return stem; }
    public SpanishVerbEndings getVerbEndingType() { return ending; }
}
