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
import cc.blisscorp.event.game.api.rest.json.GiftsFilterDeserializer;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.ItemList;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.handler.GiftsFilteredHandler;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.GiftsManager;

import com.google.gson.GsonBuilder;
@Controller
@RequestMapping("/gifts")
public class JsonGiftsController extends AbstractJsonController implements InitializingBean {

	private BaseManager<Gifts, GiftsFilter> giftsMgr = null;

	@Override
	public void afterPropertiesSet() throws Exception {
		giftsMgr = GiftsManager.getInstance();
	}

	@Override
	protected void registerAdapter(GsonBuilder builder) {
		builder.registerTypeAdapter(GiftsFilter.class, new GiftsFilterDeserializer());
	}
	@RequestMapping(value = "get.json", method = RequestMethod.GET)
	public void get(@RequestParam(value="id") long id,
			HttpServletResponse res) throws Exception {
		Gifts gifts = giftsMgr.get(id);
		super.writeResponse(res, gifts);
	}


	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	public void search(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {
		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		GiftsFilter filter = super.jsonRead(req, GiftsFilter.class);
		PageView<Gifts> pv = giftsMgr.search(filter);
		
		GiftsFilteredHandler.getInstance().onGiftsFiltered(filter, pv);
		
		super.writeResponse(res, pv);
	}
	
	
	@RequestMapping(value = "save.json", method = RequestMethod.POST)
	public void save(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {

		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		Gifts gifts = super.jsonRead(req, Gifts.class);
		gifts = giftsMgr.saveOrUpdate(gifts);
		super.writeResponse(res, gifts);
	}

	@RequestMapping(value = "multiSave.json", method = RequestMethod.POST)
	public void multiSave(@RequestHeader("Content-Type") String contentType, 
			HttpServletRequest req, 
			HttpServletResponse res) throws Exception {
		if (!contentType.equals(JSON_REQUEST_HEADER_CONTENT_TYPE)) 
			throw new JsonRequestException(UNSUPPORTED_MEDIA_TYPE + contentType);

		@SuppressWarnings("unchecked")
		ItemList<Gifts> items = super.jsonRead(req, ItemList.class);
		for (Gifts gifts:items.getItems())
			gifts = giftsMgr.saveOrUpdate(gifts);
		super.writeResponse(res, items);
	}

}
