/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.adapter.EventAdapter;
import cc.blisscorp.lucky.box.admin.adapter.GiftsCardAdapter;
import cc.blisscorp.lucky.box.admin.adapter.GiftsNcoinAdapter;
import cc.blisscorp.lucky.box.admin.adapter.GiftsSpecialAdapter;
import cc.blisscorp.lucky.box.admin.services.EventLuckyBoxServices;
import cc.blisscorp.lucky.box.admin.utils.Constants;
import cc.blisscorp.lucky.box.admin.utils.PassingDataUtil;
import com.google.gson.JsonObject;
import com.nct.framework.util.ConvertUtils;
import com.nct.game.framework.web.view.freemarker.FreeMarker;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author anhlnt
 */
public class EventController extends BaseController {

    private static final String ID_EVENT = "id";
    private static final String PASSING_RESULT = "result";

    @Override
    void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {

            Map<String, Object> data = new HashMap<>();
            data.putAll(PassingDataUtil.generalPassing());

            String admin = (String) req.getSession().getAttribute(Constants.SESSION_USER);
            if (admin == null) {
                super.showLoginPage(req, resp, data, Constants.LOGIN_REQUIRED);
                return;
            }

            String action = req.getParameter(Constants.ACTION);
            switch (StringUtils.isBlank(action) ? Constants.DEFAULT_VALUE : action) {
                case Constants.DEFAULT_VALUE:
                    initPage(data, req, resp);
                    break;
                case Constants.ACTION_VIEW:
                    viewPage(data, req, resp);
                    break;
                case Constants.ACTION_ADD:
                    addEvent(data, req, resp);
                    break;
                case Constants.ACTION_EDIT:
                    goEditPage(data, req, resp);
                    break;
                case Constants.ACTION_UPDATE:
                    updateEvent(data, req, resp);
                    break;
                case Constants.ACTION_DUPLICATE:
                    duplicateEvent(data, req, resp);
                    break;
                case Constants.ACTION_DELETE:
                    deleteEvent(data, req, resp);
                    break;
            }

        } catch (Exception ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initPage(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        new FreeMarker(Constants.PAGE_EVENT, data).render(req, resp);
    }

    private void viewPage(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idEvent = ConvertUtils.toInt(req.getParameter(ID_EVENT));

        if (idEvent == 0) {
            data.put("user", null);
            data.put("msg", "User này không tồn tại");
        } else {
            try {
                JsonObject joEvent = EventLuckyBoxServices.getEventDetail(idEvent);
                if (joEvent.get("status").getAsInt() == 0) {
                    EventAdapter event = new EventAdapter();
                    GiftsCardAdapter gc = new GiftsCardAdapter();
                    GiftsNcoinAdapter gnc = new GiftsNcoinAdapter();
                    GiftsSpecialAdapter gs = new GiftsSpecialAdapter();

                    data.put("event", event.getEvent(joEvent));
                    data.put("list_giftcard", gc.listGiftCard(joEvent));
                    data.put("list_giftncoin", gnc.listGiftNcoin(joEvent));
                    data.put("list_giftspecial", gs.listGiftSpecial(joEvent));
                } else {
                    data.put("msg", "Không tồn tại event này");
                }
            } catch (Exception ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        new FreeMarker(Constants.PAGE_EVENT_DETAIL, data).render(req, resp);
    }

    private void addEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        try (PrintWriter out = resp.getWriter()) {
            JsonObject obj = new JsonObject();
            try {
                String eventName = req.getParameter("name");
                String startDate = req.getParameter("start_time");
                String endDate = req.getParameter("end_time");
                String status = req.getParameter("status");
                String payMoney = req.getParameter("money");
                String giftsPerPerson = req.getParameter("max_giftbox");
                String scoreGift = req.getParameter("point_giftbox");
                String scoreMoney = req.getParameter("point_money");
                String giftsCardPercent = req.getParameter("gifts_card_percent");
                String giftsNcoinPercent = req.getParameter("gifts_ncoin_percent");
                String giftsSpecialPercent = req.getParameter("gifts_special_percent");
                String giftsCard = req.getParameter("gifts_card");
                String giftsNcoin = req.getParameter("gifts_ncoin");
                String giftsSpecial = req.getParameter("gifts_special");

                JsonObject result = EventLuckyBoxServices.createEvent(eventName, startDate,
                        endDate, payMoney, giftsPerPerson, scoreGift, scoreMoney, giftsCard, 
                        giftsNcoin, status, giftsCardPercent, giftsNcoinPercent, giftsSpecial,
                        giftsSpecialPercent);
                
                if (result.get("msg").getAsString().equals("Thành Công")) {
                    obj.addProperty(PASSING_RESULT, true);
                }
            } catch (Exception ex) {
                obj.addProperty(PASSING_RESULT, ex.getMessage());
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.print(obj);
            out.flush();
        }
    }

    private void goEditPage(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int idEvent = ConvertUtils.toInt(req.getParameter(ID_EVENT));

        if (idEvent == 0) {
            data.put("user", null);
            data.put("msg", "User này không tồn tại");
        } else {
            try {
                JsonObject joEvent = EventLuckyBoxServices.getEventDetail(idEvent);
                if (joEvent.get("status").getAsInt() == 0) {
                    EventAdapter event = new EventAdapter();
                    GiftsCardAdapter gc = new GiftsCardAdapter();
                    GiftsNcoinAdapter gnc = new GiftsNcoinAdapter();
                    GiftsSpecialAdapter gs = new GiftsSpecialAdapter();

                    data.put("event", event.getEvent(joEvent));
                    data.put("list_giftcard", gc.listGiftCard(joEvent));
                    data.put("list_editcard", gc.listEditCard(gc.listGiftCard(joEvent)));
                    data.put("list_giftncoin", gnc.listGiftNcoin(joEvent));
                    data.put("list_editncoin", gnc.listEditNcoin(gnc.listGiftNcoin(joEvent)));
                    data.put("list_giftspecial", gs.listGiftSpecial(joEvent));
                } else {
                    data.put("msg", "Không tồn tại event này");
                }
            } catch (Exception ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        new FreeMarker(Constants.PAGE_EVENT_EDIT, data).render(req, resp);

    }

    private void updateEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        try (PrintWriter out = resp.getWriter()) {
            JsonObject obj = new JsonObject();
            try {
                String id = req.getParameter("id");
                String eventName = req.getParameter("name");
                String startDate = req.getParameter("start_time");
                String endDate = req.getParameter("end_time");
                String status = req.getParameter("status");
                String payMoney = req.getParameter("money");
                String giftsPerPerson = req.getParameter("max_giftbox");
                String scoreGift = req.getParameter("point_giftbox");
                String scoreMoney = req.getParameter("point_money");
                String giftsCardPercent = req.getParameter("gifts_card_percent");
                String giftsNcoinPercent = req.getParameter("gifts_ncoin_percent");
                String giftsSpecialPercent = req.getParameter("gifts_special_percent");
                String giftsCard = req.getParameter("gifts_card");
                String giftsNcoin = req.getParameter("gifts_ncoin");
                String giftsSpecial = req.getParameter("gifts_special");

                JsonObject result = EventLuckyBoxServices.updateEvent(eventName, startDate,
                        endDate, payMoney, giftsPerPerson, scoreGift,
                        scoreMoney, giftsCard, giftsNcoin, id, status,
                        giftsCardPercent, giftsNcoinPercent, giftsSpecial, giftsSpecialPercent);
                if (result.get("msg").getAsString().equals("Thành Công")) {
                    obj.addProperty(PASSING_RESULT, true);
                } else if (result.get("status").getAsInt()== 1) {
                    obj.addProperty(PASSING_RESULT, result.get("msg").getAsString());
                }
            } catch (Exception ex) {
                obj.addProperty(PASSING_RESULT, ex.getMessage());
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
            out.print(obj);
            out.flush();
        }
    }

    private void duplicateEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idEvent = ConvertUtils.toInt(req.getParameter(ID_EVENT));

        if (idEvent == 0) {
            data.put("user", null);
            data.put("msg", "User này không tồn tại");
        } else {
            try {
                JsonObject joEvent = EventLuckyBoxServices.getEventDetail(idEvent);
                if (joEvent.get("status").getAsInt() == 0) {
                    EventAdapter event = new EventAdapter();
                    GiftsCardAdapter gc = new GiftsCardAdapter();
                    GiftsNcoinAdapter gnc = new GiftsNcoinAdapter();
                    GiftsSpecialAdapter gs = new GiftsSpecialAdapter();

                    data.put("event", event.getEvent(joEvent));
                    data.put("list_giftcard", gc.listGiftCard(joEvent));
                    data.put("list_editcard", gc.listEditCard(gc.listGiftCard(joEvent)));
                    data.put("list_giftncoin", gnc.listGiftNcoin(joEvent));
                    data.put("list_editncoin", gnc.listEditNcoin(gnc.listGiftNcoin(joEvent)));
                    data.put("list_giftspecial", gs.listGiftSpecial(joEvent));
                } else {
                    data.put("msg", "Không tồn tại event này");
                }
            } catch (Exception ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        new FreeMarker(Constants.PAGE_EVENT_DUPLICATE, data).render(req, resp);
    }

    private void deleteEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int idEvent = ConvertUtils.toInt(req.getParameter(ID_EVENT));

        if (idEvent == 0) {
            data.put("user", null);
            data.put("msg", "User này không tồn tại");
        } else {
            try {
                JsonObject joEvent = EventLuckyBoxServices.deactiveEvent(idEvent);
                if (joEvent.get("status").getAsInt() == 0) {
                    resp.sendRedirect(Constants.ROOT_URL + "home");
                }
            } catch (Exception ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
