package cc.blisscorp.event.game.api.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cc.blisscorp.event.game.api.rest.JsonRequestException;
import cc.blisscorp.event.game.api.rest.json.UserFilterDeserializer;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.UserManager;

import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/user")
public class JsonUserController extends AbstractJsonController implements InitializingBean{

	private BaseManager<User, UserFilter> userMgr = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		userMgr = UserManager.getInstance();		
	}
	
	@Override
	protected void registerAdapter(GsonBuilder builder) {
		builder.registerTypeAdapter(UserFilter.class, new UserFilterDeserializer());
	}	

	@RequestMapping(value = "get.json", method = RequestMethod.GET)
	public void get(@RequestParam(value="user_id") long userId,
			HttpServletResponse res) throws Exception {
		User user = userMgr.get(userId);	
		super.writeResponse(res, user);
	}
	
	
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	public void search(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		UserFilter filter = super.jsonRead(req, UserFilter.class);
		PageView<User> pv = userMgr.search(filter);
		super.writeResponse(res, pv);
	}
	
	
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	public void save(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		User user = super.jsonRead(req, User.class);
		user = userMgr.saveOrUpdate(user); 
		super.writeResponse(res, user);
	}
}
