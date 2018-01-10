package cc.blisscorp.event.game.api.rest.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cc.blisscorp.event.game.api.rest.json.FilterDeserializer;
import cc.blisscorp.event.game.api.rest.json.GiftsAdapter;
import cc.blisscorp.event.game.api.rest.json.ItemListAdapter;
import cc.blisscorp.event.game.api.rest.json.PageViewSerializer;
import cc.blisscorp.event.game.api.rest.json.UserAdapter;
import cc.blisscorp.event.game.ent.DataFilter;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.ItemList;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.constant.DataFields;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractJsonController implements DataFields {
	protected final Gson GSON = buildGSON();

	protected Gson buildGSON() {
		GsonBuilder builder = new GsonBuilder()
		.registerTypeAdapter(User.class, new UserAdapter())
		.registerTypeAdapter(ItemList.class, new ItemListAdapter())
		.registerTypeAdapter(Gifts.class, new GiftsAdapter())
		.registerTypeAdapter(DataFilter.class, new FilterDeserializer())
		.registerTypeAdapter(PageView.class, new PageViewSerializer());
		registerAdapter(builder);
		return builder.create();
	}	

	protected abstract void registerAdapter(GsonBuilder builder);

	protected <T> T jsonRead(HttpServletRequest req, Class<T> type) throws IOException {
		InputStreamReader reader = new InputStreamReader(req.getInputStream(), "UTF-8");
		try {
			return GSON.fromJson(reader, type);
		}
		finally {
			reader.close();
		}
	}


	protected void writeResponse(HttpServletResponse res, Object obj) throws IOException {
		Map<String, Object> dataResp = new HashMap<String, Object>();		
		dataResp.put(RESPONSE_FIELD_DATA, obj);		
		dataResp.put(RESPONSE_FIELD_STATUS, RESPONSE_FIELD_STATUS_SUCCESSFULLY);
		dataResp.put(RESPONSE_FIELD_MESSAGE, RESPONSE_DEFAULT_MESSAGE_SUCCESSFULLY);
		writeResponse(res, GSON.toJson(dataResp));
	}


	protected void writeResponse(HttpServletResponse res, String json) throws IOException {
		res.setContentType("application/json;charset=utf-8");
		res.setCharacterEncoding("UTF-8");
		res.getWriter().append(json);
	}
}
