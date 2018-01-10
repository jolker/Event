package cc.blisscorp.lucky.box.admin.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    private static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ss";

    private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {
        @Override
        public DateFormat get() {
            DateFormat f = super.get();
            if (f == null) {
                f = new SimpleDateFormat(PATTERN_DATE);
                f.setTimeZone(TimeZone.getTimeZone("GMT+7"));
                set(f);
            }
            return f;
        }
    };

    public static Date getDateTime(String strDate) throws Exception {
        if (strDate != null) {
            try {
                return DATE_FORMAT.get().parse(strDate);
            } catch (ParseException e) {
                throw new Exception("failed to parse dateTime: " + strDate);
            }
        }
        return null;
    }

    public static String getDateTime(Date date) throws Exception {
        if (date == null) {
            return null;
        }
        return DATE_FORMAT.get().format(date);
    }

}
