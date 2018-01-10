package cc.blisscorp.event.game.jdbc.manager;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.AccrueEvent;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.utils.MetaObjectUtils;
import cc.blisscorp.event.game.handler.AccrueEventFilteringHandler;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.AccrueEventDAOImpl;
import ga.log4j.GA;

public class AccrueEventManager implements BaseManager<AccrueEvent, EventFilter> {
	private static AccrueEventManager instance = null;

	public static AccrueEventManager getInstance() {
		if (instance == null) {
			synchronized (AccrueEventManager.class) {
				instance = new AccrueEventManager();
				GA.app.info("created singleton = " + AccrueEventManager.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<AccrueEvent> eventDAO = null;

	public AccrueEventManager() {
		eventDAO = AccrueEventDAOImpl.getInstance();
	}

	@Override
	public AccrueEvent saveOrUpdate(AccrueEvent event) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame(false);
			if (event.getId() == 0)
				return createEvent(tFrame, event);

			AccrueEvent locked = eventDAO.find(tFrame, event.getId(), true);
			// CumulativeEvent saved = locked.getValue(DataFields.META_KEY_SAVED_OBJECT);
			copyFieldsChanged(event, locked);

			// UserScoreChangeHandler.getInstance().onEventUpdating(tFrame, saved, locked);

			// GiftsStatusChangeHandler.getInstance().onEventUpdating(tFrame, saved,
			// locked);

			eventDAO.update(tFrame, locked);
			tFrame.returnResource();

			// TODO: fire event category updated

			return locked;
		} catch (Exception e) {
			tFrame.returnBrokenResource();
			GA.app.error("update cumulative event failed(event) = " + event.toString() + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public AccrueEvent get(long eventId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			return eventDAO.find(tFrame, eventId, false);

		} catch (Exception ex) {
			GA.app.error("get cumulative event failed(eventId) = " + eventId + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public PageView<AccrueEvent> search(EventFilter filter) throws Exception {
		TransactionFrame tFrame = null;
		try {
			ArrayList<String> params = new ArrayList<String>();
			ArrayList<Object> values = new ArrayList<Object>();
			if (!StringUtils.isBlank(filter.getName())) {
				params.add("name");
				values.add(filter.getName());
			}
			if (filter.getStatus() != null) {
				params.add("status_");
				values.add(filter.getStatus().ordinal());
			}
			if (filter.getType() != null) {
				params.add("type_");
				values.add(filter.getType().ordinal());
			}

			tFrame = new TransactionFrame();
			PageView<AccrueEvent> pv = eventDAO.find(tFrame, params.toArray(new String[params.size()]),
					values.toArray(), filter.getOrderBy(), filter.isAsc(),
					(filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize());

			AccrueEventFilteringHandler.getInstance().onEventFiltering(tFrame, filter, pv);

			return pv;

		} catch (Exception ex) {
			GA.app.error("search accrue event failed(filter) = " + filter.toString() + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public void remove(AccrueEvent eventId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			eventDAO.delete(tFrame, eventId);
		} catch (Exception ex) {
			GA.app.error("delete cumulative event failed(eventId) = " + eventId + " - " + ex.getMessage(), ex);
			throw ex;
		}
	}

	private AccrueEvent createEvent(TransactionFrame tFrame, AccrueEvent event) throws Exception {
		// validation(event);
		// orderGifts(event);
		eventDAO.save(tFrame, event); // generator id
		tFrame.returnResource();
		return event;
	}

	private void copyFieldsChanged(AccrueEvent src, AccrueEvent dest) {
		if (!StringUtils.isBlank(src.getName()))
			dest.setName(src.getName());
		if (src.getType() != null)
			dest.setType(src.getType());
		if (src.getStartDate() != null)
			dest.setStartDate(src.getStartDate());
		if (src.getEndDate() != null)
			dest.setEndDate(src.getEndDate());
		if (src.getStatus() != null)
			dest.setStatus(src.getStatus());
		dest.setGiftsLimit(src.isGiftsLimit());
		// copy meta key
		MetaObjectUtils.copy(src, dest);
	}
}
