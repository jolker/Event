package cc.blisscorp.event.game.ent;

import java.util.Date;

import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;

/**
 * @author aitd
 */
public class EventFilter extends DataFilter {
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private Type type;
	
	private Status status;
	
	private Date startDate;

	private Date endDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
}
