/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author huynxt
 */
public class ServiceUtils {

    public static String createSign(JsonObject json, String appKey, String separator) {
        try {
            List<String> strs = new ArrayList<>();
            Set<Map.Entry<String, JsonElement>> sets = json.entrySet();
            for (Map.Entry<String, JsonElement> entry : sets) {
                String key = entry.getKey();
                if (!key.equals("sign")) {
                    strs.add(key);
                }
            }
            Collections.sort(strs);
            String sign = org.apache.commons.lang.StringUtils.EMPTY;
            String keys = "";
            for (String key : strs) {
                JsonElement elem = json.get(key);
                if (elem.isJsonObject()) {
                    sign += json.get(key).toString() + separator;
                } else {
                    sign += json.get(key).getAsString() + separator;
                }
                keys += key + ",";
            }
            return DigestUtils.md5Hex(sign + appKey);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String createParams(JsonObject json) {
        String params = org.apache.commons.lang.StringUtils.EMPTY;
        try {
            List<String> strs = new ArrayList<>();
            Set<Map.Entry<String, JsonElement>> sets = json.entrySet();
            for (Map.Entry<String, JsonElement> entry : sets) {
                String key = entry.getKey();
                String value = entry.getValue().getAsString();
                if (entry.getValue().isJsonObject()) {
                    value = entry.getValue().toString();
                }
                params += key + "=" + value + "&";
            }
            return params;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
