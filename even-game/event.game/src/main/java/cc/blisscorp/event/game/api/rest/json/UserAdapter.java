package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;
import java.util.ArrayList;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.User;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.reflect.TypeToken;

public class UserAdapter extends DataObjectAdapter<User>{

	@Override
	protected void marshal(JsonObject jsonObj, User user, JsonSerializationContext context)
			throws JsonParseException {
		jsonObj.add("user_id", JsonUtils.toJsonElement(user.getUserId()));
		jsonObj.add("user_name", JsonUtils.toJsonElement(user.getUserName()));
		jsonObj.add("score", JsonUtils.toJsonElement(user.getScore())); 
		if (user.hasValue("gifts")) {
			Type typeToken = new TypeToken<ArrayList<Gifts>>(){}.getType();
			jsonObj.add("meta_gifts", context.serialize(user.getValue(Gifts.META_KEY_GIFTS), typeToken));	
		}		
		jsonObj.add("gifts_total", JsonUtils.toJsonElement(user.getGiftsTotal()));
		jsonObj.add("accrue_score", JsonUtils.toJsonElement(user.getAccrueScore())); 
	}
	

	@Override
	protected User unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		User user = new User();
		user.setUserId(JsonUtils.getLong(jsonObj, "user_id"));
		user.setUserName(JsonUtils.getString(jsonObj, "user_name"));
		user.setScore(JsonUtils.getInteger(jsonObj, "score"));
		user.setGiftsTotal(JsonUtils.getInteger(jsonObj, "gifts_total"));
		user.setAccrueScore(JsonUtils.getInteger(jsonObj, "accrue_score"));
		return user;
	}

}
