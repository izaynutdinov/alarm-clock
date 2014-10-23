package net.iskandar.alarmclock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by iskandar on 10/21/14.
 */
public class Utils {

    public static String formatToYesterdayOrTodayOrTomorrow(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        DateFormat timeFormatter = new SimpleDateFormat("hh:mma");

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today " + timeFormatter.format(date);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday " + timeFormatter.format(date);
        } else if(calendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
            return "Tomorrow " + timeFormatter.format(date);
        } else {
            return String.format(Locale.US, "%tb %<td, %<tY %<tH:%<tM ", date);
        }
    }
}
