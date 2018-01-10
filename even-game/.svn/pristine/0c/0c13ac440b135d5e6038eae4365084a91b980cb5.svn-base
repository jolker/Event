package cc.blisscorp.event.game.api.rest.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @deprecated
 * @author tuyenpv
 *
 */
public class AuthenticationFilter extends AbstractJsonController implements Filter {

	private static final String SECECT_KEY = "900150983cd24fb0d6963f7d28e17f72";

	private static final String SIGN = "sign";

	private static final String USER_ID = "userId";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String sign = req.getHeader(SIGN);
		String userId = req.getHeader(USER_ID);
		String validSign = userId + "_" + SECECT_KEY;
		if (StringUtils.equals(DigestUtils.md5Hex(validSign), sign)) {
			chain.doFilter(request, response);
		} else {
			Map<String, Object> messErr = new HashMap<String, Object>();
			messErr.put(RESPONSE_FIELD_MESSAGE, RESPONSE_SIGN_INVALID);
			messErr.put(RESPONSE_FIELD_STATUS, RESPONSE_FIELD_STATUS_FAILED);
			String json = new Gson().toJson(messErr);
			super.writeResponse(res, json);
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void registerAdapter(GsonBuilder builder) {
		// TODO Auto-generated method stub

	}
	
}
