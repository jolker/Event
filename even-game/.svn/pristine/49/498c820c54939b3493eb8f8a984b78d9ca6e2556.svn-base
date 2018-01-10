package cc.blisscorp.event.game.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.ent.PageView;
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

public class GiftsDAOImpl implements BaseDAO<Gifts>, DBContants, DataFields{
	private static GiftsDAOImpl instance = null;
	public static GiftsDAOImpl getInstance() {
		if (instance == null) {
			synchronized(GiftsDAOImpl.class) {
				instance = new GiftsDAOImpl();
				 GA.app.info("created singleton = " + GiftsDAOImpl.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private static final int INSERT_COLUMNS_TOTAL = 9;
	private static final String PREFIX_HASH_CREATE_GIFTS = "create-";
	private static final String PREFIX_HASH_UPDATE_GIFTS = "update-";
	private static final String PREFIX_HASH_FIND_GIFTS_BY_ID = "findById-";
	private static final String PREFIX_HASH_SEARCH_GIFTS = "search-";
	
	public GiftsDAOImpl() {}

	@Override
	public Gifts save(TransactionFrame tFrame, Gifts gifts) throws Exception {
		try {
			DBInsert insert = DBCacheImpl.getInstance().insert(PREFIX_HASH_CREATE_GIFTS + ENTIRY_GIFTS_NAME);
			insert.initialization(tFrame);

			if (!insert.isCached()) {
				insert.setTable(ENTIRY_GIFTS_NAME);
				addColums(insert);
				insert.addPlaceHolder(INSERT_COLUMNS_TOTAL);
			}

			addParams(gifts, insert);

			long id = insert.executeInsert();
			if (id <= 0) throw new Exception(SAVE2DB_FAILED);

			gifts.setId(id);
			return gifts;

		} catch (Exception ex) {
			GA.app.error("Don't save GiftsDAO " + ex);
			throw ex;
		}
	}

	@Override
	public Gifts update(TransactionFrame tFrame, Gifts gifts) throws Exception {
		try {
			DBUpdate update = DBCacheImpl.getInstance().update(PREFIX_HASH_UPDATE_GIFTS + ENTIRY_GIFTS_NAME);
			update.initialization(tFrame);

			if (!update.isCached()) {
				update.setTable(ENTIRY_GIFTS_NAME);
				addColums(update);
				update.addClause(COLUMN_ID, "=?");
			}

			addParams(gifts, update);

			int execResult = update.executeUpdate();
			if (execResult <= 0) throw new Exception(SAVE2DB_FAILED);		

			return gifts;

		} catch (Exception ex) {
			GA.app.error("Don't update GiftsDAO " + ex);
			throw ex;
		}
	}

	@Override
	public Gifts find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_FIND_GIFTS_BY_ID + ENTIRY_GIFTS_NAME);
			query.initialization(tFrame);
			if (!query.isCached()) {
				query.addTable(ENTIRY_GIFTS_NAME);
				query.addClause(COLUMN_ID, "=?");
			}

			query.getParameter().add(id);

			if (forUpdate) query.lock();

			// execute 
			ResultSet resultSet = query.executeQuery();
			List<Gifts> gifts = getGifts(resultSet);
			if (gifts.isEmpty() || gifts.size() == 0)
				throw new Exception(NOT_FOUND + " giftId = " + id);
			
			Gifts gift = gifts.get(0);
			if (forUpdate)
				gift.setValue(META_KEY_SAVED_OBJECT, Gifts.clone(gift));
			return gift;

		} catch (Exception ex) {
			GA.app.error("Don't find GiftsDAO " + ex);
			throw ex; 
		}
	}

	@Override
	public PageView<Gifts> find(TransactionFrame tFrame, String[] params, Object[] values, String orderBy, boolean asc,
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
			PageView<Gifts> pageView = new PageView<Gifts>(getGifts(resultSet));
			pageView.setPageNumber(offset+1);		
			pageView.setPageSize(limit);
			pageView.setTotalItems(count);
			return pageView;
		} catch (Exception e) {			
			throw new Exception(DB_SEARCH_DATA_FAILED + " - " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(TransactionFrame tFrame, Gifts instance) throws Exception {
		// TODO Auto-generated method stub
		throw new OperationNotSupportedException("not yet impl");
	}

	@Override
	public List<Gifts> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	
	private DBQuery defineCriterion(TransactionFrame transFrame, String[] params, Object[] values) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_SEARCH_GIFTS + ENTIRY_GIFTS_NAME);
			query.initialization(transFrame);
			// assign entity
			query.addTable(ENTIRY_GIFTS_NAME);
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
	 * insert_column_total = 9
	 * @param dbObject
	 * @throws SQLException
	 */
	private void addColums(DBObject dbObject) throws SQLException {
		dbObject.addColumn("user_id");	// 1
		dbObject.addColumn("event_id");		// 2
		dbObject.addColumn("type_");	// 3
		dbObject.addColumn("status_");		// 4
		dbObject.addColumn("amount");		// 5
		dbObject.addColumn("percentage");		// 6
		dbObject.addColumn("received_date");	// 7
		dbObject.addColumn("description");		//8
		dbObject.addColumn("created_at");	// 9
		if (dbObject instanceof DBUpdate)
			dbObject.addColumn("updated_at");
	}

	private void addParams(Gifts gifts, DBObject dbObject) throws SQLException {
		if (dbObject instanceof DBInsert) {
			gifts.setCreatedAt(new Date());
			if (gifts.getStatus() == null)
				gifts.setStatus(Status.PENDING);
		}
		dbObject.getParameter().add(gifts.getUserId());
		dbObject.getParameter().add(gifts.getEventId());
		dbObject.getParameter().add(gifts.getType().ordinal());
		dbObject.getParameter().add(gifts.getStatus().ordinal());
		dbObject.getParameter().add(gifts.getAmount());
		dbObject.getParameter().add(gifts.getPercentage());		
		dbObject.getParameter().add(gifts.getReceivedDate());
		dbObject.getParameter().add(gifts.getDescription());
		dbObject.getParameter().add(gifts.getCreatedAt());
		if (dbObject instanceof DBUpdate) {
			dbObject.getParameter().add(new Date());
			dbObject.getParameter().add(gifts.getId());	// where id = ?
		}
	}
	
	private List<Gifts> getGifts(ResultSet resultSet) throws Exception {
		List<Gifts> gifts = new ArrayList<Gifts>();
		while (resultSet != null && resultSet.next()) {
			Gifts gift = new Gifts();	
			gift.setId(resultSet.getLong("id"));
			gift.setUserId(resultSet.getLong("user_id"));
			gift.setEventId(resultSet.getLong("event_id"));
			gift.setType(Type.values()[resultSet.getInt("type_")]);
			gift.setStatus(Status.values()[resultSet.getInt("status_")]);
			gift.setAmount(resultSet.getLong("amount"));
			gift.setPercentage(resultSet.getFloat("percentage"));
			gift.setReceivedDate(DateUtils.getDateTime(resultSet.getString("received_date")));
			gift.setDescription(resultSet.getString("description"));
			gift.setCreatedAt(DateUtils.getDateTime(resultSet.getString("created_at")));
			gift.setUpdatedAt(DateUtils.getDateTime(resultSet.getString("updated_at")));
			gifts.add(gift);
		}
		return gifts;
	}
}
