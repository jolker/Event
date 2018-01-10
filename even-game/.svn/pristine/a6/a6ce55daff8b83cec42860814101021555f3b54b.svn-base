package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.DataFilter;

public abstract class DataFilterDeserializer <X extends DataFilter> implements JsonDeserializer<X> {

	protected abstract X unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException;

	public X deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject dataObj = (JsonObject)jsonElement;
		X obj = unmarshal(dataObj, context);
		
		if (dataObj.has("page_number"))
			obj.setPageNumber(dataObj.get("page_number").getAsInt());
		
		if (dataObj.has("page_size"))
			obj.setPageSize(dataObj.get("page_size").getAsInt());
		
		obj.setOrderBy(JsonUtils.getString(dataObj, "order_by"));
		if (dataObj.has("asc"))
			obj.setAsc(dataObj.get("asc").getAsBoolean());

		JsonObject meta = dataObj.has("meta")?dataObj.getAsJsonObject("meta"):null;
		@SuppressWarnings("unchecked")
		Map<String, Object> md = (Map<String, Object>) JsonUtils.getValue(meta);
		if (md!=null)
			obj.getProperties().putAll(md);
		return obj;
	}
}
