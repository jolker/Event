package cc.blisscorp.event.game.ent;

import java.util.Date;

import org.apache.commons.lang3.SerializationUtils;

public class Gifts extends DataObject {
	private static final long serialVersionUID = 1L;
	
	public static final String META_KEY_GIFTS = "gifts";

	public static enum Type { 
		NCOIN, CARD, SPECIAL
	}
	
	public static enum Status { 
		PENDING, ACTIVED ,
		AWAITING_CARD,
		AUTHORIZED, TRANSFER,
		OPENED, CLOSED
	}

	private long userId;
	
	private long eventId;
	
	private Type type;
	
	private Status status;
	
	private long amount;
	
	private float percentage;
	
	private Date receivedDate;
	
	private int quanlity;
	
	private String description;
	
	public int getQuanlity() {
		return quanlity;
	}

	public void setQuanlity(int quanlity) {
		this.quanlity = quanlity;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	
	public long getEventId() {
		return eventId;
	}
	
	public void setEventId(long eventId) {
		this.eventId = eventId;
	}
	
	public static Gifts clone(Gifts gifts) {
		return SerializationUtils.clone(gifts);
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Gifts [userId=" + userId + ", eventId=" + eventId + ", type="
				+ type + ", status=" + status + ", amount=" + amount
				+ ", percentage=" + percentage + ", receivedDate="
				+ receivedDate + ", quanlity=" + quanlity + ", description="
				+ description + ", id=" + id + ", createdAt=" + createdAt
				+ ", updatedAt=" + updatedAt + "]";
	}
	
}
