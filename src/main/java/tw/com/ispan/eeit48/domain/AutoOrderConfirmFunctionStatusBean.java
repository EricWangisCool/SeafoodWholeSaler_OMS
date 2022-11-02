package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "autoorderconfirmfunctionstatus")
public class AutoOrderConfirmFunctionStatusBean {
	@Id
	private Integer statusid;
	@Column(columnDefinition = "char")
	private String status;

	@Override
	public String toString() {
		return "AutoOrderConfirmFunctionStatus [statusId=" + statusid + ", status=" + status + "]";
	}

	public Integer getStatusId() {
		return statusid;
	}

	public void setStatusId(Integer statusId) {
		this.statusid = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("statusid", statusid);
		obj.put("account", status);
		return obj;
	}
}
