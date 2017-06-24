package resources;

public class StringFormatUtility {
    private StringFormatUtility() {
        throw new AssertionError();
    }

    public static String capitalize(String word) {
        return word.toUpperCase().substring(0, 1) + word.toLowerCase().substring(1);
    }

}
