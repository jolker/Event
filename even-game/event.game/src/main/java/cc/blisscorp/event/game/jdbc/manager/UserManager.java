package cc.blisscorp.event.game.jdbc.manager;

import java.util.ArrayList;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.ent.utils.MetaObjectUtils;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.UserDAOImpl;
import ga.log4j.GA;

public class UserManager implements BaseManager<User, UserFilter>{

	private static UserManager instance = null;
	public static UserManager getInstance() {
		if (instance == null) {
			synchronized(UserManager.class) {
				instance = new UserManager();
				GA.app.info("created singleton = " + UserManager.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<User> userDAO = null;

	public UserManager() {
		userDAO = UserDAOImpl.getInstance();
	}
	
	@Override
	public User saveOrUpdate(User user) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame(false);
			if (user.getId() == 0)
				return createUser(tFrame, user);

			User locked = userDAO.find(tFrame, user.getUserId(), true);
			// Category saved = locked.getValue(DataFields.META_KEY_SAVED_OBJECT);
			copyFieldsChanged(user, locked);

			// TODO: fire event category updating
			
			
			
			userDAO.update(tFrame, locked);
			tFrame.returnResource();

			// TODO: fire event category updated

			return locked;

		} catch (Exception e) {
			tFrame.returnBrokenResource();
			GA.app.error("update user failed(user) = "  + user.toString() + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public User get(long userId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			return userDAO.find(tFrame, userId, false);

		} catch (Exception ex) {
			GA.app.error("get user failed(userId) = " + userId  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public PageView<User> search(UserFilter filter) throws Exception {
		TransactionFrame tFrame = null;
		try {
			ArrayList<String> params = new ArrayList<String>();
			ArrayList<Object> values = new ArrayList<Object>();
			if (filter.getUserId() != 0) {
				params.add("user_id");
				values.add(filter.getUserId());
			}
			
			if (filter.getValue("userIds") != null) {
				params.add("user_id");
				List<String> userIds = filter.getValue("userIds");
				values.add(userIds.toArray());	
			}
			
			if (filter.getValue("fromAccrueScore") != null) {
				params.add("fromAccrueScore");
				values.add(filter.getIntValue("fromAccrueScore"));
			}
			
			tFrame = new TransactionFrame();
			return userDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
					filter.getOrderBy(), filter.isAsc(), (filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize());
			
		} catch (Exception ex) {
			GA.app.error("search user failed(filter) = " + filter.toString()  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public void remove(User instance) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}

	private User createUser(TransactionFrame tFrame, User user) throws Exception {
		userDAO.save(tFrame, user); // generator id
		tFrame.returnResource();
		return user;
	}
	private void copyFieldsChanged(User src, User dest) {
		if(src.getUserName() != null)
			dest.setUserName(src.getUserName());
		if(src.getScore() >= 0)
			dest.setScore(src.getScore());
		if(src.getGiftsTotal() >= 0)
			dest.setGiftsTotal(src.getGiftsTotal());
		if(src.getAccrueScore() >= 0)
			dest.setAccrueScore(src.getAccrueScore());
		// copy meta key
		MetaObjectUtils.copy(src, dest);
	}
}
