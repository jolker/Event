package cc.blisscorp.lucky.box.admin.utils;

import com.nct.framework.common.Config;
import com.nct.framework.util.ConvertUtils;

/**
 *
 * @author anhlnt
 */
public class Constants {
    
    public static final String BLISS_SERVICE_DOMAIN = ConvertUtils.toString(Config.getParam("bliss_service", "domain"),"https://api.nivi.vn");
    
    public static final String PAGE_SIZE = ConvertUtils.toString(Config.getParam("bliss_service", "page_size"));
    public static final String ADMIN = ConvertUtils.toString(Config.getParam("bliss_service", "admin"));
    public static final String PASSWORD = ConvertUtils.toString(Config.getParam("bliss_service", "password"));
    
    public static final String SLACK_URL = Config.getParam("web", "slack_url");
    public static final String ROOT_URL = ConvertUtils.toString(Config.getParam("jetty", "root_url"), "http://console.nivi.vn/");
    public static final String RESOURCE_PATH = Config.getParam("jetty", "resource_path");
    
    public static final String CONTEXT_PATH = ConvertUtils.toString(Config.getParam("jetty", "context_path"), "");
    public static final String CURRENT_DIR = new java.io.File(".").getAbsolutePath() + "/";
    
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "hh:mm:ss";
    public static final String DATETIME_FORMAT_DB = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT_FOLDER = "yyyyMMdd";
    
    public static final String DATETIME_FORMAT_REPORT = "d/M";
    
    public static final String DATETIME_FORMAT_COPYRIGHT = "yyyy-MM-dd HH:mm";
    
    public static final String DATETIME_FORMAT_GAME = "yyyy-MM-dd hh:mm:ss";

    public static final String HOST = Config.getParam("web", "host");
    public static final int PORT = ConvertUtils.toInt(Config.getParam("web", "port"));
    public static final int MAX_THREADS = ConvertUtils.toInt(Config.getParam("web", "max_threads"));
    public static final int MIN_THREADS = ConvertUtils.toInt(Config.getParam("web", "min_threads"), 10);
    /**
     * event Gift
     */
    public static final String PAGE_HOME = "pages/homePage";
    public static final String PAGE_GIFT = "pages/giftPage";
    public static final String PAGE_LOGIN = "pages/loginPage";
    public static final String PAGE_EVENT = "pages/eventPage";
    public static final String PAGE_EVENT_DETAIL = "pages/eventDetailPage";
    public static final String PAGE_EVENT_EDIT = "pages/eventEditPage";
    public static final String PAGE_EVENT_DUPLICATE = "pages/eventDuplicatePage";
    /**
     * event Point
     */
    public static final String PAGE_HOME_POINT = "pages/homePointPage";
    public static final String PAGE_EVENT_POINT = "pages/eventPointPage";
    public static final String PAGE_EVENT_POINT_DETAIL = "pages/eventPointDetailPage";
    public static final String PAGE_EVENT_POINT_DUPLICATE = "pages/eventPointDuplicatePage";
    public static final String CONTENT_TYPE_JSON = "application/json; charset=UTF-8";
    public static final String CONTENT_TYPE_TEXT = "text/html; charset=UTF-8";
    
    public static final String PASSING_ROOT_URL = "root_url";
    
    public static final String PATH_TEMPLATE = Config.getParam("web_view_freemarker", "template_path");
    public static final String URL_LOGIN = "/login";
    
    public static final String ACTION = "action";
    
    public static final String ACTION_ADD = "add";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_DUPLICATE = "duplicate";
    public static final String ACTION_DELETE = "delete";
    public static final String ACTION_VIEW = "view";
    public static final String ACTION_EDIT = "edit";
    public static final String TAB = "tab";
    public static final String DEFAULT_VALUE = "";
    
    public static final String PASSING_MESSAGE_FROM_SERVER = "messageFromServer";
    
    public static final int SHOW = 1, NOT_SHOW = 0;
    public static final String SESSION_USER = "user";
    
    public static final String LOGIN_REQUIRED = "You must login first so you can use this function";
}
