package tw.com.ispan.eeit48.mainFunction.model.table;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "t_auto_order_confirm_function_status")
public class AutoOrderConfirmFunctionStatus {
	@Id
	private Integer statusId;
	@Column(columnDefinition = "char")
	private String status;

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
}
