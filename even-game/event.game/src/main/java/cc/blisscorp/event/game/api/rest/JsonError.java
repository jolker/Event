package cc.blisscorp.event.game.api.rest;

import javax.servlet.ServletException;

public class JsonError extends ServletException{
private static final long serialVersionUID = 1L;
	
	public JsonError(Throwable cause) {
		super("Just an error wrapper", cause);
	}
}
