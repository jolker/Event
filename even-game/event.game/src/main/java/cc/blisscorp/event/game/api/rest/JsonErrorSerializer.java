package cc.blisscorp.event.game.api.rest;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import cc.blisscorp.event.game.ent.constant.DataFields;

public class JsonErrorSerializer implements JsonSerializer<JsonError>{

	public JsonElement serialize(JsonError src, Type typeOfSrc, 
			JsonSerializationContext context) {
		// TODO Auto-generated method stub
		Throwable cause = src.getCause();
		return serialize(cause);
	}
	JsonElement serialize(Throwable src) {
		JsonObject obj = new JsonObject();
		obj.add(DataFields.RESPONSE_FIELD_STATUS, JsonUtils.toJsonElement(DataFields.RESPONSE_FIELD_STATUS_FAILED));
		obj.add(DataFields.RESPONSE_FIELD_MESSAGE, JsonUtils.toJsonElement(src.getMessage()!=null?src.getMessage():src.toString()));
		return obj;
	}
}
