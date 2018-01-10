package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.DataObject;

public abstract class DataObjectAdapter <X extends DataObject> implements JsonDeserializer<X>, JsonSerializer<X>{
	protected abstract void marshal(JsonObject jsonObj, X dataObj, JsonSerializationContext context) throws JsonParseException;

	protected abstract X unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException;
	
	@Override
	public JsonElement serialize(X data, Type type, JsonSerializationContext context) {
		JsonObject obj = new JsonObject();
		marshal(obj, data, context);
		if (data.getId() > 0)
			obj.add("id", JsonUtils.toJsonElement(data.getId()));
		obj.add("created_at", JsonUtils.toJsonElement(data.getCreatedAt()));
		obj.add("updated_at", JsonUtils.toJsonElement(data.getUpdatedAt()));
		return obj;
	}


	@Override
	public X deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObj = (JsonObject)jsonElement;		
		X obj = unmarshal(jsonObj, context);

		obj.setId(JsonUtils.getLong(jsonObj, "id"));	
		JsonObject meta = jsonObj.has("meta")?jsonObj.getAsJsonObject("meta"):null;
		@SuppressWarnings("unchecked")
		Map<String, Object> md = (Map<String, Object>) JsonUtils.getValue(meta);
		if (md!=null)
			obj.getProperties().putAll(md);
		return obj;
	}
}
