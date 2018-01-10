package cc.blisscorp.event.game.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.GiftsDAOImpl;
import cc.blisscorp.event.game.jdbc.dao.LuckyBoxEventDAOImpl;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;
import ga.log4j.GA;

public class AssignScore4UserHandler {
	private static AssignScore4UserHandler instance = null;

	public static AssignScore4UserHandler getInstance() {
		if (instance == null) {
			synchronized (AssignScore4UserHandler.class) {
				instance = new AssignScore4UserHandler();
				GA.app.info("created singleton = " + AssignScore4UserHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<User> userDAO = null;

	private BaseDAO<LuckyBoxEvent> eventDAO = null;

	private BaseDAO<Gifts> giftsDAO = null;

	public AssignScore4UserHandler() {
		userDAO = UserDAOImpl.getInstance();
		eventDAO = LuckyBoxEventDAOImpl.getInstance();
		giftsDAO = GiftsDAOImpl.getInstance();
	}

	public void onTransCreating(TransactionFrame tFrame, Transaction trans) throws Exception {

		LuckyBoxEvent event = getEventActived(tFrame);
		if (event == null) {
			GA.app.info("not found any event status is actived");
			return;
		}
		// check amount
		if (event.getPayMoney() > trans.getAmount()) {
			GA.app.info("amount < payMoney. eventId : " + event.getId() + " transId: " + trans.getId());
			return;
		}

		// calculator newScore
		User user = userDAO.find(tFrame, trans.getUserId(), true);

		Long newScore = (trans.getAmount() * event.getMoney2Score()) / event.getPayMoney();
		newScore += user.getScore();

		// limit giftsPerPerson
		int giftsRecieved = getGiftsReceived(tFrame, trans.getUserId(), event.getId());
		if (event.getGiftsPerPerson() > 0 && giftsRecieved >= event.getGiftsPerPerson()) { // out of limit gifts
			user.setScore(newScore.intValue());
			userDAO.update(tFrame, user);
			return;
		}

		int newGifts = newScore.intValue() / event.getScore2Gifts();
		if (newGifts >= 1) {
			// update gifts
			if (event.getGiftsPerPerson() > 0 && (newGifts + giftsRecieved) > event.getGiftsPerPerson())
				newGifts = event.getGiftsPerPerson() - giftsRecieved;

			// update score
			newScore = newScore - newGifts * event.getScore2Gifts();

			// record gifts4user
			int giftsIndex;
			for (giftsIndex = 1; giftsIndex <= newGifts; giftsIndex++)
				if (!StringUtils.equals(event.getStatus().name(), Status.DONE.name()))
					generateGifts(tFrame, event, trans.getUserId());

			// gifts is not enough
			// ex: - newGifts = 10
			// - gifts quanlity = 5
			if (newGifts - giftsIndex > 0 && StringUtils.equals(event.getStatus().name(), Status.DONE.name())) {
				newScore = newScore + ((newGifts - giftsIndex) * event.getScore2Gifts());
				newGifts = giftsIndex;
			}
		}

		// update user
		if (newGifts > 0)
			user.setGiftsTotal(user.getGiftsTotal() + newGifts);
		user.setScore(newScore.intValue());
		userDAO.update(tFrame, user);

	}

	private void generateGifts(TransactionFrame tFrame, LuckyBoxEvent event, long userId) throws Exception {
		Type type = null;
		long amount = 0L;
		String desc = "";
		boolean updateEvent = false; // update quanlity event

		if (event.getGiftsCardPercent() == 0) { // only giftsNcoin
			type = Type.NCOIN;
			amount = getAmount(event.getGiftsNcoin());
		} else if (event.getGiftsNcoinPercent() == 0) { // only giftsCard
			type = Type.CARD;
			List<Gifts> giftsActive = getGiftsQuanlityGreatZero(event.getGiftsCard());
			amount = getAmount(giftsActive);
			updateGifts(amount, event.getGiftsCard());
			// final gifts
			if (giftsActive.size() == 1 && giftsActive.get(0).getQuanlity() == 0)
				EventAssignStatusHandler.getInstance().onStatusChange(Status.DONE, event);

			updateEvent = true;

		} else { // both (giftsNcoin + giftsCard)
			List<Gifts> giftsCardActive = getGiftsQuanlityGreatZero(event.getGiftsCard());
			if (giftsCardActive.isEmpty()) { // giftsCard quanlity = 0
				type = Type.NCOIN;
				amount = getAmount(event.getGiftsNcoin());
			} else {
				int[] freq = getPercents(event);
				long[] types = getTypes(event);

				long randType = randomNumber(types, freq);
				type = Type.values()[(int) randType];
				if (StringUtils.equals(type.name(), Type.NCOIN.name())) {
					amount = getAmount(event.getGiftsNcoin());
				}
				if (StringUtils.equals(type.name(), Type.SPECIAL.name())) {
					List<Gifts> giftsSpecial = event.getGiftsSpecial();
					Gifts giftsSpec = giftsSpecial.get(0);
					if (giftsSpec.getQuanlity() > 0) {
						desc = giftsSpec.getDescription();
						// update gifts
						giftsSpec.setQuanlity(giftsSpec.getQuanlity() - 1);

						updateEvent = true;
					} else //
						type = Type.CARD;
				}
				if (StringUtils.equals(type.name(), Type.CARD.name())) {
					amount = getAmount(giftsCardActive);
					updateGifts(amount, event.getGiftsCard());

					updateEvent = true;
				}
			}
		}

		Gifts gifts = new Gifts();
		gifts.setType(type);
		gifts.setAmount(amount);
		gifts.setUserId(userId);
		gifts.setEventId(event.getId());
		gifts.setStatus(Gifts.Status.PENDING);
		gifts.setDescription(desc);
		giftsDAO.save(tFrame, gifts);

		if (updateEvent)
			eventDAO.update(tFrame, event);
	}

	private long[] getTypes(LuckyBoxEvent event) {
		int size = 2;
		if (event.getGiftsSpecialPercent() > 0)
			size++;

		int index = 0;
		long[] types = new long[size];
		if (event.getGiftsSpecialPercent() > 0)
			types[index++] = Type.SPECIAL.ordinal();

		types[index++] = Type.CARD.ordinal();
		types[index++] = Type.NCOIN.ordinal();

		if (event.getGiftsCardPercent() > event.getGiftsNcoinPercent()) {
			if (event.getGiftsSpecialPercent() > 0) {
				types[1] = Type.NCOIN.ordinal();
				types[2] = Type.CARD.ordinal();
			} else {
				types[0] = Type.NCOIN.ordinal();
				types[1] = Type.CARD.ordinal();
			}
		}
		return types;
	}

	private int[] getPercents(LuckyBoxEvent event) {
		int size = 2;
		if (event.getGiftsSpecialPercent() > 0)
			size++;
		int[] percens = new int[size];
		int index = 0;
		if (event.getGiftsSpecialPercent() > 0)
			percens[index++] = (int) event.getGiftsSpecialPercent();
		percens[index++] = (int) event.getGiftsCardPercent();
		percens[index++] = (int) event.getGiftsNcoinPercent();
		// order arrays
		Arrays.sort(percens);

		return percens;
	}

	private List<Gifts> getGiftsQuanlityGreatZero(List<Gifts> giftsList) {
		List<Gifts> newGifts = new ArrayList<Gifts>();
		for (Gifts gifts : giftsList) {
			if (gifts.getQuanlity() > 0)
				newGifts.add(gifts);
		}
		return newGifts;
	}

	private void updateGifts(long amount, List<Gifts> giftsList) {
		for (Gifts gifts : giftsList) {
			if (gifts.getAmount() == amount) {
				gifts.setQuanlity(gifts.getQuanlity() - 1);
				if (gifts.getQuanlity() == 0) {
					// gifts.setPercentage(0F);
					// TODO: impl move percent
				}
			}
		}
	}

	private long getAmount(List<Gifts> giftsList) {
		long[] amounts = getAmounts(giftsList);
		int[] freq = getPercents(giftsList);
		return randomNumber(amounts, freq);
	}

	private int getGiftsReceived(TransactionFrame tFrame, long userId, long eventId) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("user_id");
		values.add(userId);
		params.add("event_id");
		values.add(eventId);
		int offset = 0, limit = 1;
		String orderKey = "created_at";
		PageView<Gifts> pv = giftsDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		return (int) pv.getTotalItems();
	}

	private LuckyBoxEvent getEventActived(TransactionFrame tFrame) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("status_");
		values.add(LuckyBoxEvent.Status.ACTIVE.ordinal());
		params.add("type_");
		values.add(LuckyBoxEvent.Type.lucky_box.ordinal());
		int offset = 0, limit = 1;
		String orderKey = "created_at";
		PageView<LuckyBoxEvent> pv = eventDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		if (pv.getItems().size() > 0) {
			LuckyBoxEvent event = pv.getItems().get(0);
			if (event.getGiftsCard() != null)
				event = eventDAO.find(tFrame, event.getId(), true); // lock quanlity
			return event;
		}
		return null;
	}

	private long[] getAmounts(List<Gifts> gifts) {
		long[] amounts = new long[gifts.size()];
		for (int index = 0; index < gifts.size(); index++)
			amounts[index] = gifts.get(index).getAmount();
		return amounts;
	}

	private int[] getPercents(List<Gifts> gifts) {
		int[] freq = new int[gifts.size()];
		for (int index = 0; index < gifts.size(); index++) {
			Float percent = gifts.get(index).getPercentage();
			freq[index] = percent.intValue();
		}
		return freq;
	}

	private long randomNumber(long[] arr, int[] freq) {
		// Create and fill the prefix array
		int[] prefix = new int[arr.length];
		prefix[0] = freq[0];
		for (int i = 1; i < arr.length; i++) {
			prefix[i] = freq[i - 1] + freq[i];
		}
		int r = (int) (Math.random() * prefix[arr.length - 1]) + 1;
		// findCeil convert the random value to index of original array.
		int indexCeil = findCeil(prefix, r, 0, arr.length - 1);
		return arr[indexCeil];
	}

	private int findCeil(int[] prefix, int r, int lo, int hi) {
		while (lo < hi) {
			int mid = lo + (hi - lo) / 2;
			if (prefix[mid] < r) {
				lo = mid + 1;
			} else {
				hi = mid;
			}
		}
		return prefix[lo] >= r ? lo : -1;
	}

}
