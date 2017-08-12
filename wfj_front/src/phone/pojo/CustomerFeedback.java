package phone.pojo;

import java.io.Serializable;

import util.pojo.BaseEntity;

/**
 * CustomerFeedback 
 */

public class CustomerFeedback extends BaseEntity implements Serializable {

	// Fields

	private Integer fbId;//反馈意见id
	private Integer customerId;//反馈用户id
	private Integer fbtype;//反馈意见类型：1功能意见2页面意见3您的需求4操作意见5流量问题6其他
	private String fbcontent;//反馈内容
	private String customerPhone;//反馈联系电话
	private String customerEmail;//反馈联系邮箱
	private Integer isDeal;//0未处理1处理中2已处理
	// Constructors

	/** default constructor */
	public CustomerFeedback() {
	}

	/** minimal constructor */
	public CustomerFeedback(Integer customerId) {
		this.customerId = customerId;
	}

	/** full constructor */
	public CustomerFeedback(Integer customerId, Integer fbtype,
			String fbcontent, String customerPhone, String customerEmail,
			Integer isDeal) {
		this.customerId = customerId;
		this.fbtype = fbtype;
		this.fbcontent = fbcontent;
		this.customerPhone = customerPhone;
		this.customerEmail = customerEmail;
		this.isDeal = isDeal;
	}
	// Property accessors

	public Integer getFbId() {
		return this.fbId;
	}

	public void setFbId(Integer fbId) {
		this.fbId = fbId;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getFbtype() {
		return this.fbtype;
	}

	public void setFbtype(Integer fbtype) {
		this.fbtype = fbtype;
	}

	public String getFbcontent() {
		return this.fbcontent;
	}

	public void setFbcontent(String fbcontent) {
		this.fbcontent = fbcontent;
	}

	public String getCustomerPhone() {
		return this.customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	public String getCustomerEmail() {
		return this.customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
	public Integer getIsDeal() {
		return this.isDeal;
	}

	public void setIsDeal(Integer isDeal) {
		this.isDeal = isDeal;
	}

}