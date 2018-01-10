package cc.blisscorp.event.game.jdbc.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;
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

public class TransDAOImpl implements BaseDAO<Transaction>, DBContants, DataFields{
	private static TransDAOImpl instance = null;
	public static TransDAOImpl getInstance() {
		if (instance == null) {
			synchronized(TransDAOImpl.class) {
				instance = new TransDAOImpl();
				 GA.app.info("created singleton = " + TransDAOImpl.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private static final int INSERT_COLUMNS_TOTAL = 5;
	private static final String PREFIX_HASH_CREATE_TRANSACTION = "create-";
	private static final String PREFIX_HASH_UPDATE_TRANSACTION = "update-";
	private static final String PREFIX_HASH_FIND_TRANSACTION_BY_ID = "findById-";
	private static final String PREFIX_HASH_SEARCH_TRANSACTION = "search-";
	
	public TransDAOImpl() {}
	
	@Override
	public Transaction save(TransactionFrame tFrame, Transaction tran) throws Exception {
		try {
			DBInsert insert = DBCacheImpl.getInstance().insert(PREFIX_HASH_CREATE_TRANSACTION + ENTIRY_TRANSACTION_NAME);
			insert.initialization(tFrame);

			if (!insert.isCached()) {
				insert.setTable(ENTIRY_TRANSACTION_NAME);
				addColums(insert);
				insert.addPlaceHolder(INSERT_COLUMNS_TOTAL);
			}

			addParams(tran, insert);

			long id = insert.executeInsert();
			if (id <= 0) throw new Exception(SAVE2DB_FAILED);

			tran.setId(id);
			return tran;

		} catch (Exception ex) {
			GA.app.error("Don't save TransactionDAO " + ex);
			throw ex;
		}
	}

	@Override
	public Transaction update(TransactionFrame tFrame, Transaction tran) throws Exception {
		try {
			DBUpdate update = DBCacheImpl.getInstance().update(PREFIX_HASH_UPDATE_TRANSACTION + ENTIRY_TRANSACTION_NAME);
			update.initialization(tFrame);

			if (!update.isCached()) {
				update.setTable(ENTIRY_TRANSACTION_NAME);
				addColums(update);
				update.addClause(COLUMN_ID, "=?");
			}

			addParams(tran, update);

			int execResult = update.executeUpdate();
			if (execResult <= 0) throw new Exception(SAVE2DB_FAILED);		

			return tran;

		} catch (Exception ex) {
			GA.app.error("Don't update TransactionDAO " + ex);
			throw ex;
		}
	}

	@Override
	public Transaction find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_FIND_TRANSACTION_BY_ID + ENTIRY_TRANSACTION_NAME);
			query.initialization(tFrame);
			if (!query.isCached()) {
				query.addTable(ENTIRY_TRANSACTION_NAME);
				query.addClause(COLUMN_ID, "=?");
			}

			query.getParameter().add(id);

			if (forUpdate) query.lock();

			// execute 
			ResultSet resultSet = query.executeQuery();
			List<Transaction> trans = getTrans(resultSet);
			if (trans.isEmpty() || trans.size() == 0)
				throw new Exception(NOT_FOUND + " tranId = " + id);
			
			Transaction tran = trans.get(0);
			if (forUpdate)
				tran.setValue(META_KEY_SAVED_OBJECT, Transaction.clone(tran));
			return tran;

		} catch (Exception ex) {
			GA.app.error("can not find transId: " + id + " - " + ex.getMessage(), ex);
			throw ex; 
		}
	}

	@Override
	public PageView<Transaction> find(TransactionFrame tFrame, String[] params, Object[] values, String orderBy,
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
			PageView<Transaction> pageView = new PageView<Transaction>(getTrans(resultSet));
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
	public void delete(TransactionFrame tFrame, Transaction instance) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}

	@Override
	public List<Transaction> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	
	private DBQuery defineCriterion(TransactionFrame transFrame, String[] params, Object[] values) throws Exception {
		try {
			DBQuery query = DBCacheImpl.getInstance().query(PREFIX_HASH_SEARCH_TRANSACTION + ENTIRY_TRANSACTION_NAME);
			query.initialization(transFrame);
			// assign entity
			query.addTable(ENTIRY_TRANSACTION_NAME);
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
	 * insert_column_total = 5
	 * @param dbObject
	 * @throws SQLException
	 */
	private void addColums(DBObject dbObject) throws SQLException {
		dbObject.addColumn("game_id");	// 1
		dbObject.addColumn("user_id");	// 2
		dbObject.addColumn("amount");	// 3
		dbObject.addColumn("status_");	// 4
		dbObject.addColumn("created_at");// 5
		if (dbObject instanceof DBUpdate)
			dbObject.addColumn("updated_at");
	}

	private void addParams(Transaction trans, DBObject dbObject) throws SQLException {
		if (dbObject instanceof DBInsert) {
			trans.setCreatedAt(new Date());
			if (trans.getStatus() == null)
				trans.setStatus(Status.PENDING);
		}
		dbObject.getParameter().add(trans.getGameId());
		dbObject.getParameter().add(trans.getUserId());
		dbObject.getParameter().add(trans.getAmount());
		dbObject.getParameter().add(trans.getStatus().ordinal());
		dbObject.getParameter().add(trans.getCreatedAt());
		if (dbObject instanceof DBUpdate) {
			dbObject.getParameter().add(new Date());
			dbObject.getParameter().add(trans.getId());	// where id = ?
		}
	}
	
	private List<Transaction> getTrans(ResultSet resultSet) throws Exception {
		List<Transaction> trans = new ArrayList<Transaction>();
		while (resultSet != null && resultSet.next()) {
			Transaction tran = new Transaction();	
			tran.setId(resultSet.getLong("id"));
			tran.setGameId(resultSet.getInt("game_id"));
			tran.setUserId(resultSet.getInt("user_id"));
			tran.setAmount(resultSet.getLong("amount"));
			tran.setStatus(Status.values()[resultSet.getInt("status_")]);
			tran.setCreatedAt(DateUtils.getDateTime(resultSet.getString("created_at")));
			tran.setUpdatedAt(DateUtils.getDateTime(resultSet.getString("updated_at")));
			trans.add(tran);
		}
		return trans;
	}
	
}
