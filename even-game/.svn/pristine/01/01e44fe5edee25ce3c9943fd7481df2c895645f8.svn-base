package cc.blisscorp.event.game.api.rest.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Event.Type;

public class AccrueEventAdapter extends DataObjectAdapter<AccrueEvent>{

	@Override
	protected void marshal(JsonObject jsonObj, AccrueEvent event, JsonSerializationContext context)
			throws JsonParseException {
		jsonObj.add("name", JsonUtils.toJsonElement(event.getName()));
		if(event.getType() != null)
			jsonObj.add("type", JsonUtils.toJsonElement(event.getType().name()));
		jsonObj.add("start_date", JsonUtils.toJsonElement(event.getStartDate()));
		jsonObj.add("end_date", JsonUtils.toJsonElement(event.getEndDate()));
		if (event.getStatus() != null)
			jsonObj.add("status", JsonUtils.toJsonElement(event.getStatus().name()));
		jsonObj.add("reward", context.serialize(event.getValue(AccrueEvent.META_REWARD)));
		jsonObj.add("gifts_limit", JsonUtils.toJsonElement(event.isGiftsLimit()));
		jsonObj.add("meta_user", context.serialize(event.getValue(User.META_KEY_USER), User.class));
	}
	

	@Override
	protected AccrueEvent unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		AccrueEvent event = new AccrueEvent();
		event.setName(JsonUtils.getString(jsonObj, "name"));
		if(jsonObj.has("type"))
			event.setType(Type.valueOf(JsonUtils.getString(jsonObj, "type")));
		event.setStartDate(JsonUtils.getDateTime(jsonObj, "start_date"));
		event.setEndDate(JsonUtils.getDateTime(jsonObj, "end_date"));
		if (jsonObj.has("status"))
			event.setStatus(Status.valueOf(JsonUtils.getString(jsonObj, "status")));
		event.setValue(AccrueEvent.META_REWARD, JsonUtils.getValue(jsonObj.get("reward")));
		event.setGiftsLimit(JsonUtils.getBoolean(jsonObj, "gifts_limit"));
		return event;
	}

}
