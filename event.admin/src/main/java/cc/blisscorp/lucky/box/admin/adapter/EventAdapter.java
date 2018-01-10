/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.adapter;

import cc.blisscorp.lucky.box.admin.model.Event;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anhlnt
 */
public class EventAdapter {

    public Event getEvent(JsonObject joEvent) {

        Event event = new Event();

        event.setId(joEvent.getAsJsonObject("data").get("id").getAsString());
        event.setEventName(joEvent.getAsJsonObject("data").get("name").getAsString());
        event.setStartDate(joEvent.getAsJsonObject("data").get("start_date").getAsString());
        event.setEndDate(joEvent.getAsJsonObject("data").get("end_date").getAsString());
        event.setPayMoney(joEvent.getAsJsonObject("data").get("pay_money").getAsString());
        event.setGiftsPerPerson(joEvent.getAsJsonObject("data").get("gifts_per_person").getAsString());
        event.setScoreGift(joEvent.getAsJsonObject("data").get("score2_gifts").getAsString());
        event.setScoreMoney(joEvent.getAsJsonObject("data").get("money2_score").getAsString());
        event.setStatus(joEvent.getAsJsonObject("data").get("status").getAsString());
        event.setGiftsCardPercent(joEvent.getAsJsonObject("data").get("gifts_card_percent").getAsString());
        event.setGiftsNcoinPercent(joEvent.getAsJsonObject("data").get("gifts_ncoin_percent").getAsString());
        event.setGiftsSpecialPercent(joEvent.getAsJsonObject("data").get("gifts_special_percent").getAsString());
        
        return event;

    }

    public List<Event> listEvent(JsonObject jo) {
        List<Event> myEvent = new ArrayList<Event>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("items");
        for (int i = 0; i < arr.size(); i++) {

            Event e = new Event();

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
