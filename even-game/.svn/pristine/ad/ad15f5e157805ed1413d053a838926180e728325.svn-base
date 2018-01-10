package cc.blisscorp.event.game.jdbc.manager;

import java.util.ArrayList;

import javax.naming.OperationNotSupportedException;

import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;
import cc.blisscorp.event.game.ent.TransactionFilter;
import cc.blisscorp.event.game.ent.utils.MetaObjectUtils;
import cc.blisscorp.event.game.handler.AssignScore4UserHandler;
import cc.blisscorp.event.game.handler.GenerateScore4UserHandler;
import cc.blisscorp.event.game.handler.TransAssignStatusHandler;
import cc.blisscorp.event.game.handler.UserCreatingHandler;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.TransDAOImpl;
import ga.log4j.GA;

public class TransManager implements BaseManager<Transaction, TransactionFilter>{
	private static TransManager instance = null;
	public static TransManager getInstance() {
		if (instance == null) {
			synchronized(TransManager.class) {
				instance = new TransManager();
				GA.app.info("created singleton = " + TransManager.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<Transaction> transDAO = null;

	public TransManager() {
		transDAO = TransDAOImpl.getInstance();
	}
	
	@Override
	public Transaction saveOrUpdate(Transaction trans) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame(false);
			if (trans.getId() == 0)
				return createTrans(tFrame, trans);

			Transaction locked = transDAO.find(tFrame, trans.getId(), true);
			// Transaction saved = locked.getValue(DataFields.META_KEY_SAVED_OBJECT);
			
			copyFieldsChanged(trans, locked);
			
			AssignScore4UserHandler.getInstance().onTransCreating(tFrame, locked);
			
			GenerateScore4UserHandler.getInstance().onTransCreating(tFrame, locked);
			
			TransAssignStatusHandler.getInstance().onStatusChange(Status.COMPLETED, locked);

			transDAO.update(tFrame, locked);
			
			tFrame.returnResource();

			return locked;

		} catch (Exception e) {
			tFrame.returnBrokenResource();
			GA.app.error("update transaction failed(trans) = "  + trans.toString() + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Transaction get(long transId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			return transDAO.find(tFrame, transId, false);

		} catch (Exception ex) {
			GA.app.error("get transaction failed(transId) = " + transId  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public PageView<Transaction> search(TransactionFilter filter) throws Exception {
		TransactionFrame tFrame = null;
		try {
			ArrayList<String> params = new ArrayList<String>();
			ArrayList<Object> values = new ArrayList<Object>();
			if (filter.getUserId() != 0) {
				params.add("user_id");
				values.add(filter.getUserId());
			}
			if (filter.getStatus() != null) {
				params.add("status_");
				values.add(filter.getStatus().ordinal());
			}
			
			tFrame = new TransactionFrame();
			return transDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
					filter.getOrderBy(), filter.isAsc(), (filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize());
			
		} catch (Exception ex) {
			GA.app.error("search transaction failed(filter) = " + filter.toString()  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public void remove(Transaction instance) throws Exception {
		throw new OperationNotSupportedException("not yet impl");
	}
	
	private Transaction createTrans(TransactionFrame tFrame, Transaction trans) throws Exception {
		transDAO.save(tFrame, trans); // generator id
		
		UserCreatingHandler.getInstance().onTransCreating(tFrame, trans);
		
		
//		 transDAO.update(tFrame, trans);
		tFrame.returnResource();
		
		return trans;
	}
	
	private void copyFieldsChanged(Transaction src, Transaction dest) {
		if (src.getStatus() != null)
			dest.setStatus(src.getStatus());
		// copy meta key
		MetaObjectUtils.copy(src, dest);
	}
}
