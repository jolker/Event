package cc.blisscorp.event.game.handler;

import org.apache.commons.lang3.StringUtils;

import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.AccrueEventDAOImpl;
import ga.log4j.GA;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient.MailAvatar;

public class AccrueGiftsUpdatingHandler {
	private static AccrueGiftsUpdatingHandler instance = null;

	public static AccrueGiftsUpdatingHandler getInstance() {
		if (instance == null) {
			synchronized (AccrueGiftsUpdatingHandler.class) {
				instance = new AccrueGiftsUpdatingHandler();
				GA.app.info("created singleton = " + AccrueGiftsUpdatingHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private TMailboxClient tMailboxClient = null;
	private AccrueEventDAOImpl eventDAO = null;
	public AccrueGiftsUpdatingHandler() {
		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
		eventDAO = AccrueEventDAOImpl.getInstance();
	}

	public void onGiftsUpdating(TransactionFrame tFrame, Gifts saved, Gifts locked) throws Exception {
		if (!StringUtils.equals(saved.getStatus().name(), Status.PENDING.name()))
			return;
		if(!(StringUtils.equals(eventDAO.find(tFrame, saved.getEventId(), false).getType().name(), Event.Type.accrue.name()))) {
			return;
		}
		boolean result = true;
		if (StringUtils.equals(Type.NCOIN.name(), locked.getType().name())) {
			result = tMailboxClient.sendGift(MailAvatar.GOLD, locked.getAmount(), "Accrue Event", "Gift Ncoin",
					"Ncoin", (int) locked.getUserId(), StringUtils.EMPTY);
		}

		if (!result)
			throw new Exception("transfer ncoin to game server failed. giftsId: " + locked.getId());
	}
}
