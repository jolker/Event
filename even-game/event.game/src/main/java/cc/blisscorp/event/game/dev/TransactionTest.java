package cc.blisscorp.event.game.dev;

import cc.blisscorp.event.game.ent.Transaction;
import cc.blisscorp.event.game.ent.Transaction.Status;
import cc.blisscorp.event.game.ent.TransactionFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.TransManager;

public class TransactionTest {
	
	static BaseManager<Transaction, TransactionFilter> transMgr = TransManager.getInstance();

	public static void main(String[] args) {
		try {
			Transaction tran = new Transaction();
			tran.setUserId(1);
			tran.setAmount(30000);
			tran.setGameId(1);
			tran.setStatus(Status.PENDING);
			
			transMgr.saveOrUpdate(tran);
	
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
