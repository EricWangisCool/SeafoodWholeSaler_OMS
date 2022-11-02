package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "productclassification")
public class ProductClassIficationBean {

	@Id
	private Integer classid;
	@Column(columnDefinition = "char")
	private String classdesc;

	@Override
	public String toString() {
		return "productclassificationBean[class=" + classid + ", ClassDesc=" + classdesc + "]";
	}

	public Integer getClassid() {
		return classid;
	}

	public void setClassid(Integer classid) {
		this.classid = classid;
	}

	public String getClassDesc() {
		return classdesc;
	}

	public void setClassDesc(String classDesc) {
		this.classdesc = classDesc;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("classid", classid);
		obj.put("classdesc", classdesc);
		return obj;
	}

}
