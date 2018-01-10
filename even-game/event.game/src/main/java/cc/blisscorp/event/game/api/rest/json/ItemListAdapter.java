package cc.blisscorp.event.game.api.rest.json;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.ItemList;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class ItemListAdapter implements JsonSerializer<ItemList<?>>, JsonDeserializer<ItemList<?>> {

	@Override
	public JsonElement serialize(ItemList<?> src, Type typeOfSrc,
			JsonSerializationContext context) {
		// TODO Auto-generated method stub		
		return null;
	}
	
	@Override
	public ItemList<?> deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObj = (JsonObject)json;
		if (jsonObj.has("gifts-items")) {
			Type typeToken = new TypeToken<ArrayList<Gifts>>(){}.getType();
			List<Gifts> items = context.deserialize(jsonObj.get("gifts-items"), typeToken);
			return new ItemList<Gifts>(items);	
		} else 
			throw new JsonParseException("json element invalid");
		
	}

}
