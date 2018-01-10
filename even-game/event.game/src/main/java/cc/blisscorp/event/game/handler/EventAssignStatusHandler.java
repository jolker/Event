package cc.blisscorp.event.game.handler;

import ga.log4j.GA;
import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;

public class EventAssignStatusHandler {
	private static EventAssignStatusHandler instance = null;
	public static EventAssignStatusHandler getInstance() {
		if (instance == null) {
			synchronized(EventAssignStatusHandler.class) {
				instance = new EventAssignStatusHandler();
				GA.app.info("created singleton = " + EventAssignStatusHandler.class.getCanonicalName());
			}
		}
		return instance;
	}


	public EventAssignStatusHandler() {

	}

	public void onStatusChange(Status status, Event event) {
		GA.app.info("status eventId: " + event.getId() 
				+ " changing from " + event.getStatus().name() + " to " + status.name());
		event.setStatus(status);
	}

}
