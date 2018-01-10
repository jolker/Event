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
public class GiftsNcoinAdapter {

    public List<Gifts> listGiftNcoin(JsonObject jo) {
        List<Gifts> myGiftsNcoin = new ArrayList<>();
        JsonArray arr = jo.getAsJsonObject("data").getAsJsonArray("gifts_ncoin");

        if (arr != null) {
            for (int i = 0; i < arr.size(); i++) {

                Gifts gnc = new Gifts();

                gnc.setAmount(arr.get(i).getAsJsonObject().get("amount").getAsString());
                gnc.setPercentage(arr.get(i).getAsJsonObject().get("percentage").getAsString());
                if (arr.get(i).getAsJsonObject().has("status")) {
                    gnc.setStatus(arr.get(i).getAsJsonObject().get("status").getAsString());
                }

                myGiftsNcoin.add(gnc);

            }
        }

        return myGiftsNcoin;
    }

    public List<String> listEditNcoin(List<Gifts> lg) {

        List<String> arrNcoin = new ArrayList<>();
        arrNcoin.add("100000000");
        arrNcoin.add("50000000");
        arrNcoin.add("20000000");
        arrNcoin.add("10000000");
        arrNcoin.add("5000000");
        arrNcoin.add("2000000");
        arrNcoin.add("1000000");
        arrNcoin.add("500000");
        arrNcoin.add("200000");
        arrNcoin.add("100000");

        for (final Gifts g : lg) {
            if (arrNcoin.contains(g.getAmount())) {
                arrNcoin.remove(g.getAmount());
            }
        }

        return arrNcoin;
    }

}
