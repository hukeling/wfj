package promotion.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * PromotionApply - 热销pojo类
 */
@SuppressWarnings("serial")
public class PromotionApply extends BaseEntity implements Serializable {
	/**
	 * 促销活动申请ID
	 */
	private Integer promotionApplyId;
	/**
	 * 会员ID
	 */
	private Integer customerId;
	/**
	 * 会员名称
	 */
	private String customerName;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 促销活动详情
	 */
	private String shopPromotionInfo;
	/**
	 * 活动开始时间
	 */
	private Date beginTime;
	/**
	 * 活动结束时间
	 */
	private Date endTime;
	/**
	 * 申请时间
	 */
	private Date applyTime;
	/**
	 * 是否通过
	 */
	private Integer isPass;
	public Integer getPromotionApplyId() {
		return promotionApplyId;
	}
	public void setPromotionApplyId(Integer promotionApplyId) {
		this.promotionApplyId = promotionApplyId;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Integer getShopInfoId() {
		return shopInfoId;
	}
	public void setShopInfoId(Integer shopInfoId) {
		this.shopInfoId = shopInfoId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopPromotionInfo() {
		return shopPromotionInfo;
	}
	public void setShopPromotionInfo(String shopPromotionInfo) {
		this.shopPromotionInfo = shopPromotionInfo;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
}
