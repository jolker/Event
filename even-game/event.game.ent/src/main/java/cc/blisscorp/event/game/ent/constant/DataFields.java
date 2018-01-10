package cc.blisscorp.event.game.ent.constant;
/**
 * @author aitd
 */
public interface DataFields {
	/**
	 * params request APIs
	 */
	public static final String JSON_REQUEST_HEADER_CONTENT_TYPE = "application/json";
	public static final String UNSUPPORTED_MEDIA_TYPE = "unsupported media type";
	public static final String REQUEST_PARAMS_INVALID = "request params invalid";
	/**
	 * params response APIs
	 */
	public static final String RESPONSE_FIELD_DATA = "data";
	public static final String RESPONSE_FIELD_MESSAGE = "msg";
	public static final String RESPONSE_FIELD_STATUS = "status";
	public static final byte RESPONSE_FIELD_STATUS_FAILED = 1;
	public static final byte RESPONSE_FIELD_STATUS_SUCCESSFULLY = 0;
	public static final String RESPONSE_DEFAULT_MESSAGE_SUCCESSFULLY = "Thành Công";
	public static final String RESPONSE_SIGN_INVALID = "sign không hợp lệ";
	/**
	 * keys query database 
	 */
	public static final String COLUMN_ID = "id";	
	public static final String KEY_ORDER_ASC = "asc";
	public static final String KEY_ORDER_DESC = "desc";
	public static final String TOTAL_ITEMS_QUERIED = "totalItems";
	public static final String META_KEY_SAVED_OBJECT = "-savedObject";
	public static final String QUERY_COUNT_COLUMN = "count(id) as totalItems";
	
	/**
	 * message notice when execute database function
	 */
	// Khong tim thay
	public static final String NOT_FOUND = "Không tìm thấy: ";
	// Khong the cap nhat xuong database
	public static final String SAVE2DB_FAILED = "Không thể cập nhật xuống database: ";
	// khong tim thay du lieu
	public static final String DB_SEARCH_DATA_FAILED = "Không tìm thấy dữ liệu: "; 
}
