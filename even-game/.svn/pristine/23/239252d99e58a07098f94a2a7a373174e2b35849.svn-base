package cc.blisscorp.event.game.handler;

import java.util.ArrayList;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.DataNotFoundException;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.GiftsDAOImpl;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;
import ga.log4j.GA;

public class AccrueEventFilteringHandler {
	private static AccrueEventFilteringHandler instance = null;
	public static AccrueEventFilteringHandler getInstance() {
		if (instance == null) {
			synchronized(AccrueEventFilteringHandler.class) {
				instance = new AccrueEventFilteringHandler();
				GA.app.info("created singleton = " + AccrueEventFilteringHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<User> userDAO = null;

	private BaseDAO<Gifts> giftsDAO = null;

	private static final String SECECT_KEY = "900150983cd24fb0d6963f7d28e17f72";

	public AccrueEventFilteringHandler() {
		userDAO = UserDAOImpl.getInstance();
		giftsDAO = GiftsDAOImpl.getInstance();
	}


	public void onEventFiltering(TransactionFrame tFrame, EventFilter filter, PageView<AccrueEvent> pv) throws Exception {
		if (!filter.hasValue("userId")) 
			return;
		if (pv.getItems().isEmpty() || pv.getTotalItems() > 1)
			return;

		// find gifts
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("user_id");
		values.add(filter.getValue("userId"));
//		params.add("status_");
//		values.add(Gifts.Status.PENDING.ordinal());
		params.add("event_id");
		values.add(pv.getItems().get(0).getId());
		PageView<Gifts> pvGifts = giftsDAO.find(tFrame, params.toArray(new String[params.size()]),
				values.toArray(), "created_at", true, 0, 100);

		// find user
		User user = null;
		try {
			user = userDAO.find(tFrame, filter.getLongValue("userId"), false);
		} catch (DataNotFoundException e) {
			user = new User();
			user.setUserId(filter.getLongValue("userId"));
			userDAO.save(tFrame, user);
		}
		user.setValue(Gifts.META_KEY_GIFTS, pvGifts.getItems());

		// append user2event
		AccrueEvent event = pv.getItems().get(0);
		event.setValue(User.META_KEY_USER, user); 
	}
	

	public void onAuthen(EventFilter eventF) throws Exception {
		if (!eventF.hasValue("userId"))
			return;

		long userId = eventF.getLongValue("userId");
		String sign = eventF.getValue("sign");
		String validSign = userId + "_" + SECECT_KEY;

		if (!StringUtils.equals(DigestUtils.md5Hex(validSign), sign)) {
			throw new Exception("Th\u00F4ng tin user kh\u00F4ng h\u1EE3p l\u1EC7"); 
		}
	}
}
