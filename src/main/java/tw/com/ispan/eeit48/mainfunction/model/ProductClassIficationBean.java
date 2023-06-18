package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "t_product_classification")
public class ProductClassIficationBean {

	@Id
	private Integer class_id;
	@Column(columnDefinition = "char")
	private String class_desc;





	@Override
	public String toString() {
		return "ProductClassIficationBean [class_id=" + class_id + ", class_desc=" + class_desc + "]";
	}

	public Integer getClassid() {
		return class_id;
	}

	public void setClassid(Integer class_id) {
		this.class_id = class_id;
	}

	public String getClassDesc() {
		return class_desc;
	}

	public void setClassDesc(String class_desc) {
		this.class_desc = class_desc;
	}
}
