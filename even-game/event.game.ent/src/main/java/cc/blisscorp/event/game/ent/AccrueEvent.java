package cc.blisscorp.event.game.ent;

import org.apache.commons.lang3.SerializationUtils;

public class AccrueEvent extends Event {
	private static final long serialVersionUID = 1L;
	
	public static final String META_REWARD = "reward";
	
	private boolean isGiftsLimit;
	
	public boolean isGiftsLimit() {
		return isGiftsLimit;
	}

	public void setGiftsLimit(boolean isGiftsLimit) {
		this.isGiftsLimit = isGiftsLimit;
	}

	public static AccrueEvent clone(AccrueEvent accrueEvent) {
		return SerializationUtils.clone(accrueEvent);
	}
	
}
