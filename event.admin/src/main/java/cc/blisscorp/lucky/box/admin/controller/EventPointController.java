/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.adapter.EventPointAdapter;
import cc.blisscorp.lucky.box.admin.services.EventLuckyBoxServices;
import cc.blisscorp.lucky.box.admin.services.EventPointServices;
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
 * @author tiennv
 */
public class EventPointController extends BaseController {

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
        new FreeMarker(Constants.PAGE_EVENT_POINT, data).render(req, resp);
    }

    private void viewPage(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idEvent = ConvertUtils.toInt(req.getParameter(ID_EVENT));

        if (idEvent == 0) {
            data.put("user", null);
            data.put("msg", "User này không tồn tại");
        } else {
            try {
                JsonObject joEvent = EventPointServices.getEventDetail(idEvent);
                if (joEvent.get("status").getAsInt() == 0) {
                    EventPointAdapter event = new EventPointAdapter();
                    data.put("event", event.getEventPoint(joEvent));
                    data.put("type_button", String.valueOf(req.getParameter("type_button")));
                } else {
                    data.put("msg", "Không tồn tại event này");
                }
            } catch (Exception ex) {
                Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        new FreeMarker(Constants.PAGE_EVENT_POINT_DETAIL, data).render(req, resp);
    }

    private void addEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        try (PrintWriter out = resp.getWriter()) {
            JsonObject obj = new JsonObject();
            try {
                String eventName = req.getParameter("name");
                String startDate = req.getParameter("start_date");
                String endDate = req.getParameter("end_date");
                String type = req.getParameter("type");
                String status = req.getParameter("status");
                boolean gifts_limit = Boolean.valueOf(req.getParameter("gifts_limit"));
                String reward = req.getParameter("reward");
                JsonObject result = EventPointServices.createEvent(eventName, startDate,
                        endDate, type, status, gifts_limit, reward);
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

    private void updateEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        try (PrintWriter out = resp.getWriter()) {
            JsonObject obj = new JsonObject();
            try {
                String id = req.getParameter("id");
                String eventName = req.getParameter("name");
                String startDate = req.getParameter("start_date");
                String endDate = req.getParameter("end_date");
                String type = req.getParameter("type");
                String status = req.getParameter("status");
                boolean gifts_limit = Boolean.valueOf(req.getParameter("gifts_limit"));
                String reward = req.getParameter("reward");
                JsonObject result = EventPointServices.updateEvent(id, eventName, startDate,
                        endDate, type, status, gifts_limit, reward);
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

    private void duplicateEvent(Map<String, Object> data, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType(Constants.CONTENT_TYPE_JSON);
        try (PrintWriter out = resp.getWriter()) {
            JsonObject obj = new JsonObject();
            try {
                String eventName = req.getParameter("name");
                String startDate = req.getParameter("start_date");
                String endDate = req.getParameter("end_date");
                String type = req.getParameter("type");
                String status = req.getParameter("status");
                boolean gifts_limit = Boolean.valueOf(req.getParameter("gifts_limit"));
                String reward = req.getParameter("reward");
                JsonObject result = EventPointServices.createEvent(eventName, startDate,
                        endDate, type, status, gifts_limit, reward);
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
