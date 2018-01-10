package cc.blisscorp.event.game.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.LuckyBoxEvent;
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

public class LuckyBoxEventDAOImpl implements BaseDAO<LuckyBoxEvent>, DBContants, DataFields{
	private static LuckyBoxEventDAOImpl instance = null;
	public static LuckyBoxEventDAOImpl getInstance() {
		if (instance == null) {
			synchronized(LuckyBoxEventDAOImpl.class) {
				instance = new LuckyBoxEventDAOImpl();
				 GA.app.info("created singleton = " + LuckyBoxEventDAOImpl.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private static final int INSERT_COLUMNS_TOTAL = 16;
	private static final String PREFIX_HASH_CREATE_EVENT = "create-";
	private static final String PREFIX_HASH_UPDATE_EVENT = "update-";
	private static final String PREFIX_HASH_FIND_EVENT_BY_ID = "findById-";
	private static final String PREFIX_HASH_SEARCH_EVENT = "search-";
	
	public LuckyBoxEventDAOImpl() {}
	
	public LuckyBoxEvent save(TransactionFrame tFrame, LuckyBoxEvent event) throws Exception {
		// TODO Auto-generated method stub
		try {
			DBInsert insert = DBCacheImpl.getInstance().insert(PREFIX_HASH_CREATE_EVENT + ENTIRY_EVENTS_NAME);
			insert.initialization(tFrame);

			if (!insert.isCached()) {
				insert.setTable(ENTIRY_EVENTS_NAME);
				addColums(insert);
				insert.addPlaceHolder(INSERT_COLUMNS_TOTAL);
			}

			addParams(event, insert);

			long id = insert.executeInsert();
			if (id <= 0) throw new Exception(SAVE2DB_FAILED);

			event.setId(id);
			return event;

		} catch (Exception ex) {
			GA.app.error("Don't save EventDAO " + ex);
			throw ex;
		}
	}
	public LuckyBoxEvent update(TransactionFrame tFrame, LuckyBoxEvent event) throws Exception {
		// TODO Auto-generated method stub
		try {
			DBUpdate update = DBCacheImpl.getInstance().update(PREFIX_HASH_UPDATE_EVENT + ENTIRY_EVENTS_NAME);
			update.initialization(tFrame);

			if (!update.isCached()) {
				update.setTable(ENTIRY_EVENTS_NAME);
				addColums(update);
				update.addClause(COLUMN_ID, "=?");
			}

			addParams(event, update);

			int execResult = update.executeUpdate();
			if (execResult <= 0) throw new Exception(SAVE2DB_FAILED);		

			return event;

		} catch (Exception ex) {
			GA.app.error("Don't update EventDAO " + ex);
			throw ex;
		}
	}
	public LuckyBoxEvent find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception {
		// TODO Auto-generated method stub
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_FIND_EVENT_BY_ID + ENTIRY_EVENTS_NAME);
			query.initialization(tFrame);
			if (!query.isCached()) {
				query.addTable(ENTIRY_EVENTS_NAME);
				query.addClause(COLUMN_ID, "=?");
			}

			query.getParameter().add(id);

			if (forUpdate) query.lock();

			// execute 
			ResultSet resultSet = query.executeQuery();
			List<LuckyBoxEvent> events = getEvents(resultSet);
			if (events.isEmpty() || events.size() == 0)
				throw new Exception(NOT_FOUND + " eventId = " + id);
			
			LuckyBoxEvent event = events.get(0);
			if (forUpdate)
				event.setValue(META_KEY_SAVED_OBJECT, LuckyBoxEvent.clone(event));
			return event;

		} catch (Exception ex) {
			GA.app.error("Don't find EventDAO " + ex);
			throw ex; 
		}
	}
	
	@Override
	public PageView<LuckyBoxEvent> find(TransactionFrame tFrame, String[] params, Object[] values, String orderBy, boolean asc,
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
			PageView<LuckyBoxEvent> pageView = new PageView<LuckyBoxEvent>(getEvents(resultSet));
			pageView.setPageNumber(offset+1);		
			pageView.setPageSize(limit);
			pageView.setTotalItems(count);
			return pageView;
		} catch (Exception e) {			
			throw new Exception(DB_SEARCH_DATA_FAILED + " - " + e.getMessage(), e);
		}
	}
	
	public void delete(TransactionFrame tFrame, LuckyBoxEvent event) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	public List<LuckyBoxEvent> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	
	
	private DBQuery defineCriterion(TransactionFrame transFrame, String[] params, Object[] values) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_SEARCH_EVENT + ENTIRY_EVENTS_NAME);
			query.initialization(transFrame);
			// assign entity
			query.addTable(ENTIRY_EVENTS_NAME);
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
	 * insert_column_total = 15
	 * @param dbObject
	 * @throws SQLException
	 */
	private void addColums(DBObject dbObject) throws SQLException {
		dbObject.addColumn("name");			// 1
		dbObject.addColumn("type_");			// 2
		dbObject.addColumn("start_date");	// 3
		dbObject.addColumn("end_date");		// 4
		dbObject.addColumn("status_");		// 5
		dbObject.addColumn("gifts_card");		// 6
		dbObject.addColumn("gifts_ncoin");	// 7
		dbObject.addColumn("gifts_special");// 8
		
		dbObject.addColumn("gifts_card_percent");	// 9
		dbObject.addColumn("gifts_ncoin_percent");	// 10
		dbObject.addColumn("gifts_special_percent"); //11
		
		dbObject.addColumn("pay_money");		// 12
		dbObject.addColumn("gifts_per_person");	// 13
		dbObject.addColumn("money2_score");	// 14
		dbObject.addColumn("score2_gifts");	// 15
		dbObject.addColumn("created_at");	// 16
		if (dbObject instanceof DBUpdate)
			dbObject.addColumn("updated_at");
	}

	private void addParams(LuckyBoxEvent event, DBObject dbObject) throws SQLException {
		if (dbObject instanceof DBInsert) {
			event.setCreatedAt(new Date());
			if (event.getStatus() == null)
				event.setStatus(Event.Status.PENDING);
		}
		dbObject.getParameter().add(event.getName());
		dbObject.getParameter().add(event.getType().ordinal());
		dbObject.getParameter().add(event.getStartDate());
		dbObject.getParameter().add(event.getEndDate());
		dbObject.getParameter().add(event.getStatus().ordinal());
		dbObject.getParameter().add(new Gson().toJson(event.getGiftsCard())); // jsonStringGiftCard
		dbObject.getParameter().add(new Gson().toJson(event.getGiftsNcoin())); // jsonStringNCoin
		dbObject.getParameter().add(new Gson().toJson(event.getGiftsSpecial())); // jsonStringSpecial
		
		dbObject.getParameter().add(event.getGiftsCardPercent());
		dbObject.getParameter().add(event.getGiftsNcoinPercent());
		dbObject.getParameter().add(event.getGiftsSpecialPercent());
		
		dbObject.getParameter().add(event.getPayMoney());
		dbObject.getParameter().add(event.getGiftsPerPerson());
		dbObject.getParameter().add(event.getMoney2Score());
		dbObject.getParameter().add(event.getScore2Gifts());
		dbObject.getParameter().add(event.getCreatedAt());
		if (dbObject instanceof DBUpdate) {
			dbObject.getParameter().add(new Date());
			dbObject.getParameter().add(event.getId());	// where id = ?
		}
	}
	
	private List<LuckyBoxEvent> getEvents(ResultSet resultSet) throws Exception {
		List<LuckyBoxEvent> events = new ArrayList<LuckyBoxEvent>();
		while (resultSet != null && resultSet.next()) {
			LuckyBoxEvent event = new LuckyBoxEvent();	
			event.setId(resultSet.getLong("id"));
			event.setName(resultSet.getString("name"));
			event.setType(Type.values()[resultSet.getInt("type_")]);
			event.setStartDate(DateUtils.getDateTime(resultSet.getString("start_date")));
			event.setEndDate(DateUtils.getDateTime(resultSet.getString("end_date")));
			event.setStatus(Status.values()[resultSet.getInt("status_")]);
			List<Gifts> giftsCard = new Gson().fromJson(resultSet.getString("gifts_card"),
					new TypeToken<List<Gifts>>(){}.getType());
			event.setGiftsCard(giftsCard);
			List<Gifts> giftsNcoin = new Gson().fromJson(resultSet.getString("gifts_ncoin"),
					new TypeToken<List<Gifts>>(){}.getType());
			event.setGiftsNcoin(giftsNcoin);
			List<Gifts> giftsSpecial = new Gson().fromJson(resultSet.getString("gifts_special"),
					new TypeToken<List<Gifts>>(){}.getType());
			event.setGiftsSpecial(giftsSpecial);
			event.setPayMoney(resultSet.getLong("pay_money"));
			
			event.setGiftsCardPercent(resultSet.getFloat("gifts_card_percent"));
			event.setGiftsNcoinPercent(resultSet.getFloat("gifts_ncoin_percent"));
			event.setGiftsSpecialPercent(resultSet.getFloat("gifts_special_percent"));
			
			event.setGiftsPerPerson(resultSet.getInt("gifts_per_person"));
			event.setMoney2Score(resultSet.getInt("money2_score"));
			event.setScore2Gifts(resultSet.getInt("score2_gifts"));
			event.setCreatedAt(DateUtils.getDateTime(resultSet.getString("created_at")));
			event.setUpdatedAt(DateUtils.getDateTime(resultSet.getString("updated_at")));
			events.add(event);
		}
		return events;
	}

	
}
