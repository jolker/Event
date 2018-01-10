package cc.blisscorp.event.game.ent.utils;

import java.util.Map;

import cc.blisscorp.event.game.ent.DataObject;


/**
 * 
 * @author tuyenpv
 *
 */
public class MetaObjectUtils {
	
	public static void copy(DataObject src, DataObject dest) {
		for (Map.Entry<String, Object> e:src.getProperties().entrySet()) {
			if (e.getKey().startsWith("-")) continue;
			dest.setValue(e.getKey(), e.getValue());
		}
	}
}
