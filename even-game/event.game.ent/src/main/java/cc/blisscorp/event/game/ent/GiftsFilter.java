package cc.blisscorp.event.game.ent;
/**
 * 
 * @author aitd
 *
 */

import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;

public class GiftsFilter extends DataFilter {
	private static final long serialVersionUID = 1L;
	
	private long userId;
	
	private Status status;
	
	private Type type;
	
	private long eventId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getEventId() {
		return eventId;
	}

	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
}
