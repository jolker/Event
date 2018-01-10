/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.adapter;

import cc.blisscorp.lucky.box.admin.model.Gifts;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author anhlnt
 */
public class GiftsCardAdapter {

    public List<Gifts> listGiftCard(JsonObject jo) {
        List<Gifts> myGiftsCard = new ArrayList<>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("gifts_card");

        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {

                Gifts gc = new Gifts();

                gc.setAmount(arr.get(i).getAsJsonObject().get("amount").getAsString());
                gc.setPercentage(arr.get(i).getAsJsonObject().get("percentage").getAsString());
                if (arr.get(i).getAsJsonObject().has("status")) {
                    gc.setStatus(arr.get(i).getAsJsonObject().get("status").getAsString());
                }
                gc.setQuantity(arr.get(i).getAsJsonObject().get("quanlity").getAsString());

                myGiftsCard.add(gc);

            }
        }

        return myGiftsCard;
    }

    public List<String> listEditCard(List<Gifts> lg) {

        List<String> arrCard = new ArrayList<>();
        arrCard.add("500000");
        arrCard.add("200000");
        arrCard.add("100000");
        arrCard.add("50000");
        arrCard.add("20000");
        arrCard.add("10000");

        for (final Gifts g : lg) {
            if (arrCard.contains(g.getAmount())) {
                arrCard.remove(g.getAmount());
            }
        }

        return arrCard;
    }
    
    public List<Gifts> listGiftAwaiting(JsonObject jo) {
        List<Gifts> myGiftsCard = new ArrayList<>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("items");
        
        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {

                Gifts gc = new Gifts();

                gc.setUserId(arr.get(i).getAsJsonObject().get("user_id").getAsString());
                gc.setEventId(arr.get(i).getAsJsonObject().get("event_id").getAsString());
                gc.setAmount(arr.get(i).getAsJsonObject().get("amount").getAsString());
                gc.setStatus(arr.get(i).getAsJsonObject().get("status").getAsString());
                gc.setCreatedAt(arr.get(i).getAsJsonObject().get("created_at").getAsString());
                gc.setUpdatedAt(arr.get(i).getAsJsonObject().get("updated_at").getAsString());
                
                myGiftsCard.add(gc);

            }
        }

        return myGiftsCard;
    }

}
