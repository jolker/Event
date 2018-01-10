package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.utils.Constants;
import com.nct.game.framework.web.view.freemarker.FreeMarker;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutController extends HttpServlet{
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }
    
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute(Constants.SESSION_USER, null);
        request.getSession().invalidate();
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.PASSING_ROOT_URL, Constants.ROOT_URL);
        new FreeMarker(Constants.PAGE_LOGIN, data).render(request, response);
    }
}
