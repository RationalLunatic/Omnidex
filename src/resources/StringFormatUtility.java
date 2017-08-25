package resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class StringFormatUtility {
    private StringFormatUtility() {
        throw new AssertionError();
    }

    public static String capitalize(String word) {
        String[] resultBuilder = word.split("\\s+");
        if(resultBuilder.length < 2) return word.toUpperCase().substring(0, 1) + word.toLowerCase().substring(1);
        String result = "";
        for(String st : resultBuilder) {
            result += st.toUpperCase().substring(0, 1) + st.toLowerCase().substring(1) + " ";
        }
        return result.substring(0, result.length()-1);
    }

    public static String convertToHour(LocalTime time) {
        String toReturn = "";
        toReturn += (time.getHour() + 1 > 12) ? time.getHour() + 1 - 12 : time.getHour() + 1;
        toReturn += ":00 ";
        toReturn += (time.getHour() + 1 > 12) ? "P.M." : "A.M.";
        return toReturn;
    }

    public static String convertToHourMinutes(LocalTime time) {
        String toReturn = "";
        if(time.getHour() == 0) toReturn += "12";
        else {
            toReturn += (time.getHour() > 12) ? time.getHour() - 12 : time.getHour();
        }
        toReturn += ":";
        if(time.getMinute() < 10) toReturn += "0";
        toReturn += time.getMinute() + " ";
        toReturn += (time.getHour() >= 12) ? "P.M." : "A.M.";
        return toReturn;
    }

    public static String convertToHourMinutesSeconds(LocalTime time) {
        String toReturn = "";
        if(time.getHour() == 0) toReturn += "12";
        else {
            toReturn += (time.getHour() > 12) ? time.getHour() - 12 : time.getHour();
        }
        toReturn += ":";
        if(time.getMinute() < 10) toReturn += "0";
        toReturn += time.getMinute() + ":";
        int second = time.getSecond();
        if(second < 10) toReturn += "0";
        toReturn+= second + " ";
        toReturn += (time.getHour() >= 12) ? "P.M." : "A.M.";
        return toReturn;
    }

    public static LocalTime convertToLocalTimeFromHH_MM_AMPM(String time) {
        if(time.substring(0, 2).contains(":")) time = "0" + time;
        int hour = Integer.parseInt(time.substring(0, 2));
        if(time.contains("A.M.") && hour == 12) hour = 0;
        int minute = Integer.parseInt(time.substring(3, 5));
        if(hour != 12) hour = (time.contains("A.M.")) ? hour : hour + 12;
        String toConvert = "";
        if(hour >= 24) hour -= 24;
        if(hour < 10) toConvert += "0";
        toConvert += "" + hour + ":";
        if(minute < 10) toConvert += "0";
        toConvert += minute;
        return LocalTime.parse(toConvert);
    }

    public static String addDoubleQuotes(String word) {
        return "\"" + word + "\"";
    }

    public static String convertDate(LocalDate date) {
        String toReturn = "";
        toReturn += capitalize(date.getDayOfWeek().toString()) + " ";
        toReturn += capitalize(date.getMonth().toString()) + " ";
        toReturn += date.getDayOfMonth() + ", " + date.getYear();
        return toReturn;
    }

}
