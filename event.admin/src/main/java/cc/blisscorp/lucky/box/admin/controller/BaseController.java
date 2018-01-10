/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.utils.Constants;
import com.nct.game.framework.web.view.freemarker.FreeMarker;
import java.io.IOException;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author anhlnt
 */
public abstract class BaseController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    abstract void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException;

    public void showLoginPage(HttpServletRequest req, HttpServletResponse resp, Map<String, Object> data, String msg) throws ServletException, IOException {
        data.put(Constants.PASSING_MESSAGE_FROM_SERVER, msg);
        new FreeMarker(Constants.PAGE_LOGIN, data).render(req, resp);
    }
}