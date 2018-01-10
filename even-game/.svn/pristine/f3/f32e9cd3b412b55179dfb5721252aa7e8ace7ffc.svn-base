package cc.blisscorp.event.game.api.rest.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;

public class GiftsAdapter extends DataObjectAdapter<Gifts> {

	@Override
	protected void marshal(JsonObject jsonObj, Gifts gifts, JsonSerializationContext context)
			throws JsonParseException {
		if (gifts.getUserId() > 0)
			jsonObj.add("user_id", JsonUtils.toJsonElement(gifts.getUserId()));
		if (gifts.getEventId() > 0)
			jsonObj.add("event_id", JsonUtils.toJsonElement(gifts.getEventId()));
		jsonObj.add("type", JsonUtils.toJsonElement(gifts.getType().name()));
		if (gifts.getStatus() != null)
			jsonObj.add("status", JsonUtils.toJsonElement(gifts.getStatus().name()));
		jsonObj.add("amount", JsonUtils.toJsonElement(gifts.getAmount()));
		if (gifts.getPercentage() > 0)
			jsonObj.add("percentage", JsonUtils.toJsonElement(gifts.getPercentage()));
		if (gifts.getReceivedDate() != null)
			jsonObj.add("received_date", JsonUtils.toJsonElement(gifts.getReceivedDate()));
		jsonObj.add("quanlity", JsonUtils.toJsonElement(gifts.getQuanlity()));
		if (gifts.getDescription() != null)
			jsonObj.add("description", JsonUtils.toJsonElement(gifts.getDescription()));
		jsonObj.add("meta_userName", context.serialize(gifts.getValue("userName")));
	}

	@Override
	protected Gifts unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		Gifts gifts = new Gifts();
		gifts.setUserId(JsonUtils.getLong(jsonObj, "user_id"));
		gifts.setEventId(JsonUtils.getLong(jsonObj, "event_id"));
		gifts.setType(Type.valueOf(JsonUtils.getString(jsonObj, "type")));
		if (jsonObj.has("status"))
			gifts.setStatus(Status.valueOf(JsonUtils.getString(jsonObj, "status")));
		gifts.setAmount(JsonUtils.getLong(jsonObj, "amount"));
		gifts.setPercentage(JsonUtils.getFloat(jsonObj, "percentage"));
		gifts.setReceivedDate(JsonUtils.getDateTime(jsonObj, "received_date"));
		gifts.setQuanlity(JsonUtils.getInteger(jsonObj, "quanlity"));
		gifts.setDescription(JsonUtils.getString(jsonObj, "description"));
		return gifts;
	}

}
