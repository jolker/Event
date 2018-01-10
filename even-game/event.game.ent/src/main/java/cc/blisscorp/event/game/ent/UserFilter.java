package cc.blisscorp.event.game.ent;
/**
 * 
 * @author aitd
 *
 */
public class UserFilter extends DataFilter {

	private static final long serialVersionUID = 1L;
	
	private long userId;
	
	private int accrueScore;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public int getAccrueScore() {
		return accrueScore;
	}
	
	public void setAccrueScore(int accrueScore) {
		this.accrueScore = accrueScore;
	}
}
