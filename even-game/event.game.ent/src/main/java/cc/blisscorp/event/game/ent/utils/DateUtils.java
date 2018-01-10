package cc.blisscorp.event.game.ent.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

	/**
	 * https://en.wikipedia.org/wiki/ISO_8601
	 */
	private static final String PATTERN_DATE = "yyyy-MM-dd HH:mm:ss";

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
		if (date == null)
			return null;
		return DATE_FORMAT.get().format(date);
	}

	public static long getDateTimeByLong(Date date) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = null;
		if (date != null) {
			try {
				d = sdf.parse(DATE_FORMAT.get().format(date));
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(d);
				long time = calendar.getTimeInMillis();
				return time;
			} catch (ParseException e) {
				throw new Exception("failed to parse dateTime: " + date);
			}
		}
		return 0;
	}
	
	public static Date getCurrentDayWithTimeZero(Date date) throws Exception {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		return getDateTime(getDateTime(cal.getTime()));
	}

}
