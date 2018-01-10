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
import cc.blisscorp.event.game.api.rest.json.EventAdapter;
import cc.blisscorp.event.game.api.rest.json.EventFilterDeserializer;
import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.handler.EventFilteringHandler;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.LuckyBoxEventManager;

import com.google.gson.GsonBuilder;

@Controller
@RequestMapping("/event")
public class JsonEventController extends AbstractJsonController implements InitializingBean {

	private BaseManager<LuckyBoxEvent, EventFilter> eventMgr = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventMgr = LuckyBoxEventManager.getInstance();
	}

	@Override
	protected void registerAdapter(GsonBuilder builder) {
		builder.registerTypeAdapter(LuckyBoxEvent.class, new EventAdapter())
		.registerTypeAdapter(EventFilter.class, new EventFilterDeserializer());
	}
	
	@RequestMapping(value = "get.json", method = RequestMethod.GET)
	public void get(@RequestParam(value="id") long id,
			HttpServletResponse res) throws Exception {
		LuckyBoxEvent event = eventMgr.get(id);
		super.writeResponse(res, event);
	}
	
	
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	public void search(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		EventFilter filter = super.jsonRead(req, EventFilter.class);
		EventFilteringHandler.getInstance().onAuthen(filter);
		PageView<LuckyBoxEvent> pv = eventMgr.search(filter);
		super.writeResponse(res, pv);
	}
	
	
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	public void save(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		LuckyBoxEvent event = super.jsonRead(req, LuckyBoxEvent.class);
		event = eventMgr.saveOrUpdate(event);
		super.writeResponse(res, event);
	}
	
}
