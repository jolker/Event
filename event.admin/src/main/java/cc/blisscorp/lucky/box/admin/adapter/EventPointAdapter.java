/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.adapter;

import cc.blisscorp.lucky.box.admin.model.Event;
import cc.blisscorp.lucky.box.admin.model.EventPoint;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author tiennv
 */
public class EventPointAdapter {

    public EventPoint getEventPoint(JsonObject joEvent) {
        EventPoint event = new EventPoint();

        event.setId(joEvent.getAsJsonObject("data").get("id").getAsString());
        event.setEventName(joEvent.getAsJsonObject("data").get("name").getAsString());
        event.setStartDate(joEvent.getAsJsonObject("data").get("start_date").getAsString());
        event.setEndDate(joEvent.getAsJsonObject("data").get("end_date").getAsString());
        event.setStatus(joEvent.getAsJsonObject("data").get("status").getAsString());
        event.setGiftsLimit(joEvent.getAsJsonObject("data").get("gifts_limit").getAsBoolean());
        Type mapType = new TypeToken<Map<Long, Long>>() {
        }.getType();
        Gson gson = new Gson();
        Map<Long, Long> data = gson.fromJson(joEvent.getAsJsonObject("data").get("reward").toString(), mapType);
        Map<String, String> categoryReward = new HashMap<>();
        for (Map.Entry<Long, Long> entry : data.entrySet()) {
            categoryReward.put(String.format("%,d", entry.getKey()), String.format("%,d", entry.getValue()));

        }

        event.setReward(categoryReward);
        return event;

    }

    public List<EventPoint> listEvent(JsonObject jo) {
        List<EventPoint> myEvent = new ArrayList<>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("items");
        for (int i = 0; i < arr.size(); i++) {

            EventPoint e = new EventPoint();

            e.setEventName(arr.get(i).getAsJsonObject().get("name").getAsString());
            e.setStartDate(arr.get(i).getAsJsonObject().get("start_date").getAsString());
            e.setEndDate(arr.get(i).getAsJsonObject().get("end_date").getAsString());
            e.setStatus(arr.get(i).getAsJsonObject().get("status").getAsString());
            e.setId(arr.get(i).getAsJsonObject().get("id").getAsString());

            myEvent.add(e);

        }

        return myEvent;
    }
}
