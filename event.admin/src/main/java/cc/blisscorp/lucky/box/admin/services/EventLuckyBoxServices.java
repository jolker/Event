/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.services;

import cc.blisscorp.lucky.box.admin.utils.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author anhlnt
 */
public class EventLuckyBoxServices {

    private static final String API_USER_DETAIL = Constants.BLISS_SERVICE_DOMAIN + "/event-game/event/get.json?";
    private static final String API_SAVE_EVENT = Constants.BLISS_SERVICE_DOMAIN + "/event-game/event/save.json";
    private static final String API_SEARCH_EVENT = Constants.BLISS_SERVICE_DOMAIN + "/event-game/event/search.json";
    private static final String API_SEARCH_GIFT = Constants.BLISS_SERVICE_DOMAIN + "/event-game/gifts/search.json";

    public static JsonObject getEventDetail(int id) throws Exception {

        JsonObject params = new JsonObject();
        params.addProperty("id", id);

        String url = API_USER_DETAIL + ServiceUtils.createParams(params);

        HttpRequestService result = new HttpRequestService();
        return result.get(url);

    }

    public static JsonObject getListEvent(int pageNumber) throws Exception {

        if (pageNumber == 0) {
            pageNumber = 1;
        }

        JsonObject params = new JsonObject();
        params.addProperty("page_number", pageNumber);
        params.addProperty("page_size", Constants.PAGE_SIZE);
        params.addProperty("type", "lucky_box");

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SEARCH_EVENT, params.toString());

    }

    public static JsonObject createEvent(String eventName, String startDate,
            String endDate, String payMoney, String giftsPerPerson,
            String scoreGift, String scoreMoney, String giftsCard, String giftNcoin,
            String status, String giftsCardPercent, String giftsNcoinPercent,
            String giftsSpecial, String giftsSpecialPercent) throws Exception {

        JsonObject params = new JsonObject();
        
        params.addProperty("type", "lucky_box");
        params.addProperty("name", eventName);
        params.addProperty("start_date", startDate);
        params.addProperty("end_date", endDate);
        params.addProperty("status", status);
        params.addProperty("pay_money", payMoney);
        if (!giftsPerPerson.equals("")) {
            params.addProperty("gifts_per_person", giftsPerPerson);
        }
        params.addProperty("score2_gifts", scoreGift);
        params.addProperty("money2_score", scoreMoney);
        params.addProperty("gifts_card_percent", giftsCardPercent);
        params.addProperty("gifts_ncoin_percent", giftsNcoinPercent);
        params.addProperty("gifts_special_percent", giftsSpecialPercent);

        JsonArray jaGiftsCard = new JsonParser().parse(giftsCard).getAsJsonArray();
        JsonArray jaGiftNcoin = new JsonParser().parse(giftNcoin).getAsJsonArray();
        JsonArray jaGiftSpecial = new JsonParser().parse(giftsSpecial).getAsJsonArray();

        params.add("gifts_card", jaGiftsCard);
        params.add("gifts_ncoin", jaGiftNcoin);
        params.add("gifts_special", jaGiftSpecial);
        
        HttpRequestService result = new HttpRequestService();
        return result.post(API_SAVE_EVENT, params.toString());

    }

    public static JsonObject updateEvent(String eventName, String startDate,
            String endDate, String payMoney, String giftsPerPerson,
            String scoreGift, String scoreMoney, String giftsCard,
            String giftNcoin, String id, String status,
            String giftsCardPercent, String giftsNcoinPercent,
            String giftsSpecial, String giftsSpecialPercent) throws Exception {

        JsonObject params = new JsonObject();
        
        params.addProperty("id", id);
        params.addProperty("type", "lucky_box");
        params.addProperty("name", eventName);
        params.addProperty("start_date", startDate);
        params.addProperty("end_date", endDate);
        params.addProperty("status", status);
        params.addProperty("pay_money", payMoney);
        if (!giftsPerPerson.equals("")) {
            params.addProperty("gifts_per_person", giftsPerPerson);
        } else {
            params.addProperty("gifts_per_person", 0);
        }
        params.addProperty("score2_gifts", scoreGift);
        params.addProperty("money2_score", scoreMoney);
        params.addProperty("gifts_card_percent", giftsCardPercent);
        params.addProperty("gifts_ncoin_percent", giftsNcoinPercent);
        params.addProperty("gifts_special_percent", giftsSpecialPercent);

        JsonArray jaGiftsCard = new JsonParser().parse(giftsCard).getAsJsonArray();
        JsonArray jaGiftNcoin = new JsonParser().parse(giftNcoin).getAsJsonArray();
        JsonArray jaGiftSpecial = new JsonParser().parse(giftsSpecial).getAsJsonArray();

        params.add("gifts_card", jaGiftsCard);
        params.add("gifts_ncoin", jaGiftNcoin);
        params.add("gifts_special", jaGiftSpecial);

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SAVE_EVENT, params.toString());

    }

    public static JsonObject deactiveEvent(int id) throws Exception {

        JsonObject params = new JsonObject();
        params.addProperty("id", id);
        params.addProperty("status", "DONE");

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SAVE_EVENT, params.toString());

    }

    public static JsonObject duplicateEvent(JsonObject jo) throws Exception {

        jo.getAsJsonObject("data").remove("id");
        jo.getAsJsonObject("data").remove("created_at");
        jo.getAsJsonObject("data").remove("updated_at");

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SAVE_EVENT, jo.getAsJsonObject("data").toString());

    }

    public static JsonObject getListCardAwaiting(int pageNumber) throws Exception {

        if (pageNumber == 0) {
            pageNumber = 1;
        }

        JsonObject params = new JsonObject();
        params.addProperty("status", "AWAITING_CARD");
        params.addProperty("page_number", pageNumber);
        params.addProperty("page_size", Constants.PAGE_SIZE);

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SEARCH_GIFT, params.toString());

    }

}
