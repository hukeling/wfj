package shop.homeModule.pojo;
import java.io.Serializable;

import util.pojo.BaseEntity;
/**
 * DayRecommendLBT - 前台每日推荐轮播图实体类
 * @author 张攀攀
 * 2014-01-03
 */
public class DayRecommendLBT extends BaseEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 图片轮播ID
	 */
	private Integer broadcastingId;
	/**
	 * 图片
	 */
	private String broadcastingIamgeUrl;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 简介
	 */
	private String synopsis;
	/**
	 * 链接
	 */
	private String interlinkage;
	/**
	 * 排序
	 */
	private Integer sortCode;
	/**
	 * 显示位置
	 */
	private Integer showLocation;
	/**
	 * 是否显示
	 */
	private Integer isShow;
	public Integer getBroadcastingId() {
		return broadcastingId;
	}
	public void setBroadcastingId(Integer broadcastingId) {
		this.broadcastingId = broadcastingId;
	}
	public String getBroadcastingIamgeUrl() {
		return broadcastingIamgeUrl;
	}
	public void setBroadcastingIamgeUrl(String broadcastingIamgeUrl) {
		this.broadcastingIamgeUrl = broadcastingIamgeUrl;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSynopsis() {
		return synopsis;
	}
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	public String getInterlinkage() {
		return interlinkage;
	}
	public void setInterlinkage(String interlinkage) {
		this.interlinkage = interlinkage;
	}
	public Integer getSortCode() {
		return sortCode;
	}
	public void setSortCode(Integer sortCode) {
		this.sortCode = sortCode;
	}
	public Integer getShowLocation() {
		return showLocation;
	}
	public void setShowLocation(Integer showLocation) {
		this.showLocation = showLocation;
	}
	public Integer getIsShow() {
		return isShow;
	}
	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
}
