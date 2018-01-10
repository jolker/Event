package cc.blisscorp.event.game.scheduler;

import ga.log4j.GA;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.bliss.framework.common.Config;
import com.nct.framework.util.ConvertUtils;

import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.jdbc.manager.AccrueEventManager;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.LuckyBoxEventManager;

public class EventScheduler {
	private static EventScheduler instance = null;

	public static EventScheduler getInstance() {
		if (instance == null) {
			synchronized (EventScheduler.class) {
				instance = new EventScheduler();
			}
		}
		return instance;
	}

	ScheduledFuture<?> scheduled = null;

	private int eventExecIntervalMin = 60;

	private BaseManager<LuckyBoxEvent, EventFilter> luckyBoxEventMgr = null;
	
	private BaseManager<AccrueEvent, EventFilter> accrueEventMgr = null;

	private ThreadPoolExecutorScheduler threadPoolSheduler = null;

	public EventScheduler() {
		luckyBoxEventMgr = LuckyBoxEventManager.getInstance();
		accrueEventMgr = AccrueEventManager.getInstance();
		threadPoolSheduler = ThreadPoolExecutorScheduler.getInstance();
		eventExecIntervalMin = ConvertUtils.toInt(Config.getParam("scheduler", "event_exec_interval_min"));
	}

	public void execute() {
		synchronized (this) {
			scheduled = threadPoolSheduler.getResource().schedule(new ScheduledRunnable(), eventExecIntervalMin, TimeUnit.MINUTES);
		}
	}

	class ScheduledRunnable implements Runnable {
		@Override
		public void run() {
			try {
				GA.app.info("scheduling lucky box event");
				// Lucky box event status change from active->done
				EventFilter filter = new EventFilter();
				filter.setStatus(Status.ACTIVE);
				PageView<LuckyBoxEvent> pv = luckyBoxEventMgr.search(filter);
				for (LuckyBoxEvent event : pv.getItems()) {
					if (new Date().after(event.getEndDate())) {
						event.setStatus(Status.DONE);
						luckyBoxEventMgr.saveOrUpdate(event);
					}
				}

				// Lucky box event status change from pending->active
				filter.setStatus(Status.PENDING);
				pv = luckyBoxEventMgr.search(filter);
				for (LuckyBoxEvent event : pv.getItems()) {
					if (new Date().after(event.getStartDate())) {
						event.setStatus(Status.ACTIVE);
						luckyBoxEventMgr.saveOrUpdate(event);
					}
				}
				
				GA.app.info("scheduling accrue event");
				// Accrue event status change from active->done
				filter.setStatus(Status.ACTIVE);
				PageView<AccrueEvent> pvAccrueEvent = accrueEventMgr.search(filter);
				for (AccrueEvent event : pvAccrueEvent.getItems()) {
					if (new Date().after(event.getEndDate())) {
						event.setStatus(Status.DONE);
						accrueEventMgr.saveOrUpdate(event);
					}
				}

				// Accrue event status change from pending->active
				filter.setStatus(Status.PENDING);
				pvAccrueEvent = accrueEventMgr.search(filter);
				for (AccrueEvent event : pvAccrueEvent.getItems()) {
					if (new Date().after(event.getStartDate())) {
						event.setStatus(Status.ACTIVE);
						accrueEventMgr.saveOrUpdate(event);
					}
				}				
				
			} catch (Exception e) {
				GA.app.error("scheduler event failed. " + e.getMessage(), e);
			} finally {
				synchronized (this) {
					scheduled = threadPoolSheduler.getResource().schedule(new ScheduledRunnable(), eventExecIntervalMin, TimeUnit.MINUTES);
				}
			}
		}
	}

}
