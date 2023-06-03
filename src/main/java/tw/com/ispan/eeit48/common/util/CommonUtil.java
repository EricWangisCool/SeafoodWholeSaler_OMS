package tw.com.ispan.eeit48.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

public class CommonUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {};
    /**
     *  轉換物件成Map型態以利後續擴充屬性使用
     *  @Param object - 要轉換的物件
     *  @Return Map - 該物件的Map型態
     */
    public static Map<String, Object> convertObjectToMap(Object object) {
        return objectMapper.convertValue(object, typeReference);
    }
}
