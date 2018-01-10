package cc.blisscorp.event.game.handler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.bliss.framework.common.Config;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xct.nplay.thrift.card.TCardRespone;
import com.xct.nplay.thrift.client.TCardClient;

import cc.blisscorp.event.game.api.rest.JsonUtils;
import cc.blisscorp.event.game.ent.Event;
import cc.blisscorp.event.game.ent.Gifts;
import cc.blisscorp.event.game.ent.Gifts.Status;
import cc.blisscorp.event.game.ent.Gifts.Type;
import cc.blisscorp.event.game.jdbc.TransactionFrame;
import cc.blisscorp.event.game.jdbc.dao.LuckyBoxEventDAOImpl;
import cc.blisscorp.event.game.message.delivery.DeliveryMessage;
import cc.blisscorp.event.game.security.RSASign;
import ga.log4j.GA;
import vn.nct.cardgame.mailbox.ent.Telco;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient;
import vn.nct.cardgame.mailbox.thriftclient.TMailboxClient.MailAvatar;

public class GiftsUpdatingHandler {
	private static GiftsUpdatingHandler instance = null;

	public static GiftsUpdatingHandler getInstance() {
		if (instance == null) {
			synchronized (GiftsUpdatingHandler.class) {
				instance = new GiftsUpdatingHandler();
				GA.app.info("created singleton = " + GiftsUpdatingHandler.class.getCanonicalName());
			}
		}
		return instance;
	}

	private static String EVENT_TYPE = "EVENT1";
	private static final String PAYMENT_TYPE = "card";
	private static final String SUBJECT = "EVENT-GIFTS";

	private String urlPayment = null;
	private TCardClient tCardClient = null;
	private TMailboxClient tMailboxClient = null;
	private LuckyBoxEventDAOImpl eventDAO = null;

	public GiftsUpdatingHandler() {
		tMailboxClient = TMailboxClient.getInstance("thrift_mailbox");
		tCardClient = TCardClient.getInstance("thrift_card");
		urlPayment = Config.getParam("card_payment", "url");
		EVENT_TYPE = Config.getParam("card_payment", "event_type");
		eventDAO = LuckyBoxEventDAOImpl.getInstance();
	}

	public void onGiftsUpdating(TransactionFrame tFrame, Gifts saved, Gifts locked) throws Exception {
		if (!StringUtils.equals(saved.getStatus().name(), Status.PENDING.name()))
			return;
		if (!StringUtils.equals(locked.getStatus().name(), Status.AUTHORIZED.name())
				&& !StringUtils.equals(locked.getStatus().name(), Status.TRANSFER.name()))
			return;
		Event event =  eventDAO.find(tFrame, saved.getEventId(), false);
		if(!(StringUtils.equals(event.getType().name(), Event.Type.lucky_box.name()))) {
			return;
		}
		locked.setValue("-meta_event", event);
		
		boolean result = true;
		if (StringUtils.equals(Type.NCOIN.name(), locked.getType().name())) {
			result = tMailboxClient.sendGift(MailAvatar.GOLD, locked.getAmount(), "Lucky Box Event", "Gift Ncoin",
					"Ncoin", (int) locked.getUserId(), StringUtils.EMPTY);
		} else if (StringUtils.equals(Type.CARD.name(), locked.getType().name())) {
			// Get info card
			TCardRespone cardRespone = new TCardRespone();
			cardRespone = tCardClient.getCardInfo((int) locked.getAmount(), EVENT_TYPE);
			if (cardRespone.getCardCode() == null) {
				locked.setStatus(Status.AWAITING_CARD);
				GA.app.info("tCardReponse empty. gifts: " + locked.toString());
				return;
			}

			// TODO: REVIEW SEND CARD
			if (StringUtils.equals(locked.getStatus().name(), Status.AUTHORIZED.name())) {
				if (cardRespone.cardType.equals("Viettel")) {
					result = tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
							(int) locked.getAmount(), cardRespone.expireDate, Telco.VTT, "Gift Card", "Viettel card",
							"Viettel", (int) locked.getUserId(), null);
				} else if (cardRespone.cardType.equals("Mobiphone")) {
					result = tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
							(int) locked.getAmount(), cardRespone.expireDate, Telco.MOBI, "Gift Card", "Mobiphone card",
							"Mobiphone", (int) locked.getUserId(), null);
				} else if (cardRespone.cardType.equals("Vinaphone")) {
					result = tMailboxClient.sendCard(MailAvatar.CARD, cardRespone.cardCode, cardRespone.cardSerial,
							(int) locked.getAmount(), cardRespone.expireDate, Telco.VINA, "Gift Card", "Vinaphone card",
							"Vinaphone", (int) locked.getUserId(), null);
				}
			} else { // TRANSFER
				try {
					result = sendCardToPayment(cardRespone.cardCode, cardRespone.cardSerial, (int) locked.getAmount(),
							String.valueOf(locked.getUserId()), PAYMENT_TYPE, cardRespone.cardType);
				} catch (Exception e) {
					result = false;
					GA.app.error("can not send card to payment server. " + e.getMessage(), e);
				}
			}
		} else if (StringUtils.equals(Type.SPECIAL.name(), locked.getType().name())) {
			DeliveryMessage.sendNotification(SUBJECT, "GIFTS SPECIAL: " + locked.toString());
		} else
			throw new Exception("type gifts invalid. Type: " + locked.getType().name());

		if (!result)
			throw new Exception("transfer ncoin to game server failed. giftsId: " + locked.getId());
	}

	public boolean sendCardToPayment(String cardCode, String cardSerial, int amount, String userId, String paymentType,
			String telco) throws Exception {
		String url = urlPayment + "?cardcode=%s" + "&cardserial=%s" + "&amount=%s" + "&userid=%s" + "&paymenttype=%s"
				+ "&sign=%s" + "&telco=%s";

		StringBuilder signData = new StringBuilder();
		signData.append("npl@info@pay@#99");
		signData.append(cardCode);
		signData.append(cardSerial);
		signData.append(userId);
		String signature = URLEncoder.encode(RSASign.base64Sign(signData.toString()), "utf-8");

		String urlPost = String.format(url, cardCode, cardSerial, amount, userId, paymentType, signature, telco);

		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(urlPost);

		GA.app.info("\nSending 'GET' request to URL: " + urlPost);

		HttpResponse response = client.execute(request);

		GA.app.info("Response Code: " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		JsonParser jsonParser = new JsonParser();
		JsonObject jsonObject = (JsonObject) jsonParser.parse(result.toString());
		if (JsonUtils.getString(jsonObject, "status").equals("false")) {
			GA.app.error("msg payment: " + JsonUtils.getString(jsonObject, "msg"));
			return false;
		}

		GA.app.info("msg payment: " + result.toString());
		return true;
	}

}
