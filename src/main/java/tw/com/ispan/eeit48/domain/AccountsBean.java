package tw.com.ispan.eeit48.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name = "accounts")
public class AccountsBean {
	@Id
	private Integer accountid;
	@Column(columnDefinition = "char")
	private String account;
	@Column(columnDefinition = "char")
	private String passw;
	@Column(columnDefinition = "char")
	private String mobile;
	@Column(columnDefinition = "char")
	private String companyname;
	@Column(columnDefinition = "char")
	private String taxid;
	@Column(columnDefinition = "char")
	private String address;
	@Column(columnDefinition = "char")
	private String emailverifycode;
	@Column(columnDefinition = "char")
	private String email;
	@Column(columnDefinition = "char")
	private String contactperson;
	@Column(columnDefinition = "char")
	private String fax;
	@Column(columnDefinition = "char")
	private String bankaccount;
	@Column(columnDefinition = "char")
	private String bankname;
	private Integer bankswiftcode;
	@Column(columnDefinition = "char")
	private String lineaccount;
	@Column(columnDefinition = "char")
	private String contactpersonnum;

	@Override
	public String toString() {
		return "AccountsBean [accountid=" + accountid + ", account=" + account + ", passw=" + passw + ", mobile="
				+ mobile + ", companyname=" + companyname + ", taxid=" + taxid + ", address=" + address
				+ ", emailverifycode=" + emailverifycode + ", email=" + email + ", contactperson=" + contactperson
				+ ", fax=" + fax + ", bankaccount=" + bankaccount + ", bankname=" + bankname + ", bankswiftcode="
				+ bankswiftcode + ", lineaccount=" + lineaccount + ", contactpersonnum=" + contactpersonnum + "]";
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();
		obj.put("accountid", accountid);
		obj.put("account", account);
		obj.put("passw", passw);
		obj.put("mobile", mobile);
		obj.put("companyname", companyname);
		obj.put("taxid", taxid);
		obj.put("address", address);
		obj.put("emailverifycode", emailverifycode);
		obj.put("email", email);
		obj.put("contactperson", contactperson);
		obj.put("emailverifycode", emailverifycode);
		obj.put("fax", fax);
		obj.put("bankaccount", bankaccount);
		obj.put("bankname", bankname);
		obj.put("lineaccount", lineaccount);
		obj.put("contactpersonnum", contactpersonnum);
		return obj;
	}

	public String getContactpersonnum() {
		return contactpersonnum;
	}

	public void setContactpersonnum(String contactpersonnum) {
		this.contactpersonnum = contactpersonnum;
	}

	public Integer getAccountid() {
		return accountid;
	}

	public void setAccountid(Integer accountid) {
		this.accountid = accountid;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
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

	public String getEmailverifycode() {
		return emailverifycode;
	}

	public void setEmailverifycode(String emailverifycode) {
		this.emailverifycode = emailverifycode;
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
}
