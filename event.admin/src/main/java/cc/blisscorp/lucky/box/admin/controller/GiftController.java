/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.adapter.GiftsCardAdapter;
import cc.blisscorp.lucky.box.admin.services.EventLuckyBoxServices;
import cc.blisscorp.lucky.box.admin.utils.Constants;
import cc.blisscorp.lucky.box.admin.utils.PassingDataUtil;
import com.google.gson.JsonObject;
import com.nct.framework.util.ConvertUtils;
import com.nct.game.framework.web.view.freemarker.FreeMarker;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anhlnt
 */
public class GiftController extends BaseController {
    
    private static final String PAGE = "page";

    @Override
    void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Map<String, Object> data = new HashMap<>();
        data.putAll(PassingDataUtil.generalPassing());

        String user = (String) req.getSession().getAttribute(Constants.SESSION_USER);
        if (user == null) {
            super.showLoginPage(req, resp, data, Constants.LOGIN_REQUIRED);
            return;
        }

        int page = ConvertUtils.toInt(req.getParameter(PAGE));
        try {
            JsonObject result = EventLuckyBoxServices.getListCardAwaiting(page);
            GiftsCardAdapter gift = new GiftsCardAdapter();
            data.put("total_gift", result.getAsJsonObject("data").get("total_items").getAsString());
            data.put("list_gift", gift.listGiftAwaiting(result));
            new FreeMarker(Constants.PAGE_GIFT, data).render(req, resp);
        } catch (Exception ex) {
            Logger.getLogger(GiftController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
