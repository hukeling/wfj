package shop.store.pojo;
import java.math.BigDecimal;
import java.util.Date;

import util.pojo.BaseEntity;
/**
 * 店铺申请结算明细表
 * 
 * @author 刘钦楠
 * 
 */
public class ShopSettlementDetail extends BaseEntity {
	private static final long serialVersionUID = -4603159700100225674L;
	/**
	 * 店铺申请结算明细Id
	 */
	private Integer settlementId;
	/**
	 * 店铺Id
	 */
	private Integer shopInfoId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 会员Id
	 */
	private Integer customerId;
	/**
	 * 会员登录名
	 */
	private String loginName;
	/**
	 * 企业名称
	 */
	private String companyName;
	/**
	 * 店铺申请结算金额
	 */
	private BigDecimal totalCost;
	/**
	 * 店铺申请结算订单
	 */
	private String ordersIds;
	/**
	 * 申请状态（1待审核，2审核通过，3审核未通过，4已结算）
	 */
	private Integer status;
	/**
	 * 审核人
	 */
	private String reviewer;
	/**
	 * 审核时间
	 */
	private Date reviewerDate;
	/**
	 * 申请时间
	 */
	private Date createDate;
	/**
	 * 店铺佣金比例
	 */
	private BigDecimal commissionProportion;
	/**
	 * 平台给店铺结算后的支付信息
	 */
	private String paymentInfo;
	
	
	public Integer getSettlementId() {
		return settlementId;
	}
	public void setSettlementId(Integer settlementId) {
		this.settlementId = settlementId;
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
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public BigDecimal getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getReviewer() {
		return reviewer;
	}
	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}
	public Date getReviewerDate() {
		return reviewerDate;
	}
	public void setReviewerDate(Date reviewerDate) {
		this.reviewerDate = reviewerDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getOrdersIds() {
		return ordersIds;
	}
	public void setOrdersIds(String ordersIds) {
		this.ordersIds = ordersIds;
	}
	public BigDecimal getCommissionProportion() {
		return commissionProportion;
	}
	public void setCommissionProportion(BigDecimal commissionProportion) {
		this.commissionProportion = commissionProportion;
	}
	public String getPaymentInfo() {
		return paymentInfo;
	}
	public void setPaymentInfo(String paymentInfo) {
		this.paymentInfo = paymentInfo;
	}
}
