package cc.blisscorp.event.game.api.rest.json;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.ent.GiftsFilter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GiftsFilterDeserializer extends DataFilterDeserializer<GiftsFilter>{

	@Override
	protected GiftsFilter unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		GiftsFilter filter = new GiftsFilter();
		filter.setUserId(JsonUtils.getLong(jsonObj, "user_id"));
		if (jsonObj.has("status"))
			filter.setStatus(Status.valueOf(JsonUtils.getString(jsonObj, "status")));		
		if (jsonObj.has("type"))
			filter.setType(Type.valueOf(JsonUtils.getString(jsonObj, "type")));
		JsonElement  ele = jsonObj.get("types");
		filter.setValue("types", JsonUtils.getValue(ele));
		filter.setEventId(JsonUtils.getLong(jsonObj, "event_id"));
		filter.setValue("-topGifts", JsonUtils.getString(jsonObj, "meta_topGifts"));
		filter.setValue("fromCreatedDate", JsonUtils.getValue(jsonObj.get("fromCreatedDate")));
		filter.setValue("toCreatedDate", JsonUtils.getValue(jsonObj.get("toCreatedDate")));
		return filter;
	}

}
