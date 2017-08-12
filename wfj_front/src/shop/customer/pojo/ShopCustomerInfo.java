package shop.customer.pojo;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 会员信息entity
 * @author mqm
 *
 */
public class ShopCustomerInfo extends BaseEntity implements Serializable{
	private static final long serialVersionUID = -6718838800112233445L;
	/**商城会员id**/
	private Integer shopCustomerInfoId;
	/**客户id**/
	private Integer customerId;
	/**客户登记id**/
	private Integer customerCreditId;
	/**真实姓名**/
	private String trueName;
	/**性别**/
	private Integer sex;
	/**出生日期**/
	private Date birthday;
	/**身份证号**/
	private String idCard;
	/**州省地区**/
	private String regionLocation;
	/**城市**/
	private String city;
	/**国家**/
	private String country;
	/**详细地址**/
	private String address;
	/**邮政编码**/
	private String postcode;
	/**联系电话**/
	private String phone;
	/**固定电话**/
	private String telephone;
	/**QQ号**/
	private String qq;
	/**当前账户余额**/
	private BigDecimal accountBalance;
	/**持卡人**/
	private String cardHolder;
	/**银行卡号**/
	private String cardNumber;
	/**最后登录ip**/
	private String loginIp;
	/**最后登录日期**/
	private Date loginDate;
	/**联系登录失败次数**/
	private Integer loginFailureCount;
	/**密码随机码**/
	private String passwordRandomCode;
	/**备注**/
	private String notes;
	public Integer getShopCustomerInfoId() {
		return shopCustomerInfoId;
	}
	public void setShopCustomerInfoId(Integer shopCustomerInfoId) {
		this.shopCustomerInfoId = shopCustomerInfoId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getCustomerCreditId() {
		return customerCreditId;
	}
	public void setCustomerCreditId(Integer customerCreditId) {
		this.customerCreditId = customerCreditId;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPostcode() {
		return postcode;
	}
	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public BigDecimal getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(BigDecimal accountBalance) {
		this.accountBalance = accountBalance;
	}
	public String getCardHolder() {
		return cardHolder;
	}
	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getLoginIp() {
		return loginIp;
	}
	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}
	public Date getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}
	public Integer getLoginFailureCount() {
		return loginFailureCount;
	}
	public void setLoginFailureCount(Integer loginFailureCount) {
		this.loginFailureCount = loginFailureCount;
	}
	public String getPasswordRandomCode() {
		return passwordRandomCode;
	}
	public void setPasswordRandomCode(String passwordRandomCode) {
		this.passwordRandomCode = passwordRandomCode;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
}
