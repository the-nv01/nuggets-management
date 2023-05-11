package nuggets.demo.Model;
import org.apache.logging.log4j.util.Strings;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {

    public static final String CHARGE_CAMPAIGN_USE_RESULT_PATTERN = "yyyy/MM/dd HH:mm";
    public static final String CHARGE_USE_RESULT_TIMESTAMP_REPORT_PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final String YEAR_MONTH_DAY_DASH = "yyyy-MM-dd";

    public static final String format(Date date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }

    public static final String formatOrDefault(Date date, String pattern, String defaultValue) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(date);
        }catch (Exception ex){
            return defaultValue;
        }
    }

    public static final Date parse(String pattern, String date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        try {
            return simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static final String toString(Date date) {
        if (date == null) return Strings.EMPTY;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String strDate = dateFormat.format(date);
        return strDate;
    }



    public static final int diffInMinutes(Date fromDate, Date toDate) {
        // TODO: check null
        Instant start = fromDate.toInstant();
        Instant end = toDate.toInstant();
        Duration timeElapsed = Duration.between(start, end);
        return (int) timeElapsed.toMinutes();
    }

    public static final String getTimeByMinutes(int times) {
        int hours = times / 60; // since both are ints, you get an int
        int minutes = times % 60;
        return String.format("%02d:%02d", hours, minutes);
    }

    public static final String getDateDiffIn24HFormatByDuration(Date fromDate, Date toDate) {
        //TODO: check NPE
        long duration = toDate.getTime() - fromDate.getTime();
        long hours = TimeUnit.MILLISECONDS.toHours(duration);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration) % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}

