package discountcoupon.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * DiscountCoupon - 优惠券类描述信息
 */
@SuppressWarnings("serial")
public class DiscountCoupon extends BaseEntity implements Serializable {
	/**
	 * 优惠券ID
	 */
	private Integer discountCouponID;
	/**
	 * 店铺ID
	 */
//	private Integer shopInfoId;
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
	 * 发放个数
	 */
	private Integer distributionCount;
	/**
	 * 剩余个数
	 */
	private Integer surplus;
	/**
	 * 启用状态0：不启用 1：启用',
	 */
	private Integer useStatus;
	/**
	 * 审核状态 1：待审核 2：审核通过 3：审核不通过',
	 */
	private Integer isPass;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 审核人
	 */
	private String verifier;
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
	/**
	 * 优惠券模板
	 */
	private Integer templatetype;
	
	public Integer getDiscountCouponID() {
		return discountCouponID;
	}
	public void setDiscountCouponID(Integer discountCouponID) {
		this.discountCouponID = discountCouponID;
	}
//	public Integer getShopInfoId() {
//		return shopInfoId;
//	}
//	public void setShopInfoId(Integer shopInfoId) {
//		this.shopInfoId = shopInfoId;
//	}
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
	public Date getExpirationTime() {
		return expirationTime;
	}
	public void setExpirationTime(Date expirationTime) {
		this.expirationTime = expirationTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDiscountImage() {
		return discountImage;
	}
	public void setDiscountImage(String discountImage) {
		this.discountImage = discountImage;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public String getDiscountCouponCode() {
		return discountCouponCode;
	}
	public void setDiscountCouponCode(String discountCouponCode) {
		this.discountCouponCode = discountCouponCode;
	}
	public Integer getDistributionCount() {
		return distributionCount;
	}
	public void setDistributionCount(Integer distributionCount) {
		this.distributionCount = distributionCount;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	public Date getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(Date checkTime) {
		this.checkTime = checkTime;
	}
	public String getVerifier() {
		return verifier;
	}
	public void setVerifier(String verifier) {
		this.verifier = verifier;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getSurplus() {
		return surplus;
	}
	public void setSurplus(Integer surplus) {
		this.surplus = surplus;
	}
	public Integer getTemplatetype() {
		return templatetype;
	}
	public void setTemplatetype(Integer templatetype) {
		this.templatetype = templatetype;
	}
}
