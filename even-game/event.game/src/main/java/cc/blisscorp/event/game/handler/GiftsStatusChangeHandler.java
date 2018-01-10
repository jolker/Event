package cc.blisscorp.event.game.handler;

import ga.log4j.GA;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.GiftsDAOImpl;

public class GiftsStatusChangeHandler {
	private static GiftsStatusChangeHandler instance = null;
	public static GiftsStatusChangeHandler getInstance() {
		if (instance == null) {
			synchronized(GiftsStatusChangeHandler.class) {
				instance = new GiftsStatusChangeHandler();
				GA.app.info("created singleton = " + GiftsStatusChangeHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<Gifts> giftsDAO = null;

	public GiftsStatusChangeHandler() {
		giftsDAO = GiftsDAOImpl.getInstance();
	}

	public void onEventUpdating(TransactionFrame tFrame, Event saved, Event locked) throws Exception {
		if (!StringUtils.equals(saved.getStatus().name(), Status.ACTIVE.name()))
			return;
		if (!StringUtils.equals(locked.getStatus().name(), Status.DONE.name()))
			return;

		int offset = 0, limit = 100;
		PageView<Gifts> pv = getGifts(tFrame, locked.getId(), offset++, limit);
		while (!pv.getItems().isEmpty()) {
			for (Gifts gifts:pv.getItems()) {
				GiftsAssignStatusHandler.getInstance().onStatusChange(Gifts.Status.CLOSED, gifts);
				giftsDAO.update(tFrame, gifts);
			}
			pv = getGifts(tFrame, locked.getId(), offset++, limit);
		}
	}

	private PageView<Gifts> getGifts(TransactionFrame tFrame, long eventId, int offset, int limit) throws Exception {
		ArrayList<String> params = new ArrayList<String>();
		ArrayList<Object> values = new ArrayList<Object>();
		params.add("event_id");
		values.add(eventId);

		params.add("status_");
		values.add(Gifts.Status.PENDING.ordinal());

		return giftsDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
				null, true, offset * limit, limit);
	}

}
