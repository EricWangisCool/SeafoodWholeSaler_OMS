package tw.com.ispan.eeit48.domain;

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
@Table(name = "view_companyfollowinglist_accounts")
@IdClass(View_companyfollowinglist_accountsPK.class)
@DynamicUpdate
public class View_companyfollowinglist_accountsBean implements Serializable {
	@Id
	private Integer buyerid;
	@Id
	private Integer sellerid;
	@Column(columnDefinition = "char")
	private String companyname;
	@Column(columnDefinition = "char")
	private String taxid;
	@Column(columnDefinition = "char")
	private String mobile;
	@Column(columnDefinition = "char")
	private String address;
	@Column(columnDefinition = "char")
	private String email;
	@Column(columnDefinition = "char")
	private String contactperson;
	@Column(columnDefinition = "char")
	private String contactpersonnum;
	@Column(columnDefinition = "char")
	private String fax;
	@Column(columnDefinition = "char")
	private String bankaccount;
	@Column(columnDefinition = "char")
	private String bankname;
	private Integer bankswiftcode;
	@Column(columnDefinition = "char")
	private String lineaccount;

	@Override
	public String toString() {
		return "view_companyfollowinglist_accountsBean [buyerid=" + buyerid + ", sellerid=" + sellerid
				+ ", companyname=" + companyname + ", taxid=" + taxid + ", mobile=" + mobile + ", address=" + address
				+ ", email=" + email + ", contactperson=" + contactperson + ", contactpersonnum=" + contactpersonnum
				+ ", fax=" + fax + ", bankaccount=" + bankaccount + ", bankname=" + bankname + ", bankswiftcode="
				+ bankswiftcode + ", lineaccount=" + lineaccount + "]";
	}

	public String getContactpersonnum() {
		return contactpersonnum;
	}

	public void setContactpersonnum(String contactpersonnum) {
		this.contactpersonnum = contactpersonnum;
	}

	public Integer getBuyerid() {
		return buyerid;
	}

	public void setBuyerid(Integer buyerid) {
		this.buyerid = buyerid;
	}

	public Integer getSellerid() {
		return sellerid;
	}

	public void setSellerid(Integer sellerid) {
		this.sellerid = sellerid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCompanyname() {
		return companyname;
	}

	public void setCompanyname(String companyname) {
		this.companyname = companyname;
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
		return contactperson;
	}

	public void setContactperson(String contactperson) {
		this.contactperson = contactperson;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBankaccount() {
		return bankaccount;
	}

	public void setBankaccount(String bankaccount) {
		this.bankaccount = bankaccount;
	}

	public String getBankname() {
		return bankname;
	}

	public void setBankname(String bankname) {
		this.bankname = bankname;
	}

	public Integer getBankswiftcode() {
		return bankswiftcode;
	}

	public void setBankswiftcode(Integer bankswiftcode) {
		this.bankswiftcode = bankswiftcode;
	}

	public String getLineaccount() {
		return lineaccount;
	}

	public void setLineaccount(String lineaccount) {
		this.lineaccount = lineaccount;
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("buyerid", buyerid);
		obj.put("sellerid", sellerid);
		obj.put("mobile", mobile);
		obj.put("companyname", companyname);
		obj.put("taxid", taxid);
		obj.put("address", address);
		obj.put("email", email);
		obj.put("contactperson", contactperson);
		obj.put("contactpersonnum", contactpersonnum);
		obj.put("fax", fax);
		obj.put("bankaccount", bankaccount);
		obj.put("bankname", bankname);
		obj.put("lineaccount", lineaccount);
		obj.put("bankswiftcode", bankswiftcode);
		return obj;
	}

}
