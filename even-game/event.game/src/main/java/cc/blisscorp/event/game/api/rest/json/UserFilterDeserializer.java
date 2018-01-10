package cc.blisscorp.event.game.api.rest.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.UserFilter;

public class UserFilterDeserializer extends DataFilterDeserializer<UserFilter>{

	@Override
	protected UserFilter unmarshal(JsonObject jsonObj, JsonDeserializationContext context) throws JsonParseException {
		UserFilter filter = new UserFilter();
		filter.setUserId(JsonUtils.getLong(jsonObj, "user_id"));
		return filter;
	}

}
