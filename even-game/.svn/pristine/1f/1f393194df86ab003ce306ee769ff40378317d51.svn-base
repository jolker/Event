package cc.blisscorp.event.game.handler;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import com.bliss.framework.common.Config;
import com.bliss.framework.util.ConvertUtils;

import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.utils.DateUtils;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.AccrueEventDAOImpl;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.GiftsDAOImpl;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;
import ga.log4j.GA;

public class GenerateScore4UserHandler {
	private static GenerateScore4UserHandler instance = null;

	public static GenerateScore4UserHandler getInstance() {
		if (instance == null) {
			synchronized (GenerateScore4UserHandler.class) {
				instance = new GenerateScore4UserHandler();
				GA.app.info("created singleton = " + GenerateScore4UserHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private int REWARD_LIMIT = 5;
	
	private BaseDAO<User> userDAO = null;

	private BaseDAO<AccrueEvent> eventDAO = null;

	private BaseDAO<Gifts> giftsDAO = null;

	public GenerateScore4UserHandler() {
		userDAO = UserDAOImpl.getInstance();
		eventDAO = AccrueEventDAOImpl.getInstance();
		giftsDAO = GiftsDAOImpl.getInstance();
		REWARD_LIMIT = ConvertUtils.toInt(Config.getParam("reward", "reward_limit"));
	}

	public void onTransCreating(TransactionFrame tFrame, Transaction trans) throws Exception {
		AccrueEvent event = getEventActived(tFrame);
		if (event == null) {
			GA.app.info("not found any event status is actived");
			return;
		}
		// generate gifts
		User user = userDAO.find(tFrame, trans.getUserId(), true);

		Long newScore = trans.getAmount();
		newScore += user.getAccrueScore();
		ArrayList<Long> giftsReceived = new ArrayList<Long>();
		PageView<Gifts> pvGifts = getGiftsActived(tFrame, trans.getUserId(), event.getId());
		for (Gifts gift : pvGifts.getItems()) {
			if (pvGifts.getTotalItems() != 0 && !gift.getStatus().equals(Status.CLOSED)) {
				giftsReceived = getArrayRewardReceived(tFrame, gift);
			}
		}

		// Check map reward by milestone
		for (Map.Entry<Long, Long> entryReward : getMapReward(tFrame, trans).entrySet()) {
			// Receive reward by milestone
			if (newScore >= entryReward.getKey() && !giftsReceived.contains(entryReward.getValue())) {
				// Create gift
				if (giftsReceived.size() < REWARD_LIMIT) {
					Gifts gifts = new Gifts();
					gifts.setType(Gifts.Type.NCOIN);
					gifts.setAmount(getMapReward(tFrame, trans).get(entryReward.getKey()));
					gifts.setUserId(trans.getUserId());
					gifts.setEventId(event.getId());
					gifts.setStatus(Gifts.Status.PENDING);
					giftsDAO.save(tFrame, gifts);

					giftsReceived.add(entryReward.getValue());
				}
			} 
		}
		// Update accrue score
		user.setAccrueScore(newScore.intValue());
		userDAO.update(tFrame, user);
	}

	private AccrueEvent getEventActived(TransactionFrame tFrame) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("status_");
		values.add(AccrueEvent.Status.ACTIVE.ordinal());
		params.add("type_");
		values.add(AccrueEvent.Type.accrue.ordinal());
		int offset = 0, limit = 1;
		String orderKey = "created_at";
		PageView<AccrueEvent> pv = eventDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		if (pv.getItems().size() > 0) {
			AccrueEvent event = pv.getItems().get(0);
			return event;
		}
		return null;
	}

	private PageView<Gifts> getGiftsActived(TransactionFrame tFrame, long userId, long eventId) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("user_id");
		values.add(userId);
		params.add("event_id");
		values.add(eventId);
		int offset = 0, limit = 100;
		String orderKey = "created_at";
		PageView<Gifts> pv = giftsDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		return pv;
	}

	private Map<Long, Long> getMapReward(TransactionFrame tFrame, Transaction trans) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("status_");
		values.add(AccrueEvent.Status.ACTIVE.ordinal());
		params.add("type_");
		values.add(AccrueEvent.Type.accrue.ordinal());
		int offset = 0, limit = 1;
		String orderKey = "created_at";
		PageView<AccrueEvent> pv = eventDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		if (pv.getItems().size() > 0) {
			Map<Long, Long> mapReward = pv.getItems().get(0).getValue("reward");
			return mapReward;
		}
		return null;
	}

	private ArrayList<Long> getArrayRewardReceived(TransactionFrame tFrame, Gifts gift) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("user_id");
		values.add(gift.getUserId());
		params.add("event_id");
		values.add(gift.getEventId());
		params.add("fromCreatedDate");
		values.add(DateUtils.getCurrentDayWithTimeZero(new Date()));
		params.add("toCreatedDate");
		values.add(DateUtils.getDateTime(DateUtils.getDateTime(new Date())));
		int offset = 0, limit = 100;
		String orderKey = "created_at";
		PageView<Gifts> pv = giftsDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				orderKey, true, offset, limit);
		ArrayList<Long> giftsReceived = new ArrayList<Long>();
		if (pv.getItems().size() > 0) {
			for (Gifts gifts : pv.getItems()) {
				if (!gifts.getStatus().equals(Gifts.Status.CLOSED)) {
					giftsReceived.add(gifts.getAmount());
				}
			}
		}
		return giftsReceived;
	}
}
