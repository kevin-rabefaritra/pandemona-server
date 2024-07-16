package studio.startapps.pandemona.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public interface DateUtils {

    /**
     * Formats a local date to String
     * @param localDate The LocalDate instance to be processed
     * @return A long string representation of the localDate
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

    static LocalDateTime now() {
        return LocalDateTime.now();
    }

    static String formatDateTimeISO(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME;
        return localDateTime.format(dateTimeFormatter);
    }

    static LocalDateTime parseDateTimeISO(String dateTime) {
        return LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_DATE_TIME);
    }
}
