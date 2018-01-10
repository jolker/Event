package cc.blisscorp.event.game.jdbc.manager;

import cc.blisscorp.event.game.ent.DataFilter;
import cc.blisscorp.event.game.ent.DataObject;
import cc.blisscorp.event.game.ent.PageView;

public interface BaseManager<T extends DataObject, S extends DataFilter> {
	
	T saveOrUpdate(T instance) throws Exception;

	T get(long instanceId) throws Exception;
	
	PageView<T> search(S filter) throws Exception;

	void remove(T instance) throws Exception;

//	PageView<T> advancedSearch(S filter) throws Exception;
}