package tw.com.ispan.eeit48.common.dto.response;

public class StatusInfo {
    String message;
    Exception detail;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Exception getDetail() {
        return detail;
    }

    public void setDetail(Exception detail) {
        this.detail = detail;
    }
}
