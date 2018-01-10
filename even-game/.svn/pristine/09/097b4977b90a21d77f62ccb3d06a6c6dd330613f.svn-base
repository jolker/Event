package cc.blisscorp.event.game.scheduler;

import java.util.concurrent.ScheduledThreadPoolExecutor;

import com.bliss.framework.common.Config;
import com.bliss.framework.util.ConvertUtils;

/**
 * 
 * @author tuyenpv
 *
 */
public class ThreadPoolExecutorScheduler {
	private static ThreadPoolExecutorScheduler instance = null;
	public static ThreadPoolExecutorScheduler getInstance() {
		if (instance == null) {
			synchronized(ThreadPoolExecutorScheduler.class) {
				instance = new ThreadPoolExecutorScheduler();
			}
		}
		return instance;
	}
	
	int corePoolSize = 32;
	
	private ScheduledThreadPoolExecutor threadPoolExec = null;
	
	public ThreadPoolExecutorScheduler() {
		corePoolSize = ConvertUtils.toInt(Config.getParam("scheduler", "thread_pool_size"));
		threadPoolExec = new ScheduledThreadPoolExecutor(corePoolSize);
	}
	
	public ScheduledThreadPoolExecutor getResource() {
		return threadPoolExec;
	}
}
