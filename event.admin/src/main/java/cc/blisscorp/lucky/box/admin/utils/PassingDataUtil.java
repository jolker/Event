package cc.blisscorp.lucky.box.admin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author dientt
 */
public class PassingDataUtil {

    private static Map<String, Object> mapData = new HashMap<>();

    public static Map<String, Object> generalPassing() {
        if (mapData.isEmpty()) {
            Map<String, Object> passingData = new HashMap<>();
            passingData.put(Constants.PASSING_ROOT_URL, Constants.ROOT_URL);
            return passingData;
        } else {
            return mapData;
        }
    }
}
