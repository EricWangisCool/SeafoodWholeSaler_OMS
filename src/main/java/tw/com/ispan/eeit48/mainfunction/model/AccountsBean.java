package tw.com.ispan.eeit48.mainfunction.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;

@Entity
@Table(name = "t_accounts")
public class AccountsBean implements UserDetails {
	@Id
	private Integer accountId;
	@Column(columnDefinition = "char")
	private String account;
	@Column(columnDefinition = "char")
	private String passW;
	@Column(columnDefinition = "char")
	private String companyPhone;
	@Column(columnDefinition = "char")
	private String companyName;
	@Column(columnDefinition = "char")
	private String taxId;
	@Column(columnDefinition = "char")
	private String address;
	@Column(columnDefinition = "char")
	private String email;
	@Column(columnDefinition = "char")
	private String contactPerson;
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
	@Column(columnDefinition = "char")
	private String contactPersonNum;
	@Column(columnDefinition = "char")
	private String authority;



	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassW() {
		return passW;
	}

	public void setPassW(String passW) {
		this.passW = passW;
	}

	public String getCompanyPhone() {
		return companyPhone;
	}

	public void setCompanyPhone(String companyPhone) {
		this.companyPhone = companyPhone;
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

	public String getContactPersonNum() {
		return contactPersonNum;
	}

	public void setContactPersonNum(String contactPersonNum) {
		this.contactPersonNum = contactPersonNum;
	}

	public String getAuthority() { return authority; }

	public void setAuthority(String authority) { this.authority = authority; }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authority);
	}

	@JsonIgnore
	@Override
	public String getPassword() { return getPassW(); }

	@JsonIgnore
	@Override
	public String getUsername() { return Integer.toString(getAccountId()); }

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
