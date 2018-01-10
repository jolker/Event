package cc.blisscorp.event.game.dev;

import org.apache.commons.codec.digest.DigestUtils;

import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.GiftsManager;
import ga.log4j.GA;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient.MailAvatar;

public class Test {
	private static BaseManager<Gifts, GiftsFilter> giftMgr = null;
	private static TMailboxClient tMailboxClient = null;

	public Test() {
		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
	}

	public static void main(String[] args) {
		long userId = 100;
		String sign = "ca6228eb320b5fc088af9faa61ff505e";
		if (checkValidUser(userId, sign)) {
			System.out.println("User valid");
		} else {
			System.out.println("User invalid");
		}
		
		giftMgr = GiftsManager.getInstance();
		try {
			GiftsFilter filter = new GiftsFilter();
			filter.setUserId(1);
			PageView<Gifts> pv = giftMgr.search(filter);
			for (Gifts gift : pv.getItems()) {
				sendGiftsToPaymentServer(gift);
			}
		} catch (Exception e) {
			GA.app.error("Dont load database Gifts: " + e.getMessage());
		}
	}

	public static boolean checkValidUser(long userId, String sign) {
		String secrectKey = "900150983cd24fb0d6963f7d28e17f72";
		String validSign = userId + "_" + secrectKey;
		
		if(DigestUtils.md5Hex(validSign).equals(sign)) {
			return true;
		}
		return false;
	}

	public static void sendGiftsToPaymentServer(Gifts gifts) {
		giftMgr = GiftsManager.getInstance();
		try {
			if (gifts.getType().name() == "CARD") {
				try {
					// NAP VAO GAME
					if (gifts.getStatus().name() == "TRANSFERRED") {
						if (tMailboxClient.sendGift(MailAvatar.CARD, gifts.getAmount(), "", "", "",
								(int) gifts.getUserId(), "")) {
							// Update status gifts ---> Opened
							gifts.setStatus(Gifts.Status.OPENED);
							giftMgr.saveOrUpdate(gifts);
						}
					}
					// XAC NHAN GUI VE MAIL CARD DO
					else if (gifts.getStatus().name() == "AUTHORIZED") {
						if (tMailboxClient.sendCard(MailAvatar.CARD, null, null, (int) gifts.getAmount(), null, null,
								null, null, null, (int) gifts.getUserId(), null)) {
							// Update status gifts ---> Opened
							System.out.println("Send card");
							gifts.setStatus(Gifts.Status.OPENED);
							giftMgr.saveOrUpdate(gifts);
						}
					}

				} catch (Exception e) {
					// TODO: handle exception
					GA.app.error("Dont send gifts to server" + e.getMessage());
				}
			} else if (gifts.getType().name() == "NCOIN") {
				try {
					if (tMailboxClient.sendGift(MailAvatar.GOLD, gifts.getAmount(), null, null, null,
							(int) gifts.getUserId(), null)) {
						// Update status gifts ---> Opened
						System.out.println("Send card");
						gifts.setStatus(Gifts.Status.OPENED);
						giftMgr.saveOrUpdate(gifts);
					}
				} catch (Exception e) {
					// TODO: handle exception
					GA.app.error("Dont send gifts to server" + e.getMessage());
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
			GA.app.error("Dont change status Gifts" + e.getMessage());
		}
	}
}
