package tw.com.ispan.eeit48.websocket.common;

import java.util.Date;

public class NewsBean {
	String titleType;
	String message;
	String From;
	String To;
	Date messageTime;
	public String getTitleType() {
		return titleType;
	}
	public void setTitleType(String titleType) {
		this.titleType = titleType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Date getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Date messageTime) {
		this.messageTime = messageTime;
	}
	
	public String getFrom() {
		return From;
	}
	public void setFrom(String from) {
		From = from;
	}
	public String getTo() {
		return To;
	}
	public void setTo(String to) {
		To = to;
	}
	public NewsBean(String titleType, String message, Date messageTime) {
		super();
		this.titleType = titleType;
		this.message = message;
		this.messageTime = messageTime;
	}
	public NewsBean() {}
	@Override
	public String toString() {
		return "NewsBean [titleType=" + titleType + ", message=" + message + ", messageTime=" + messageTime + "]";
	}
}
