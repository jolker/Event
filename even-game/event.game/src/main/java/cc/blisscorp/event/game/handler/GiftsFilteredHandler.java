package cc.blisscorp.event.game.handler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.UserManager;
import ga.log4j.GA;

public class GiftsFilteredHandler {
	private static GiftsFilteredHandler instance = null;
	public static GiftsFilteredHandler getInstance() {
 		if (instance == null) {
			synchronized(GiftsFilteredHandler.class) {
				instance = new GiftsFilteredHandler();
				GA.app.info("created singleton = " + GiftsFilteredHandler.class.getCanonicalName());
			}
		}
		return instance;
	}
	
	private BaseManager<User, UserFilter> userMgr = null;
	
	public GiftsFilteredHandler() {
		userMgr = UserManager.getInstance();	
	}
	
	public void onGiftsFiltered(GiftsFilter filter, PageView<Gifts> pv) throws Exception {
		if (!filter.hasValue("-topGifts"))
			return;
		if (pv.getItems() == null || pv.getItems().size() == 0)
			return;
		
		List<Gifts> giftsList = pv.getItems();
		List<Long> userIds = new ArrayList<Long>();
		for (Gifts gifts:giftsList)
			userIds.add(gifts.getUserId());
		
		UserFilter UFilter = new UserFilter();
		UFilter.setValue("userIds", userIds);
		PageView<User> uPv = userMgr.search(UFilter);
		for(User user : uPv.getItems()) {
			for (Gifts gifts : giftsList) {
				if(gifts.getUserId() == user.getUserId()) {
					gifts.setValue("userName", user.getUserName());
				}
			}
		}
		List<Gifts> specialList = new ArrayList<Gifts>();
		List<Gifts> cardList = new ArrayList<Gifts>();
		List<Gifts> coinList = new ArrayList<Gifts>();
		for (Gifts gifts : giftsList) {
			if(StringUtils.equals(gifts.getType().name(), Type.CARD.name())) {
				cardList.add(gifts);
			} else if (StringUtils.equals(gifts.getType().name(), Type.NCOIN.name())) {
				coinList.add(gifts);
			} else if (StringUtils.equals(gifts.getType().name(), Type.SPECIAL.name())) {
				specialList.add(gifts);
			}
		}
		
		Collections.sort(cardList, new GiftsComparator());
		Collections.sort(coinList, new GiftsComparator());
		
		List<Gifts> temp =  new ArrayList<>();
		if(!specialList.isEmpty()) {
			temp.addAll(specialList);
		}
		temp.addAll(cardList);
		temp.addAll(coinList);
		
		pv.getItems().clear();
		pv.getItems().addAll(temp);
	}
	
	class GiftsComparator implements Comparator<Gifts> {
		@Override
		public int compare(Gifts o1, Gifts o2) {
			Long result = o2.getAmount() - o1.getAmount();
			return result.intValue();
		}
	}
	
}
