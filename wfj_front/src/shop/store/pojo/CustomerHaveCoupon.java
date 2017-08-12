package shop.store.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * CustomerHaveCoupon - 优惠券客户表类描述信息
 */
@SuppressWarnings("serial")
public class CustomerHaveCoupon extends BaseEntity implements Serializable {
	/**
	 * 主键ID
	 */
	private Integer haveCouponId;
	/**
	 * 优惠券ID
	 */
	private Integer discountCouponID;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
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
	 * 优惠券图片
	 */
	private String discountImage;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 优惠卷状态
	 */
	private Integer state;
	/**
	 * SET GET
	 */
	public Integer getHaveCouponId() {
		return haveCouponId;
	}
	public void setHaveCouponId(Integer haveCouponId) {
		this.haveCouponId = haveCouponId;
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
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
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
	public String getDiscountImage() {
		return discountImage;
	}
	public void setDiscountImage(String discountImage) {
		this.discountImage = discountImage;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
}
