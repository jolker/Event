package cc.blisscorp.event.game.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.DataNotFoundException;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.constant.DataFields;
import cc.blisscorp.event.game.ent.utils.DateUtils;
import cc.blisscorp.event.game.jdbc.DBCacheImpl;
import cc.blisscorp.event.game.jdbc.DBContants;
import cc.blisscorp.event.game.jdbc.DBInsert;
import cc.blisscorp.event.game.jdbc.DBObject;
import cc.blisscorp.event.game.jdbc.DBQuery;
import cc.blisscorp.event.game.jdbc.DBUpdate;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import ga.log4j.GA;

public class UserDAOImpl implements BaseDAO<User>, DBContants, DataFields{

	private static UserDAOImpl instance = null;
	public static UserDAOImpl getInstance() {
		if (instance == null) {
			synchronized(UserDAOImpl.class) {
				instance = new UserDAOImpl();
				 GA.app.info("created singleton = " + UserDAOImpl.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private static final int INSERT_COLUMNS_TOTAL = 6;
	private static final String PREFIX_HASH_CREATE_USER = "create-";
	private static final String PREFIX_HASH_UPDATE_USER = "update-";
	private static final String PREFIX_HASH_FIND_USER_BY_ID = "findById-";
	private static final String PREFIX_HASH_SEARCH_USER = "search-";
	
	public UserDAOImpl() {}
	
	@Override
	public User save(TransactionFrame tFrame, User user) throws Exception {
		try {
			DBInsert insert = DBCacheImpl.getInstance().insert(PREFIX_HASH_CREATE_USER + ENTIRY_USERS_NAME);
			insert.initialization(tFrame);

			if (!insert.isCached()) {
				insert.setTable(ENTIRY_USERS_NAME);
				addColums(insert);
				insert.addPlaceHolder(INSERT_COLUMNS_TOTAL);
			}

			addParams(user, insert);

			long id = insert.executeInsert();
			if (id <= 0) throw new Exception(SAVE2DB_FAILED);

			user.setId(id);
			return user;

		} catch (Exception ex) {
			GA.app.error("Don't save UserDAO " + ex);
			throw ex;
		}
	}

	@Override
	public User update(TransactionFrame tFrame, User user) throws Exception {
		try {
			DBUpdate update = DBCacheImpl.getInstance().update(PREFIX_HASH_UPDATE_USER + ENTIRY_USERS_NAME);
			update.initialization(tFrame);

			if (!update.isCached()) {
				update.setTable(ENTIRY_USERS_NAME);
				addColums(update);
				update.addClause(COLUMN_ID, "=?");
			}

			addParams(user, update);

			int execResult = update.executeUpdate();
			if (execResult <= 0) throw new Exception(SAVE2DB_FAILED);		

			return user;

		} catch (Exception ex) {
			GA.app.error("Don't update UserDAO " + ex);
			throw ex;
		}
	}

	@Override
	public User find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_FIND_USER_BY_ID + ENTIRY_USERS_NAME);
			query.initialization(tFrame);
			if (!query.isCached()) {
				query.addTable(ENTIRY_USERS_NAME);
				query.addClause("user_id", "=?");
			}

			query.getParameter().add(id);

			if (forUpdate) query.lock();

			// execute 
			ResultSet resultSet = query.executeQuery();
			List<User> users = getUsers(resultSet);
			if (users.isEmpty() || users.size() == 0)
				throw new DataNotFoundException(NOT_FOUND + " userId = " + id);
			
			User user = users.get(0);
			if (forUpdate)
				user.setValue(META_KEY_SAVED_OBJECT, User.clone(user));
			return user;

		} catch (Exception ex) {
			GA.app.error("Don't find UserDAO " + ex);
			throw ex; 
		}
	}

	@Override
	public PageView<User> find(TransactionFrame tFrame, String[] params, Object[] values, String orderBy, boolean asc,
			int offset, int limit) throws Exception {
		try {			
			DBQuery countQuery = defineCriterion(tFrame, params, values);
			countQuery.addColumn(QUERY_COUNT_COLUMN);
			// get totalItems
			ResultSet resultSet = countQuery.executeQuery();
			resultSet.next();
			int count = resultSet.getInt(TOTAL_ITEMS_QUERIED);

			DBQuery query = defineCriterion(tFrame, params, values);
			// order by
			if (orderBy != null)
				query.setQueryOrder(orderBy, asc==true?KEY_ORDER_ASC:KEY_ORDER_DESC);
			// pageSize
			query.setLimit("?,?");
			query.getParameter().add(offset);
			query.getParameter().add(limit);
			// execute query
			resultSet = query.executeQuery();
			// return pageView
			PageView<User> pageView = new PageView<User>(getUsers(resultSet));
			pageView.setPageNumber(offset+1);		
			pageView.setPageSize(limit);
			pageView.setTotalItems(count);
			return pageView;
		} catch (Exception e) {	
			GA.app.error(DB_SEARCH_DATA_FAILED + " - " + e.getMessage(), e);
			throw new Exception(DB_SEARCH_DATA_FAILED + " - " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(TransactionFrame tFrame, User instance) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}

	@Override
	public List<User> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}

	private DBQuery defineCriterion(TransactionFrame transFrame, String[] params, Object[] values) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_SEARCH_USER + ENTIRY_USERS_NAME);
			query.initialization(transFrame);
			// assign entity
			query.addTable(ENTIRY_USERS_NAME);
			// assign clause params
			if (params != null)
				for (int i = 0; i < params.length; i++) 
					DBQuery.defineCriterion(params[i], values[i], query);
			return query;
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * insert_column_total = 6
	 * @param dbObject
	 * @throws SQLException
	 */
	private void addColums(DBObject dbObject) throws SQLException {
		dbObject.addColumn("user_id");	// 1
		dbObject.addColumn("user_name"); //2
		dbObject.addColumn("score");	// 3
		dbObject.addColumn("gifts_total");		// 4
		dbObject.addColumn("accrue_score");		//5
		dbObject.addColumn("created_at");	// 6
		if (dbObject instanceof DBUpdate)
			dbObject.addColumn("updated_at");
	}

	private void addParams(User user, DBObject dbObject) throws SQLException {
		if (dbObject instanceof DBInsert) {
			user.setCreatedAt(new Date());
		}
		dbObject.getParameter().add(user.getUserId());
		dbObject.getParameter().add(user.getUserName());
		dbObject.getParameter().add(user.getScore());
		dbObject.getParameter().add(user.getGiftsTotal());
		dbObject.getParameter().add(user.getAccrueScore());
		dbObject.getParameter().add(user.getCreatedAt());
		if (dbObject instanceof DBUpdate) {
			dbObject.getParameter().add(new Date());
			dbObject.getParameter().add(user.getId());	// where id = ?
		}
	}
	
	private List<User> getUsers(ResultSet resultSet) throws Exception {
		List<User> users = new ArrayList<User>();
		while (resultSet != null && resultSet.next()) {
			User user = new User();	
			user.setId(resultSet.getLong("id"));
			user.setUserId(resultSet.getLong("user_id"));
			user.setUserName(resultSet.getString("user_name"));
			user.setScore(resultSet.getInt("score"));
			user.setGiftsTotal(resultSet.getInt("gifts_total"));
			user.setAccrueScore(resultSet.getInt("accrue_score"));
			user.setCreatedAt(DateUtils.getDateTime(resultSet.getString("created_at")));
			user.setUpdatedAt(DateUtils.getDateTime(resultSet.getString("updated_at")));
			users.add(user);
		}
		return users;
	}
}
