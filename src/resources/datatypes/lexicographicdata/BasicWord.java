package resources.datatypes.lexicographicdata;

import resources.datatypes.lexicographicdata.spanish.SpanishPartsOfSpeech;

public class BasicWord {
    private SpanishPartsOfSpeech partOfSpeech;
    private String word;
    private String translation;

    public BasicWord(String word, String translation, SpanishPartsOfSpeech partOfSpeech) {
        this.word = word;
        this.translation = translation;
        this.partOfSpeech = partOfSpeech;
    }

    public SpanishPartsOfSpeech getPartOfSpeech() { return partOfSpeech; }
    public String getWord() { return word; }
    public String getTranslation() { return translation; }
}
