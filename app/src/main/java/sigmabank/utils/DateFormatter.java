package sigmabank.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    /**
     * 
     * @param date
     * @return
     */
    public static String format(LocalDate date) {
        return date.format(formatter);
    }
}
