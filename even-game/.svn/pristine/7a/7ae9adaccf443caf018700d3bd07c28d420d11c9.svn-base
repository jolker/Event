package cc.blisscorp.event.game.dev;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xct.nplay.thrift.card.TCardRespone;
import com.xct.nplay.thrift.client.TCardClient;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.GiftsFilter;
import cc.blisscorp.event.game.ent.PageView;
import cc.blisscorp.event.game.ent.User;
import cc.blisscorp.event.game.ent.UserFilter;
import cc.blisscorp.event.game.jdbc.manager.BaseManager;
import cc.blisscorp.event.game.jdbc.manager.GiftsManager;
import cc.blisscorp.event.game.security.RSASign;
import ga.log4j.GA;
import vn.nct.cardgame.mailbox.ent.Telco;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient.MailAvatar;

public class CardTest {
	private static BaseManager<Gifts, GiftsFilter> giftMgr = null;
	private static TMailboxClient tMailboxClient = null;
	private static BaseManager<User, UserFilter> userMgr = null;

	public static void main(String[] args){
		// TODO Auto-generated method stub

//		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
//		TCardRespone cardRespone = new TCardRespone();
//		cardRespone = TCardClient.getInstance("thrift_card").getCardInfo(10000, "EVENT1");
//
//		System.out.println("Card code: " + cardRespone.cardCode);
//		System.out.println("Card serial: " + cardRespone.cardSerial);
//		System.out.println("Card type: " + cardRespone.cardType);
//		System.out.println("Money: " + cardRespone.money);
//		System.out.println("Expire date: " + cardRespone.expireDate);

		// if (tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode,
		// cardRespone.cardSerial, 50000,
		// cardRespone.expireDate, Telco.MOBI, "Card ne", "hehe", "he", 25, "")) {
		// System.out.println("End");
		// }

		// giftMgr = GiftsManager.getInstance();
		// try {
		// GiftsFilter filter = new GiftsFilter();
		// filter.setUserId(1);
		// PageView<Gifts> pv = giftMgr.search(filter);
		// for (Gifts gift : pv.getItems()) {
		//// sendGiftsToPaymentServer(gift);
		//// System.out.println(gift.getType().name());
		//// System.out.println(gift.getStatus());
		// }
		// } catch (Exception e) {
		// GA.app.error("Dont load database Gifts: " + e.getMessage());
		// }
		// System.out.println(Telco.VTT);

		// System.out.println("Get gifts: ");
		// getGifts();
		
		
		try {
			sendGet();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void sendGet() throws Exception {
		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
		TCardRespone cardRespone = new TCardRespone();
		cardRespone = TCardClient.getInstance("thrift_card").getCardInfo(10000, "EVENT1");
		if(cardRespone.cardCode == null)
		{
			GA.app.error("gift card is empty");
			return;
		}
		String url = getURL(cardRespone.cardCode, cardRespone.cardSerial, cardRespone.money, "47", "card", "viettel");

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);


		HttpResponse response = client.execute(request);

		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " +
                       response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
                       new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			
			result.append(line);
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result.toString());
		String status = JsonUtils.getString(jsonObject, "status");
		if(JsonUtils.getString(jsonObject, "status").equals("false")) {
			GA.app.error("msg: " + JsonUtils.getString(jsonObject, "msg"));
			return;
		}
		System.out.println(status);
		System.out.println(result);
	}
	private static String getURL(String cardCode, String cardSerial, int amount, String userId, String paymentType, String telco) {
		try {
			String url = "http://192.168.12.246:8182/payment_by_id"
					+ "?cardcode=%s"
					+ "&cardserial=%s"
					+ "&amount=%s"
					+ "&userid=%s"
					+ "&paymenttype=%s"
					+ "&sign=%s"
					+ "&telco=%s";
			StringBuilder str2Hash = new StringBuilder();
			str2Hash.append("npl@info@pay@#99");
			str2Hash.append(cardCode);
			str2Hash.append(cardSerial);
			str2Hash.append(userId);
			String signature = URLEncoder.encode(RSASign.base64Sign(str2Hash.toString()), "utf-8");

			String urlPost = String.format(url, cardCode, cardSerial, amount, userId, paymentType, signature, telco);
			return urlPost;
			// return new JsonObject(Helper.restFullPostMethod(urlPost, null));
		} catch (Exception ex) {
			GA.app.error("exception", ex);
		}
		return null;
	}

	public static void sendGiftsToPaymentServer(Gifts gifts) {
		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
		giftMgr = GiftsManager.getInstance();
		try {
			if (gifts.getType().name() == "CARD") {
				try {
					// NAP VAO GAME
					if (gifts.getStatus().name() == "TRANSFER") {
						if (tMailboxClient.sendGift(MailAvatar.CARD, gifts.getAmount(), "", "", "",
								(int) gifts.getUserId(), "")) {
							// Update status gifts ---> Opened
							gifts.setStatus(Gifts.Status.OPENED);
							giftMgr.saveOrUpdate(gifts);
						}
					}
					// XAC NHAN GUI VE MAIL CARD DO
					else if (gifts.getStatus().name() == "AUTHORIZED") {
						TCardRespone cardRespone = new TCardRespone();
						cardRespone = TCardClient.getInstance("thrift_card").getCardInfo((int) gifts.getAmount(),
								"EVENT1");
						if (cardRespone.cardType.equals("Viettel")) {
							if (tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
									(int) gifts.getAmount(), cardRespone.expireDate, Telco.VTT, null, null, null,
									(int) gifts.getUserId(), null)) {
								// Update status gifts ---> Opened
								System.out.println("Send card");
								gifts.setStatus(Gifts.Status.OPENED);
								giftMgr.saveOrUpdate(gifts);
							}
						} else if (cardRespone.cardType.equals("Mobiphone")) {
							if (tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
									(int) gifts.getAmount(), cardRespone.expireDate, Telco.MOBI, null, null, null,
									(int) gifts.getUserId(), null)) {
								// Update status gifts ---> Opened
								System.out.println("Send card");
								gifts.setStatus(Gifts.Status.OPENED);
								giftMgr.saveOrUpdate(gifts);
							}
						} else if (cardRespone.cardType.equals("Vinaphone")) {
							if (tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
									(int) gifts.getAmount(), cardRespone.expireDate, Telco.VINA, null, null, null,
									(int) gifts.getUserId(), null)) {
								// Update status gifts ---> Opened
								System.out.println("Send card");
								gifts.setStatus(Gifts.Status.OPENED);
								giftMgr.saveOrUpdate(gifts);
							}
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

	public static void updateUser(String userId) {
		try {

			UserFilter filter = new UserFilter();
			filter.setUserId(Long.valueOf(userId));
			PageView<User> pv = userMgr.search(filter);
			if (pv.getTotalItems() == 0) {
				User user = new User();
				user.setUserId(Long.valueOf(userId));
				userMgr.saveOrUpdate(user);
			}
		} catch (Exception e) {
			// TODO: handle exception
			GA.app.error("Dont check user exists by sign! " + e.getMessage());
		}
	}
}
