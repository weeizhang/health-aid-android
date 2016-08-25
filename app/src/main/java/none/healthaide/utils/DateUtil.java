package none.healthaide.utils;

import android.text.format.DateFormat;

import org.joda.time.LocalDateTime;

public class DateUtil {
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static String dateToString(int year, int month, int day) {
        return DateFormat.format(DateUtil.DATE_FORMAT,
                new LocalDateTime().withYear(year).withMonthOfYear(month).withDayOfMonth(day).toDate()).toString();
    }
}
