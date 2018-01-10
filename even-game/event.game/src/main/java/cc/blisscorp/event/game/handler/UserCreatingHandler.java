package cc.blisscorp.event.game.handler;

import ga.log4j.GA;
import cc.blisscorp.event.game.ent.DataNotFoundException;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;

public class UserCreatingHandler {
	private static UserCreatingHandler instance = null;
	public static UserCreatingHandler getInstance() {
		if (instance == null) {
			synchronized(UserCreatingHandler.class) {
				instance = new UserCreatingHandler();
				GA.app.info("created singleton = " + UserCreatingHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private BaseDAO<User> userDAO = null;
	
	public UserCreatingHandler() {
		userDAO = UserDAOImpl.getInstance();
	}
	
	public void onTransCreating(TransactionFrame tFrame, Transaction trans) throws Exception {
		User user = null;
		try {
			user = userDAO.find(tFrame, trans.getUserId(), true);	
			user.setUserName(trans.getValue("userName"));
			userDAO.update(tFrame, user);
		} catch (DataNotFoundException e) {
			user = new User();
			user.setUserId(trans.getUserId());
			user.setUserName(trans.getValue("userName"));
			userDAO.save(tFrame, user);
		}
		
		// assign meta 
		trans.setValue(User.META_KEY_USER, user);
	}

}
