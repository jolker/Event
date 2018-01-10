package cc.blisscorp.event.game.handler;

import ga.log4j.GA;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;

public class TransAssignStatusHandler {
	private static TransAssignStatusHandler instance = null;
	public static TransAssignStatusHandler getInstance() {
		if (instance == null) {
			synchronized(TransAssignStatusHandler.class) {
				instance = new TransAssignStatusHandler();
				GA.app.info("created singleton = " + TransAssignStatusHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	public void onStatusChange(Status status, Transaction trans) {
		GA.app.info("status transId: " + trans.getId() 
				+ " changed from " + trans.getStatus().name() + " to " + status.name());
		trans.setStatus(status);
	}

}
