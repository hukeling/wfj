package shop.prosceniumInstall.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * BestsellingAndCoupon - 前台热销和优惠券实体类
 */
public class BestsellingAndCoupon extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 前台热销和优惠券ID
	 */
	private Integer bestsellingAndCouponId;
	/**
	 * 显示ID
	 */
	private Integer showId;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 类型
	 */
	private Integer type;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	public Integer getBestsellingAndCouponId() {
		return bestsellingAndCouponId;
	}
	public void setBestsellingAndCouponId(Integer bestsellingAndCouponId) {
		this.bestsellingAndCouponId = bestsellingAndCouponId;
	}
	public Integer getShowId() {
		return showId;
	}
	public void setShowId(Integer showId) {
		this.showId = showId;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
