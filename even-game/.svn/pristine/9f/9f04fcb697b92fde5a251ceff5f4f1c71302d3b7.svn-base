package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

public class EventAdapter extends DataObjectAdapter<LuckyBoxEvent> {

	@Override
	protected void marshal(JsonObject jsonObj, LuckyBoxEvent event, JsonSerializationContext context) throws JsonParseException {
		jsonObj.add("name", JsonUtils.toJsonElement(event.getName()));
		if (event.getType() != null)
			jsonObj.add("type", JsonUtils.toJsonElement(event.getType().name()));
		jsonObj.add("start_date", JsonUtils.toJsonElement(event.getStartDate()));
		jsonObj.add("end_date", JsonUtils.toJsonElement(event.getEndDate()));
		if (event.getStatus() != null)
			jsonObj.add("status", JsonUtils.toJsonElement(event.getStatus().name()));
		jsonObj.add("gifts_card", context.serialize(event.getGiftsCard()));
		jsonObj.add("gifts_ncoin", context.serialize(event.getGiftsNcoin()));
		jsonObj.add("gifts_special", context.serialize(event.getGiftsSpecial()));
		jsonObj.add("pay_money", JsonUtils.toJsonElement(event.getPayMoney()));
		jsonObj.add("money2_score", JsonUtils.toJsonElement(event.getMoney2Score()));
		jsonObj.add("score2_gifts", JsonUtils.toJsonElement(event.getScore2Gifts()));
		jsonObj.add("gifts_per_person", JsonUtils.toJsonElement(event.getGiftsPerPerson()));
		
		jsonObj.add("gifts_card_percent", JsonUtils.toJsonElement(event.getGiftsCardPercent()));
		jsonObj.add("gifts_ncoin_percent", JsonUtils.toJsonElement(event.getGiftsNcoinPercent()));
		jsonObj.add("gifts_special_percent", JsonUtils.toJsonElement(event.getGiftsSpecialPercent()));
		
		jsonObj.add("meta_user", context.serialize(event.getValue(User.META_KEY_USER), User.class));
	}

	@Override
	protected LuckyBoxEvent unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		LuckyBoxEvent event = new LuckyBoxEvent();
		event.setName(JsonUtils.getString(jsonObj, "name"));
		if(jsonObj.has("type"))
			event.setType(Event.Type.valueOf(JsonUtils.getString(jsonObj, "type")));
		event.setStartDate(JsonUtils.getDateTime(jsonObj, "start_date"));
		event.setEndDate(JsonUtils.getDateTime(jsonObj, "end_date"));
		if (jsonObj.has("status"))
			event.setStatus(Status.valueOf(JsonUtils.getString(jsonObj, "status")));
		Type typeToken = new TypeToken<ArrayList<Gifts>>(){}.getType();
		List<Gifts> gifts = context.deserialize(jsonObj.get("gifts_card"), typeToken);
		event.setGiftsCard(gifts);
		event.setGiftsCardPercent(JsonUtils.getFloat(jsonObj, "gifts_card_percent"));
		
		gifts = context.deserialize(jsonObj.get("gifts_ncoin"), typeToken);
		event.setGiftsNcoin(gifts);
		event.setGiftsNcoinPercent(JsonUtils.getFloat(jsonObj, "gifts_ncoin_percent"));
		
		gifts = context.deserialize(jsonObj.get("gifts_special"), typeToken);
		event.setGiftsSpecial(gifts);;
		event.setGiftsSpecialPercent(JsonUtils.getFloat(jsonObj, "gifts_special_percent"));
		
		event.setPayMoney(JsonUtils.getLong(jsonObj, "pay_money"));
		event.setMoney2Score(JsonUtils.getInteger(jsonObj, "money2_score"));
		event.setScore2Gifts(JsonUtils.getInteger(jsonObj, "score2_gifts"));
		event.setGiftsPerPerson(JsonUtils.getInteger(jsonObj, "gifts_per_person"));
		return event;
	}

}
