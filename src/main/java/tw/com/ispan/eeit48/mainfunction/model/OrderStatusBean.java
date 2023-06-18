package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_order_status")
public class OrderStatusBean {

	@Id
	private Integer status_id;
	@Column(columnDefinition = "char")
	private String status;



	

	@Override
	public String toString() {
		return "OrderStatusBean [status_id=" + status_id + ", status=" + status + "]";
	}

	public Integer getStatusid() {
		return status_id;
	}

	public void setStatusid(Integer status_id) {
		this.status_id = status_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
