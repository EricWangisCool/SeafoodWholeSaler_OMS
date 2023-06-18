package tw.com.ispan.eeit48.mainfunction.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Immutable;
import org.json.JSONObject;

@Entity
@Immutable
@Table(name = "v_company_following_list_accounts")
@IdClass(View_companyfollowinglist_accountsPK.class)
@DynamicUpdate
public class View_companyfollowinglist_accountsBean implements Serializable {
	@Id
	private Integer buyerId;
	@Id
	private Integer sellerId;
	@Column(columnDefinition = "char")
	private String companyName;
	@Column(columnDefinition = "char")
	private String taxid;
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

	
	@Override
	public String toString() {
		return "View_companyfollowinglist_accountsBean [buyerId=" + buyerId + ", sellerId=" + sellerId
				+ ", companyName=" + companyName + ", taxid=" + taxid + ", companyPhone=" + companyPhone + ", address="
				+ address + ", email=" + email + ", contactPerson=" + contactPerson + ", contactPersonNum="
				+ contactPersonNum + ", fax=" + fax + ", bankAccount=" + bankAccount + ", bankName=" + bankName
				+ ", bankSwiftCode=" + bankSwiftCode + ", lineAccount=" + lineAccount + "]";
	}

	public String getContactpersonnum() {
		return contactPersonNum;
	}

	public void setContactpersonnum(String contactpersonnum) {
		this.contactPersonNum = contactpersonnum;
	}

	public Integer getBuyerid() {
		return buyerId;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerId = buyerid;
	}

	public Integer getSellerid() {
		return sellerId;
	}

	public void setSellerid(Integer sellerid) {
		this.sellerId = sellerid;
	}

	public String getCompanyphone() { return companyPhone; }

	public void setCompanyphone(String companyphone) { this.companyPhone = companyphone; }

	public String getCompanyname() {
		return companyName;
	}

	public void setCompanyname(String companyname) {
		this.companyName = companyname;
	}

	public String getTaxid() {
		return taxid;
	}

	public void setTaxid(String taxid) {
		this.taxid = taxid;
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

	public String getContactperson() {
		return contactPerson;
	}

	public void setContactperson(String contactperson) {
		this.contactPerson = contactperson;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBankaccount() {
		return bankAccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankAccount = bankaccount;
	}

	public String getBankname() {
		return bankName;
	}

	public void setBankname(String bankname) {
		this.bankName = bankname;
	}

	public String getBankswiftcode() {
		return bankSwiftCode;
	}

	public void setBankswiftcode(String bankswiftcode) {
		this.bankSwiftCode = bankswiftcode;
	}

	public String getLineaccount() {
		return lineAccount;
	}

	public void setLineaccount(String lineaccount) {
		this.lineAccount = lineaccount;
	}
}
