package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_auto_order_confirm_function_status")
public class AutoOrderConfirmFunctionStatusBean {
	@Id
	private Integer status_id;
	@Column(columnDefinition = "char")
	private String status;

	

	

	@Override
	public String toString() {
		return "AutoOrderConfirmFunctionStatusBean [status_id=" + status_id + ", status=" + status + "]";
	}

	public Integer getStatusId() {
		return status_id;
	}

	public void setStatusId(Integer status_id) {
		this.status_id = status_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
