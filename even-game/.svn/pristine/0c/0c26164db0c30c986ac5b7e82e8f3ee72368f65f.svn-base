package cc.blisscorp.event.game.ent;

import org.apache.commons.lang3.SerializationUtils;

public class Transaction extends DataObject {
	private static final long serialVersionUID = 1L;
	
	public static enum Status { 
		PENDING, AUTHORIZED, COMPLETED, ERROR
	}
	
	private int gameId;
	
	private String userName;
	
	private long userId;
	
	private long amount;
	
	private Status status;
	
	public int getGameId() {
		return gameId;
	}

	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public static Transaction clone(Transaction tran) {
		return SerializationUtils.clone(tran);
	}

	@Override
	public String toString() {
		return "Transaction [gameId=" + gameId + ", userName=" + userName + ", userId=" + userId + ", amount=" + amount
				+ ", status=" + status + "]";
	}

}
