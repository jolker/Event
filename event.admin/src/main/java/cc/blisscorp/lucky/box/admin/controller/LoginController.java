package cc.blisscorp.lucky.box.admin.controller;

import cc.blisscorp.lucky.box.admin.utils.Constants;
import com.nct.game.framework.web.view.freemarker.FreeMarker;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController extends BaseController {

    private static final String MESSAGE_INVALID = "Username or Password invalid!";

    private static final String PASSING_USERNAME = "username";
    private static final String PASSING_PASSWORD = "password";
    private static final String PASSING_ERROR_MESSAGE = "errorMessage";

    private static final String REDIRECT_TO_HOME_PAGE = "home";

    @Override
    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  
        String username = request.getParameter(PASSING_USERNAME);
        String password = request.getParameter(PASSING_PASSWORD);
        Map<String, Object> data = new HashMap<>();
        data.put(Constants.PASSING_ROOT_URL, Constants.ROOT_URL);
        
        if (username == null || password == null) {
            new FreeMarker(Constants.PAGE_LOGIN, data).render(request, response);
        } else {
            if (Constants.ADMIN.equals(username) && Constants.PASSWORD.equals(password)) {
                request.getSession().setAttribute(Constants.SESSION_USER, Constants.ADMIN);
                request.getSession().setMaxInactiveInterval(3000 * 60);
                response.sendRedirect(REDIRECT_TO_HOME_PAGE);
            } else {
                data.put(PASSING_ERROR_MESSAGE, MESSAGE_INVALID);
                new FreeMarker(Constants.PAGE_LOGIN, data).render(request, response);
            }
        }

    }

}
