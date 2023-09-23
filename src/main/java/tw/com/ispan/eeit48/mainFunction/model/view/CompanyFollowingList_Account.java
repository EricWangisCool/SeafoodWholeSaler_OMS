package tw.com.ispan.eeit48.mainFunction.model.view;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import tw.com.ispan.eeit48.mainFunction.model.view.pk.CompanyFollowingList_AccountPK;

/**
 * 由company_following_list為主，將sellerId與account資訊join在一起的view
 */
@Entity
@Immutable
@Table(name = "v_company_following_list_accounts")
@IdClass(CompanyFollowingList_AccountPK.class)
@DynamicUpdate
public class CompanyFollowingList_Account implements Serializable {
	@Id
	private Integer buyerId;
	@Id
	private Integer sellerId;
	@Column(columnDefinition = "char")
	private String companyName;
	@Column(columnDefinition = "char")
	private String taxId;
	@Column(columnDefinition = "char")
	private String companyPhone;
	@Column(columnDefinition = "char")
	private String address;
	@Column(columnDefinition = "char")
	private String email;
	@Column(columnDefinition = "char")
	private String contactPerson;
	@Column(columnDefinition = "char")
	private String contactPersonNum;
	@Column(columnDefinition = "char")
	private String fax;
	@Column(columnDefinition = "char")
	private String bankAccount;
	@Column(columnDefinition = "char")
	private String bankName;
	@Column(columnDefinition = "char")
	private String bankSwiftCode;
	@Column(columnDefinition = "char")
	private String lineAccount;

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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContactPerson() {
		return contactPerson;
	}

	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}

	public String getContactPersonNum() {
		return contactPersonNum;
	}

	public void setContactPersonNum(String contactPersonNum) {
		this.contactPersonNum = contactPersonNum;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankSwiftCode() {
		return bankSwiftCode;
	}

	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}

	public String getLineAccount() {
		return lineAccount;
	}

	public void setLineAccount(String lineAccount) {
		this.lineAccount = lineAccount;
	}
}
