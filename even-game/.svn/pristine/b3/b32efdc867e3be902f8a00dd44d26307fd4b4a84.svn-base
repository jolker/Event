package cc.blisscorp.event.game.handler;

import ga.log4j.GA;

import org.apache.commons.codec.binary.StringUtils;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;

public class GiftsAssignStatusHandler {
	private static GiftsAssignStatusHandler instance = null;
	public static GiftsAssignStatusHandler getInstance() {
		if (instance == null) {
			synchronized(GiftsAssignStatusHandler.class) {
				instance = new GiftsAssignStatusHandler();
				GA.app.info("created singleton = " + GiftsAssignStatusHandler.class.getCanonicalName());
			}
		}
		return instance;
	}


	public void onStatusChange(Status status, Gifts gifts) {
		if (StringUtils.equals(status.name(), Status.OPENED.name()) 
				&& (StringUtils.equals(gifts.getStatus().name(), Status.AUTHORIZED.name()) 
						|| StringUtils.equals(gifts.getStatus().name(), Status.TRANSFER.name()))) {
			gifts.setStatus(status);
		}
		else if (StringUtils.equals(status.name(), Status.CLOSED.name()) 
				&& StringUtils.equals(gifts.getStatus().name(), Status.PENDING.name())) {
			gifts.setStatus(status);
		}
	}

}
