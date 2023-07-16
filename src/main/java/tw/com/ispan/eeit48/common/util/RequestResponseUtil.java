package tw.com.ispan.eeit48.common.util;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import tw.com.ispan.eeit48.common.dto.response.CommonResponse;
import tw.com.ispan.eeit48.common.dto.response.Exception;
import tw.com.ispan.eeit48.common.dto.response.StatusInfo;

public class RequestResponseUtil {
    public enum ErrorFrom {
        API("A"), // API 调用失败(请求发送失败)的错误, 例如 A100 表示 URL 非法
        HTTP("H"), // HTTP 异常状态的错误, 例如 H404 表示 HTTP 请求404错误
        BACKEND_OR_BUSINESS("B"), // 接口调用失败的错误, 例如 B100 业务A错误, B200 业务B错误
        CLIENT("C"); // 客户端错误, 例如 C100 表示解析 JSON 失败
        private final String errorFromCode;
        ErrorFrom(String errorFrom) {this.errorFromCode = errorFrom; }
        public String getErrorFromCode() {
            return errorFromCode;
        }
    }

    /**
     * Error from which controller, StockController for example will be 'STOCK'
     */
    public enum BusinessType {
        COOPERATE("100"),
        DATA_ANALYZE("200"),
        EC_PAY("300"),
        GOODS("400"),
        LOGIN("500"),
        NEWS("600"),
        ORDER_BUY("700"),
        ORDER_SELL("800"),
        PAGE("900"),
        STATEMENT("1000"),
        STOCK("1100");
        private final String businessTypeCode;
        BusinessType(String businessType) {this.businessTypeCode = businessType; }
        public String getBusinessTypeCode() {
            return businessTypeCode;
        }
    }


    /**
     *  如果需要將資料傳給前端，就提供物件參數，否則由方法自動產生
     *  @Param data - 最外層一定要是物件，如有陣列等其他型態則包入物件屬性裡
     */
    public static CommonResponse createSuccessResponse(Object data) {
        CommonResponse response = new CommonResponse();
        response.setStatus("0");
        response.setData(data == null ? "Request has been processed successfully" : data);
        return response;
    }


    /**
     *  @Param errorFromCode - 哪層出錯
     *  @Param businessTypeCode - 哪個接口调用失败
     *  @Param exceptionStr - exception字串
     */
    public static CommonResponse createErrorResponse(String errorFromCode, String businessTypeCode, String exceptionStr) {
        CommonResponse response = new CommonResponse();
        response.setStatus(errorFromCode + businessTypeCode);

        Exception exception = new Exception();
        exception.setException(exceptionStr);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setMessage("Unknown error occurred, please contact our team with return exception info");
        statusInfo.setDetail(exception);
        response.setStatusInfo(statusInfo);
        return response;
    }

    public static HttpStatus returnHttpStatusByException(java.lang.Exception e) {
        if (e instanceof BadCredentialsException || e instanceof ExpiredJwtException) {
            return HttpStatus.UNAUTHORIZED;
        } else {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
    }
}
