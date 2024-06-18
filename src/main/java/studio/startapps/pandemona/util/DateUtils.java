package studio.startapps.pandemona.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    /**
     * Formats a local date
     * @param localDate
     * @return
     */
    public static String formatDate(LocalDate localDate) {
        return DateUtils.formatDate(localDate, "d LLLL uuuu");
    }

    public static String formatDateISO(LocalDate localDate) {
        return DateUtils.formatDate(localDate, "yyyy-MM-dd");
    }

    public static String formatDate(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }
}
