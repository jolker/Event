package cc.blisscorp.event.game.handler;

import ga.log4j.GA;

import java.util.ArrayList;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;

public class UserScoreChangeHandler {
	private static UserScoreChangeHandler instance = null;
	public static UserScoreChangeHandler getInstance() {
		if (instance == null) {
			synchronized(UserScoreChangeHandler.class) {
				instance = new UserScoreChangeHandler();
				GA.app.info("created singleton = " + UserScoreChangeHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<User> userDAO = null;

	public UserScoreChangeHandler() {
		userDAO = UserDAOImpl.getInstance();
	}


	public void onEventUpdating(TransactionFrame tFrame, Event saved, Event locked) throws Exception {
		if (!StringUtils.equals(saved.getStatus().name(), Status.ACTIVE.name()))
			return;
		if (!StringUtils.equals(locked.getStatus().name(), Status.DONE.name()))
			return;

		UserFilter userF = new UserFilter();
		userF.setPageNumber(1);
		userF.setPageSize(100);
		userF.setValue("fromScore", 1);
		
		updateUser(tFrame, userF);

		// filter giftsTotal > 0
		userF = new UserFilter();
		userF.setPageNumber(1);
		userF.setPageSize(100);
		userF.setValue("fromGiftsTotal", 1);
		
		updateUser(tFrame, userF);
	}

	private void updateUser(TransactionFrame tFrame, UserFilter userF) throws Exception {
		PageView<User> pv = getUser(tFrame, userF);
		while (!pv.getItems().isEmpty()) {
			for (User user:pv.getItems()) {
				user.setScore(0);
				user.setGiftsTotal(0);
				userDAO.update(tFrame, user);
				GA.app.info("reseted score user. userId" + user.getUserId());
			}

			userF.setPageNumber(userF.getPageNumber() + 1);
			pv = getUser(tFrame, userF);
		}		
	}

	private PageView<User> getUser(TransactionFrame tFrame, UserFilter filter) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		for (Entry<String, Object> entry:filter.getProperties().entrySet()) {
			params.add(entry.getKey());
			values.add(entry.getValue());
		}
		return userDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				filter.getOrderBy(), filter.isAsc(), (filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize());
	}

}
