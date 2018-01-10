package cc.blisscorp.event.game.api.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.google.gson.GsonBuilder;
import cc.blisscorp.event.game.api.rest.JsonError;
import cc.blisscorp.event.game.api.rest.JsonErrorSerializer;
@Controller
@RequestMapping("error")
public class JsonErrorController extends AbstractJsonController{

	@Override
	protected void registerAdapter(GsonBuilder builder) {
		// TODO Auto-generated method stub
		builder.registerTypeAdapter(JsonError.class, new JsonErrorSerializer());
	}
	
	@RequestMapping("unknown.json")
	public void unknown(HttpServletRequest req, HttpServletResponse res) throws Exception {		
		Throwable cause = (Throwable) req.getAttribute("exception");
		if (cause == null) cause = new RuntimeException("unknown error");
		super.writeResponse(res, super.GSON.toJson(new JsonError(cause)));
	}
}
