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
	private Integer classId;
	@Column(columnDefinition = "char")
	private String classSesc;
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassSesc() {
		return classSesc;
	}
	public void setClassSesc(String classSesc) {
		this.classSesc = classSesc;
	}

}
