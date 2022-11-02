package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "systemnoticemessage")
public class SystemNoticeMessageBean {

	@Id
	private Integer messageid;
	private Integer receiverid;
	@Column(columnDefinition = "char")
	private String messageread;
	@Column(columnDefinition = "char")
	private String messagecontent;

	@Override
	public String toString() {
		return "SystemNoticeMessageBean [messageid=" + messageid + ", receiverid=" + receiverid + ", messageread="
				+ messageread + ", messagecontent=" + messagecontent + "]";
	}

	public Integer getMessageid() {
		return messageid;
	}

	public void setMessageid(Integer messageid) {
		this.messageid = messageid;
	}

	public Integer getReceiverid() {
		return receiverid;
	}

	public void setReceiverid(Integer receiverid) {
		this.receiverid = receiverid;
	}

	public String getMessageread() {
		return messageread;
	}

	public void setMessageread(String messageread) {
		this.messageread = messageread;
	}

	public String getMessagecontent() {
		return messagecontent;
	}

	public void setMessagecontent(String messagecontent) {
		this.messagecontent = messagecontent;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("messageid", messageid);
		obj.put("receiverid", receiverid);
		obj.put("messageread", messageread);
		obj.put("messagecontent", messagecontent);
		return obj;
	}
}
