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
public class GiftsSpecialAdapter {
    
    public List<Gifts> listGiftSpecial(JsonObject jo) {
        List<Gifts> myGiftsSpecial = new ArrayList<>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("gifts_special");

        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {

                Gifts gc = new Gifts();

                if (arr.get(i).getAsJsonObject().has("status")) {
                    gc.setStatus(arr.get(i).getAsJsonObject().get("status").getAsString());
                }
                gc.setDescription(arr.get(i).getAsJsonObject().get("description").getAsString());
                gc.setQuantity(arr.get(i).getAsJsonObject().get("quanlity").getAsString());

                myGiftsSpecial.add(gc);

            }
        }

        return myGiftsSpecial;
    }
    
}
