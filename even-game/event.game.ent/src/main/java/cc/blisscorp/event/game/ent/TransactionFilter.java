package cc.blisscorp.event.game.ent;

import cc.blisscorp.event.game.ent.Transaction.Status;

/**
 * @author aitd
 */
public class TransactionFilter extends DataFilter {

	private static final long serialVersionUID = 1L;

	private long userId;
	
	private Status status;
	
	private String eventType;

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

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}	
}
