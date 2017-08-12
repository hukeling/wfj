package promotion.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/** 
* StoreApplyPromotion - 店铺申请促销活动类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public class StoreApplyPromotion extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 申请促销ID
	 */
	private Integer applyPromotionId;
	/**
	 * 促销活动ID
	 */
	private Integer promotionId;
	/**
	 * 促销活动编号
	 */
	private String promotionNumber;
	/**
	 * 商品实体ID
	 */
	private Integer productId;
	/**
	 * 商品全名称
	 */
	private String productFullName;
	/**
	 * 店铺ID
	 */
	private Integer shopInfoId;
	/**
	 * 店铺名称
	 */
	private String shopName;
	/**
	 * 申请促销状态
	 */
	private Integer promotionState;
	/**
	 * 审核时间
	 */
	private Date checkTime;
	/**
	 * 审核人
	 */
	private String verifier;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	public Integer getApplyPromotionId() {
		return applyPromotionId;
	}
	public void setApplyPromotionId(Integer applyPromotionId) {
		this.applyPromotionId = applyPromotionId;
	}
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public String getProductFullName() {
		return productFullName;
	}
	public void setProductFullName(String productFullName) {
		this.productFullName = productFullName;
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
	public Integer getPromotionState() {
		return promotionState;
	}
	public void setPromotionState(Integer promotionState) {
		this.promotionState = promotionState;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
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
	public String getPromotionNumber() {
		return promotionNumber;
	}
	public void setPromotionNumber(String promotionNumber) {
		this.promotionNumber = promotionNumber;
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
}
