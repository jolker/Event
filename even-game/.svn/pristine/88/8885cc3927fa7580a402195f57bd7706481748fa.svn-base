package cc.blisscorp.event.game.api.rest.controller;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.GsonBuilder;

@Controller
public class JsonHelloController extends AbstractJsonController {

	@Override
	protected void registerAdapter(GsonBuilder builder) {}
	
	@RequestMapping(value = "hello.json", method = RequestMethod.GET)
	public void get(HttpServletResponse res) throws Exception {	
		super.writeResponse(res, "now " + new Date());
	}

}
