package cc.blisscorp.event.game.jdbc.dao;

import ga.log4j.GA;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class AccrueEventDAOImpl implements BaseDAO<AccrueEvent>, DBContants, DataFields{
	private static AccrueEventDAOImpl instance = null;
	public static AccrueEventDAOImpl getInstance() {
		if (instance == null) {
			synchronized(AccrueEventDAOImpl.class) {
				instance = new AccrueEventDAOImpl();
				 GA.app.info("created singleton = " + AccrueEventDAOImpl.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private static final int INSERT_COLUMNS_TOTAL = 8;
	private static final String PREFIX_HASH_CREATE_ACCRUE_EVENT = "create-accrue-";
	private static final String PREFIX_HASH_UPDATE_ACCRUE_EVENT = "update-accrue-";
	private static final String PREFIX_HASH_FIND_ACCRUE_EVENT_BY_ID = "findById-accrue-";
	private static final String PREFIX_HASH_SEARCH_ACCRUE_EVENT = "search-accrue-";
	
	public AccrueEventDAOImpl() {}

	@Override
	public AccrueEvent save(TransactionFrame tFrame, AccrueEvent event) throws Exception {
		try {
			DBInsert insert = DBCacheImpl.getInstance().insert(PREFIX_HASH_CREATE_ACCRUE_EVENT + ENTIRY_EVENTS_NAME);
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
			GA.app.error("Don't save CumulativeEventDAO " + ex);
			throw ex;
		}
		
	}

	@Override
	public AccrueEvent update(TransactionFrame tFrame, AccrueEvent event) throws Exception {
		try {
			DBUpdate update = DBCacheImpl.getInstance().update(PREFIX_HASH_UPDATE_ACCRUE_EVENT + ENTIRY_EVENTS_NAME);
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
			GA.app.error("Don't update CumulativeEventDAO " + ex);
			throw ex;
		}
	}

	@Override
	public AccrueEvent find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_FIND_ACCRUE_EVENT_BY_ID + ENTIRY_EVENTS_NAME);
			query.initialization(tFrame);
			if (!query.isCached()) {
				query.addTable(ENTIRY_EVENTS_NAME);
				query.addClause(COLUMN_ID, "=?");
			}

			query.getParameter().add(id);

			if (forUpdate) query.lock();

			// execute 
			ResultSet resultSet = query.executeQuery();
			List<AccrueEvent> events = getEvents(resultSet);
			if (events.isEmpty() || events.size() == 0)
				throw new Exception(NOT_FOUND + " eventId = " + id);
			
			AccrueEvent event = events.get(0);
			if (forUpdate)
				event.setValue(META_KEY_SAVED_OBJECT, AccrueEvent.clone(event));
			return event;

		} catch (Exception ex) {
			GA.app.error("Don't find CumulativeEventDAO " + ex);
			throw ex; 
		}
	}

	@Override
	public PageView<AccrueEvent> find(TransactionFrame tFrame, String[] params, Object[] values, String orderBy,
			boolean asc, int offset, int limit) throws Exception {
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
			PageView<AccrueEvent> pageView = new PageView<AccrueEvent>(getEvents(resultSet));
			pageView.setPageNumber(offset+1);		
			pageView.setPageSize(limit);
			pageView.setTotalItems(count);
			return pageView;
		} catch (Exception e) {			
			throw new Exception(DB_SEARCH_DATA_FAILED + " - " + e.getMessage(), e);
		}
	}

	@Override
	public void delete(TransactionFrame tFrame, AccrueEvent instance) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}

	@Override
	public List<AccrueEvent> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	
	private DBQuery defineCriterion(TransactionFrame transFrame, String[] params, Object[] values) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_SEARCH_ACCRUE_EVENT + ENTIRY_EVENTS_NAME);
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
	 * insert_column_total = 8
	 * @param dbObject
	 * @throws SQLException
	 */
	private void addColums(DBObject dbObject) throws SQLException {
		dbObject.addColumn("name");			// 1
		dbObject.addColumn("type_");			// 2
		dbObject.addColumn("start_date");	// 3
		dbObject.addColumn("end_date");		// 4
		dbObject.addColumn("status_");		// 5
		dbObject.addColumn("reward");	// 6
		dbObject.addColumn("gifts_limit");	// 7
		dbObject.addColumn("created_at");	// 8
		if (dbObject instanceof DBUpdate)
			dbObject.addColumn("updated_at");
	}
	
	private void addParams(AccrueEvent event, DBObject dbObject) throws SQLException {
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
		Map<Long, Long> mapReward = event.getValue(AccrueEvent.META_REWARD);
		dbObject.getParameter().add(new Gson().toJson(mapReward));
		dbObject.getParameter().add(event.isGiftsLimit());
		dbObject.getParameter().add(event.getCreatedAt());
		if (dbObject instanceof DBUpdate) {
			dbObject.getParameter().add(new Date());
			dbObject.getParameter().add(event.getId());	// where id = ?
		}
	}
	
	private List<AccrueEvent> getEvents(ResultSet resultSet) throws Exception {
		List<AccrueEvent> events = new ArrayList<AccrueEvent>();
		while (resultSet != null && resultSet.next()) {
			AccrueEvent event = new AccrueEvent();	
			event.setId(resultSet.getLong("id"));
			event.setName(resultSet.getString("name"));
			event.setType(Type.values()[resultSet.getInt("type_")]);
			event.setStartDate(DateUtils.getDateTime(resultSet.getString("start_date")));
			event.setEndDate(DateUtils.getDateTime(resultSet.getString("end_date")));
			event.setStatus(Status.values()[resultSet.getInt("status_")]);
			
			Map<Long, Long> mapReward = new Gson().fromJson(resultSet.getString("reward"),
					new TypeToken<Map<Long, Long>>(){}.getType());
			event.setValue("reward", mapReward);	
			event.setGiftsLimit(resultSet.getBoolean("gifts_limit"));
			event.setCreatedAt(DateUtils.getDateTime(resultSet.getString("created_at")));
			event.setUpdatedAt(DateUtils.getDateTime(resultSet.getString("updated_at")));
			events.add(event);
		}
		return events;
	}
}
