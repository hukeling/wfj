package promotion.pojo;
import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;
/** 
* PlatformPromotion - 平台管理促销活动类 
* ============================================================================ 
* 版权所有 2010-2013 XXXX软件有限公司，并保留所有权利。 
* 官方网站：http://www.shopjsp.com
* ============================================================================ 
* @author 孟琦瑞
*/ 
public class PlatformPromotion extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 促销活动ID
	 */
	private Integer promotionId;
	/**
	 * 促销活动名称
	 */
	private String promotionName;
	/**
	 * 促销活动编号
	 */
	private String promotionNumber;
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
	 * 大海报URL
	 */
	private String largePoster;
	/**
	 * 小海报URL
	 */
	private String smallPoster;
	/**
	 * 启用状态
	 * 0：未开启，1：已开启，2已结束
	 */
	private Integer useStatus;
	/**
	 * 审核状态
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
	 * 更新时间
	 */
	private Date updateTime;
	public Integer getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(Integer promotionId) {
		this.promotionId = promotionId;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
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
	public String getLargePoster() {
		return largePoster;
	}
	public void setLargePoster(String largePoster) {
		this.largePoster = largePoster;
	}
	public String getSmallPoster() {
		return smallPoster;
	}
	public void setSmallPoster(String smallPoster) {
		this.smallPoster = smallPoster;
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
	public Integer getIsPass() {
		return isPass;
	}
	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}
	public String getPromotionNumber() {
		return promotionNumber;
	}
	public void setPromotionNumber(String promotionNumber) {
		this.promotionNumber = promotionNumber;
	}
	public Integer getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
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
