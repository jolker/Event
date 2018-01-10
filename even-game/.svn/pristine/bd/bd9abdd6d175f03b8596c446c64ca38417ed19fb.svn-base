package cc.blisscorp.event.game.api.rest.json;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;
import cc.blisscorp.event.game.ent.EventFilter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class EventFilterDeserializer extends DataFilterDeserializer<EventFilter>{

	@Override
	protected EventFilter unmarshal(JsonObject jsonObj, 
			JsonDeserializationContext context) throws JsonParseException {
		EventFilter filter = new EventFilter();
		filter.setName(JsonUtils.getString(jsonObj, "name"));
		if (jsonObj.has("status"))
			filter.setStatus(Status.valueOf(JsonUtils.getString(jsonObj, "status")));
		if (jsonObj.has("meta_userId"))
			filter.setValue("userId", JsonUtils.getLong(jsonObj, "meta_userId"));
		if(jsonObj.has("type"))
			filter.setType(Type.valueOf(JsonUtils.getString(jsonObj, "type")));
		filter.setValue("sign", JsonUtils.getString(jsonObj, "meta_sign")); 
		return filter;
	}

}
