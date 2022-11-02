package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "orderstatus")
public class OrderStatusBean {

	@Id
	private Integer statusid;
	@Column(columnDefinition = "char")
	private String status;

	@Override
	public String toString() {
		return "OrderStatusBean [statusid=" + statusid + ", status=" + status + "]";
	}

	public Integer getStatusid() {
		return statusid;
	}

	public void setStatusid(Integer statusid) {
		this.statusid = statusid;
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
		obj.put("status", status);
		return obj;
	}
}
