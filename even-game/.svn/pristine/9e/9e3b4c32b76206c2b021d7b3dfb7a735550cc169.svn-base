package cc.blisscorp.event.game.handler;

import org.apache.commons.codec.binary.StringUtils;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import ga.log4j.GA;

public class AccrueGiftsStatusChangeHandler {
	private static AccrueGiftsStatusChangeHandler instance = null;
	public static AccrueGiftsStatusChangeHandler getInstance() {
		if (instance == null) {
			synchronized(AccrueGiftsStatusChangeHandler.class) {
				instance = new AccrueGiftsStatusChangeHandler();
				GA.app.info("created singleton = " + AccrueGiftsStatusChangeHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	public void onStatusChange(Status status, Gifts gifts) {
		if (StringUtils.equals(status.name(), Status.OPENED.name()) 
				|| StringUtils.equals(gifts.getStatus().name(), Status.PENDING.name())) {
			gifts.setStatus(status);
		}		
	}
	
}
