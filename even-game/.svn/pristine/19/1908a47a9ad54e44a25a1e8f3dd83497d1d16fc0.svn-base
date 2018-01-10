package cc.blisscorp.event.game.jdbc.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.LuckyBoxEvent;
import cc.blisscorp.event.game.ent.Event.Status;
import cc.blisscorp.event.game.ent.EventFilter;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.constant.DataFields;
import cc.blisscorp.event.game.ent.utils.MetaObjectUtils;
import cc.blisscorp.event.game.handler.EventFilteringHandler;
import cc.blisscorp.event.game.handler.GiftsStatusChangeHandler;
import cc.blisscorp.event.game.handler.UserScoreChangeHandler;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.BaseDAO;
import cc.blisscorp.event.game.jdbc.dao.LuckyBoxEventDAOImpl;
import ga.log4j.GA;

public class LuckyBoxEventManager implements BaseManager<LuckyBoxEvent, EventFilter> {
	private static LuckyBoxEventManager instance = null;

	public static LuckyBoxEventManager getInstance() {
		if (instance == null) {
			synchronized (LuckyBoxEventManager.class) {
				instance = new LuckyBoxEventManager();
				GA.app.info("created singleton = " + LuckyBoxEventManager.class.getCanonicalName());
			}
		}
		return instance;
	}

	private BaseDAO<LuckyBoxEvent> eventDAO = null;

	public LuckyBoxEventManager() {
		eventDAO = LuckyBoxEventDAOImpl.getInstance();
	}

	@Override
	public LuckyBoxEvent saveOrUpdate(LuckyBoxEvent event) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame(false);
			if (event.getId() == 0)
				return createEvent(tFrame, event);

			LuckyBoxEvent locked = eventDAO.find(tFrame, event.getId(), true);
			LuckyBoxEvent saved = locked.getValue(DataFields.META_KEY_SAVED_OBJECT);
			copyFieldsChanged(event, locked);

			UserScoreChangeHandler.getInstance().onEventUpdating(tFrame, saved, locked);

			GiftsStatusChangeHandler.getInstance().onEventUpdating(tFrame, saved, locked);

			eventDAO.update(tFrame, locked);
			tFrame.returnResource();

			// TODO: fire event category updated

			return locked;
		} catch (Exception e) {
			tFrame.returnBrokenResource();
			GA.app.error("update event failed(event) = " + event.toString() + " - " + e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public LuckyBoxEvent get(long eventId) throws Exception {
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			return eventDAO.find(tFrame, eventId, false);

		} catch (Exception ex) {
			GA.app.error("get event failed(eventId) = " + eventId + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public PageView<LuckyBoxEvent> search(EventFilter filter) throws Exception {
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
			PageView<LuckyBoxEvent> pv = eventDAO.find(tFrame, params.toArray(new String[params.size()]), values.toArray(),
					filter.getOrderBy(), filter.isAsc(), (filter.getPageNumber() - 1) * filter.getPageSize(),
					filter.getPageSize());

			EventFilteringHandler.getInstance().onEventFiltering(tFrame, filter, pv);

			return pv;

		} catch (Exception ex) {
			GA.app.error("search event failed(filter) = " + filter.toString() + " - " + ex.getMessage(), ex);
			throw ex;
		} finally {
			if (tFrame != null)
				tFrame.returnResource();
		}
	}

	@Override
	public void remove(LuckyBoxEvent eventId) throws Exception {
		// TODO Auto-generated method stub
		TransactionFrame tFrame = null;
		try {
			tFrame = new TransactionFrame();
			eventDAO.delete(tFrame, eventId);
		} catch (Exception ex) {
			GA.app.error("delete event failed(eventId) = " + eventId + " - " + ex.getMessage(), ex);
			throw ex;
		}

	}

	private LuckyBoxEvent createEvent(TransactionFrame tFrame, LuckyBoxEvent event) throws Exception {
		validation(event);
		orderGifts(event);
		eventDAO.save(tFrame, event); // generator id
		tFrame.returnResource();
		return event;
	}

	private void validation(LuckyBoxEvent event) throws Exception {
		if(event.getGiftsCard() != null) {
			for (int i = 0; i < event.getGiftsCard().size(); i++) {
				int quanlityTotal = event.getGiftsCard().get(i).getQuanlity();
				if (quanlityTotal <= 0)
					throw new Exception("quanlity invalid ");
			}
		}	
		if (event.getGiftsSpecial() != null) {
			for (int i = 0; i < event.getGiftsSpecial().size(); i++) {
				int quanlityTotal = event.getGiftsSpecial().get(i).getQuanlity();
				if (quanlityTotal <= 0)
					throw new Exception("quanlity invalid ");
			}
			if (event.getGiftsCard() != null && event.getGiftsNcoin() != null) {
				if ((event.getGiftsCardPercent() + event.getGiftsNcoinPercent())
						+ event.getGiftsSpecialPercent() != 100) {
					throw new Exception("percentage total cards and gifts invalid");
				}
				if (checkTotalPercentCard(event) == false) {
					throw new Exception("percentage total cards invalid");
				}
				if (checkTotalPercentNcoin(event) == false) {
					throw new Exception("percentage total ncoids invalid");
				}
			} else if (event.getGiftsCard() != null && event.getGiftsNcoin() == null) {
				if (event.getGiftsCardPercent() + event.getGiftsSpecialPercent() != 100) {
					throw new Exception("percentage total cards invalid");
				}
				if (checkTotalPercentCard(event) == false) {
					throw new Exception("percentage total cards invalid");
				}
			} else if (event.getGiftsNcoin() != null && event.getGiftsCard() == null) {
				if (event.getGiftsNcoinPercent() + event.getGiftsSpecialPercent() != 100) {
					throw new Exception("percentage total ncoids invalid");
				}
				if (checkTotalPercentNcoin(event) == false) {
					throw new Exception("percentage total ncoids invalid");
				}
			}
		} else {
			if (event.getGiftsCard() != null && event.getGiftsNcoin() != null) {
				if ((event.getGiftsCardPercent() + event.getGiftsNcoinPercent()) != 100) {
					throw new Exception("percentage total cards and gifts invalid");
				}
				if (checkTotalPercentCard(event) == false) {
					throw new Exception("percentage total cards invalid");
				}
				if (checkTotalPercentNcoin(event) == false) {
					throw new Exception("percentage total ncoids invalid");
				}
			} else if (event.getGiftsCard() != null && event.getGiftsNcoin() == null) {
				if (event.getGiftsCardPercent() != 100) {
					throw new Exception("percentage total cards invalid");
				}
				if (checkTotalPercentCard(event) == false) {
					throw new Exception("percentage total cards invalid");
				}
			} else if (event.getGiftsNcoin() != null && event.getGiftsCard() == null) {
				if (event.getGiftsNcoinPercent() != 100) {
					throw new Exception("percentage total ncoids invalid");
				}
				if (checkTotalPercentNcoin(event) == false) {
					throw new Exception("percentage total ncoids invalid");
				}
			}
		}
	}

	private boolean checkTotalPercentNcoin(LuckyBoxEvent event) {
		float totalPercentNcoin = 0;
		for (int i = 0; i < event.getGiftsNcoin().size(); i++) {
			totalPercentNcoin += event.getGiftsNcoin().get(i).getPercentage();
		}
		if (totalPercentNcoin != 100) {
			return false;
		}
		return true;
	}

	private boolean checkTotalPercentCard(LuckyBoxEvent event) {
		float totalPercentCard = 0;
		for (int i = 0; i < event.getGiftsCard().size(); i++) {
			totalPercentCard += event.getGiftsCard().get(i).getPercentage();
		}
		if (totalPercentCard != 100) {
			return false;
		}
		return true;
	}

	private void orderGifts(LuckyBoxEvent event) {
		if (event.getGiftsNcoin() != null && event.getGiftsSpecial() == null)
			Collections.sort(event.getGiftsCard(), new GiftsComparator());
		if (event.getGiftsNcoin() != null)
			Collections.sort(event.getGiftsNcoin(), new GiftsComparator());
		if (event.getGiftsNcoin() != null && event.getGiftsCard() == null)
			Collections.sort(event.getGiftsSpecial(), new GiftsComparator());
	}

	private void copyFieldsChanged(LuckyBoxEvent src, LuckyBoxEvent dest) throws Exception {
		if (!StringUtils.isBlank(src.getName()))
			dest.setName(src.getName());
		if (src.getType() != null)
			dest.setType(src.getType());
		if (src.getStartDate() != null)
			dest.setStartDate(src.getStartDate());
		if (src.getEndDate() != null)
			dest.setEndDate(src.getEndDate());
		if(StringUtils.equals(dest.getStatus().name(), Status.PENDING.name()) 
				|| StringUtils.equals(dest.getStatus().name(), Status.ACTIVE.name())) {
			dest.setStatus(src.getStatus());
		} else if (StringUtils.equals(dest.getStatus().name(), Status.DONE.name())) {
			throw new Exception("Sorry! You can't change the status.");
		}
		if (src.getGiftsCard() != null)
			dest.setGiftsCard((src.getGiftsCard()));
		if (src.getGiftsNcoin() != null)
			dest.setGiftsNcoin((src.getGiftsNcoin()));
		if (src.getGiftsSpecial() != null)
			dest.setGiftsSpecial((src.getGiftsSpecial()));
		if (src.getGiftsCardPercent() >= 0)
			dest.setGiftsCardPercent(src.getGiftsCardPercent());
		if (src.getGiftsNcoinPercent() >= 0)
			dest.setGiftsNcoinPercent(src.getGiftsNcoinPercent());
		if (src.getGiftsSpecialPercent() >= 0)
			dest.setGiftsSpecialPercent(src.getGiftsSpecialPercent());
		if (src.getPayMoney() > 0)
			dest.setPayMoney(src.getPayMoney());
		if (src.getMoney2Score() > 0)
			dest.setMoney2Score(src.getMoney2Score());
		if (src.getScore2Gifts() > 0)
			dest.setScore2Gifts(src.getScore2Gifts());
		if (src.getGiftsPerPerson() >= 0)
			dest.setGiftsPerPerson(src.getGiftsPerPerson());
		// copy meta key
		MetaObjectUtils.copy(src, dest);
	}

	class GiftsComparator implements Comparator<Gifts> {
		@Override
		public int compare(Gifts o1, Gifts o2) {
			Float result = o1.getPercentage() - o2.getPercentage();
			return result.intValue();
		}
	}

}
