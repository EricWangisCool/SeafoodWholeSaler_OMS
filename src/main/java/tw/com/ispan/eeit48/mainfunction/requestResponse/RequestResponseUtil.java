package tw.com.ispan.eeit48.mainfunction.requestResponse;

import tw.com.ispan.eeit48.mainfunction.requestResponse.model.response.CommonResponse;
import tw.com.ispan.eeit48.mainfunction.requestResponse.model.response.Exception;
import tw.com.ispan.eeit48.mainfunction.requestResponse.model.response.StatusInfo;

public class RequestResponseUtil {
    public enum ErrorFrom {
        API, // API 调用失败(请求发送失败)的错误, 例如 A100 表示 URL 非法
        HTTP, // HTTP 异常状态的错误, 例如 H404 表示 HTTP 请求404错误
        BACKEND_OR_BUSINESS, // 接口调用失败的错误, 例如 B100 业务A错误, B200 业务B错误
        CLIENT // 客户端错误, 例如 C100 表示解析 JSON 失败
    }

    public enum BusinessType {
        STOCK // StockAPIController
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
     *  如果需要將資料傳給前端，就提供物件參數，否則由方法自動產生
     *  @Param errorFrom - 哪層出錯
     *  @Param businessType - 哪個接口调用失败
     *  @Param exceptionStr - exception字串
     */
    public static CommonResponse createErrorResponse(ErrorFrom errorFrom, BusinessType businessType, String exceptionStr) {
        CommonResponse response = new CommonResponse();
        String status = "";
        switch (errorFrom) {
            case BACKEND_OR_BUSINESS -> status += "B";
            case API -> status += "A";
            case HTTP -> status += "H";
            case CLIENT -> status += "C";
        }
        switch (businessType) {
            case STOCK -> status += "100";
        }
        response.setStatus(status);

        Exception exception = new Exception();
        exception.setException(exceptionStr);

        StatusInfo statusInfo = new StatusInfo();
        statusInfo.setMessage("Unknown error occurred, please contact our team with return exception info");
        statusInfo.setDetail(exception);
        response.setStatusInfo(statusInfo);
        return response;
    }
}
