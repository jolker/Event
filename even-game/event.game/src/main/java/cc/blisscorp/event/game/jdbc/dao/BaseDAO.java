package cc.blisscorp.event.game.jdbc.dao;

import java.util.List;
import cc.blisscorp.event.game.ent.DataObject;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.jdbc.TransactionFrame;

public interface BaseDAO <T extends DataObject> {
	
	public T save(TransactionFrame tFrame, T instance) throws Exception;
	
	public T update(TransactionFrame tFrame, T instance) throws Exception;
	
	public T find(TransactionFrame tFrame, long id, boolean forUpdate) throws Exception;
	
	public PageView<T> find(TransactionFrame tFrame, String[] params, Object[] values, 
			String orderBy,boolean asc, int offset, int limit) throws Exception;
	
	public void delete(TransactionFrame tFrame, T instance) throws Exception;
	
	public List<T> find(TransactionFrame tFrame, String[] params, Object[] values) throws Exception;
	
}
