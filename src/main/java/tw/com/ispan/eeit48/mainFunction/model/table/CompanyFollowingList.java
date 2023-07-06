package tw.com.ispan.eeit48.mainFunction.model.table;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import tw.com.ispan.eeit48.mainFunction.model.table.pk.CompanyFollowingListPK;

@Entity
@Table(name = " t_company_following_list")
@IdClass(CompanyFollowingListPK.class)
@DynamicUpdate
public class CompanyFollowingList implements Serializable {
	@Id
	private Integer buyerId;
	private Integer sellerId;
	public Integer getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Integer buyerId) {
		this.buyerId = buyerId;
	}
	public Integer getSellerId() {
		return sellerId;
	}
	public void setSellerId(Integer sellerId) {
		this.sellerId = sellerId;
	}

}
