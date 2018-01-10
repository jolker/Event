/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.services;

import cc.blisscorp.lucky.box.admin.utils.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 *
 * @author tiennv
 */
public class EventPointServices {

    private static final String API_USER_DETAIL = Constants.BLISS_SERVICE_DOMAIN + "/event-game/accrue-event/get.json?";
    private static final String API_SAVE_EVENT = Constants.BLISS_SERVICE_DOMAIN + "/event-game/accrue-event/save.json";
    private static final String API_SEARCH_EVENT = Constants.BLISS_SERVICE_DOMAIN + "/event-game/accrue-event/search.json";


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
        params.addProperty("type", "accrue");

        HttpRequestService result = new HttpRequestService();
        return result.post(API_SEARCH_EVENT, params.toString());

    }

    public static JsonObject createEvent(String eventName, String startDate,
            String endDate, String type, String status,
            boolean gifts_limit, String reward) throws Exception {
        JsonObject params = new JsonObject();
        params.addProperty("name", eventName);
        params.addProperty("start_date", startDate);
        params.addProperty("type", "accrue");
        params.addProperty("end_date", endDate);
        params.addProperty("status", status);

        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(reward).getAsJsonObject();

        params.add("reward", o);
        params.addProperty("gifts_limit", gifts_limit);
        HttpRequestService result = new HttpRequestService();
        return result.post(API_SAVE_EVENT, params.toString());

    }

    public static JsonObject updateEvent(String id,String eventName, String startDate,
            String endDate, String type, String status,
            boolean gifts_limit, String reward) throws Exception {
        JsonObject params = new JsonObject();
        params.addProperty("id", id);
        params.addProperty("name", eventName);
        params.addProperty("start_date", startDate);
        params.addProperty("type", "accrue");
        params.addProperty("end_date", endDate);
        params.addProperty("status", status);

        JsonParser parser = new JsonParser();
        JsonObject o = parser.parse(reward).getAsJsonObject();

        params.add("reward", o);
        params.addProperty("gifts_limit", gifts_limit);
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

}
