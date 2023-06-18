package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.json.JSONObject;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Entity
@Table(name = "t_accounts")
public class AccountsBean implements UserDetails {
	@Id
	private Integer account_id;
	@Column(columnDefinition = "char")
	private String account;
	@Column(columnDefinition = "char")
	private String pass_w;
	@Column(columnDefinition = "char")
	private String company_phone;
	@Column(columnDefinition = "char")
	private String company_name;
	@Column(columnDefinition = "char")
	private String tax_id;
	@Column(columnDefinition = "char")
	private String address;
	@Column(columnDefinition = "char")
	private String email;
	@Column(columnDefinition = "char")
	private String contact_person;
	@Column(columnDefinition = "char")
	private String fax;
	@Column(columnDefinition = "char")
	private String bank_account;
	@Column(columnDefinition = "char")
	private String bank_name;
	@Column(columnDefinition = "char")
	private String bank_swift_code;
	@Column(columnDefinition = "char")
	private String line_account;
	@Column(columnDefinition = "char")
	private String contact_person_num;
	@Column(columnDefinition = "char")
	private String authority;




	@Override
	public String toString() {
		return "AccountsBean [account_id=" + account_id + ", account=" + account + ", pass_w=" + pass_w
				+ ", company_phone=" + company_phone + ", company_name=" + company_name + ", tax_id=" + tax_id
				+ ", address=" + address + ", email=" + email + ", contact_person=" + contact_person + ", fax=" + fax
				+ ", bank_account=" + bank_account + ", bank_name=" + bank_name + ", bank_swift_code=" + bank_swift_code
				+ ", line_account=" + line_account + ", contact_person_num=" + contact_person_num + ", authority="
				+ authority + "]";
	}

	public String getContactpersonnum() {
		return contact_person_num;
	}

	public void setContactpersonnum(String contact_person_num) {
		this.contact_person_num = contact_person_num;
	}

	public Integer getAccountid() {
		return account_id;
	}

	public void setAccountid(Integer account_id) {
		this.account_id = account_id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassw() {
		return pass_w;
	}

	public void setPassw(String pass_w) {
		this.pass_w = pass_w;
	}

	public String getCompanyphone() { return company_phone; }

	public void setCompanyphone(String companyphone) { this.company_phone = company_phone; }

	public String getCompanyname() {
		return company_name;
	}

	public void setCompanyname(String company_name) {
		this.company_name = company_name;
	}

	public String getTaxid() {
		return tax_id;
	}

	public void setTaxid(String taxid) {
		this.tax_id = tax_id;
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
		return contact_person;
	}

	public void setContactperson(String contact_person) {
		this.contact_person = contact_person;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getBankaccount() {
		return bank_account;
	}

	public void setBankaccount(String bank_account) {
		this.bank_account = bank_account;
	}

	public String getBankname() {
		return bank_name;
	}

	public void setBankname(String bank_name) {
		this.bank_name = bank_name;
	}

	public String getBankswiftcode() {
		return bank_swift_code;
	}

	public void setBankswiftcode(String bank_swift_code) {
		this. bank_swift_code =  bank_swift_code;
	}

	public String getLineaccount() {
		return line_account;
	}

	public void setLineaccount(String line_account) {
		this.line_account = line_account;
	}

	public String getAuthority() { return authority; }

	public void setAuthority(String authority) { this.authority = authority; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}

	@JsonIgnore
	@Override
	public String getPassword() { return getPassw(); }

	@JsonIgnore
	@Override
	public String getUsername() { return Integer.toString(getAccountid()); }

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() { return true; }

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() { return true; }

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() { return true; }

	@JsonIgnore
	@Override
	public boolean isEnabled() { return true; }
}
