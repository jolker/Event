package cc.blisscorp.event.game.ent;

import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

public class LuckyBoxEvent extends Event {
	private static final long serialVersionUID = 1L;

	private List<Gifts> giftsCard;

	private float giftsCardPercent;

	private List<Gifts> giftsNcoin;

	private float giftsNcoinPercent;

	private List<Gifts> giftsSpecial;

	private float giftsSpecialPercent;

	private long payMoney;

	private long money2Score;

	private int score2Gifts;

	private int giftsPerPerson;

	public List<Gifts> getGiftsCard() {
		return giftsCard;
	}

	public void setGiftsCard(List<Gifts> giftsCard) {
		this.giftsCard = giftsCard;
	}

	public float getGiftsCardPercent() {
		return giftsCardPercent;
	}

	public void setGiftsCardPercent(float giftsCardPercent) {
		this.giftsCardPercent = giftsCardPercent;
	}

	public List<Gifts> getGiftsNcoin() {
		return giftsNcoin;
	}

	public void setGiftsNcoin(List<Gifts> giftsNcoin) {
		this.giftsNcoin = giftsNcoin;
	}

	public float getGiftsNcoinPercent() {
		return giftsNcoinPercent;
	}

	public void setGiftsNcoinPercent(float giftsNcoinPercent) {
		this.giftsNcoinPercent = giftsNcoinPercent;
	}

	public List<Gifts> getGiftsSpecial() {
		return giftsSpecial;
	}

	public void setGiftsSpecial(List<Gifts> giftsSpecial) {
		this.giftsSpecial = giftsSpecial;
	}

	public float getGiftsSpecialPercent() {
		return giftsSpecialPercent;
	}

	public void setGiftsSpecialPercent(float giftsSpecialPercent) {
		this.giftsSpecialPercent = giftsSpecialPercent;
	}

	public long getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(long payMoney) {
		this.payMoney = payMoney;
	}

	public long getMoney2Score() {
		return money2Score;
	}

	public void setMoney2Score(long money2Score) {
		this.money2Score = money2Score;
	}

	public int getScore2Gifts() {
		return score2Gifts;
	}

	public void setScore2Gifts(int score2Gifts) {
		this.score2Gifts = score2Gifts;
	}

	public int getGiftsPerPerson() {
		return giftsPerPerson;
	}

	public void setGiftsPerPerson(int giftsPerPerson) {
		this.giftsPerPerson = giftsPerPerson;
	}
	
	public static LuckyBoxEvent clone(LuckyBoxEvent luckyBoxEvent) {
		return SerializationUtils.clone(luckyBoxEvent);
	}
}
