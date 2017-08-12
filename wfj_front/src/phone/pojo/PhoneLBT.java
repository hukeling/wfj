package phone.pojo;

import java.io.Serializable;
import java.util.Date;

import util.pojo.BaseEntity;

/**
 * 手机首页轮播图实体
 * 
 * @author xxk
 * @time 20160329
 */
public class PhoneLBT extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 图片轮播ID
	 * 
	 */
	private Integer broadcastingId;
	/**
	 * 图片
	 * 
	 */
	private String broadcastingIamgeUrl;
	/**
	 * 标题
	 * 
	 */
	private String title;
	/**
	 * 简介
	 * 
	 */
	private String synopsis;
	/**
	 * 链接
	 * 
	 */
	private String interlinkage;
	/**
	 * 排序
	 * 
	 */
	private Integer sortCode;
	/**
	 * 显示位置
	 * 
	 */
	private Integer showLocation;
	/**
	 * 是否显示
	 * 
	 */
	private Integer isShow;
	/**
	 * 创建时间
	 * 
	 */
	private Date createTime;
	/**
	 * 创建人
	 * 
	 */
	private String publishUser;
	/**
	 * 修改时间
	 * 
	 */
	private Date updateTime;
	/**
	 * 修改人
	 * 
	 */
	private String modifyUser;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(String publishUser) {
		this.publishUser = publishUser;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
}
