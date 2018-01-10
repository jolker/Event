package cc.blisscorp.event.game.api.rest.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.GsonBuilder;

import cc.blisscorp.event.game.api.rest.JsonRequestException;
import cc.blisscorp.event.game.api.rest.json.AccrueEventAdapter;
import cc.blisscorp.event.game.api.rest.json.EventFilterDeserializer;
import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.handler.AccrueEventFilteringHandler;
import cc.blisscorp.event.game.jdbc.manager.AccrueEventManager;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;

@Controller
@RequestMapping("/accrue-event")
public class JsonAccrueEventController extends AbstractJsonController implements InitializingBean{
	
	private BaseManager<AccrueEvent, EventFilter> eventMgr = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		eventMgr = AccrueEventManager.getInstance();
	}

	@Override
	protected void registerAdapter(GsonBuilder builder) {
		builder.registerTypeAdapter(AccrueEvent.class, new AccrueEventAdapter())
		.registerTypeAdapter(EventFilter.class, new EventFilterDeserializer());
	}

	@RequestMapping(value = "get.json", method = RequestMethod.GET)
	public void get(@RequestParam(value="id") long id,
			HttpServletResponse res) throws Exception {
		AccrueEvent event = eventMgr.get(id);
		super.writeResponse(res, event);
	}
	
	
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	public void search(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		EventFilter filter = super.jsonRead(req, EventFilter.class);
		AccrueEventFilteringHandler.getInstance().onAuthen(filter);
		PageView<AccrueEvent> pv = eventMgr.search(filter);
		super.writeResponse(res, pv);
	}
	
	
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	public void save(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		AccrueEvent event = super.jsonRead(req, AccrueEvent.class);
		event = eventMgr.saveOrUpdate(event);
		super.writeResponse(res, event);
	}
}
