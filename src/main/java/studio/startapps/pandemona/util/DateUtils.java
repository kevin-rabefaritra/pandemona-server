package studio.startapps.pandemona.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface DateUtils {

    /**
     * Formats a local date
     * @param localDate
     * @return
     */
    static String formatDate(LocalDate localDate) {
        return DateUtils.formatDate(localDate, "d LLLL uuuu");
    }

    static String formatDateISO(LocalDate localDate) {
        return DateUtils.formatDate(localDate, "yyyy-MM-dd");
    }

    static String formatDate(LocalDate localDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
        return localDate.format(dateTimeFormatter);
    }

    static LocalDate nextSaturdayAfter(LocalDate localDate) {
        while (localDate.getDayOfWeek() != DayOfWeek.SATURDAY) {
            localDate = localDate.plusDays(1);
        }
        return localDate;
    }

    static LocalDate nextSaturdayAfter() {
        return nextSaturdayAfter(LocalDate.now());
    }
}
