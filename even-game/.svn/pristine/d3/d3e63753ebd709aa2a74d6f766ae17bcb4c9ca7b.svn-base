package cc.blisscorp.event.game.thrift.server;

import org.apache.thrift.TException;

import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;
import cc.blisscorp.event.game.ent.TransactionFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.TransManager;
import cc.blisscorp.event.game.thrift.gen.TEventGameService;
import cc.blisscorp.event.game.thrift.gen.TTransaction;

public class TEventGameProcessor implements TEventGameService.Iface {
	
	private BaseManager<Transaction, TransactionFilter> transMgr = null;
	
	public TEventGameProcessor() {
		transMgr = TransManager.getInstance();
	}

	@Override
	public boolean ping() throws TException {
		return true;
	}
	
	@Override
	public TTransaction saveTrans(TTransaction tTrans) throws TException {
		Transaction trans = null;
		try {
			trans = new Transaction();
			trans.setUserId(tTrans.getUserId());
			trans.setValue("userName", tTrans.getUserName());
			trans.setAmount(tTrans.getAmount());
			trans.setGameId(tTrans.getGameId());
			trans.setStatus(Status.PENDING);
			transMgr.saveOrUpdate(trans);
			return tTrans;
		} catch (Exception e) {
			throw new TException(e.getMessage(), e);
		}
		
	}

}
