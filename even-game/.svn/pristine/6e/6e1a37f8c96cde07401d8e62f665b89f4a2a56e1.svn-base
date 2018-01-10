package cc.blisscorp.event.game.scheduler;

import ga.log4j.GA;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;
import cc.blisscorp.event.game.ent.TransactionFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.TransManager;

public class TransactionScheduler {
	private static TransactionScheduler instance = null;

	public static TransactionScheduler getInstance() {
		if (instance == null) {
			synchronized (TransactionScheduler.class) {
				instance = new TransactionScheduler();
			}
		}
		return instance;
	}

	private int intervalExec = 1;
	
	ScheduledFuture<?> scheduled = null;

	private ThreadPoolExecutorScheduler threadPoolSheduler = null;
	
	private BaseManager<Transaction, TransactionFilter> transMgr = null;
	
	public TransactionScheduler() {
		transMgr = TransManager.getInstance();
		threadPoolSheduler = ThreadPoolExecutorScheduler.getInstance();
	}
	
	public void execute() {
		synchronized (this) {
			scheduled = threadPoolSheduler.getResource().schedule(new ScheduledRunnable(), intervalExec, TimeUnit.SECONDS);
		}
	}
	
	
	class ScheduledRunnable implements Runnable {
		@Override
		public void run() {
			try {
				GA.app.info("scheduling transaction ... ");
				TransactionFilter filter = new TransactionFilter();
				filter.setStatus(Status.PENDING);
				filter.setPageNumber(1); 
				filter.setPageSize(100);
				
				PageView<Transaction> pv = transMgr.search(filter);
				while (pv.getItems() != null && pv.getItems().size() > 0) {
					for (Transaction trans : pv.getItems()) {
						trans.setStatus(Status.AUTHORIZED);
						transMgr.saveOrUpdate(trans);
					}
					
					// increment pageNumber
					filter.setPageNumber(filter.getPageNumber() + 1);
					pv = transMgr.search(filter);
				}				
			} catch (Exception e) {
				GA.app.error(e.getMessage(), e);
			}  finally {
				synchronized (this) {
					scheduled = threadPoolSheduler.getResource().schedule(new ScheduledRunnable(), intervalExec, TimeUnit.SECONDS);
				}
			}
		}		
	}

}
