package cc.blisscorp.event.game.scheduler;

import java.util.Calendar;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.jdbc.manager.AccrueEventManager;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.GiftsManager;
import cc.blisscorp.event.game.jdbc.manager.UserManager;
import ga.log4j.GA;

public class AccrueEventScheduler {
	private static AccrueEventScheduler instance = null;

	public static AccrueEventScheduler getInstance() {
		if (instance == null) {
			synchronized (AccrueEventScheduler.class) {
				instance = new AccrueEventScheduler();
			}
		}
		return instance;
	}

	ScheduledFuture<?> scheduled = null;

	private BaseManager<AccrueEvent, EventFilter> eventMgr = null;

	private BaseManager<User, UserFilter> userMgr = null;

	private BaseManager<Gifts, GiftsFilter> giftMgr = null;

	private ThreadPoolExecutorScheduler threadPoolSheduler = null;
	
	public AccrueEventScheduler() {
		eventMgr = AccrueEventManager.getInstance();
		userMgr = UserManager.getInstance();
		giftMgr = GiftsManager.getInstance();
		threadPoolSheduler = ThreadPoolExecutorScheduler.getInstance();
	}

	public void execute() throws Exception {
		synchronized (this) {
			long execIntervalMillis = 24 * 60 * 60 * 1000;
			threadPoolSheduler.getResource().scheduleAtFixedRate(new ScheduledAccrue(), execDelayMillis(), execIntervalMillis ,TimeUnit.MILLISECONDS);
		}
	}
	/**
	 * Default set time 00:00:00
	 * @DelayTime: Lay thoi gian tu gioi hien tai toi Time da set tren, roi chay.
	 */
	private long execDelayMillis() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1);
		return calendar.getTimeInMillis() - System.currentTimeMillis();
	}

	class ScheduledAccrue implements Runnable {
		@Override
		public void run() {
			try {
				GA.app.info("scheduling accrue event");
				EventFilter filter = new EventFilter();
				filter.setType(Type.accrue);
				filter.setStatus(Status.ACTIVE);
				PageView<AccrueEvent> pv = eventMgr.search(filter);
				for (AccrueEvent event : pv.getItems()) {
					if (event.isGiftsLimit()) {
						updateUser();
						updateStatusGift(event);
					}
				}

			} catch (Exception e) {
				GA.app.error("scheduler accrue event failed. " + e.getMessage(), e);
			}
		}

		private void updateUser() throws Exception {
			UserFilter userF = new UserFilter();
			userF.setValue("fromAccrueScore", 1);
			PageView<User> pv = userMgr.search(userF);
			while (!pv.getItems().isEmpty()) {
				for (User user : pv.getItems()) {
					user.setAccrueScore(0);
					userMgr.saveOrUpdate(user);
					GA.app.info("reseted accrue score user. userId" + user.getUserId());
				}
				userF.setPageNumber(userF.getPageNumber() + 1);
				pv = userMgr.search(userF);
			}
		}

		private void updateStatusGift(AccrueEvent event) throws Exception {
			GiftsFilter giftsFilter = new GiftsFilter();
			giftsFilter.setEventId(event.getId());
			PageView<Gifts> pv = giftMgr.search(giftsFilter);
			while (!pv.getItems().isEmpty()) {
				for (Gifts gift : pv.getItems()) {
					gift.setStatus(Gifts.Status.CLOSED);
					giftMgr.saveOrUpdate(gift);
					GA.app.info("Updated gift status: Status " + gift.getStatus());
				}
				giftsFilter.setPageNumber(giftsFilter.getPageNumber() + 1);
				pv = giftMgr.search(giftsFilter);
			}
		}
	}
}
