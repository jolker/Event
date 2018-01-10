package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.PageView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class PageViewSerializer implements JsonSerializer<PageView<?>> {
	
	@Override
	public JsonElement serialize(PageView<?> pv, Type type,
			JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		obj.add("items", context.serialize(pv.getItems()));
		obj.add("total_items", JsonUtils.toJsonElement(pv.getTotalItems()));
		return obj;
	}

}
