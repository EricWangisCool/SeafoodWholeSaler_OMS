package tw.com.ispan.eeit48.mainfunction.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.json.JSONObject;

@Entity
@Table(name = " t_company_following_list")
@IdClass(CompanyFollowingListPK.class)
@DynamicUpdate
public class CompanyFollowingListBean implements Serializable {

	@Id
	private Integer buyer_id;
	private Integer seller_id;

	

	

	@Override
	public String toString() {
		return "CompanyFollowingListBean [buyer_id=" + buyer_id + ", seller_id=" + seller_id + "]";
	}

	public Integer getBuyerId() {
		return buyer_id;
	}

	public void setBuyerId(Integer buyer_id) {
		this.buyer_id = buyer_id;
	}

	public Integer getSellerId() {
		return seller_id;
	}

	public void setSellerId(Integer seller_id) {
		this.seller_id = seller_id;
	}

}
