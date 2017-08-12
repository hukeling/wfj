package shop.customer.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * 用户地址实体类
 * @author ss
 *
 */
public class CustomerAcceptAddress extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 4918245696625993831L;
	/**
	 * 用户地址ID
	 */
	private Integer customerAcceptAddressId;
	/**
	 * 用户ID
	 */
	private Integer customerId;
	/**
	 * 收货人姓名
	 */
	private String consignee;
	/**
	 * 最后的名字
	 */
	private String lastName;
	/**
	 * 州省地区
	 */
	private String regionLocation;
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 国家
	 */
	private String areaCounty;
	/**
	 * 详细地址
	 */
	private String address;
	/**
	 * 邮编
	 */
	private String postcode;
	/**
	 * 住宅电话
	 */
	private String phone;
	/**
	 * 手机
	 */
	private String mobilePhone;
	/**
	 * 电子邮件
	 */
	private String email;
	/**
	 * 标志性建筑
	 */
	private String flagContractor;
	/**
	 * 最佳送货时间
	 * 1.只工作日送货(双休日、假日不用送)
	   2.工作日、双休日与假日均可送货
	   3.只双休日、假日送货(工作日不用送)
	 */
	private Integer bestSendDate;
	/**
	 * 是否为确定地址
	 */
	private Integer isSendAddress;
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String country() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCity() {
		return city;
	}
	public String getAreaCounty() {
		return areaCounty;
	}
	public void setAreaCounty(String areaCounty) {
		this.areaCounty = areaCounty;
	}
	public Integer getIsSendAddress() {
		return isSendAddress;
	}
	public void setIsSendAddress(Integer isSendAddress) {
		this.isSendAddress = isSendAddress;
	}
	public String getFlagContractor() {
		return flagContractor;
	}
	public void setFlagContractor(String flagContractor) {
		this.flagContractor = flagContractor;
	}
	public Integer getCustomerAcceptAddressId() {
		return customerAcceptAddressId;
	}
	public void setCustomerAcceptAddressId(Integer customerAcceptAddressId) {
		this.customerAcceptAddressId = customerAcceptAddressId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getBestSendDate() {
		return bestSendDate;
	}
	public void setBestSendDate(Integer bestSendDate) {
		this.bestSendDate = bestSendDate;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRegionLocation() {
		return regionLocation;
	}
	public void setRegionLocation(String regionLocation) {
		this.regionLocation = regionLocation;
	}
}
