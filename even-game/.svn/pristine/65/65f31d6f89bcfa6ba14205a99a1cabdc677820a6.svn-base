package cc.blisscorp.event.game.handler;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;
import ga.log4j.GA;

public class UserGiftsTotalChangeHandler {
	private static UserGiftsTotalChangeHandler instance = null;
	public static UserGiftsTotalChangeHandler getInstance() {
		if (instance == null) {
			synchronized(UserGiftsTotalChangeHandler.class) {
				instance = new UserGiftsTotalChangeHandler();
				GA.app.info("created singleton = " + UserGiftsTotalChangeHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private BaseDAO<User> userDAO = null;
	
	public UserGiftsTotalChangeHandler() {
		userDAO = UserDAOImpl.getInstance();
	}
	
	
	public void onGiftsUpdating(TransactionFrame tFrame, Gifts saved, Gifts locked) throws Exception {
		if (!StringUtils.equals(saved.getStatus().name(), Status.PENDING.name()))
			return;
		if (!StringUtils.equals(locked.getStatus().name(), Status.AUTHORIZED.name()) 
				&& !StringUtils.equals(locked.getStatus().name(), Status.TRANSFER.name()))
			return;
		
		Event event = locked.getValue("-meta_event");
		
		if(event != null && StringUtils.equals(event.getType().name(), Event.Type.lucky_box.name()) ) {
			long userId = locked.getUserId();
			User user = userDAO.find(tFrame, userId, true);
			user.setGiftsTotal(user.getGiftsTotal() -1);
			userDAO.update(tFrame, user);
		}
	}
}
