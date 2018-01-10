package cc.blisscorp.event.game.thrift.dev;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cc.blisscorp.event.game.thrift.client.TEventThriftClient;
import cc.blisscorp.event.game.thrift.gen.TTransaction;
import ga.log4j.GA;

public class TTransDev {
	private static Integer taskIdExec = 0;
	private static Integer maxTaskExec = 50;
	private static TEventThriftClient client = TEventThriftClient.getInstance("thrift_client");

	public static void main(String[] args) throws Exception {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(50);
		Task task = null;
		for (int i = 0; i < 1; i++) {
			while (taskIdExec >= maxTaskExec) {
				Thread.sleep(1000);
			}
			synchronized (taskIdExec) {
				taskIdExec++;
			}
			task = new Task(taskIdExec);
			scheduledExecutorService.schedule(task, 0, TimeUnit.MILLISECONDS);
		}
		
		while (taskIdExec > 0)
			Thread.sleep(100);
	}

	public static class Task implements Runnable {
		private int threadId;

		public Task(int threadId) {
			super();
			// TODO Auto-generated constructor stub
			this.threadId = threadId;
		}

		public int getThreadId() {
			return threadId;
		}

		public void setThreadId(int threadId) {
			this.threadId = threadId;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int id = 47;
			try {
				TTransaction tTrans = new TTransaction();
				tTrans.setUserId(id);
				tTrans.setAmount(100000);
				tTrans.setUserName("bliss_" + id);
				tTrans.setGameId(1);
				client.saveTrans(tTrans);
				GA.app.info(tTrans + "Thread ID: " + threadId + " success");
			} catch (Exception e) {
				GA.app.error("Fail: " + e.getMessage());
			} finally {
				synchronized (taskIdExec) {
					taskIdExec--;
				}
			}
		}

	}
}
