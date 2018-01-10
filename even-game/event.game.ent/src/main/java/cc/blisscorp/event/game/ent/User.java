package cc.blisscorp.event.game.ent;

public class User extends DataObject {
	private static final long serialVersionUID = 1L;
	
	public static final String META_KEY_USER = "user";
	
	private long userId;
	
	private String userName;
	
	private int score;
	
	private int giftsTotal;
	
	private int accrueScore;
	
	public int getAccrueScore() {
		return accrueScore;
	}

	public void setAccrueScore(int accrueScore) {
		this.accrueScore = accrueScore;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
	
	public static User clone(User user) {
		return null;
	}

	public int getGiftsTotal() {
		return giftsTotal;
	}

	public void setGiftsTotal(int giftsTotal) {
		this.giftsTotal = giftsTotal;
	}

}
