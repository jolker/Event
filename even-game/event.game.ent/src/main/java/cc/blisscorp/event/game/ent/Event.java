package cc.blisscorp.event.game.ent;

import java.util.Date;

/**
 * @author aitd
 */
public class Event extends DataObject {

	private static final long serialVersionUID = 1L;

	public static enum Status {
		PENDING, ACTIVE, DONE
	}
	
	public static enum Type{
		lucky_box, accrue
	}
	
	private String name;

	private Date startDate;

	private Date endDate;

	private Status status;
	
	private Type type;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}
