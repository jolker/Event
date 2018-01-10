package cc.blisscorp.event.game.dev;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;

import cc.blisscorp.event.game.ent.utils.DateUtils;

public class Main {
	
	// private BaseDAO<AccrueEvent> eventDAO = AccrueEventDAOImpl.getInstance();
	public static void main(String[] args) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.set(Calendar.MILLISECOND, 1);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		System.out.println(cal.getTime());
		
		if(DateUtils.getDateTime(new Date()).equals(cal.getTime())) {
			System.out.println("Done!");
			System.out.println(DateUtils.getDateTime(new Date()));
			System.out.println(cal.getTime());
			
		}
		System.out.println(execDelayMillis());
		System.out.println(System.currentTimeMillis());
		System.out.println(execDelayMillis() - System.currentTimeMillis() - 1);
		
	}
	protected static long execDelayMillis() throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 1);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
		return calendar.getTimeInMillis();
	}
	public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				return entry.getKey();
			}
		}
		return null;
	}

	public static <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
		Set<T> keys = new HashSet<T>();
		for (Entry<T, E> entry : map.entrySet()) {
			if (Objects.equals(value, entry.getValue())) {
				keys.add(entry.getKey());
			}
		}
		return keys;
	}
}
