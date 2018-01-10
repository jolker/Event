package cc.blisscorp.event.game.jdbc.manager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.naming.OperationNotSupportedException;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.constant.DataFields;
import cc.blisscorp.event.game.ent.utils.MetaObjectUtils;
import cc.blisscorp.event.game.handler.AccrueGiftsUpdatingHandler;
import cc.blisscorp.event.game.handler.GiftsAssignStatusHandler;
import cc.blisscorp.event.game.handler.GiftsUpdatingHandler;
import cc.blisscorp.event.game.handler.UserGiftsTotalChangeHandler;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.GiftsDAOImpl;
import ga.log4j.GA;

public class GiftsManager implements BaseManager<Gifts, GiftsFilter>{
	private static GiftsManager instance = null;
	public static GiftsManager getInstance() {
		if (instance == null) {
			synchronized(GiftsManager.class) {
				instance = new GiftsManager();
				GA.app.info("created singleton = " + GiftsManager.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<Gifts> giftsDAO = null;

	public GiftsManager() {
		giftsDAO = GiftsDAOImpl.getInstance();
	}

	@Override
	public Gifts saveOrUpdate(Gifts gifts) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame(false);
			if (gifts.getId() == 0)
				return createGifts(tFrame, gifts);

			Gifts locked = giftsDAO.find(tFrame, gifts.getId(), true);
			Gifts saved = locked.getValue(DataFields.META_KEY_SAVED_OBJECT);
			copyFieldsChanged(gifts, locked);

			GiftsUpdatingHandler.getInstance().onGiftsUpdating(tFrame, saved, locked);
			AccrueGiftsUpdatingHandler.getInstance().onGiftsUpdating(tFrame, saved, locked);
			UserGiftsTotalChangeHandler.getInstance().onGiftsUpdating(tFrame, saved, locked);
			GiftsAssignStatusHandler.getInstance().onStatusChange(Status.OPENED, locked);

			giftsDAO.update(tFrame, locked);
			tFrame.returnResource();

			// TODO: fire event gifts updated

			return locked;

		} catch (Exception e) {
			tFrame.returnBrokenResource();
			GA.app.error("update gifts failed (gifts) = "  + gifts.toString() + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public Gifts get(long giftsId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			return giftsDAO.find(tFrame, giftsId, false);

		} catch (Exception ex) {
			GA.app.error("get gifts failed(giftsId) = " + giftsId  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public PageView<Gifts> search(GiftsFilter filter) throws Exception {
		TransactionFrame tFrame = null;
		try {
			ArrayList<String> params = new ArrayList<String>();
			ArrayList<Object> values = new ArrayList<Object>();
			if (filter.getUserId() != 0) {
				params.add("user_id");
				values.add(filter.getUserId());
			}
			if(filter.getStatus() != null) {
				params.add("status_");
				values.add(filter.getStatus().ordinal());
			}
			if(filter.getType() != null) {
				params.add("type_");
				values.add(filter.getType().ordinal());
			}
			if (filter.getValue("types") != null) {
				params.add("type_");
				List<Integer> types = filter.getValue("types");
				values.add(types.toArray());
			}
			if (filter.getEventId() != 0) {
				params.add("event_id");
				values.add(filter.getEventId());
			}
			if (filter.getValue("types") != null) {
				params.add("type_");
				List<String> types = filter.getValue("types");
				values.add(types.toArray());	
			}
			if(filter.getValue("fromCreatedDate") != null) {
				params.add("fromCreatedDate");
				values.add(filter.getDateValue("fromCreatedDate"));
			}
			if(filter.getValue("toCreatedDate") != null) {
				params.add("toCreatedDate");
				values.add(filter.getDateValue("toCreatedDate"));
			}
			
			if (StringUtils.equals("type", filter.getOrderBy()))
				filter.setOrderBy("type_");

			tFrame = new TransactionFrame();
			return giftsDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
					filter.getOrderBy(), filter.isAsc(), (filter.getPageNumber() - 1) * filter.getPageSize(), filter.getPageSize());

		} catch (Exception ex) {
			GA.app.error("search gifts failed(filter) = " + filter.toString()  + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}
	

	@Override
	public void remove(Gifts instance) throws Exception {
		// TODO Auto-generated method stub
		throw new OperationNotSupportedException("not yet impl");
	}

	private Gifts createGifts(TransactionFrame tFrame, Gifts gifts) throws Exception {
		giftsDAO.save(tFrame, gifts); // generator id
		tFrame.returnResource();
		return gifts;
	}

	private void copyFieldsChanged(Gifts src, Gifts dest) {
		if(src.getPercentage() != dest.getPercentage())
			dest.setPercentage(src.getPercentage());

		if (StringUtils.equals(dest.getStatus().name(), Status.PENDING.name()) 
				&& (StringUtils.equals(src.getStatus().name(), Status.AUTHORIZED.name()) 
						|| StringUtils.equals(src.getStatus().name(), Status.TRANSFER.name())))
			dest.setReceivedDate(new Date());

		if(!StringUtils.equals(src.getStatus().name(), dest.getStatus().name()))
			dest.setStatus(src.getStatus());
		if(src.getDescription() != null)
			dest.setDescription(src.getDescription());
		// copy meta key
		MetaObjectUtils.copy(src, dest);
	}
}
