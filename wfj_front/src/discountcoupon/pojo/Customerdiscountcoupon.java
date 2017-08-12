package discountcoupon.pojo;

import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * DiscountCoupon - 用户优惠券类描述信息
 * 
 */
public class Customerdiscountcoupon extends BaseEntity implements Serializable {
	private static final long serialVersionUID = -5825952220785691689L;
	/**
	 * 用户优惠券ID
	 */
	private Integer customerDiscountCouponID;
	/**
	 * 优惠券ID
	 */
	private Integer discountCouponID;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 优惠券码
	 */
	private String discountCouponCode;
	/**
	 * 优惠劵名称
	 */
	private String discountCouponName;
	/**
	 * 优惠金额
	 */
	private Double discountCouponAmount;
	/**
	 * 优惠下限金额
	 */
	private Double discountCouponLowAmount;
	/**
	 * 开始时间
	 */
	private Date beginTime;
	/**
	 * 到期时间
	 */
	private Date expirationTime;
	/**
	 * 使用状态0：未使用 1：已使用,
	 */
	private Integer useStatus;
	/**
	 * '优惠券状态 1：正常 2：过期 3：作废',
	 */
	private Integer status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	/**
	 * 优惠券图片
	 */
	private String discountImage;
	public Integer getCustomerDiscountCouponID() {
		return customerDiscountCouponID;
	}
	public void setCustomerDiscountCouponID(Integer customerDiscountCouponID) {
		this.customerDiscountCouponID = customerDiscountCouponID;
	}
	public Integer getDiscountCouponID() {
		return discountCouponID;
	}
	public void setDiscountCouponID(Integer discountCouponID) {
		this.discountCouponID = discountCouponID;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getDiscountCouponCode() {
		return discountCouponCode;
	}
	public void setDiscountCouponCode(String discountCouponCode) {
		this.discountCouponCode = discountCouponCode;
	}
	public String getDiscountCouponName() {
		return discountCouponName;
	}
	public void setDiscountCouponName(String discountCouponName) {
		this.discountCouponName = discountCouponName;
	}
	public Double getDiscountCouponAmount() {
		return discountCouponAmount;
	}
	public void setDiscountCouponAmount(Double discountCouponAmount) {
		this.discountCouponAmount = discountCouponAmount;
	}
	public Double getDiscountCouponLowAmount() {
		return discountCouponLowAmount;
	}
	public void setDiscountCouponLowAmount(Double discountCouponLowAmount) {
		this.discountCouponLowAmount = discountCouponLowAmount;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDiscountImage() {
		return discountImage;
	}
	public void setDiscountImage(String discountImage) {
		this.discountImage = discountImage;
	}
	
}
