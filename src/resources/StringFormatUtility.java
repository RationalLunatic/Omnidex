package resources;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class StringFormatUtility {
    private StringFormatUtility() {
        throw new AssertionError();
    }

    public static String capitalize(String word) {
        return word.toUpperCase().substring(0, 1) + word.toLowerCase().substring(1);
    }

    public static String convertToHour(LocalTime dateTime) {
        String toReturn = "";
        toReturn += (dateTime.getHour() + 1 > 12) ? dateTime.getHour() + 1 - 12 : dateTime.getHour() + 1;
        toReturn += ":00 ";
        toReturn += (dateTime.getHour() + 1 > 12) ? "P.M." : "A.M.";
        return toReturn;
    }

}
